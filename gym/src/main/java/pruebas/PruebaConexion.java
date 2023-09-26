package pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import conexion.Conexion;

public class PruebaConexion {

	public static void main(String[] args) throws SQLException {
		
		Connection conn = new Conexion().recuperaConexion();
		
		System.out.println("Cerrando conexion");
		conn.close();
	}
}
