package gym.modelo;

import java.sql.Timestamp;

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
