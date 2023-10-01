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
    				
    			}
    		}
    
    
    //guardar
    
    public void guardar(Cliente cliente) {
		try {
			String sql = "INSERT INTO clientes (nombre, apellido, direccion) VALUES (?, ?, ?)";

			try (PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				pstm.setString(1, cliente.getNombre());
				pstm.setString(2, cliente.getApellido());
				pstm.setString(3, cliente.getDireccion());

				pstm.execute();

				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						cliente.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    
    //eliminar
    public void Eliminar(Integer id) {
		try (PreparedStatement stm = con.prepareStatement("DELETE FROM clientes WHERE id = ?")) {
			stm.setInt(1, id);
			stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    
    //EDITAR
    public void Actualizar(String nombre, String apellido, String domicilio) {
		try (PreparedStatement stm = con
				.prepareStatement("UPDATE clientes SET nombre = ?, apellido = ?, domicilio = ? WHERE id = ?")) {
			stm.setString(1, nombre);
			stm.setString(2, apellido);
			stm.setString(3, domicilio);
			stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
