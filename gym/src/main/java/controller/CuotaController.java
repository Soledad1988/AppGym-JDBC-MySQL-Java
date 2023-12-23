package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import conexion.Conexion;
import dao.CuotasDAO;

public class CuotaController {
	
	private CuotasDAO cuotaDAO;
	 
	 public CuotaController() throws SQLException {
		 Connection connection = new Conexion().recuperaConexion();
		 this.cuotaDAO = new CuotasDAO(connection);
		}
	 
	 public void asignarCuota(Integer clienteId, Double monto, java.util.Date fechaPago) throws SQLException {
	        cuotaDAO.asignarCuota(clienteId, monto, fechaPago);
	    }
	 
	 public void asignarPagos(Integer idCliente, Double montoPago, Date fechaPago) throws SQLException {
	        cuotaDAO.asignarPago(idCliente, montoPago, fechaPago);
	    }

}
