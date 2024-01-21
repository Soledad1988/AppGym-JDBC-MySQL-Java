package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import conexion.Conexion;
import dao.ReporteGastosDAO;
import gym.modelo.Gastos;

public class ReporteGastosController {

	private ReporteGastosDAO reporteGastosDAO;
	 
	public ReporteGastosController() throws SQLException {
		 Connection connection = new Conexion().recuperaConexion();
		 this.reporteGastosDAO = new ReporteGastosDAO(connection);
	}
	
	public List<Gastos> reporteGastos() throws SQLException {
	     return reporteGastosDAO.reporteGastos();
	}
	
	public List<Gastos> listarGastosPorMes(int numeroMes, int año) throws SQLException {
	     return reporteGastosDAO.listarGastosPorMes(numeroMes, año);
	}

	public double obtenerSumaCostosPorMes(int numeroMes, int año) throws SQLException {
	     return reporteGastosDAO.obtenerSumaCostosPorMes(numeroMes, año);
	    
	}
	
}
