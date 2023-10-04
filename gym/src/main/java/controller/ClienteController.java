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
import dao.ClienteDAO;
import gym.modelo.Cliente;


public class ClienteController {
	
	private ClienteDAO clienteDAO;

	//Listar
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
	
	//guardar
	public void guardar(Cliente cliente) throws SQLException {
    	ClienteDAO clienteDao = new ClienteDAO(new Conexion().recuperaConexion());
    	clienteDao.guardar(cliente);
    	
	}
	
	public int eliminar(Integer id) throws SQLException {
		final Connection con = new Conexion().recuperaConexion();
		final PreparedStatement statement = con.prepareStatement("DELETE FROM CLIENTES WHERE ID = ?");
			statement.setInt(1, id);
			statement.execute();
			return statement.getUpdateCount(); //me devuelve la cantidad de filas modificadas
	}
	


	public int actualizar(String nombre, String apellido, String direccion, Integer id) throws SQLException {
		Conexion factory = new Conexion();
	    final Connection con = factory.recuperaConexion();
	    final  PreparedStatement statement = con.prepareStatement("UPDATE clientes SET NOMBRE = ?, APELLIDO = ?, DIRECCION =  ? WHERE ID =  ?");
	  
		    statement.setString(1, nombre);
			statement.setString(2, apellido);
			statement.setString(3, direccion);
			statement.setInt(4, id);
		    statement.execute();
	
		    int updateCount = statement.getUpdateCount();
		    return updateCount;
	}



	

}
