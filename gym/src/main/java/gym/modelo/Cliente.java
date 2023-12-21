package gym.modelo;

import java.sql.Date;

public class Cliente {

	private Integer id;
	private Date fechaAlta;
	private String nombre;
	private String apellido;
	private String direccion;
	private Double precio;
	
	public Cliente() {
		
	}
	
	
	public Cliente(Integer id, Date fechaAlta, String nombre, String apellido, String direccion,
			Double precio) {
		super();
		this.id = id;
		this.fechaAlta = fechaAlta;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.precio = precio;
	}

	public Cliente(Date fechaAlta, String nombre, String apellido, String direccion, Double precio) {
		this.fechaAlta = fechaAlta;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.precio = precio;
	}



	public Cliente(String nombre, String apellido, String direccion, Integer id) {
		this.id = id;
	    this.nombre = nombre;
	    this.apellido = apellido;
	    this.direccion = direccion;
	}
	
	

	public Cliente(Date fechaAlta, String nombre, String apellido, Double precio) {
		this.fechaAlta = fechaAlta;
		this.nombre = nombre;
		this.apellido = apellido;
		this.precio = precio;
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

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
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

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	

}
