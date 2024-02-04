package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import gym.modelo.Gastos;


/**
 * Esta clase proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * en la tabla de gastos de la base de datos.
 */
public class GastoDAO {

	/**
     * La conexión a la base de datos utilizada por este DAO.
     */
	final private Connection con;

	/**
	 * Constructor que inicializa un nuevo GastoDAO con la conexión especificada.
	 * @param con La conexión a la base de datos que se utilizará para las operaciones del DAO.
	 */
	public GastoDAO(Connection con) {
		this.con = con;
	}
	
	/**
	 * Realiza el registro de un nuevo gasto en el sistema.
	 * @param gastos es el objeto Gasto que representa la información del nuevo gasto.
	 */
    public void guardar(Gastos gastos) {
		try {
			String sql = "INSERT INTO gastos (fechaGasto, nombreGasto, descripcion, costo) VALUES (?, ?, ?, ?)";
			try (PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stm.setDate(1, gastos.getFechaGasto());
				stm.setString(2, gastos.getNombreGasto());
				stm.setString(3, gastos.getDescripcion());
				stm.setDouble(4, gastos.getCosto());
				stm.execute();
				try (ResultSet rst = stm.getGeneratedKeys()) {
					while (rst.next()) {
						gastos.setIdGasto(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    
    /**
     * Elimina el registro de un gasto en el sistema
     * @param idGasto Identificador único de un Gasto que se desea eliminar.
     */
    public void eliminar(Integer idGasto) {
    	String sql = "DELETE FROM gastos WHERE idGasto = ?";
		try (PreparedStatement stm = con.prepareStatement(sql)) {
			stm.setInt(1, idGasto);
			stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    
    /**
     * Actualiza el registro de un gasto en el sistema.
     */
    public void actualizar(String nombreGasto, String descripcion, Double costo, Integer idGasto) {
        String sql = "UPDATE gastos SET nombreGasto = ?, descripcion = ?, costo = ? WHERE idGasto = ?";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, nombreGasto);
            stm.setString(2, descripcion);
            stm.setDouble(3, costo);
            stm.setInt(4, idGasto);

            int rowsUpdated = stm.executeUpdate();
            System.out.println(rowsUpdated + " fila(s) modificadas.");
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
 
    /**
     * Recupera una lista de todos los gastos registrados en el sistema.
     * @return retorna una lista que contiene registros de gastos en el sistema.
     * La lista puede estar vacía si no hay gastos registrados.
     */
    public List<Gastos> listar() {
		List<Gastos> gasto = new ArrayList<Gastos>();
		try {
			String sql = "SELECT idGasto, fechaGasto, nombreGasto, descripcion, costo FROM gastos";

			try (PreparedStatement stm = con.prepareStatement(sql)) {
				stm.execute();

				transformarResultSetEnGastos(gasto, stm);
			}
			return gasto;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    
  		
    
    /**
     * Transforma los resultados de un ResultSet en objetos de la clase Gastos y los agrega a una lista.
     * @param gasto  La lista en la que se agregarán los objetos de la clase Gasto.
     * @param pstm PreparedStatement con el ResultSet obtenido de la consulta.
     */
    private void transformarResultSetEnGastos(List<Gastos> gasto, PreparedStatement pstm) {
		try (ResultSet rst = pstm.getResultSet()) {
			while (rst.next()) {
				Gastos gastos = new Gastos(
						rst.getInt(1), 
						rst.getDate(2), 
						rst.getString(3), 
						rst.getString(4),
						rst.getDouble(5));
				gasto.add(gastos);
			}
		}catch (SQLException e) {
            System.err.println("Error al transformar ResultSet en Gastos: " + e.getMessage());
        } 			
	}
}
