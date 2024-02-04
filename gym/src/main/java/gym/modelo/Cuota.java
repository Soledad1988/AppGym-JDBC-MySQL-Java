package gym.modelo;

import java.sql.Date;

/**
 * Representa una cuota en el sistema.
 * 
 * <p>La clase encapsula la información relacionada con una cuota, incluyendo su identificador,
 * el identificador del cliente al que pertenece, el monto abonado y la fecha de pago.</p>
 * 
 * <p>La clase proporciona constructores para la creación de instancias con diferentes conjuntos
 * de atributos. Los métodos de acceso y modificación permiten interactuar con los datos de la cuota.</p>
 */
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
