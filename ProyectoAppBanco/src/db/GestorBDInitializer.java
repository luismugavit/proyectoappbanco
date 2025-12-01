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
import domain.Prestamo;

public class GestorBDInitializer {

	private static final String FILE = "resources/db/banco.db";
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
                    + " ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " NUMERO_CUENTA TEXT NOT NULL UNIQUE,\n"
                    + " SALDO REAL NOT NULL,\n"
                    + " ID_CLIENTE INTEGER NOT NULL,\n"
                    + " FOREIGN KEY(ID_CLIENTE) REFERENCES CLIENTE(ID)\n"
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
	public void insertCuenta(Cuenta cuenta, Cliente cliente) {
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	        PreparedStatement pstmt = con.prepareStatement("INSERT INTO CUENTA (NUMERO_CUENTA, SALDO, ID_CLIENTE) VALUES (?, ?, ?)")) {
			
			pstmt.setString(1, cuenta.getNumeroCuenta());
            pstmt.setFloat(2, cuenta.getSaldo());
            pstmt.setLong(3, cliente.getId());
			pstmt.executeUpdate();
			
			System.out.format("- Cuenta '%s' insertada para el cliente ID %d\n", cuenta.getNumeroCuenta(), cliente.getId());
		}
		catch (SQLException e) {
			System.err.format("* Error al insertar Cuenta '%s': %s\n", cuenta.getNumeroCuenta(), e.getMessage());
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

