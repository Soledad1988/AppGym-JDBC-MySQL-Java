package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gym.modelo.Rol;
import gym.modelo.Usuario;

public class UsuarioDAO {

	final private Connection con;

	public UsuarioDAO(Connection con) {
		this.con = con;
	}
	
	public void guardar2(Usuario usuario) {
		try {
			String sql = "INSERT INTO usuarios (nombreUsuario, contrasena) VALUES (?, ?)";
			try (PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stm.setString(1, usuario.getNombreUsuario());
				stm.setString(2, usuario.getContrasena());
				stm.execute();
				try (ResultSet rst = stm.getGeneratedKeys()) {
					while (rst.next()) {
						usuario.setIdUsuario(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
	
	 
	public void guardar(Usuario usuario, String nombreRol) {
        try {
            String sqlUsuario = "INSERT INTO usuarios (nombreUsuario, contrasena) VALUES (?, ?)";
            try (PreparedStatement stmUsuario = con.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                stmUsuario.setString(1, usuario.getNombreUsuario());
                stmUsuario.setString(2, usuario.getContrasena());
                stmUsuario.executeUpdate();

                try (ResultSet rstUsuario = stmUsuario.getGeneratedKeys()) {
                    while (rstUsuario.next()) {
                        usuario.setIdUsuario(rstUsuario.getInt(1));
                    }
                }
            }

            String sqlRoles = "INSERT INTO usuarios_roles (idUsuario, idRol) VALUES (?, ?)";
            try (PreparedStatement stmRoles = con.prepareStatement(sqlRoles)) {
                int idRol = obtenerIdRol(nombreRol); // Método para obtener el ID del rol
                stmRoles.setInt(1, usuario.getIdUsuario());
                stmRoles.setInt(2, idRol);
                stmRoles.executeUpdate();
            
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int obtenerIdRol(String nombreRol) throws SQLException {
        String sql = "SELECT idRol FROM roles WHERE nombreRol = ?";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, nombreRol);

            try (ResultSet rst = stm.executeQuery()) {
                if (rst.next()) {
                    return rst.getInt("idRol");
                } else {
                    throw new RuntimeException("No se encontró el ID del rol para el nombre: " + nombreRol);
                }
            }
        }
    }
	
	
    
    public void eliminar(Integer idUsuario) {
    	String sql = "DELETE FROM usuarios WHERE id = ?";
		try (PreparedStatement stm = con.prepareStatement(sql)) {
			stm.setInt(1, idUsuario);
			stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    
    public void actualizar(String nombreUsuario, String password, Integer idUsuario) {
    	
    	String sql = "UPDATE usuarios SET nombreUsuario = ?, contrasena = ? WHERE idUsuario = ?";
    	System.out.println("Consulta SQL: " + sql);
        
        System.out.println("ID: " + idUsuario);
        System.out.println("Nombre: " + nombreUsuario);
        System.out.println("Apellido: " + password);
        
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, nombreUsuario);
            stm.setString(2, password);
            stm.setInt(3, idUsuario);

            int rowsUpdated = stm.executeUpdate();
            System.out.println(rowsUpdated + " fila(s) modificadas.");
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
 
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            String sql = "SELECT u.idUsuario, u.nombreUsuario, r.nombreRol " +
                         "FROM usuarios u " +
                         "INNER JOIN usuarios_roles ur ON u.idUsuario = ur.idUsuario " +
                         "INNER JOIN roles r ON ur.idRol = r.idRol";

            try (PreparedStatement stm = con.prepareStatement(sql);
                 ResultSet rst = stm.executeQuery()) {

                while (rst.next()) {
                    int idUsuario = rst.getInt("idUsuario");
                    String nombreUsuario = rst.getString("nombreUsuario");
                    String rolAsignado = rst.getString("nombreRol");

                    usuarios.add(new Usuario(idUsuario, nombreUsuario, rolAsignado));
                }
            }
            return usuarios;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    private void transformarResultSetEnUsuario(List<Usuario> usuarios, PreparedStatement pstm) throws SQLException {
		try (ResultSet rst = pstm.getResultSet()) {
			while (rst.next()) {
				Usuario usuario = new Usuario(rst.getInt(1), rst.getString(2), rst.getString(3));
				usuarios.add(usuario);
			}
		}				
	}
	
	
	
	public List<Usuario> buscar(String usuario, String password) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT u.idUsuario, u.nombreUsuario, u.contrasena, r.nombreRol " +
                     "FROM usuarios u " +
                     "JOIN usuarios_roles ur ON u.idUsuario = ur.idUsuario " +
                     "JOIN roles r ON ur.idRol = r.idRol " +
                     "WHERE u.nombreUsuario = ? AND u.contrasena = ?";

        try (PreparedStatement stm = con.prepareStatement(sql)) {
        	stm.setString(1, usuario);
        	stm.setString(2, password);

            try (ResultSet rst = stm.executeQuery()) {
                Map<Integer, Usuario> usuariosMap = new HashMap<>();

                while (rst.next()) {
                    Integer idUsuario = rst.getInt("idUsuario");
                    String nombreUsuario = rst.getString("nombreUsuario");
                    String contrasena = rst.getString("contrasena");
                    String nombreRol = rst.getString("nombreRol");

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

	    try (PreparedStatement stm = con.prepareStatement(sql)) {
	    	stm.setInt(1, idUsuario);

	        try (ResultSet rst = stm.executeQuery()) {
	            while (rst.next()) {
	                String nombreRol = rst.getString("nombreRol");
	                roles.add(Rol.valueOf(nombreRol)); // Asumiendo que el nombreRol coincide con el nombre del Enum
	            }
	        }
	    }

	    return roles;
	}

}
