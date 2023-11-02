package controller;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import conexion.Conexion;
import dao.ClienteDAO;
import gym.modelo.Cliente;


public class ClienteController {

	private ClienteDAO clienteDAO;
	 
	 public ClienteController() {
		 Connection connection = new Conexion().recuperarConexion();
		 this.clienteDAO = new ClienteDAO(connection);
		}
	 
		public void guardar(Cliente cliente) {
			this.clienteDAO.guardar(cliente);
		}
		public List<Cliente> listar() {
			return this.clienteDAO.listar();
		}
		
		public void actualizar(String nombre, String apellido, String direccion, Double precio, Integer id) {
			this.clienteDAO.actualizar(nombre, apellido, direccion, precio, id);
		}
		
		public void eliminar(Integer id) {
			this.clienteDAO.eliminar(id);
		}

}
