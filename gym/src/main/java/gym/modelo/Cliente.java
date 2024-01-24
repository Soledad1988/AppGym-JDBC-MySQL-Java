package gym.modelo;

import java.sql.Date;

public class Cliente {

	private Integer id;
	private Date fechaAlta;
	private String nombre;
	private String apellido;
	private String direccion;
	private String telefono;
	private Boolean pago = false;
	
	public Cliente() {
		
	}
	
	public Cliente(Integer id, Date fechaAlta, String nombre, String apellido, String direccion, String telefono,
			Boolean pago) {
		super();
		this.id = id;
		this.fechaAlta = fechaAlta;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.telefono = telefono;
		this.pago = pago;
	}

	public Cliente(Date fechaAlta, String nombre, String apellido, String direccion, String telefono) {
		this.fechaAlta = fechaAlta;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.telefono = telefono;
	}

	public Cliente(String nombre, String apellido, String direccion, Integer id) {
		this.id = id;
	    this.nombre = nombre;
	    this.apellido = apellido;
	    this.direccion = direccion;
	}
	
	public Cliente(Date fechaAlta, String nombre, String apellido, String telefono) {
		this.fechaAlta = fechaAlta;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Boolean getPago() {
		return pago;
	}

	public void setPago(Boolean pago) {
		this.pago = pago;
	}
	
		
}
