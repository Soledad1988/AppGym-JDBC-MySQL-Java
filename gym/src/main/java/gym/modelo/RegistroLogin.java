package gym.modelo;

import java.sql.Timestamp;

/**
 * Representa un registro de inicio de sesión en el sistema.
 * 
 * <p>La clase encapsula la información relacionada con un registro de inicio de sesión,
 * incluyendo su identificador, nombre de usuario y la fecha y hora del inicio de sesión.</p>
 * 
 * <p>La clase proporciona constructores para crear instancias con diferentes conjuntos
 * de atributos. Los métodos de acceso y modificación permiten interactuar con los datos del registro.</p>
 */
public class RegistroLogin {

	private int idUsuario;
	private String nombreUsuario;
    private Timestamp fechaHora;
    
	public RegistroLogin(int idUsuario, String nombreUsuario, Timestamp fechaHora) {
		super();
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.fechaHora = fechaHora;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public Timestamp getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Timestamp fechaHora) {
		this.fechaHora = fechaHora;
	}
	
	
}
