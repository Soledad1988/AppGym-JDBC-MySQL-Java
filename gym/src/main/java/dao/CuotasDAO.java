package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CuotasDAO {

	final private Connection con;

	public CuotasDAO(Connection con) {
		this.con = con;
	}
	
	public void asignarCuota(Integer idCliente, Double monto, Date fechaPago){
	    // Primero, insertar la cuota.
	    String sqlCuota = "INSERT INTO cuotas (clienteId, monto, fechaPago) VALUES (?, ?, ?)";

	    // Segundo, actualizar el estado de pago del cliente.
	    String sqlCliente = "UPDATE clientes SET pago = true WHERE id = ?";

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
	    	System.err.println("Error al asignar cuota: " + e.getMessage());
	    }
	}
	
    
}
