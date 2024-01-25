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

public class ReporteIngresosDAO {
	
	final private Connection con;

	public ReporteIngresosDAO(Connection con) {
		this.con = con;
	}

	//listar reporte
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
