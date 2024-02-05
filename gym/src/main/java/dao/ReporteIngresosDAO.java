package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexion.Conexion;

/**
 * Esta clase proporciona un método para realizar leectura
 * en la tabla de clientes y cuotas de la base de datos.
 */
public class ReporteIngresosDAO {
	
	/**
     * La conexión a la base de datos utilizada por este DAO.
     */
	final private Connection con;

	/**
     * Constructor que inicializa un nuevo ReporteIngresosDAO con la conexión especificada.
     *
     * @param con La conexión a la base de datos que se utilizará para las operaciones del DAO.
     */
	public ReporteIngresosDAO(Connection con) {
		this.con = con;
	}

	/**
	 * Recupera una lista con la fecha de alta, nombre, apellido y telefono de los clientes registrados en el sistema.
     * @return retorna una lista que contiene registros de clientes en el sistema.
	 * @throws SQLException
	 */
	public List<Map<String,String>> reporteClientes() throws SQLException {
				  
		final String sql = "SELECT fechaAlta, nombre, apellido, telefono FROM clientes";
			        
		try (PreparedStatement stm = con.prepareStatement(sql)) {
			
		stm.execute();

		ResultSet resultSet = stm.getResultSet();

		List<Map<String, String>> resultado = new ArrayList<>();

			      //leemos el contendio para agregrlo a un listado
		while (resultSet.next()) {
			 Map<String, String> fila = new HashMap<>();
			 fila.put("fechaAlta", String.valueOf(resultSet.getDate("fechaAlta")));
			 fila.put("nombre", resultSet.getString("nombre"));
			 fila.put("apellido", resultSet.getString("apellido"));
			 fila.put("telefono", resultSet.getString("telefono"));

			 resultado.add(fila);
		  }
					
		return resultado;
		}
	}
			
	
	/**
	 * Obtiene la suma de las cuotas generadas segùn el año y mes seleccionado.
	 * @param numeroMes hace refencia al mes.
	 * @param año hace referencia al año.
	 * @return retorna un importe segùn las cuotas generadas en base a el año y mes seleccionado.
	 */
    public static double realizarSumaPorMes(int numeroMes, int año) {
    	  Conexion factory = Conexion.getInstance();

             String sql = "SELECT SUM(monto) AS resultado FROM cuotas WHERE MONTH(fechaPago) = ? AND YEAR(fechaPago) = ?";

             try (Connection con = factory.getConnection();
            		 PreparedStatement stm = con.prepareStatement(sql)) {
                 stm.setInt(1, numeroMes);
                 stm.setInt(2, año);

                 try (ResultSet resultSet = stm.executeQuery()) {
                     if (resultSet.next()) {
                         // Obtener el resultado de la suma
                         return resultSet.getDouble("resultado");
                     }
                 }
             } catch (SQLException e) {
            	 System.err.println("Error al realizar la operación: " + e.getMessage());
             }

             // En caso de error o si no hay resultados, retornar un valor indicativo
             return 0;   
    }
}
