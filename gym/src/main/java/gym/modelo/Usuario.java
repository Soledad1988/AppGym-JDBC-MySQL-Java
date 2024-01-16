package gym.modelo;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Usuario {
	
	private Integer idUsuario; 
	private String nombreUsuario;
	private String contrasena;
	private Set<Rol> roles = new HashSet<>();
	
	public Usuario() {
		
	}

	public Usuario(Integer idUsuario, String nombreUsuario, String contrasena) {
		super();
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
	}

	
	public Usuario(String nombreUsuario, String contrasena) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
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
	

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }
    
    public void addRol(Rol rol) {
        roles.add(rol);
    }

    public Set<Rol> getRoles() {
        return roles;
    }
    
    //Convertir los roles a string antes, asi cargar la tabla en la vista
    public String getRolesAsString() {
 	    return roles.stream()
 	                .map(Rol::toString)
 	                .collect(Collectors.joining(", "));
 	}

	
	
}
