package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
			String sql = "INSERT INTO clientes (fechaAlta, nombre, apellido, direccion, precio) VALUES (?, ?, ?, ?, ?)";
			try (PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				pstm.setDate(1, cliente.getFechaAlta());
				pstm.setString(2, cliente.getNombre());
				pstm.setString(3, cliente.getApellido());
				pstm.setString(4, cliente.getDireccion());
				pstm.setDouble(5, cliente.getPrecio());
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
    public void eliminar(Integer id) {
    	String sql = "DELETE FROM clientes WHERE id = ?";
		try (PreparedStatement stm = con.prepareStatement(sql)) {
			stm.setInt(1, id);
			stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    
    //EDITAR
    public void actualizar(String nombre, String apellido, String direccion, double precio, Integer id) {
    	String sql = "UPDATE clientes SET nombre = ?, apellido = ?, direccion = ?, precio = ? WHERE id = ?";
		try (PreparedStatement stm = con.prepareStatement(sql)) {
			stm.setString(1, nombre);
			stm.setString(2, apellido);
			stm.setString(3, direccion);
			stm.setDouble(4, precio);
			stm.setInt(5, id);
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    
    
    //nuevo
    public List<Cliente> listar() {
		List<Cliente> cliente = new ArrayList<Cliente>();
		try {
			String sql = "SELECT id, fechaAlta, nombre, apellido, direccion, precio FROM clientes";

			try (PreparedStatement pstm = con.prepareStatement(sql)) {
				pstm.execute();

				transformarResultSetEnCliente(cliente, pstm);
			}
			return cliente;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    
    private void transformarResultSetEnCliente(List<Cliente> clientes, PreparedStatement pstm) throws SQLException {
		try (ResultSet rst = pstm.getResultSet()) {
			while (rst.next()) {
				Cliente cliente = new Cliente(rst.getInt(1), rst.getDate(2), rst.getString(3), rst.getString(4),
						rst.getString(5), rst.getDouble(6));
				clientes.add(cliente);
			}
		}				
	}
}
