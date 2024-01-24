package controller;

import java.sql.Date;
import java.sql.SQLException;

import conexion.Conexion;
import dao.CuotasDAO;

public class CuotaController {
	
	private CuotasDAO cuotaDAO;
	
	 public CuotaController() throws SQLException {
	        this.cuotaDAO = new CuotasDAO(Conexion.getInstance().getConnection());
	    }

    public void asignarCuota(Integer idCliente, Double montoPago, Date fechaPago){
        cuotaDAO.asignarCuota(idCliente, montoPago, fechaPago);
    }

}
