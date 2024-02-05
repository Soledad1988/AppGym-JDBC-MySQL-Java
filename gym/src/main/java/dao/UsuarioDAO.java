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
import gym.modelo.RegistroLogin;
import gym.modelo.Rol;
import gym.modelo.Usuario;

/**
 * Esta clase proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * en la tabla de Usuarios de la base de datos.
 */
public class UsuarioDAO {

	/**
     * La conexión a la base de datos utilizada por este DAO.
     */
	final private Connection con;

	/**
     * Constructor que inicializa un nuevo UsuarioDAO con la conexión especificada.
     *
     * @param con La conexión a la base de datos que se utilizará para las operaciones del DAO.
     */
	public UsuarioDAO(Connection con) {
		this.con = con;
	}
	 
	/**
	 * Realiza el registro de un nuevo usaurio en el sistema.
	 * @param usuario es el objeto Usuario que representa la información del nuevo usuario. 
	 * @param nombreRol es el rol asignado.
	 */
	public void guardar(Usuario usuario, String nombreRol) {
        try {
        	 String sqlUsuario = "INSERT INTO usuarios (nombreUsuario, contrasena, habilitado) VALUES (?, ?, ?)";
            try (PreparedStatement stmUsuario = con.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                stmUsuario.setString(1, usuario.getNombreUsuario());
                stmUsuario.setString(2, usuario.getContrasena());
                //los nuevos usuarios están habilitados por defecto
                stmUsuario.setBoolean(3, true);
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

	/**
	 * Obtiene el Identificador del rol segun el nombre.
	 * @param nombreRol nombre del rol.
	 * @return retorna el Identificador del rol.
	 * @throws SQLException
	 */
    private int obtenerIdRol(String nombreRol) throws SQLException {
    	nombreRol = nombreRol.toUpperCase().replace(" ", "_");
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
	
	
    /*
    public void eliminar(Integer idUsuario) {
        // Primero, eliminar registros relacionados de usuarios_roles
        String sqlRoles = "DELETE FROM usuarios_roles WHERE idUsuario = ?";
        try (PreparedStatement stmRoles = con.prepareStatement(sqlRoles)) {
            stmRoles.setInt(1, idUsuario);
            stmRoles.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Luego, eliminar el usuario de la tabla usuarios
        String sqlUsuario = "DELETE FROM usuarios WHERE idUsuario = ?";
        try (PreparedStatement stmUsuario = con.prepareStatement(sqlUsuario)) {
            stmUsuario.setInt(1, idUsuario);
            stmUsuario.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
    
    /**
     * Actualiza el registro de un usaurio en el sistema.
     * @param nombreUsuario  Nombre de Usuario actualizado del usuario.
     * @param password  Contraseña actualizada del usuario.
     * @param idUsuario Identificador ùnico del usuario.
     */
    public void actualizar(String nombreUsuario, String password, Integer idUsuario) {
    	
    	String sql = "UPDATE usuarios SET nombreUsuario = ?, contrasena = ? WHERE idUsuario = ?";
    	System.out.println("Consulta SQL: " + sql);
        
        System.out.println("ID: " + idUsuario);
        System.out.println("Nombre: " + nombreUsuario);
        System.out.println("Password: " + password);
        
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
    
    /**
     * Recupera una lista de todos los usuarios registrados en el sistema.
     * @return Lista de usuarios registrados.
     */
    public List<Usuario> listar() {
        Map<Integer, Usuario> usuarioMap = new HashMap<>();

        try {
            String sql = "SELECT u.idUsuario, u.nombreUsuario, u.contrasena, u.habilitado, r.nombreRol " +
                    "FROM usuarios u " +
                    "INNER JOIN usuarios_roles ur ON u.idUsuario = ur.idUsuario " +
                    "INNER JOIN roles r ON ur.idRol = r.idRol";

            try (PreparedStatement stm = con.prepareStatement(sql);
                 ResultSet rst = stm.executeQuery()) {

                while (rst.next()) {
                    int idUsuario = rst.getInt("idUsuario");
                    String nombreUsuario = rst.getString("nombreUsuario");
                    String password = rst.getString("contrasena");
                    boolean habilitado = rst.getBoolean("habilitado");
                    String nombreRol = rst.getString("nombreRol");

                    Usuario usuario = usuarioMap.get(idUsuario);
                    if (usuario == null) {
                        usuario = new Usuario(idUsuario, nombreUsuario, password, habilitado);
                        usuarioMap.put(idUsuario, usuario);
                    }
                    usuario.addRol(Rol.valueOf(nombreRol.toUpperCase().replace(" ", "_")));
                }
            }

            return new ArrayList<>(usuarioMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
 
	/**
	 * Busca el usuario en la base de datos
	 * @param usuario nombre de usuario.
	 * @param password contraseña.
	 * @return lista de usuarios que coinciden con los parámetros proporcionados.
	 * @throws SQLException
	 */
    public List<Usuario> buscar(String usuario, String password) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT u.idUsuario, u.nombreUsuario, u.contrasena, u.habilitado, r.nombreRol " +
                     "FROM usuarios u " +
                     "JOIN usuarios_roles ur ON u.idUsuario = ur.idUsuario " +
                     "JOIN roles r ON ur.idRol = r.idRol " +
                     "WHERE u.nombreUsuario = ? AND u.contrasena = ? AND u.habilitado = TRUE";

        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, usuario);
            stm.setString(2, password);

            try (ResultSet rst = stm.executeQuery()) {
                Map<Integer, Usuario> usuariosMap = new HashMap<>();

                while (rst.next()) {
                    Integer idUsuario = rst.getInt("idUsuario");
                    String nombreUsuario = rst.getString("nombreUsuario");
                    String contrasena = rst.getString("contrasena");
                    boolean habilitado = rst.getBoolean("habilitado");
                    String nombreRol = rst.getString("nombreRol");

                    if (habilitado) {
                        Usuario usu = usuariosMap.computeIfAbsent(idUsuario,
                                k -> new Usuario(idUsuario, nombreUsuario, contrasena, habilitado));

                        Set<Rol> roles = usu.getRoles();
                        if (roles == null) {
                            roles = new HashSet<>();
                            usu.setRoles(roles);
                        }

                        roles.add(Rol.valueOf(nombreRol)); // Asumiendo que el nombreRol coincide con el nombre del Enum
                    }
                }

                usuarios.addAll(usuariosMap.values());

                // Si se encontró al menos un usuario, registra el inicio de sesión
                if (!usuarios.isEmpty()) {
                    // Puedes tomar el ID del primer usuario en la lista
                    registrarInicioSesion(usuarios.get(0).getIdUsuario());
                }
            }
        }

        return usuarios;
    }
    
	/**
	 * Obtiene roles de la base de datos para un usuario determinado.
	 * @param idUsuario El ID del usuario.
	 * @return Un conjunto de roles asociados con el usuario.
	 * @throws SQLException
	 */
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
	                roles.add(Rol.valueOf(nombreRol));
	            }
	        }
	    }

	    return roles;
	}
	
	/**
	 * Registra en el sistema el usuario que ha inicado sesiòn
	 * @param idUsuario Id del usuario.
	 */
	public void registrarInicioSesion(int idUsuario) {
	    String sql = "INSERT INTO registros_login (idUsuario, fechaHora) VALUES (?, NOW())";
	    
	    try (PreparedStatement stm = con.prepareStatement(sql)) {
	        stm.setInt(1, idUsuario);
	        int rowsAffected = stm.executeUpdate();
	        System.out.println(rowsAffected + " filas afectadas.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Obtiene una lista de los usuarios que han iniciado sesiòn.
	 * @return retorna una lista de los usuarios que han iniciado sesiòn.
	 */
	public List<RegistroLogin> obtenerRegistrosLogin() {
	    List<RegistroLogin> registros = new ArrayList<>();

	    String sql = "SELECT rl.idUsuario, u.nombreUsuario, rl.fechaHora " +
	                 "FROM registros_login rl " +
	                 "JOIN usuarios u ON rl.idUsuario = u.idUsuario";

	    try (PreparedStatement stm = con.prepareStatement(sql);
	         ResultSet rs = stm.executeQuery()) {

	        while (rs.next()) {
	            int idUsuario = rs.getInt("idUsuario");
	            String nombreUsuario = rs.getString("nombreUsuario");
	            java.sql.Timestamp fechaHora = rs.getTimestamp("fechaHora");

	            registros.add(new RegistroLogin(idUsuario, nombreUsuario, fechaHora));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return registros;
	}
	
	/**
	 * Cambia el estado del usuario en habiltado o deshabilitado.
	 * @param idUsuario Id del usuario.
	 * @param nuevoEstado Estado actualizado.
	 */
	public void cambiarEstadoHabilitado(int idUsuario, boolean nuevoEstado) {
	    String sql = "UPDATE usuarios SET habilitado = ? WHERE idUsuario = ?";
	    
	    try (PreparedStatement stm = con.prepareStatement(sql)) {
	  
	        stm.setBoolean(1, nuevoEstado);
	        stm.setInt(2, idUsuario);

	        int filasAfectadas = stm.executeUpdate();
	        if (filasAfectadas > 0) {
	            System.out.println("El estado del usuario ha sido actualizado.");
	        } else {
	            System.out.println("No se encontró un usuario con el ID especificado.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } 
	}
	

}
