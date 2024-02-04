package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Esta clase proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * en la tabla de clientes de la base de datos.
 */
public class CuotasDAO {

	/**
     * La conexión a la base de datos utilizada por este DAO.
     */
	final private Connection con;

	/**
	 * Constructor que inicializa una nueva CuotasDAO con la conexión especificada.
	 * @param con La conexión a la base de datos que se utilizará para las operaciones del DAO.
	 */
	public CuotasDAO(Connection con) {
		this.con = con;
	}
	
	/**
	 * Asignación de cuotas a clientes previamente registrados y actualiza su estado de pago.
	 * @param idCliente Identificador del cliente que abonó la cuota
	 * @param monto Monto abonado
	 * @param fechaPago Fecha de pago.
	 */
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
