package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gym.modelo.Cliente;
import gym.modelo.Rol;
import gym.modelo.Usuario;

public class UsuarioDAO {

	final private Connection con;

	public UsuarioDAO(Connection con) {
		this.con = con;
	}
	
	public List<Usuario> buscar2(String nombreUsuario, String password) throws SQLException {
	    List<Usuario> usuarios = new ArrayList<>();
	    String sql = "SELECT * FROM usuarios WHERE nombreUsuario = ? AND contrasena = ?";
	    try (PreparedStatement statement = con.prepareStatement(sql)) {
	        statement.setString(1, nombreUsuario);
	        statement.setString(2, password);

	        try (ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                String usuario = resultSet.getString("nombreUsuario");
	                String pass = resultSet.getString("contrasena");

	                Usuario usu = new Usuario();
	                usu.setNombreUsuario(usuario);
	                usu.setContrasena(pass);

	                usuarios.add(usu);
	            }
	        }
	    }
		return usuarios;
	    
	 }
	
	public List<Usuario> buscar(String usuario, String password) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT u.idUsuario, u.nombreUsuario, u.contrasena, r.nombreRol " +
                     "FROM usuarios u " +
                     "JOIN usuarios_roles ur ON u.idUsuario = ur.idUsuario " +
                     "JOIN roles r ON ur.idRol = r.idRol " +
                     "WHERE u.nombreUsuario = ? AND u.contrasena = ?";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, usuario);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                Map<Integer, Usuario> usuariosMap = new HashMap<>();

                while (resultSet.next()) {
                    Integer idUsuario = resultSet.getInt("idUsuario");
                    String nombreUsuario = resultSet.getString("nombreUsuario");
                    String contrasena = resultSet.getString("contrasena");
                    String nombreRol = resultSet.getString("nombreRol");

                    Usuario usu = usuariosMap.computeIfAbsent(idUsuario,
                            k -> new Usuario(idUsuario, nombreUsuario, contrasena));

                    Set<Rol> roles = usu.getRoles();
                    if (roles == null) {
                        roles = new HashSet<>();
                        usu.setRoles(roles);
                    }

                    roles.add(Rol.valueOf(nombreRol)); // Asumiendo que el nombreRol coincide con el nombre del Enum
                }

                usuarios.addAll(usuariosMap.values());
            }
        }

        return usuarios;
    }
	
	public Set<Rol> obtenerRolesDesdeBaseDeDatos(Integer idUsuario) throws SQLException {
	    Set<Rol> roles = new HashSet<>();

	    String sql = "SELECT r.nombreRol " +
	                 "FROM roles r " +
	                 "JOIN usuarios_roles ur ON r.idRol = ur.idRol " +
	                 "WHERE ur.idUsuario = ?";

	    try (PreparedStatement statement = con.prepareStatement(sql)) {
	        statement.setInt(1, idUsuario);

	        try (ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                String nombreRol = resultSet.getString("nombreRol");
	                roles.add(Rol.valueOf(nombreRol)); // Asumiendo que el nombreRol coincide con el nombre del Enum
	            }
	        }
	    }

	    return roles;
	}

}
