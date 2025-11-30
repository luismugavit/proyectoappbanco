package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import domain.Cliente;
import domain.Cuenta;
import domain.Prestamo;

public class GestorBD {
	private static final String FILE = "resources/db/banco.db";
	private static final String CONNECTION_STRING = "jbdc:sqlite:" + FILE;
	
	public GestorBD() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("Error al cargar el driver de la BD");
		}
	}
	
	public ArrayList<Cliente> loadClientes() {
		ArrayList<Cliente> clientes  = new ArrayList<>();
		ArrayList<Cuenta> cuentas = loadCuentas();
		ArrayList<Prestamo> prestamos = loadPrestamos();
		
		try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
		PreparedStatement pstCliente = conn.prepareStatement("SELECT * FROM CLIENTES")) {
		ResultSet rsCliente = pstCliente.executeQuery();
			
		while (rsCliente.next()) {
			int id = rsCliente.getInt("ID");
			String nombre = rsCliente.getString("Nombre");
			String apellido1 = rsCliente.getString("Apellido1");
			String apellido2 = rsCliente.getString("Apellido2");
			String dni = rsCliente.getString("DNI");
			ArrayList<Cuenta> listacuentas = new ArrayList<Cuenta>();
			for (Cuenta c : cuentas) {
                if (c.getPropietario().getId() == id) {
                    listacuentas.add(c);
                }
            }

			ArrayList<Prestamo> listaPrestamos = new ArrayList<>();          
			for (Prestamo p : prestamos) {
				if (p.getCliente().getId() == id) {
			        listaPrestamos.add(p);
				}
			}
			Cliente cliente = new Cliente(id, nombre, apellido1, apellido2, dni, listacuentas, prestamos);
			clientes.add(cliente);
		}
		rsCliente.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return clientes;
	}

	private ArrayList<Prestamo> loadPrestamos() {
		ArrayList<Prestamo> prestamos  = new ArrayList<Prestamo>();
		ArrayList<Cliente> clientes  = loadClientes();
		try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pstPrestamo = conn.prepareStatement("SELECT * FROM PRESTAMOS")) {
				ResultSet rsPrestamo = pstPrestamo.executeQuery();
				
				while (rsPrestamo.next()) {
					int id = rsPrestamo.getInt("ID");
					double cantidadSolicitada = rsPrestamo.getDouble("CANTIDAD_SOLICITADA");
					double cantidadPendiente = rsPrestamo.getDouble("CANTIDAD_PENDIENTE");
					double interesAnual = rsPrestamo.getDouble("INTERES_ANUAL");
					int plazoMeses = rsPrestamo.getInt("PLAZO_MESES");
					double cuotaMensual = rsPrestamo.getDouble("CUOTA_MENSUAL");
					LocalDate fechaInicio = LocalDate.of(0000, 00, 00); //Falta por implementar
					LocalDate fechaFin = LocalDate.of(0000, 00, 00);	//Falta por implementar
					Cliente cliente = new Cliente(0, null, null, null, null, null, null); //Falta por implementar
					Prestamo prestamo = new Prestamo(cliente, cantidadSolicitada, interesAnual, plazoMeses);
					prestamos.add(prestamo);
				}
				}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return prestamos;
	}

	private ArrayList<Cuenta> loadCuentas() {
		ArrayList<Cuenta> cuentas = new ArrayList<>();
		ArrayList<Cliente> clientes  = loadClientes();
		Cliente cliente = new Cliente(0, null, null, null, null, null, null);
		try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pstCuenta = conn.prepareStatement("SELECT * FROM CUENTAS")) {
				ResultSet rsCuenta = pstCuenta.executeQuery();
					
				while (rsCuenta.next()) {
					String numeroCuenta= rsCuenta.getString("Numero");
					float saldo = rsCuenta.getFloat("Saldo");
					int idCliente = rsCuenta.getInt("ID_CLIENTE");
					for (Cliente c : clientes) {
						if (c.getId() == idCliente ) {
							cliente = c;
							break;
						}
					}
					Cuenta cuenta = new Cuenta(numeroCuenta, saldo, cliente);
					cuentas.add(cuenta);
					
				}
				rsCuenta.close();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
				return cuentas;
	}
	
	@SuppressWarnings("unused")
	private boolean UpdateCliente(Cliente cliente) {
		boolean updated = false;
		String sqlInsert = "INSERT INTO CLIENTE (DNI, NOMBRE) VALUES (?, ?)";
		
		try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
			PreparedStatement pstUpdate = conn.prepareStatement("UPDATE CLIENTES SET NOMBRE = ?, APELLIDO1 = ?, APELLIDO2 = ?, DNI = ?, WHERE ID = ?");
			PreparedStatement pstDeleteCuentas = conn.prepareStatement( "DELETE FROM CLIENTES_CUENTAS WHERE ID_CLIENTE = ?" );
			PreparedStatement pstDeletePrestamos = conn.prepareStatement("DELETE FROM CLIENTES_PRESTAMOS WHERE ID_CLIENTE = ?");
			PreparedStatement pstInsertCuentas = conn.prepareStatement("INSERT INTO CLIENTE_CUENTA (ID_CLIENTE, ID_CUENTA) VALUES (?, ?)");
			PreparedStatement pstInsertPrestamos = conn.prepareStatement("INSERT INTO CLIENTE_PRESTAMO (ID_CLIENTE, ID_PRESTAMO) VALUES (?, ?)")){

			pstUpdate.setString(1, cliente.getNombre());
			pstUpdate.setString(2, cliente.getApellido1());
			pstUpdate.setString(3, cliente.getApellido2());
			pstUpdate.setString(4, cliente.getDni());
			pstUpdate.setInt(5, cliente.getId());
			
			if (pstUpdate.executeUpdate() == 1) {
				pstDeleteCuentas.setInt(1,  cliente.getId());
				if (pstDeleteCuentas.executeUpdate()>= 0) {
					for (Cuenta cuenta : cliente.getListaCuentas()) {
	                    pstInsertCuentas.setInt(1, cliente.getId());
	                    pstInsertCuentas.setString(2, cuenta.getNumeroCuenta());
					
					if (pstInsertCuentas.executeUpdate() != 1) {
						return false;
						
					}
				}
				}
				else { 
					return false;
					
				}
				if (pstDeletePrestamos.executeUpdate() >= 0) {
					for (Prestamo prestamo : cliente.getPrestamos()) {
	                    pstInsertPrestamos.setInt(1, cliente.getId());
	                    pstInsertPrestamos.setInt(2, prestamo.getId());
	                    if (pstInsertCuentas.executeUpdate() != 1) {
							return false;
							
						}
					}
				}
					else { 
						return false;
						
					}
				}
		
				updated = true;
			
			
		} catch (Exception e) {
			System.err.format("Error actualizando el cliente '%s'", cliente.getNombre());
			e.printStackTrace();
			return false;
		}
		return updated;
	}
	// Falta el update cuentas
}
