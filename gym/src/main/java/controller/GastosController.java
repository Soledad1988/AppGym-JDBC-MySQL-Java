package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexion.Conexion;
import dao.GastoDAO;
import gym.modelo.Gastos;

public class GastosController {

	private GastoDAO gastoDAO;
	
	public GastosController() throws SQLException {
		 Connection connection = new Conexion().recuperaConexion();
		 this.gastoDAO = new GastoDAO(connection);
		}
	 
		public void guardar(Gastos gasto) {
			this.gastoDAO.guardar(gasto);
		}
		
		public List<Gastos> listar() {
			return this.gastoDAO.listar();
		}
		
		public void eliminar(Integer id) {
			this.gastoDAO.eliminar(id);
		}

		public void actualizar(String nombreGasto, String tipo, Double costo, Integer idGasto) {
			this.gastoDAO.actualizar(nombreGasto, tipo, costo, idGasto);
		}
  		
}
