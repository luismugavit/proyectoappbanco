package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import domain.Cliente;
import domain.Cuenta;
import domain.Prestamo;

public class GestorBD {
	private static final String FILE = "resources/db/banco.db";
	private static final String CONNECTION_STRING = "jdbc:sqlite:" + FILE;
	
	public GestorBD() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("Error al cargar el driver de la BD" + e.getMessage());
		}
	}
	
	public List<Cliente> loadClientes() {
		List<Cliente> clientes  = new ArrayList<>();
		List<Cuenta> cuentas = loadCuentas();
		
		try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
		PreparedStatement pstCliente = conn.prepareStatement("SELECT * FROM CLIENTE")) {
		ResultSet rsCliente = pstCliente.executeQuery();
			
		while (rsCliente.next()) {
			int id = rsCliente.getInt("ID");
			String nombre = rsCliente.getString("Nombre");
			String apellido1 = rsCliente.getString("Apellido1");
			String apellido2 = rsCliente.getString("Apellido2");
			String dni = rsCliente.getString("DNI");
			ArrayList<Cuenta> listacuentas = new ArrayList<Cuenta>(); // Falta por implementar
			ArrayList<Prestamo> prestamos = new ArrayList<Prestamo>(); // Falta por implementar
			Cliente cliente = new Cliente(id, nombre, apellido1, apellido2, dni, listacuentas, prestamos);
			clientes.add(cliente);
		}
		rsCliente.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return clientes;
	}

	private List<Cuenta> loadCuentas() {
		List<Cuenta> cuentas = new ArrayList<>();
		List<Cliente> clientes  = loadClientes();
		
		try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pstCuenta = conn.prepareStatement("SELECT * FROM CUENTA")) {
				ResultSet rsCuenta = pstCuenta.executeQuery();
					
				while (rsCuenta.next()) {
					String numeroCuenta= rsCuenta.getString("Numero");
					float saldo = rsCuenta.getFloat("Saldo");
					Cliente cliente = new Cliente(0, null,null,null,null, null, null); // Falta por implementar
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
			PreparedStatement pstUpdate = conn.prepareStatement("UPDATE CLIENTE SET NOMBRE = ?, APELLIDO1 = ?, APELLIDO2 = ?, DNI = ?, WHERE ID = ?");
			PreparedStatement pstDelete = conn.prepareStatement("DELETE FROM CLIENTES WHERE ID = ?");
			PreparedStatement pstInsert = conn.prepareStatement("INSERT INTO CLIENTE (DNI, NOMBRE) VALUES (?, ?)")){
			//Falta Borrar las cuentas y prestamos del cliente cuando se borra el cliente
			pstUpdate.setString(1, cliente.getNombre());
			pstUpdate.setString(2, cliente.getApellido1());
			pstUpdate.setString(3, cliente.getApellido2());
			pstUpdate.setString(4, cliente.getDni());
			pstUpdate.setInt(5, cliente.getId());
			
			if (pstUpdate.executeUpdate() == 1) {
				pstDelete.setInt(1,  cliente.getId());
				if (pstDelete.executeUpdate()>= 0) {
					pstInsert.setInt(1, cliente.getId());
					if (pstInsert.executeUpdate() != 1) {
						return false;
						
					}
				}
				else { 
					return false;
					
				}
				updated = true;
			}
			
		} catch (Exception e) {
			System.err.format("Error actualizando el cliente '%s'", cliente.getNombre());
			e.printStackTrace();
			return false;
		}
		return updated;
	}
	// Falta el update cuentas
}
