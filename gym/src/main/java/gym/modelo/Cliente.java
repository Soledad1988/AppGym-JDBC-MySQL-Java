package gym.modelo;

import java.sql.Date;

public class Cliente {

	private Integer id;
	private Date fechaAlta;
	private String nombre;
	private String apellido;
	private String direccion;
	private double precio;
	
	public Cliente(Integer id, Date fechaAlta, String nombre, String apellido, String direccion,
			double precio) {
		super();
		this.id = id;
		this.fechaAlta = fechaAlta;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.precio = precio;
	}

	public Cliente(Date fechaAlta, String nombre, String apellido, String direccion, double precio) {
		this.fechaAlta = fechaAlta;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.precio = precio;
	}

	public Cliente(String nombre, String apellido, String direccion, double precio) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
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
