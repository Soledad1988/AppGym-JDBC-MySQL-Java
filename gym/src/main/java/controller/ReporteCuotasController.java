package controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import conexion.Conexion;
import dao.ReporteCuotasDAO;

public class ReporteCuotasController {

	private ReporteCuotasDAO reporteCuotas;
	 
	 public ReporteCuotasController() throws SQLException {
	        this.reporteCuotas = new ReporteCuotasDAO(Conexion.getInstance().getConnection());
	    }
	
	public List<Map<String, String>> reporteCuotasPorMes(int numeroMes, int año) throws SQLException{
		return reporteCuotas.reporteCuotasPorMesYAnio(numeroMes, año);
	}
}
