package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexion.Conexion;
import gym.modelo.Gastos;

public class ReporteMensualDAO {
	

	final private Connection con;

	public ReporteMensualDAO(Connection con) {
		this.con = con;
	}
	
	public List<Gastos> reporteGastos() throws SQLException {
       // Conexion factory = new Conexion();
       // final Connection con = factory.recuperaConexion();
		Conexion factory = Conexion.getInstance();

        final String sql = "SELECT fechaGasto, nombreGasto, descripcion, costo FROM gastos";

        try (Connection con = factory.getConnection();
        		PreparedStatement stm = con.prepareStatement(sql)) {
        	
	        stm.execute();
	
	        ResultSet resultSet = stm.getResultSet();
	
	        List<Gastos> resultado = new ArrayList<>();
	
	        // Leemos el contenido para agregarlo a un listado de objetos Gastos
	        while (resultSet.next()) {
	            Gastos gasto = new Gastos();
	            gasto.setFechaGasto(resultSet.getDate("fechaGasto"));
	            gasto.setNombreGasto(resultSet.getString("nombreGasto"));
	            gasto.setDescripcion(resultSet.getString("descripcion"));
	
	            resultado.add(gasto);
	        }

        return resultado;
        
        }
    }
    
	public List<Gastos> listarGastosPorMes(int numeroDeMes, int año) throws SQLException {
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
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
	
    
	public double obtenerSumaCostosPorMes(int numeroMes, int año) throws SQLException {
       // Conexion factory = new Conexion();
         //    final Connection con = factory.recuperaConexion();
		//Conexion factory = Conexion.getInstance();

             String consulta = "SELECT SUM(costo) AS resultado FROM gastos WHERE MONTH(fechaGasto) = ? AND YEAR(fechaGasto) = ?";

             try (PreparedStatement preparedStatement = con.prepareStatement(consulta)) {
                 preparedStatement.setInt(1, numeroMes);
                 preparedStatement.setInt(2, año);

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
    
  //Listado de cuotas pagadas por mes
   /* public List<Map<String, String>> reporteCuotasPagadasPorMes(int numeroMes) throws SQLException {
        Conexion factory = new Conexion();
        final Connection con = factory.recuperaConexion();

        final String consulta = "SELECT clientes.nombre, clientes.apellido, " +
                "cuotas.monto, cuotas.fechaPago " +
                "FROM clientes " +
                "JOIN cuotas ON clientes.id = cuotas.clienteId " +
                "WHERE MONTH(cuotas.fechaPago) = ? AND cuotas.monto > 0";
        
        try (PreparedStatement statement = con.prepareStatement(consulta)) {
            statement.setInt(1, numeroMes);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();

            List<Map<String, String>> resultado = new ArrayList<>();

            while (resultSet.next()) {
                Map<String, String> fila = new HashMap<>();
                Date fechaPago = resultSet.getDate("fechaPago");
                fila.put("Fecha Pago", (fechaPago != null) ? fechaPago.toString() : "N/A");
                fila.put("Nombre", resultSet.getString("nombre"));
                fila.put("Apellido", resultSet.getString("apellido"));
                fila.put("Monto", String.valueOf(resultSet.getDouble("monto")));
                fila.put("Estado", "Pagado");

                resultado.add(fila);
            }

            return resultado;
        }
    }*/
    
    public List<Map<String, String>> reporteCuotasPagadasPorMes(int numeroMes, int ano) throws SQLException {
       // Conexion factory = new Conexion();
       // final Connection con = factory.recuperaConexion();
    	//Conexion factory = Conexion.getInstance();

        final String consulta = "SELECT clientes.nombre, clientes.apellido, " +
                "cuotas.monto, cuotas.fechaPago " +
                "FROM clientes " +
                "JOIN cuotas ON clientes.id = cuotas.clienteId " +
                "WHERE MONTH(cuotas.fechaPago) = ? AND YEAR(cuotas.fechaPago) = ? AND cuotas.monto > 0";

        try (PreparedStatement statement = con.prepareStatement(consulta)) {
            statement.setInt(1, numeroMes);
            statement.setInt(2, ano);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();

            List<Map<String, String>> resultado = new ArrayList<>();

            while (resultSet.next()) {
                Map<String, String> fila = new HashMap<>();
                Date fechaPago = resultSet.getDate("fechaPago");
                fila.put("Fecha Pago", (fechaPago != null) ? fechaPago.toString() : "N/A");
                fila.put("Nombre", resultSet.getString("nombre"));
                fila.put("Apellido", resultSet.getString("apellido"));
                fila.put("Monto", String.valueOf(resultSet.getDouble("monto")));
                fila.put("Estado", "Pagado");

                resultado.add(fila);
            }

            return resultado;
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
    
    public double obtenerTotalCuotasPagadasPorMes(int numeroMes, int año) throws SQLException {
        double totalCuotas = 0;

        //Conexion factory = new Conexion();
        //final Connection con = factory.recuperaConexion();
       // Conexion factory = Conexion.getInstance();
        
        // Actualizar la consulta para incluir el filtrado por año
        String consulta = "SELECT SUM(monto) AS total FROM cuotas WHERE MONTH(fechaPago) = ? AND YEAR(fechaPago) = ? AND monto > 0";
        try (PreparedStatement statement = con.prepareStatement(consulta)) {
            statement.setInt(1, numeroMes);
            statement.setInt(2, año); // Añadir el año como segundo parámetro

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    totalCuotas = resultSet.getDouble("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalCuotas;
    }
    
    
    public double obtenerBalancePorMes(int numeroMes, int año) throws SQLException {
        double totalGastos = obtenerSumaCostosPorMes(numeroMes, año);
        double totalCuotasPagadas = obtenerTotalCuotasPagadasPorMes(numeroMes, año);

        // Restar los gastos del total de cuotas pagadas
        return totalCuotasPagadas - totalGastos;
    }
}
