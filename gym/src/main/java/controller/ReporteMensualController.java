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
	
	public List<Gastos> listarGastosPorMes(int numeroMes, int año) throws SQLException {
	     return reporteMensual.listarGastosPorMes(numeroMes, año);
	}

	public double obtenerSumaCostosPorMes(int numeroMes, int año) throws SQLException {
	     return reporteMensual.obtenerSumaCostosPorMes(numeroMes, año); 
	}
	
	public List<Map<String, String>> reporteCuotasPagadasPorMes(int numeroMes, int año) throws SQLException{
		return reporteMensual.reporteCuotasPagadasPorMes(numeroMes, año);
	}
	
	public double obtenerBalancePorMes(int numeroMes, int año) throws SQLException {
		return reporteMensual.obtenerBalancePorMes(numeroMes, año);
	}

}
