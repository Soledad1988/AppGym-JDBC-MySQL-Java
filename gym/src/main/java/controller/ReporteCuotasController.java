package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import conexion.Conexion;
import dao.CuotasDAO;
import dao.ReporteCuotasDAO;
import dao.ReporteIngresosDAO;

public class ReporteCuotasController {

	private ReporteCuotasDAO reporteCuotas;
	 
	/*public ReporteCuotasController() throws SQLException {
		 Connection connection = new Conexion().recuperaConexion();
		 this.reporteCuotas = new ReporteCuotasDAO(connection);
	}*/
	
	 public ReporteCuotasController() throws SQLException {
	        this.reporteCuotas = new ReporteCuotasDAO(Conexion.getInstance().getConnection());
	    }
	
	public List<Map<String, String>> reporteCuotasPorMes(int numeroMes, int año) throws SQLException{
		return reporteCuotas.reporteCuotasPorMesYAnio(numeroMes, año);
	}
}
