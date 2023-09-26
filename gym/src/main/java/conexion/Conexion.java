package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//conectamos a la base de datos
public class Conexion {
		
public Connection recuperaConexion() throws SQLException {
		
		return DriverManager.getConnection(
              "jdbc:mysql://localhost/clientes?useTimeZone=true&serverTimeZone=UTC",
              "root",
              "12345678");
	}
}
