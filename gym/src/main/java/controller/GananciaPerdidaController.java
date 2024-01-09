package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import conexion.Conexion;
import dao.ReporteMensualDAO;
import gym.modelo.Gastos;

public class GananciaPerdidaController {
	
	private ReporteMensualDAO reporteMensual;
	
	public GananciaPerdidaController() throws SQLException {
		 Connection connection = new Conexion().recuperaConexion();
		 this.reporteMensual = new ReporteMensualDAO(connection);
	}
	
	public List<Gastos> reporteGastos() throws SQLException {
	     return reporteMensual.reporteGastos();
	}
	
	public List<Gastos> obtenerGastosPorMes(int numeroMes) throws SQLException {
	     return reporteMensual.listarGastosPorMes(numeroMes);
	}

	public double obtenerSumaCostosPorMes(int numeroMes) throws SQLException {
	     return reporteMensual.obtenerSumaCostosPorMes(numeroMes);
	    
	}
	
	
	 

}
