package controller;

import java.sql.SQLException;
import java.util.List;

import conexion.Conexion;
import dao.UsuarioDAO;
import gym.modelo.RegistroLogin;
import gym.modelo.Usuario;

public class UsuarioController {

	private UsuarioDAO usuarioDAO;

	 public UsuarioController() throws SQLException {
	        this.usuarioDAO = new UsuarioDAO(Conexion.getInstance().getConnection());
	    }
	 

	    public void guardar(Usuario usuario, String nombreRol) {
	        this.usuarioDAO.guardar(usuario, nombreRol);
	    }
		
	 	public List<Usuario> listar() {
	 	    return this.usuarioDAO.listar();
	 	}
		/*
		public void eliminar(Integer idUsuario) {
			this.usuarioDAO.eliminar(idUsuario);
		}*/

		public void actualizar(String nombreUsuario, String password, Integer idUsuario) {
			this.usuarioDAO.actualizar(nombreUsuario, password, idUsuario);
		}
		
		public void cambiarEstadoHabilitado(int idUsuario, boolean nuevoEstado) {
		    usuarioDAO.cambiarEstadoHabilitado(idUsuario, nuevoEstado);
		}
	 
	 
		//Inicio sesion
	 	public List<Usuario> buscar(String usuario, String password) throws SQLException {
	        return this.usuarioDAO.buscar(usuario, password);
	    }
	 	
	 	//registro de sesisiones
	 	public void registrarInicioSesion(int idUsuario) {
	 		this.usuarioDAO.registrarInicioSesion(idUsuario);
	 	}
	 	
	 	public List<RegistroLogin> obtenerRegistrosLogin() {
	 		return this.usuarioDAO.obtenerRegistrosLogin();
	 	}
	 	
}
