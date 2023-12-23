package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
	    try (PreparedStatement statement = con.prepareStatement(sql)) {
	        statement.setString(1, "%" + apellido + "%");

	        try (ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                int id = resultSet.getInt("id");
	                String nombre = resultSet.getString("nombre");
	                String apellidoCliente = resultSet.getString("apellido");
	                double precio = resultSet.getDouble("precio");

	                Cliente cliente = new Cliente();
	                cliente.setId(id);
	                cliente.setNombre(nombre);
	                cliente.setApellido(apellidoCliente);
	                cliente.setPrecio(precio);

	                clientes.add(cliente);
	            }
	        }
	    }

	    return clientes;
	}

	public void asignarPago(int idCliente, double montoPago, String periodoPago) throws SQLException {
	    // Modifica tu consulta SQL para incluir el monto y la fecha
	    String sql = "INSERT INTO pagos (id_cliente, monto, periodoPago) VALUES (?, ?, ?)";
	    try (PreparedStatement statement = con.prepareStatement(sql)) {
	        statement.setInt(1, idCliente);
	        statement.setDouble(2, montoPago);
	        statement.setString(3, periodoPago);

	        statement.executeUpdate();
	    }
	}
	
	// Método para buscar clientes por apellido y asignar pago
	/*public void buscarPorApellidoYAsignarPago(String apellido, double montoPago) {
	List<Cliente> clientes = new ArrayList<>();

	String sql = "SELECT * FROM clientes WHERE apellido LIKE ?";
	    try (PreparedStatement statement = con.prepareStatement(sql)) {
	        statement.setString(1, "%" + apellido + "%");

	        try (ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                Integer id = resultSet.getInt("id");
	                Date fechaAlta = resultSet.getDate("fecha_alta");
	                String nombre = resultSet.getString("nombre");
	                String apellidoCliente = resultSet.getString("apellido");
	                String direccion = resultSet.getString("direccion");
	                Double precio = resultSet.getDouble("precio");

	                Cliente cliente = new Cliente();
	                clientes.add(cliente);
	            }
	        }

	    // Realizar la asignación de pago
	    if (!clientes.isEmpty()) {
	        Cliente clienteSeleccionado = seleccionarCliente(clientes); // Puedes implementar este método
	        if (clienteSeleccionado != null) {
	            asignarPago(clienteSeleccionado, montoPago); // Puedes implementar este método
	            System.out.println("Pago asignado correctamente.");
	        }
	    } else {
	        System.out.println("No se encontraron clientes con el apellido proporcionado.");
	    }
	} catch (SQLException e) {
	    e.printStackTrace(); // Manejo de errores, puedes personalizar según tu aplicación
	}
	}

	private Cliente seleccionarCliente(List<Cliente> clientes) {
	// Implementa la lógica para permitir al usuario seleccionar un cliente de la lista
	// Puedes mostrar la lista de clientes y permitir al usuario elegir uno.
	// Devuelve el cliente seleccionado o null si no se selecciona ninguno.
	// ...
	return null;
	}

	private void asignarPago(Cliente cliente, double montoPago) {
	// Implementa la lógica para asignar el pago al cliente
	// Puedes actualizar la base de datos u otro almacenamiento según tus necesidades.
	// ...
	}
*/

}
