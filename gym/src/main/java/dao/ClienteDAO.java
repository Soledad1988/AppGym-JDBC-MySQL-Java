package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import gym.modelo.Cliente;


public class ClienteDAO {

	final private Connection con;

	public ClienteDAO(Connection con) {
		this.con = con;
	}
	
		
    private void ejecutaRegistro(Cliente cliente, PreparedStatement statement) throws SQLException {	
    		statement.setString(1, cliente.getNombre());
    		statement.setString(2, cliente.getApellido());
    		statement.setString(3, cliente.getDireccion());	
    		statement.execute();
    		
    		//me cierra todas los conexiones
    		final ResultSet resultSet = statement.getGeneratedKeys();
    		
    	
    			while(resultSet.next()) {
    				cliente.setId(resultSet.getInt(1));
    				System.out.println(String.format(
    						"fue insertado el producto %s ",
    						cliente));
    				
    	}}
}
