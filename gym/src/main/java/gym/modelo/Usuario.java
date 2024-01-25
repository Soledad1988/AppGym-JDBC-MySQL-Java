package gym.modelo;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
	
	 //Convertir los roles a string antes, asi carga la tabla en la vista
    public String getRolesAsString() {
 	    return roles.stream()
 	                .map(Rol::toString)
 	                .collect(Collectors.joining(", "));
 	}
	
	
}
