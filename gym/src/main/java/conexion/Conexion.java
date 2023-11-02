package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;


//conectamos a la base de datos
public class Conexion {
	
	public DataSource dataSource;

	public Conexion() {
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost/baseClientes?useTimezone=true&serverTimezone=UTC");
		comboPooledDataSource.setUser("root");
		comboPooledDataSource.setPassword("root");
		

		this.dataSource = comboPooledDataSource;
	}

	public Connection recuperarConexion() {
		try {
			return this.dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/*public Connection recuperaConexion() throws SQLException {
	
		return DriverManager.getConnection(
              "jdbc:mysql://localhost/baseClientes?useTimeZone=true&serverTimeZone=UTC",
              "root",
              "root");
	}*/

	
}

 

/*public DataSource dataSource;

public Conexion() {
	ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
	comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost/baseClientes?useTimezone=true&serverTimezone=UTC");
	comboPooledDataSource.setUser("root");
	comboPooledDataSource.setPassword("12345678");
	
	this.dataSource = comboPooledDataSource;
}

public Connection recuperarConexion() {
	try {
		return this.dataSource.getConnection();
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}

}	*/