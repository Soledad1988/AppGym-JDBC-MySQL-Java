package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CuotasDAO {

	final private Connection con;

	public CuotasDAO(Connection con) {
		this.con = con;
	}
	
	public void asignarCuota(Integer clienteId, Double monto, java.util.Date fechaPago) throws SQLException {
        String query = "INSERT INTO Cuotas (clienteId, monto, fechaPago) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, clienteId);
            preparedStatement.setDouble(2, monto);
            preparedStatement.setDate(3, new java.sql.Date(fechaPago.getTime()));
            preparedStatement.executeUpdate();
        }
    }
	
	public void asignarPago(Integer idCliente, Double monto, Date fechaPago) throws SQLException {
	    // Modifica tu consulta SQL para incluir el monto, la fecha y el periodo
	    String sql = "INSERT INTO pagos (clienteId, monto, fechaPago) VALUES (?, ?, ?)";
	    try (PreparedStatement statement = con.prepareStatement(sql)) {
	        statement.setInt(1, idCliente);
	        statement.setDouble(2, monto);
	        statement.setDate(3, fechaPago);

	        statement.executeUpdate();
	    }
	}
    
}
