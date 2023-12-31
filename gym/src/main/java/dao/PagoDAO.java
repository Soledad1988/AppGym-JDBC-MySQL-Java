package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PagoDAO {

	final private Connection con;

	public PagoDAO(Connection con) {
		this.con = con;
	}
	
	
	public void pagos() {
		// Consulta para obtener clientes y sus pagos
	     String consulta = "SELECT clientes.fechaAlta, clientes.nombre, clientes.apellido, " +
	                    "pagos.monto, pagos.periodoPago FROM clientes " +
	                    "LEFT JOIN pagos ON clientes.id = pagos.cliente_id";
	     
	    try (PreparedStatement stm = con.prepareStatement(consulta)){
	    	try (ResultSet rst = stm.executeQuery(consulta)) {
	 
	                while (rst.next()) {
	                    // Procesar resultados
	                   // Integer idCliente = resultSet.getInt("clientes.id");
	                	Date fechaAlta = rst.getDate("clientes.fechaAlta");
	                    String nombreCliente = rst.getString("clientes.nombre");
	                    String apellidoCliente = rst.getString("clientes.apellido");
	                    Double montoPago = rst.getDouble("pagos.monto");
	                    String periodoPago = rst.getString("pagos.fecha_pago");

	                    System.out.println("Cliente ID: " + fechaAlta + ", Nombre: " + nombreCliente +
	                            ", Apellido: " + apellidoCliente + ", Dirección: " +
	                            ", Monto del Pago: " + montoPago + ", Fecha del Pago: " + periodoPago);
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	
	
}
