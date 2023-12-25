package gym.modelo;

public class Pagos {

	 private Integer idPagos;
	 private int clienteId;
	 private Double monto;
	 private String periodoPago;
	 
	 public Pagos() {
			
		}
	 
	 
	public Pagos(Integer idPagos, int clienteId, Double monto, String periodoPago) {
		super();
		this.idPagos = idPagos;
		this.clienteId = clienteId;
		this.monto = monto;
		this.periodoPago = periodoPago;
	}


	public Integer getIdPagos() {
		return idPagos;
	}


	public void setIdPagos(Integer idPagos) {
		this.idPagos = idPagos;
	}


	public int getClienteId() {
		return clienteId;
	}


	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}


	public Double getMonto() {
		return monto;
	}


	public void setMonto(Double monto) {
		this.monto = monto;
	}


	public String getPeriodoPago() {
		return periodoPago;
	}


	public void setPeriodoPago(String periodoPago) {
		this.periodoPago = periodoPago;
	}
	
	
	 
	 
}
