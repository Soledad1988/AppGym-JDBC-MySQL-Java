package gym.modelo;


public class Gastos {

	private Integer idGasto;
	private String periodoGasto;
	private String nombreGasto;
	private String descripcion;
	private Double costo;
	
	public Gastos() {
		
	}
	
	public Gastos(Integer idGasto, String periodoGasto, String nombreGasto, String descripcion, Double costo) {
		super();
		this.idGasto = idGasto;
		this.periodoGasto = periodoGasto;
		this.nombreGasto = nombreGasto;
		this.descripcion = descripcion;
		this.costo = costo;
	}

	public Gastos(String periodoGasto, String nombreGasto, String descripcion, Double costo) {
		this.periodoGasto = periodoGasto;
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
