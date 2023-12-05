package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;


//conectamos a la base de datos
public class Conexion {

    public Connection recuperaConexion() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost/baseClientes?useTimeZone=true&serverTimeZone=UTC",
                "root",
                "root");           
    }    
        
}
