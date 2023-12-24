package gym.modelo;

import java.sql.Date;

public class Cuota {

	private Integer idCuota;
	private Integer clienteId;
	private Double monto;
	private Date fechaPago;
	 
	public Cuota() {
			
	}
	  
	public Cuota(Integer idCuota, Integer clienteId, Double monto, Date fechaPago) {
		super();
		this.idCuota = idCuota;
		this.clienteId = clienteId;
		this.monto = monto;
		this.fechaPago = fechaPago;
	}

	public Cuota(Integer clienteId, Double monto, Date fechaPago) {
		this.clienteId = clienteId;
		this.monto = monto;
		this.fechaPago = fechaPago;
	}


	public Integer getIdCuota() {
		return idCuota;
	}


	public void setIdCuota(Integer idCuota) {
		this.idCuota = idCuota;
	}


	public int getClienteId() {
		return clienteId;
	}


	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}


	public Double getMonto() {
		return monto;
	}


	public void setMonto(Double monto) {
		this.monto = monto;
	}


	public Date getFechaPago() {
		return fechaPago;
	}


	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
		 
}
