package gym.modelo;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Representa un usuario en el sistema.
 * 
 * <p>La clase encapsula la información relacionada con un usuario, incluyendo su identificador,
 * nombre de usuario, contraseña, si esta habilitado, y el rol asignado.</p>
 * 
 * <p>La clase proporciona constructores para la creación de instancias con diferentes conjuntos
 * de atributos. Los métodos de acceso y modificación permiten interactuar con los datos del cliente.</p>
 */
public class Usuario {
	
	private Integer idUsuario; 
	private String nombreUsuario;
	private String contrasena;
	private boolean habilitado;
	private Set<Rol> roles = new HashSet<>();
	
	public Usuario() {
		
	}

	public Usuario(Integer idUsuario, String nombreUsuario, String contrasena, boolean habilitado, Set<Rol> roles) {
		super();
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		this.habilitado = habilitado;
		this.roles = roles;
	}

	public Usuario(Integer idUsuario, String nombreUsuario, String contrasena, boolean habilitado) {
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		this.habilitado = habilitado;
	}

	public Usuario(String nombreUsuario, String contrasena, boolean habilitado) {
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		this.habilitado = habilitado;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public Set<Rol> getRoles() {
		return roles;
	
	}
	
	public void addRol(Rol rol) {
        roles.add(rol);
    }

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}
	
	/**
	 * Obtiene una representación en cadena de los roles del usuario.
	 *
	 * <p>Este método convierte los roles del usuario a cadenas de texto y las une en una sola cadena,
	 * separadas por comas.</p>
	 *
	 * @return Una cadena que representa los roles del usuario.
	 */
    public String getRolesAsString() {
 	    return roles.stream()
 	                .map(Rol::toString)
 	                .collect(Collectors.joining(", "));
 	}
	
	
}
