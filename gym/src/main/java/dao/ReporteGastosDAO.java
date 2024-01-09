package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import conexion.Conexion;
import gym.modelo.Gastos;

public class ReporteGastosDAO {
	
	final private Connection con;

	public ReporteGastosDAO(Connection con) {
		this.con = con;
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

            resultado.add(gasto);
        }

        return resultado;
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
    
}
