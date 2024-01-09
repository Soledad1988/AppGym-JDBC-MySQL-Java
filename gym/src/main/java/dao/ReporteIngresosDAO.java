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
				Conexion factory = new Conexion();
				final Connection con = factory.recuperaConexion();
				  
					final PreparedStatement statement = con.prepareStatement("SELECT fechaAlta, nombre, apellido, telefono FROM clientes");
			        
					statement.execute();

			        ResultSet resultSet = statement.getResultSet();

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
			
		    
		    public static double realizarSumaPorMes(int numeroMes) throws SQLException {
		        Conexion factory = new Conexion();
		        final Connection con = factory.recuperaConexion();
		        ResultSet resultSet = null;

		        try {
		            String consulta = "SELECT SUM(monto) AS resultado FROM cuotas WHERE MONTH(fechaPago) = ?";
		            PreparedStatement statement = con.prepareStatement(consulta);
		            
		            statement.setInt(1, numeroMes);

		            // Ejecutar la consulta
		            resultSet = statement.executeQuery();

		            // Procesar el resultado
		            if (resultSet.next()) {
		                return resultSet.getDouble("resultado");
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        } finally {
		          
		            if (resultSet != null) {
		                resultSet.close();
		            }
		        }

		        // En caso de error, retornar un valor indicativo
		        return -1;
		    }
}
