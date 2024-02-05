package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import gym.modelo.Gastos;

/**
 * Esta clase proporciona un método para realizar leectura
 * en la tabla de gastos de la base de datos.
 */
public class ReporteGastosDAO {
	
	/**
     * La conexión a la base de datos utilizada por este DAO.
     */
	final private Connection con;

	/**
     * Constructor que inicializa un nuevo ReporteGastosDAO con la conexión especificada.
     *
     * @param con La conexión a la base de datos que se utilizará para las operaciones del DAO.
     */
	public ReporteGastosDAO(Connection con) {
		this.con = con;
	}

	// Listar reporte de gastos
	/**
	 * Recupera una lista de todos los gastos registrados en el sistema.
     * @return retorna una lista que contiene registros de gastos en el sistema.
	 */
	public List<Gastos> reporteGastos() {

	    List<Gastos> resultado = new ArrayList<>();

	    try {
	    
	        final String sql = "SELECT fechaGasto, nombreGasto, descripcion, costo FROM gastos";

	        try (PreparedStatement stm = con.prepareStatement(sql)) {
	            stm.execute();
	            ResultSet resultSet = stm.getResultSet();

	            // Leemos el contenido para agregarlo a un listado de objetos Gastos
	            while (resultSet.next()) {
	                Gastos gasto = new Gastos();
	                gasto.setFechaGasto(resultSet.getDate("fechaGasto"));
	                gasto.setNombreGasto(resultSet.getString("nombreGasto"));
	                gasto.setDescripcion(resultSet.getString("descripcion"));
	                gasto.setCosto(resultSet.getDouble("costo"));

	                resultado.add(gasto);
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error al realizar la consulta: " + e.getMessage());
	    } 

	    return resultado;
	}
    
	/**
	 * Obtiene la suma de los costos generados segùn el año y mes seleccionado.
	 * @param numeroMes hace refencia al mes.
	 * @param año hace referencia al año.
	 * @return retorna un importe segùn los costos generados en base a el año y mes seleccionado.
	 */
    public double obtenerSumaCostosPorMes(int numeroMes, int año) {
    	try {
             String sql = "SELECT SUM(costo) AS resultado FROM gastos WHERE MONTH(fechaGasto) = ? AND YEAR(fechaGasto) = ?";

             try (PreparedStatement stm = con.prepareStatement(sql)) {
                 stm.setInt(1, numeroMes);
                 stm.setInt(2, año);

                 try (ResultSet resultSet = stm.executeQuery()) {
                     if (resultSet.next()) {
                         // Obtener el resultado de la suma
                         return resultSet.getDouble("resultado");
                     }
                  }
               } 
             }catch (SQLException e) {
     	        System.err.println("Error al realizar la consulta: " + e.getMessage());
     	    } 
             // En caso de error o si no hay resultados, retornar un valor indicativo
             return 0;   
    }
    
    /**
     * Lista de gastos segun un mes seleccionado.
     * @param numeroDeMes Hace referencia la mes.
     * @param año Hace referencia al año.
     * @return retorna una lista de gastos segun el mes seleccionado.
     */
    public List<Gastos> listarGastosPorMes(int numeroDeMes, int año) {
    	
    	try {
    		
        String sql = "SELECT * FROM gastos WHERE MONTH(fechaGasto) = ? AND YEAR(fechaGasto) = ?";

        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, numeroDeMes);
            stm.setInt(2, año);

            try (ResultSet resultSet = stm.executeQuery()) {
                List<Gastos> gastos = new ArrayList<>();

                while (resultSet.next()) {
                    Gastos gasto = new Gastos(
                            resultSet.getDate("fechaGasto"),
                            resultSet.getString("nombreGasto"),
                            resultSet.getString("descripcion"),
                            resultSet.getDouble("costo")
                    );
                    gastos.add(gasto);
                }
                return gastos;
            }
          }
    	} catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
    	}
    }
    	 
    
}
