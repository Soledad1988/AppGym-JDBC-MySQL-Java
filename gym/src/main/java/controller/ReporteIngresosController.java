package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import conexion.Conexion;
import dao.CuotasDAO;
import dao.ReporteIngresosDAO;


public class ReporteIngresosController {

	private ReporteIngresosDAO reporteIngresos;
	 
	/*public ReporteIngresosController() throws SQLException {
		 Connection connection = new Conexion().recuperaConexion();
		 this.reporteIngresos = new ReporteIngresosDAO(connection);
	}*/
	
	 public ReporteIngresosController() throws SQLException {
	        this.reporteIngresos = new ReporteIngresosDAO(Conexion.getInstance().getConnection());
	    }
	
	public List<Map<String,String>> reporteClientes() throws SQLException{
		return reporteIngresos.reporteClientes();
	}
	
	public double realizarSumaPorMes(int numeroMes, int año) throws SQLException {
		return ReporteIngresosDAO.realizarSumaPorMes(numeroMes, año);
	}
	 
}
