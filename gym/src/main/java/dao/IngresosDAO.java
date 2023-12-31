package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gym.modelo.Cliente;

public class IngresosDAO {
	
	final private Connection con;

	public IngresosDAO(Connection con) {
		this.con = con;
	}
	
	public List<Cliente> buscarPorApellido(String apellido) throws SQLException {
	    List<Cliente> clientes = new ArrayList<>();

	    String sql = "SELECT * FROM clientes WHERE apellido LIKE ?";
	    try (PreparedStatement stm = con.prepareStatement(sql)) {
	    	stm.setString(1, "%" + apellido + "%");

	        try (ResultSet resultSet = stm.executeQuery()) {
	            while (resultSet.next()) {
	                int id = resultSet.getInt("id");
	                String nombre = resultSet.getString("nombre");
	                String apellidoCliente = resultSet.getString("apellido");
	                String telefono = resultSet.getString("telefono");

	                Cliente cliente = new Cliente();
	                cliente.setId(id);
	                cliente.setNombre(nombre);
	                cliente.setApellido(apellidoCliente);
	                cliente.setTelefono(telefono);;

	                clientes.add(cliente);
	            }
	        }
	    }

	    return clientes;
	}

	public void asignarPago(int idCliente, double montoPago, String periodoPago) throws SQLException {
	    // Modifica tu consulta SQL para incluir el monto y la fecha
	    String sql = "INSERT INTO pagos (id_cliente, monto, periodoPago) VALUES (?, ?, ?)";
	    try (PreparedStatement stm = con.prepareStatement(sql)) {
	    	stm.setInt(1, idCliente);
	    	stm.setDouble(2, montoPago);
	    	stm.setString(3, periodoPago);

	    	stm.executeUpdate();
	    }
	}
	
}
