package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexion.Conexion;
import dao.ClienteDAO;

public class ReporteController {
	
	private ClienteDAO clienteDAO;
	
	//listar reporte
		public List<Map<String,String>> reporteClientes() throws SQLException {
			Conexion factory = new Conexion();
			final Connection con = factory.recuperaConexion();
			  
				final PreparedStatement statement = con.prepareStatement("SELECT fechaAlta, nombre, apellido, precio FROM clientes");
		        
				statement.execute();

		        ResultSet resultSet = statement.getResultSet();

		        List<Map<String, String>> resultado = new ArrayList<>();

		      //leemos el contendio para agregrlo a un listado
		        while (resultSet.next()) {
		            Map<String, String> fila = new HashMap<>();
		            fila.put("fechaAlta", String.valueOf(resultSet.getDate("fechaAlta")));
		            fila.put("nombre", resultSet.getString("nombre"));
		            fila.put("apellido", resultSet.getString("apellido"));
		            fila.put("precio", String.valueOf(resultSet.getDouble("precio")));

		            resultado.add(fila);
		        }
				
			return resultado;
		
		}
		
		
	    public static double realizarSuma() throws SQLException {
	    	
	    	Conexion factory = new Conexion();
			final Connection con = factory.recuperaConexion();
			 ResultSet resultSet = null;

	        try {
	            String consulta = "SELECT SUM(precio) AS resultado FROM clientes";
	            PreparedStatement statement = con.prepareStatement(consulta);

	            // Ejecutar la consulta
	            resultSet = statement.executeQuery();

	            // Procesar el resultado
	            if (resultSet.next()) {
	                // Obtener el resultado de la suma
	                return resultSet.getDouble("resultado");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } 

	        // En caso de error, retornar un valor indicativo
	        return -1;
	    }
	    
		//listar reporte
  		public List<Map<String,String>> reporteGastos() throws SQLException {
  			Conexion factory = new Conexion();
  			final Connection con = factory.recuperaConexion();
  			  
  				final PreparedStatement statement = con.prepareStatement("SELECT periodoGasto, nombreGasto, tipo,"
  						+ " costo FROM gastos");
  		        
  				statement.execute();

  		        ResultSet resultSet = statement.getResultSet();

  		        List<Map<String, String>> resultado = new ArrayList<>();

  		      //leemos el contendio para agregrlo a un listado
  		        while (resultSet.next()) {
  		            Map<String, String> fila = new HashMap<>();
  		            fila.put("periodoGasto", resultSet.getString("periodoGasto"));
  		            fila.put("nombreGasto", resultSet.getString("nombreGasto"));
  		            fila.put("tipo", resultSet.getString("tipo"));
  		            fila.put("costo", String.valueOf(resultSet.getDouble("costo")));

  		            resultado.add(fila);
  		        }
  				
  			return resultado;
  		
  		}
	    
	    public static double realizarSumaGastos() throws SQLException {
	    	
	    	Conexion factory = new Conexion();
			final Connection con = factory.recuperaConexion();
			 ResultSet resultSet = null;

	        try {
	            String consulta = "SELECT SUM(costo) AS resultado FROM gastos";
	            PreparedStatement statement = con.prepareStatement(consulta);

	            // Ejecutar la consulta
	            resultSet = statement.executeQuery();

	            // Procesar el resultado
	            if (resultSet.next()) {
	                // Obtener el resultado de la suma
	                return resultSet.getDouble("resultado");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } 

	        // En caso de error, retornar un valor indicativo
	        return -1;
	    }

}
