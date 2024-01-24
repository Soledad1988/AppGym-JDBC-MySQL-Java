package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//conectamos a la base de datos
public class Conexion {
	
	 private static Conexion instance;
	    private Connection connection;

	    private Conexion() {
	        try {
	            this.connection = DriverManager.getConnection(
	                "jdbc:mysql://localhost/baseClientes?useTimeZone=true&serverTimeZone=UTC",
	                "root",
	                "****");
	            System.out.println("Conexión a la base de datos establecida.");
	        } catch (SQLException e) {
	            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
	            throw new RuntimeException("Error al conectar con la base de datos", e);
	        }
	    }

	    public static Conexion getInstance() {
	        if (instance == null || instance.connection == null) {
	            instance = new Conexion();
	        } else {
	            try {
	                if (instance.getConnection().isClosed()) {
	                    instance = new Conexion();
	                }
	            } catch (SQLException e) {
	                System.err.println("Error al verificar el estado de la conexión: " + e.getMessage());
	                instance = new Conexion();
	            }
	        }

	        return instance;
	    }

	    public Connection getConnection() {
	        return connection;
	    }
        
}
