package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.Cliente;
import domain.Cuenta;
import domain.Gasto;
import domain.Ingreso;
import domain.Prestamo;

public class GestorBDInitializer {

	private static final String FILE = "ProyectoAppBanco/src/resources/Banco2.db";
	private static final String CONNECTION_STRING = "jdbc:sqlite:" + FILE;
	
	public GestorBDInitializer() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.format("* Error al cargar el driver de la BBDD: %s\\n", e.getMessage());
		}
	}
	public void crearTablas() {
    	try {
    		File dbFile = new File(FILE);
    		
    		if (dbFile.exists()) {
    			dbFile.delete();
    		}
    	} catch (Exception e) {
    		System.err.format("* Error al borrar el fichero '%s' de la base de datos: %s\n", FILE, e.getMessage());
    		System.exit(1);
    	}    	
    	
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
        	String sqlCliente = "CREATE TABLE IF NOT EXISTS CLIENTE (\n"
                    + " ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " DNI TEXT NOT NULL UNIQUE,\n"
                    + " NOMBRE TEXT NOT NULL,\n"
                    + " APELLIDO1 TEXT,\n"
                    + " APELLIDO2 TEXT\n"
                    + ");";
        	String sqlCuenta = "CREATE TABLE IF NOT EXISTS CUENTA (\n"
                    //+ " ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " NUMERO_CUENTA TEXT PRIMARY KEY NOT NULL,\n"
                    + " SALDO REAL NOT NULL,\n"
                    + " ID_CLIENTE INTEGER NOT NULL,\n"
                    + " FOREIGN KEY(ID_CLIENTE) REFERENCES CLIENTE(ID)\n"
                    + ");";
        	
        	String sqlGasto = "CREATE TABLE IF NOT EXISTS GASTO (\n"
                    + " ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " FECHA TEXT,\n"
                    + " CANTIDAD REAL,\n"
                    + " CONCEPTO TEXT,\n"
                    + " NUMERO_CUENTA TEXT NOT NULL\n"
                    + ");";
        	
        	String sqlIngreso = "CREATE TABLE IF NOT EXISTS INGRESO (\n"
                    + " ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " FECHA TEXT,\n"
                    + " CANTIDAD REAL,\n"
                    + " CONCEPTO TEXT,\n"
                    + " NUMERO_CUENTA TEXT NOT NULL\n"
                    + ");";
        	String sqlPrestamo = "CREATE TABLE IF NOT EXISTS PRESTAMO (\n"
                    + " ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " CANTIDAD_SOLICITADA REAL NOT NULL,\n"
                    + " INTERES_ANUAL REAL NOT NULL,\n"
                    + " PLAZO_MESES INTEGER NOT NULL,\n"
                    + " ID_CLIENTE INTEGER NOT NULL,\n"
                    + " FOREIGN KEY(ID_CLIENTE) REFERENCES CLIENTE(ID)\n"
                    + ");";
        	try (Statement stmt = con.createStatement()) {
                stmt.execute(sqlCliente);
                stmt.execute(sqlCuenta);
                stmt.execute(sqlGasto);
                stmt.execute(sqlIngreso);
                stmt.execute(sqlPrestamo);
        	}
        	System.out.println("- Tablas creadas.");
        	
        } catch (SQLException e) {
        	System.err.format("* Error al crear las tablas: %s\n", e.getMessage());
		}
}
	
	public void insertCliente(Cliente cliente) {
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO CLIENTE (DNI, NOMBRE, APELLIDO1, APELLIDO2) VALUES (?, ?, ?, ?)")) {
            
        	pstmt.setString(1, cliente.getDni());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setString(3, cliente.getApellido1());
            pstmt.setString(4, cliente.getApellido2());
            pstmt.executeUpdate();
            
            Cliente clienteAux = getClienteByDni(cliente.getDni());
            cliente.setId(clienteAux.getId());
            System.out.format("- Cliente '%s' insertado\n", cliente.getNombre());
        }
        catch(SQLException e) {
        	System.err.format("* Error al insertar Cliente '%s': %s\n", cliente.getNombre(), e.getMessage());
        }
	}
	private Cliente getClienteByDni(String dni) {
		Cliente cliente = null;
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM CLIENTE WHERE DNI = ?")) {
			pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
            	cliente = new Cliente(null, null, null, null);
                cliente.setId(rs.getInt("ID"));
                cliente.setDni(rs.getString("DNI"));
                cliente.setNombre(rs.getString("NOMBRE"));
                cliente.setApellido1(rs.getString("APELLIDO1"));
                cliente.setApellido2(rs.getString("APELLIDO2"));
			}
		}
		catch(SQLException e) {
			System.err.format("* Error al obtener Cliente con DNI '%s': %s\n", dni, e.getMessage());
		}
		
		return cliente;
	}
	public void insertCuenta(Cuenta cuenta) {
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	        PreparedStatement pstmt = con.prepareStatement("INSERT INTO CUENTA (NUMERO_CUENTA, SALDO, ID_CLIENTE) VALUES (?, ?, ?)")) {
			
			pstmt.setString(1, cuenta.getNumeroCuenta());
            pstmt.setFloat(2, cuenta.getSaldo());
            pstmt.setLong(3, cuenta.getPropietario().getId());
			pstmt.executeUpdate();
			
			System.out.format("- Cuenta '%s' insertada para el cliente ID %d\n", cuenta.getNumeroCuenta(), cuenta.getPropietario().getId());
		}
		catch (SQLException e) {
			System.err.format("* Error al insertar Cuenta '%s': %s\n", cuenta.getNumeroCuenta(), e.getMessage());
		}
	}
	
	public void insertGasto(Gasto gasto) {
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		        PreparedStatement pstmt = con.prepareStatement("INSERT INTO GASTO (FECHA, CANTIDAD, CONCEPTO,NUMERO_CUENTA) VALUES (?, ?, ?,?)")) {
				
				pstmt.setString(1,gasto.getFecha().toString());
				pstmt.setFloat(2, gasto.getCantidad());
				pstmt.setString(3, gasto.getConcepto());
				pstmt.setString(4,gasto.getOrigen().getNumeroCuenta());
				pstmt.executeUpdate();
				
				//System.out.format("- Cuenta '%s' insertada para el cliente ID %d\n", cuenta.getNumeroCuenta(), cuenta.getPropietario().getId());
			}
			catch (SQLException e) {
				System.err.format("* Error al insertar gasto");
			}
	}
	
	public void insertIngreso(Ingreso ingreso) {
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		        PreparedStatement pstmt = con.prepareStatement("INSERT INTO INGRESO (FECHA, CANTIDAD, CONCEPTO,NUMERO_CUENTA) VALUES (?, ?, ?,?)")) {
				
				pstmt.setString(1,ingreso.getFecha().toString());
				pstmt.setFloat(2, ingreso.getCantidad());
				pstmt.setString(3, ingreso.getConcepto());
				pstmt.setString(4,ingreso.getDestino().getNumeroCuenta());
				pstmt.executeUpdate();
				
				//System.out.format("- Cuenta '%s' insertada para el cliente ID %d\n", cuenta.getNumeroCuenta(), cuenta.getPropietario().getId());
			}
			catch (SQLException e) {
				System.err.format("* Error al insertar ingreso");
			}
	}
	
	public void insertPrestamo(Prestamo prestamo, Cliente cliente) {
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO CLIENTE (DNI, NOMBRE, APELLIDO1, APELLIDO2) VALUES (?, ?, ?, ?)")) {

			pstmt.setDouble(1, prestamo.getCantidadSolicitada());
            pstmt.setDouble(2, prestamo.getInteresAnual());
            pstmt.setInt(3, prestamo.getPlazoMeses());
            pstmt.setInt(4, cliente.getId());
	}
		catch (SQLException e){
			System.err.format("* Error al insertar Prestamo'%s': %s\n", prestamo.getId(), e.getMessage());
		}
	}
}

