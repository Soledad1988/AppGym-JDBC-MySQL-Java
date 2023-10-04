package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import gym.modelo.Cliente;


public class ClienteDAO {

	final private Connection con;

	public ClienteDAO(Connection con) {
		this.con = con;
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
    	String sql = "DELETE FROM clientes WHERE id = ?";
		try (PreparedStatement stm = con.prepareStatement(sql)) {
			stm.setInt(1, id);
			stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    
    //EDITAR
    public void Actualizar(String nombre, String apellido, String direccion, Integer id) {
    	String sql = "UPDATE clientes SET nombre = ?, apellido = ?, direccion = ? WHERE id = ?";
		try (PreparedStatement stm = con.prepareStatement(sql)) {
			stm.setString(1, nombre);
			stm.setString(2, apellido);
			stm.setString(3, direccion);
			stm.setInt(4, id);
			stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    
    private void transformarResultSetEnCliente(List<Cliente> clientes, PreparedStatement pstm) throws SQLException {
		try (ResultSet rst = pstm.getResultSet()) {
			while (rst.next()) {
				Cliente cli = new Cliente(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4));
				clientes.add((Cliente) clientes);
			}
		}				
	}
}
