package gym.modelo;

import java.sql.Date;

public class Gastos {

	private Integer idGasto;
	private String periodoGasto;
	private String nombreGasto;
	private String tipo;
	private Double costo;
	
	public Gastos() {
		
	}
	
	public Gastos(Integer idGasto, String periodoGasto, String nombreGasto, String tipo, Double costo) {
		super();
		this.idGasto = idGasto;
		this.periodoGasto = periodoGasto;
		this.nombreGasto = nombreGasto;
		this.tipo = tipo;
		this.costo = costo;
	}

	public Gastos(String periodoGasto, String nombreGasto, String tipo, Double costo) {
		this.periodoGasto = periodoGasto;
		this.nombreGasto = nombreGasto;
		this.tipo = tipo;
		this.costo = costo;
	}

	public Integer getIdGasto() {
		return idGasto;
	}

	public void setIdGasto(Integer idGasto) {
		this.idGasto = idGasto;
	}


	public String getPeriodoGasto() {
		return periodoGasto;
	}

	public void setPeriodoGasto(String periodoGasto) {
		this.periodoGasto = periodoGasto;
	}

	public String getNombreGasto() {
		return nombreGasto;
	}

	public void setNombreGasto(String nombreGasto) {
		this.nombreGasto = nombreGasto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}
	

}
