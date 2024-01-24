package controller;

import java.sql.SQLException;
import java.util.List;

import conexion.Conexion;
import dao.ClienteDAO;
import gym.modelo.Cliente;


public class ClienteController {

	    private ClienteDAO clienteDAO;
	 
	    public ClienteController() throws SQLException {
	        this.clienteDAO = new ClienteDAO(Conexion.getInstance().getConnection());
	    }
	 
		public void guardar(Cliente cliente) {
			this.clienteDAO.guardar(cliente);
		}
		
		public List<Cliente> listar() {
			return this.clienteDAO.listar();
		}
		
		public void eliminar(Integer id) {
			this.clienteDAO.eliminar(id);
		}

		public void actualizar(String nombre, String apellido, String direccion, Integer id) {
			this.clienteDAO.actualizar(nombre, apellido, direccion, id);
		}
		 
		public List<Cliente> buscarPorApellido(String apellido){
		        return this.clienteDAO.buscarPorApellido(apellido);
		}

}
