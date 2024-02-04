package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import gym.modelo.Cliente;

/**
 * Esta clase proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * en la tabla de clientes de la base de datos.
 */
public class ClienteDAO {

	/**
     * La conexión a la base de datos utilizada por este DAO.
     */
	final private Connection con;

	/**
     * Constructor que inicializa un nuevo ClienteDAO con la conexión especificada.
     *
     * @param con La conexión a la base de datos que se utilizará para las operaciones del DAO.
     */
	public ClienteDAO(Connection con) {
		this.con = con;
	}
    
	/**
	 * Realiza el registro de un nuevo cliente en el sistema.
	 * @param cliente es el objeto Cliente que representa la información del nuevo cliente. 
	 * 
	 * @throws RuntimeException Si ocurre un error durante la ejecución del registro.
	 */
    public void guardar(Cliente cliente) {
		try {
			String sql = "INSERT INTO clientes (fechaAlta, nombre, apellido, direccion, telefono, horario, pago) VALUES (?, ?, ?, ?, ?, ?, ?)";
			try (PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stm.setDate(1, cliente.getFechaAlta());
				stm.setString(2, cliente.getNombre());
				stm.setString(3, cliente.getApellido());
				stm.setString(4, cliente.getDireccion());
				stm.setString(5, cliente.getTelefono());
				stm.setString(6, cliente.getHorario());
				stm.setBoolean(7, false);
				stm.execute();
				try (ResultSet rst = stm.getGeneratedKeys()) {
					while (rst.next()) {
						cliente.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    
    /**
     * Elimina el registro de un cliente en el sistema
     * @param id Identificador único del cliente que se desea eliminar.
     */
    public void eliminar(Integer id) {
    	String sql = "DELETE FROM clientes WHERE id = ?";
		try (PreparedStatement stm = con.prepareStatement(sql)) {
			stm.setInt(1, id);
			stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    
    /**
     * Actualiza el registro de un cliente en el sistema.
     * @param nombre: Nombre actualizado del cliente.
     * @param apellido: Apellido actualizado del cliente.
     * @param direccion: Dirección actualizado del cliente.
     * @param telefono: Número de teléfono actualizado del cliente.
     * @param horario: Horario preferido actualizado del cliente.
     * @param id: Identificador único del cliente que se desea actualizar.
     * 
     * @throws RuntimeException Si ocurre un error durante la ejecución de la operación de actualización
     *  o si no se encuentra un cliente con el identificador proporcionado.
     */
    public void actualizar(String nombre, String apellido, String direccion,
    		String telefono, String horario, Integer id) {
    	
    	String sql = "UPDATE clientes SET nombre = ?, apellido = ?, "
    			+ "direccion = ?, telefono = ?, horario = ? WHERE id = ?";
    	System.out.println("Consulta SQL: " + sql);
        
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombre);
        System.out.println("Apellido: " + apellido);
        System.out.println("Dirección: " + direccion);
        
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, nombre);
            stm.setString(2, apellido);
            stm.setString(3, direccion);
            stm.setString(4, telefono);
            stm.setString(5, horario);
            stm.setInt(6, id);

            int rowsUpdated = stm.executeUpdate();
            System.out.println(rowsUpdated + " fila(s) modificadas.");
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
 
    /**
     * Recupera una lista de todos los clientes registrados en el sistema.
     * @return retorna una lista que contiene registros de clientes en el sistema.
     * La lista puede estar vacía si no hay clientes registrados.
     */
    public List<Cliente> listar() {
		List<Cliente> cliente = new ArrayList<Cliente>();
		try {
			String sql = "SELECT id, fechaAlta, nombre, apellido, direccion, telefono, horario FROM clientes";

			try (PreparedStatement stm = con.prepareStatement(sql)) {
				stm.execute();

				transformarResultSetEnCliente(cliente, stm);
			}
			return cliente;
		} catch (SQLException e) {
			 e.printStackTrace(); 
			 
			throw new RuntimeException(e);
		}
	}
    
    
    /**
     * Busca en el sistema los clientes registrados cuyo apellido coincide parcial o totalmente
     * con el apellido proporcionado.
     * @param apellido a buscar.
     * @return retorna lista que contiene registros que coinciden con el apellido proporcionado.
     *         La lista puede estar vacía si no se encuentran coincidencias.
     *
     * @throws RuntimeException Si ocurre un error durante la ejecución de la consulta.
    */
  
    public List<Cliente> buscarPorApellido(String apellido) {
	    List<Cliente> clientes = new ArrayList<>();

	    try {
	    String sql = "SELECT * FROM clientes WHERE apellido LIKE ?";
	    try (PreparedStatement stm = con.prepareStatement(sql)) {
	    	stm.setString(1, "%" + apellido + "%");

		        try (ResultSet resultSet = stm.executeQuery()) {
		            while (resultSet.next()) {
		                Integer id = resultSet.getInt("id");
		                String nombre = resultSet.getString("nombre");
		                String apellidoCliente = resultSet.getString("apellido");
	
		                Cliente cliente = new Cliente();
		                cliente.setId(id);
		                cliente.setNombre(nombre);
		                cliente.setApellido(apellidoCliente);
	
		                clientes.add(cliente);
		            }
		        }
	       }
	    }catch (SQLException e) {
	        System.err.println("Error al realizar la consulta: " + e.getMessage());
	    } 

	    return clientes;
	}
    
 
    /**
     * Cuenta el número de personas registradas en el sistema que tienen el horario especificado.
     * @param horario Horario a filtrar.
     * @return El número de personas cuyo horario coincide con el horario proporcionado.
     */
    public int contarPersonasPorHorario(String horario) {
        return this.listar().stream()
        		.filter(cliente -> horario != null && horario.equals(cliente.getHorario()))
                .mapToInt(cliente -> 1)
                .sum();
    }
    
    /**
     * Transforma los resultados de un ResultSet en objetos de la clase Cliente y los agrega a una lista.
     * @param clientes La lista en la que se agregarán los objetos de la clase Cliente.
     * @param pstm PreparedStatement con el ResultSet obtenido de la consulta.
     */
    private void transformarResultSetEnCliente(List<Cliente> clientes, PreparedStatement pstm) {
        ResultSet rst = null;
        try {
            rst = pstm.getResultSet();
            while (rst.next()) {
                Cliente cliente = new Cliente(
                		rst.getInt(1), 
                		rst.getDate(2), 
                		rst.getString(3), 
                		rst.getString(4),
                        rst.getString(5), 
                        rst.getString(6),
                        rst.getString(7),
                        null);
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Error al transformar ResultSet en Cliente: " + e.getMessage());
        } 
    }

}
