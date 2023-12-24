package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexion.Conexion;
import dao.ClienteDAO;
import dao.GastoDAO;
import gym.modelo.Cliente;
import gym.modelo.Cuota;
import gym.modelo.Gastos;

public class ReporteController {
	
	
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

	            // Establecer el parámetro del mes en la consulta
	            statement.setInt(1, numeroMes);

	            // Ejecutar la consulta
	            resultSet = statement.executeQuery();

	            // Procesar el resultado
	            if (resultSet.next()) {
	                // Obtener el resultado de la suma
	                return resultSet.getDouble("resultado");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            // Asegurar el cierre del ResultSet y el Statement
	            if (resultSet != null) {
	                resultSet.close();
	            }
	        }

	        // En caso de error, retornar un valor indicativo
	        return -1;
	    }
	    
	 // Listar reporte de gastos
	    public List<Gastos> reporteGastos() throws SQLException {
	        Conexion factory = new Conexion();
	        final Connection con = factory.recuperaConexion();

	        final PreparedStatement statement = con.prepareStatement("SELECT periodoGasto, nombreGasto, descripcion, costo FROM gastos");

	        statement.execute();

	        ResultSet resultSet = statement.getResultSet();

	        List<Gastos> resultado = new ArrayList<>();

	        // Leemos el contenido para agregarlo a un listado de objetos Gastos
	        while (resultSet.next()) {
	            Gastos gasto = new Gastos();
	            gasto.setPeriodoGasto(resultSet.getString("periodoGasto"));
	            gasto.setNombreGasto(resultSet.getString("nombreGasto"));
	            gasto.setDescripcion(resultSet.getString("descripcion"));
	            gasto.setCosto(resultSet.getDouble("costo"));

	            resultado.add(gasto);
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
	    
	    
	    public List<Cliente> listarClientesPorMes2(int numeroMes) throws SQLException {
	    	
	    	Conexion factory = new Conexion();
			final Connection con = factory.recuperaConexion();
	        // Aquí asumimos que la fecha de ingreso está almacenada en la columna 'fecha_alta'
	        String consultaSQL = "SELECT * FROM clientes WHERE MONTH(fechaAlta) = ?";

	        try (PreparedStatement preparedStatement = con.prepareStatement(consultaSQL)) {

	            preparedStatement.setInt(1, numeroMes);

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                List<Cliente> clientes = new ArrayList<>();

	                while (resultSet.next()) {
	                    // Asumimos que tienes un constructor de Cliente que acepta los valores de la consulta
	                    Cliente cliente = new Cliente(
	                            resultSet.getDate("fechaAlta"),
	                            resultSet.getString("nombre"),
	                            resultSet.getString("apellido"),
	                            resultSet.getString("telefono")
	                    );
	                    clientes.add(cliente);
	                }

	                return clientes;
	            }
	        } catch (SQLException e) {
	            // Manejo de excepciones
	            e.printStackTrace();
	            return Collections.emptyList();
	        }
	    }
	    
	    public double obtenerSumaCostosPorMes(int numeroMes) throws SQLException {
	        Conexion factory = new Conexion();
	             final Connection con = factory.recuperaConexion();

	            String nombreMes = obtenerNombreMes(numeroMes);

	            String consulta = "SELECT SUM(costo) AS resultado FROM gastos WHERE UPPER(periodoGasto) = UPPER(?)";

	            try (PreparedStatement preparedStatement = con.prepareStatement(consulta)) {
	                preparedStatement.setString(1, nombreMes);

	                try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                    if (resultSet.next()) {
	                        // Obtener el resultado de la suma
	                        return resultSet.getDouble("resultado");
	                    }
	                }
	            } catch (SQLException e) {
	                // Manejo de excepciones
	                e.printStackTrace();
	            }

	            // En caso de error o si no hay resultados, retornar un valor indicativo
	            return 0;
	        
	    }
	    
	    public List<Gastos> listarGastosPorMes(int numeroDeMes) throws SQLException {
	        Conexion factory = new Conexion();
	             final Connection con = factory.recuperaConexion();

	            String consulta = "SELECT * FROM gastos WHERE UPPER(periodoGasto) = UPPER(?)";

	            try (PreparedStatement preparedStatement = con.prepareStatement(consulta)) {
	                // Obtén el nombre del mes según el número proporcionado
	                String nombreMes = obtenerNombreMes(numeroDeMes);

	                preparedStatement.setString(1, nombreMes);

	                try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                    List<Gastos> gastos = new ArrayList<>();

	                    while (resultSet.next()) {
	                        Gastos gasto = new Gastos(
	                                resultSet.getString("periodoGasto"),
	                                resultSet.getString("nombreGasto"),
	                                resultSet.getString("descripcion"),
	                                resultSet.getDouble("costo")
	                        );
	                        gastos.add(gasto);
	                    }

	                    return gastos;
	                }
	            } catch (SQLException e) {
	                // Manejo de excepciones
	                e.printStackTrace();
	                return Collections.emptyList();
	            }
	        
	    }
	    
	 // Método auxiliar para obtener el nombre del mes según su número
	    private String obtenerNombreMes(int numeroDeMes) {
	        String[] nombresMeses = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};
	        if (numeroDeMes >= 1 && numeroDeMes <= nombresMeses.length) {
	            return nombresMeses[numeroDeMes - 1];
	        } else {
	            throw new IllegalArgumentException("Número de mes no válido: " + numeroDeMes);
	        }
	    }
	    
	    
	    /*+++++++++++++++++++++++++++++++++++++++++++++++++*/
	  //listar reporte
	    
	    public List<Map<String, String>> reporteCuotasPorMes(int numeroMes) throws SQLException {
	        Conexion factory = new Conexion();
	        final Connection con = factory.recuperaConexion();

	        // Consulta para obtener clientes y sus pagos en el mes especificado
	        final String consulta = "SELECT clientes.nombre, clientes.apellido, cuotas.monto, cuotas.fechaPago " +
	                                "FROM clientes LEFT JOIN cuotas ON clientes.id = cuotas.clienteId " +
	                                "WHERE MONTH(cuotas.fechaPago) = ?";

	        try (PreparedStatement statement = con.prepareStatement(consulta)) {
	            statement.setInt(1, numeroMes);
	            statement.execute();

	            ResultSet resultSet = statement.getResultSet();

	            List<Map<String, String>> resultado = new ArrayList<>();

	            // Leemos el contenido para agregarlo a un listado
	            while (resultSet.next()) {
	                Map<String, String> fila = new HashMap<>();
	                fila.put("Fecha Pago", String.valueOf(resultSet.getDate("cuotas.fechaPago")));
	                fila.put("Nombre", resultSet.getString("clientes.nombre"));
	                fila.put("Apellido", resultSet.getString("clientes.apellido"));
	                fila.put("Monto", String.valueOf(resultSet.getDouble("cuotas.monto")));

	                resultado.add(fila);
	            }

	            return resultado;
	        }
	    }
}
