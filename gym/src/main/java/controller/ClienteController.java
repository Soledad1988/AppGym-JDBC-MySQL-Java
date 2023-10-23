package controller;

import java.sql.Connection;
import java.sql.Date;
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

	/*public List<Cliente> listar() {
		return this.clienteDAO.listar();
	}
	
	
	public void actualizar(Date fechaAlta, String nombre, String apellido, String direccion, Double precio, Integer id) {
		this.clienteDAO.actualizar(fechaAlta, nombre, apellido, direccion, precio, id);
	}
	
	public void eliminar(Integer id) {
		this.clienteDAO.eliminar(id);
	}*/

	//Listar
	public List<Map<String,String>> listar() throws SQLException {
		Conexion factory = new Conexion();
		final Connection con = factory.recuperaConexion();
		  
			final PreparedStatement statement = con.prepareStatement("SELECT id, fechaAlta, nombre, apellido, direccion, precio FROM clientes");
	        
			statement.execute();

	        ResultSet resultSet = statement.getResultSet();

	        List<Map<String, String>> resultado = new ArrayList<>();

	      //leemos el contendio para agregrlo a un listado
	        while (resultSet.next()) {
	            Map<String, String> fila = new HashMap<>();
	            fila.put("id", String.valueOf(resultSet.getInt("id")));
	            fila.put("fechaAlta", String.valueOf(resultSet.getDate("fechaAlta")));
	            fila.put("nombre", resultSet.getString("nombre"));
	            fila.put("apellido", resultSet.getString("apellido"));
	            fila.put("direccion", resultSet.getString("direccion"));
	            fila.put("precio", String.valueOf(resultSet.getDouble("precio")));

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
		final PreparedStatement statement = con.prepareStatement("DELETE FROM clientes WHERE id = ?");
			statement.setInt(1, id);
			statement.execute();
			return statement.getUpdateCount(); //me devuelve la cantidad de filas modificadas
	}
	


	
	public int actualizar(Date fechaAlta, String nombre, String apellido, String direccion, Double precio, Integer id) throws SQLException {
		Conexion factory = new Conexion();
	    final Connection con = factory.recuperaConexion();
	    final  PreparedStatement statement = con.prepareStatement("UPDATE clientes SET fechaAlta = ?, nombre = ?, apellido = ?, direccion =  ? precio = ? WHERE id =  ?");
	  
	    	statement.setDate(1, fechaAlta);
		    statement.setString(2, nombre);
			statement.setString(3, apellido);
			statement.setString(4, direccion);
			statement.setDouble(5, precio);
			statement.setInt(6, id);
		    statement.execute();
	
		    int updateCount = statement.getUpdateCount();
		    return updateCount;
	}
	

}
