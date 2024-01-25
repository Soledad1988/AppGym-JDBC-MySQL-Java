package controller;

import java.sql.SQLException;
import java.util.List;

import conexion.Conexion;
import dao.GastoDAO;
import gym.modelo.Gastos;

public class GastosController {
	
		private GastoDAO gastoDAO;
		
		 public GastosController() throws SQLException {
		        this.gastoDAO = new GastoDAO(Conexion.getInstance().getConnection());
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
