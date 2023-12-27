package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import conexion.Conexion;
import dao.UsuarioDAO;
import gym.modelo.Usuario;

public class UsuarioController {

	private UsuarioDAO usuarioDAO;
	 
	 public UsuarioController() throws SQLException {
		 Connection connection = new Conexion().recuperaConexion();
		 this.usuarioDAO = new UsuarioDAO(connection);
		}
	 
	 	public List<Usuario> buscar(String usuario, String password) throws SQLException {
	        return this.usuarioDAO.buscar(usuario, password);
	    }
}
