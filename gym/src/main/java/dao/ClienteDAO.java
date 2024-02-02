package dao;

import java.sql.Connection;
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
    
    public void eliminar(Integer id) {
    	String sql = "DELETE FROM clientes WHERE id = ?";
		try (PreparedStatement stm = con.prepareStatement(sql)) {
			stm.setInt(1, id);
			stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    
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
    
    //Reporte turnos
    public int contarPersonasPorHorario(String horario) {
        return this.listar().stream()
        		.filter(cliente -> horario != null && horario.equals(cliente.getHorario()))
                .mapToInt(cliente -> 1)
                .sum();
    }
    
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
