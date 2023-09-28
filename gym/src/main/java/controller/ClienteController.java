package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexion.Conexion;


public class ClienteController {

	public List<Map<String,String>> listar() throws SQLException {
		Conexion factory = new Conexion();
		final Connection con = factory.recuperaConexion();
		  
			final PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, APELLIDO, DIRECCION FROM CLIENTES");
	        
			statement.execute();

	        ResultSet resultSet = statement.getResultSet();

	        List<Map<String, String>> resultado = new ArrayList<>();

	      //leemos el contendio para agregrlo a un listado
	        while (resultSet.next()) {
	            Map<String, String> fila = new HashMap<>();
	            fila.put("ID", String.valueOf(resultSet.getInt("ID")));
	            fila.put("NOMBRE", resultSet.getString("NOMBRE"));
	            fila.put("APELLIDO", resultSet.getString("APELLIDO"));
	            fila.put("DIRECCION", resultSet.getString("DIRECCION"));

	            resultado.add(fila);
	        }
			
		return resultado;
	
	}
	

}
