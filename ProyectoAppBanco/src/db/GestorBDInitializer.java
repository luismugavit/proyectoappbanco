package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
}

