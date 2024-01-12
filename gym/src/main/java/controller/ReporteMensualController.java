package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import conexion.Conexion;
import dao.ReporteMensualDAO;
import gym.modelo.Gastos;

public class ReporteMensualController {
	
	private ReporteMensualDAO reporteMensual;
	
	public ReporteMensualController() throws SQLException {
		 Connection connection = new Conexion().recuperaConexion();
		 this.reporteMensual = new ReporteMensualDAO(connection);
	}
	
	public List<Gastos> reporteGastos() throws SQLException {
	     return reporteMensual.reporteGastos();
	}
	
	public List<Gastos> listarGastosPorMes(int numeroMes) throws SQLException {
	     return reporteMensual.listarGastosPorMes(numeroMes);
	}

	public double obtenerSumaCostosPorMes(int numeroMes) throws SQLException {
	     return reporteMensual.obtenerSumaCostosPorMes(numeroMes); 
	}
	
	public List<Map<String, String>> reporteCuotasPagadasPorMes(int numeroMes) throws SQLException{
		return reporteMensual.reporteCuotasPagadasPorMes(numeroMes);
	}
	
	public double obtenerBalancePorMes(int numeroMes) throws SQLException {
		return reporteMensual.obtenerBalancePorMes(numeroMes);
	}

}
