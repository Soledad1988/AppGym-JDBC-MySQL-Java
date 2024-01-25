package gym.modelo;

import java.sql.Date;

public class Gastos {

	private Integer idGasto;
	private Date fechaGasto;
	private String nombreGasto;
	private String descripcion;
	private Double costo;
	
	public Gastos() {
		
	}

	public Gastos(Integer idGasto, Date fechaGasto, String nombreGasto, String descripcion, Double costo) {
		super();
		this.idGasto = idGasto;
		this.fechaGasto = fechaGasto;
		this.nombreGasto = nombreGasto;
		this.descripcion = descripcion;
		this.costo = costo;
	}

	public Gastos(Date fechaGasto, String nombreGasto, String descripcion, Double costo) {
		this.fechaGasto = fechaGasto;
		this.nombreGasto = nombreGasto;
		this.descripcion = descripcion;
		this.costo = costo;
	}

	public Integer getIdGasto() {
		return idGasto;
	}

	public void setIdGasto(Integer idGasto) {
		this.idGasto = idGasto;
	}

	public Date getFechaGasto() {
		return fechaGasto;
	}

	public void setFechaGasto(Date fechaGasto) {
		this.fechaGasto = fechaGasto;
	}

	public String getNombreGasto() {
		return nombreGasto;
	}

	public void setNombreGasto(String nombreGasto) {
		this.nombreGasto = nombreGasto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}
	

}
