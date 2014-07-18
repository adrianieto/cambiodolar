package mx.tipodecambio.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the santander database table.
 * 
 */
@Entity
@NamedQuery(name="Santander.findAll", query="SELECT s FROM Santander s")
public class Santander implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "BUY")
	private float compra;
	@Column(name = "SELL")
	private float venta;
	@Column(name = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	public Santander() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getCompra() {
		return this.compra;
	}

	public void setCompra(float compra) {
		this.compra = compra;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public float getVenta() {
		return this.venta;
	}

	public void setVenta(float venta) {
		this.venta = venta;
	}

}