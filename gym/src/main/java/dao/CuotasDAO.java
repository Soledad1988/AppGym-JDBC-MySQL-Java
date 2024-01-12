package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import gym.modelo.Cuota;

public class CuotasDAO {

	final private Connection con;

	public CuotasDAO(Connection con) {
		this.con = con;
	}
	
	public void asignarCuota(Integer idCliente, Double monto, Date fechaPago) throws SQLException {
	    // Primero, insertar la cuota.
	    String sqlCuota = "INSERT INTO cuotas (clienteId, monto, fechaPago) VALUES (?, ?, ?)";
	    System.out.println("Consulta SQL para Cuota: " + sqlCuota);

	    // Segundo, actualizar el estado de pago del cliente.
	    String sqlCliente = "UPDATE clientes SET pago = true WHERE id = ?";
	    System.out.println("Consulta SQL para Cliente: " + sqlCliente);

	    try (PreparedStatement stmCuota = con.prepareStatement(sqlCuota, Statement.RETURN_GENERATED_KEYS);
	         PreparedStatement stmCliente = con.prepareStatement(sqlCliente)) {

	        // Inserción de la cuota
	        stmCuota.setInt(1, idCliente);
	        stmCuota.setDouble(2, monto);
	        stmCuota.setDate(3, new java.sql.Date(fechaPago.getTime()));
	        stmCuota.executeUpdate();

	        // Actualización del estado de pago del cliente
	        stmCliente.setInt(1, idCliente);
	        stmCliente.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    }
	}
	
	 private void transformarResultSetEnCuota(List<Cuota> cuotas, PreparedStatement pstm) throws SQLException {
			try (ResultSet rst = pstm.getResultSet()) {
				while (rst.next()) {
					Cuota cuota = new Cuota(rst.getInt(1), rst.getInt(2), rst.getDouble(3), rst.getDate(4));
					cuotas.add(cuota);
				}
			}				
		}
    
}
