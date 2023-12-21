package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.Statement;

public class PagoDAO {

	final private Connection con;

	public PagoDAO(Connection con) {
		this.con = con;
	}
	
	
	public void pagos() {
		// Consulta para obtener clientes y sus pagos
	     String consulta = "SELECT clientes.id, clientes.nombre, clientes.apellido, clientes.direccion, " +
	                    "pagos.monto, pagos.fecha_pago FROM clientes " +
	                    "LEFT JOIN pagos ON clientes.id = pagos.cliente_id";
	     
	    try (PreparedStatement stm = con.prepareStatement(consulta)){
	    	try (ResultSet resultSet = stm.executeQuery(consulta)) {
	 
	                while (resultSet.next()) {
	                    // Procesar resultados
	                    int idCliente = resultSet.getInt("clientes.id");
	                    String nombreCliente = resultSet.getString("clientes.nombre");
	                    String apellidoCliente = resultSet.getString("clientes.apellido");
	                    String direccionCliente = resultSet.getString("clientes.direccion");

	                    double montoPago = resultSet.getDouble("pagos.monto");
	                    String periodoPago = resultSet.getString("pagos.fecha_pago");

	                    System.out.println("Cliente ID: " + idCliente + ", Nombre: " + nombreCliente +
	                            ", Apellido: " + apellidoCliente + ", Dirección: " + direccionCliente +
	                            ", Monto del Pago: " + montoPago + ", Fecha del Pago: " + periodoPago);
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	
	
}
