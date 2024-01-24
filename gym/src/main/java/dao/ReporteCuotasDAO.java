package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexion.Conexion;

public class ReporteCuotasDAO {
	
	final private Connection con;

	public ReporteCuotasDAO(Connection con) {
		this.con = con;
	}

	//Listado de cuotas asignadas por mes y año
	public List<Map<String, String>> reporteCuotasPorMesYAnio(int numeroMes, int anio) throws SQLException {
	    //Conexion factory = new Conexion();
	    //final Connection con = factory.recuperaConexion();
		 Conexion factory = Conexion.getInstance();

	    final String sql = "SELECT clientes.nombre, clientes.apellido, " +
	                            "IFNULL(cuotas.monto, 0) AS monto, cuotas.fechaPago " +
	                            "FROM clientes " +
	                            "LEFT JOIN cuotas ON clientes.id = cuotas.clienteId AND MONTH(cuotas.fechaPago) = ? AND YEAR(cuotas.fechaPago) = ?";

	    try (PreparedStatement stm = con.prepareStatement(sql)) {
	        stm.setInt(1, numeroMes);
	        stm.setInt(2, anio);
	        stm.execute();

	        ResultSet resultSet = stm.getResultSet();

	        List<Map<String, String>> resultado = new ArrayList<>();

	        while (resultSet.next()) {
	            Map<String, String> fila = new HashMap<>();
	            Date fechaPago = resultSet.getDate("fechaPago");
	            fila.put("Fecha Pago", (fechaPago != null) ? fechaPago.toString() : "N/A");
	            fila.put("Nombre", resultSet.getString("nombre"));
	            fila.put("Apellido", resultSet.getString("apellido"));
	            fila.put("Monto", String.valueOf(resultSet.getDouble("monto")));

	            // Lógica para determinar si el cliente ha pagado o debe
	            if (resultSet.getDouble("monto") > 0) {
	                fila.put("Estado", "Pagado");
	            } else {
	                fila.put("Estado", "Debe");
	            }

	            resultado.add(fila);
	        }

	        return resultado;
	    }
	}
    
}
