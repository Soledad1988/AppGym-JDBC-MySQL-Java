package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import conexion.Conexion;
import dao.ClienteDAO;
import gym.modelo.Cliente;


public class ClienteController {

	private ClienteDAO clienteDAO;
	 
	 public ClienteController() throws SQLException {
		 Connection connection = new Conexion().recuperaConexion();
		 this.clienteDAO = new ClienteDAO(connection);
		}
	 
		public void guardar(Cliente cliente) {
			this.clienteDAO.guardar(cliente);
		}
		
		public List<Cliente> listar() {
			return this.clienteDAO.listar();
		}
		
		public void eliminar(Integer id) {
			this.clienteDAO.eliminar(id);
		}

		public void actualizar(String nombre, String apellido, String direccion, Integer id) {
			this.clienteDAO.actualizar(nombre, apellido, direccion, id);
		}

}

/*// Método para buscar clientes por apellido y asignar pago
public void buscarPorApellidoYAsignarPago(String apellido, double montoPago) {
List<Cliente> clientes = new ArrayList<>();

try (Connection connection = obtenerConexion()) {
    String sql = "SELECT * FROM clientes WHERE apellido LIKE ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, "%" + apellido + "%");

        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Date fechaAlta = resultSet.getDate("fecha_alta");
                String nombre = resultSet.getString("nombre");
                String apellidoCliente = resultSet.getString("apellido");
                String direccion = resultSet.getString("direccion");
                double precio = resultSet.getDouble("precio");

                Cliente cliente = new Cliente(id, fechaAlta, nombre, apellidoCliente, direccion, precio);
                clientes.add(cliente);
            }
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

// ...*/