package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import conexion.Conexion;
import dao.UsuarioDAO;
import gym.modelo.Cliente;
import gym.modelo.Usuario;

public class UsuarioController {

	private UsuarioDAO usuarioDAO;
	 
	 public UsuarioController() throws SQLException {
		 Connection connection = new Conexion().recuperaConexion();
		 this.usuarioDAO = new UsuarioDAO(connection);
		}
	 

	    public void guardar(Usuario usuario, String nombreRol) {
	        this.usuarioDAO.guardar(usuario, nombreRol);
	    }
		
	 	public List<Usuario> listar() {
	 	    return this.usuarioDAO.listar();
	 	}
		
		public void eliminar(Integer idUsuario) {
			this.usuarioDAO.eliminar(idUsuario);
		}

		public void actualizar(String nombreUsuario, String password, Integer idUsuario) {
			this.usuarioDAO.actualizar(nombreUsuario, password, idUsuario);
		}
	 
	 
	 	public List<Usuario> buscar(String usuario, String password) throws SQLException {
	        return this.usuarioDAO.buscar(usuario, password);
	    }
}
