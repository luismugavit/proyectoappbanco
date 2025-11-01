package domain;

import java.time.LocalDate;

/*Clase abstracta Madre que representa un Movimiento
 */

public abstract class Movimiento {
	
	protected LocalDate fecha;
	protected float cantidad;
	protected String concepto;
	
	
	public Movimiento(LocalDate fecha, float cantidad, String concepto) {
		super();
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.concepto = concepto;
	}


	public LocalDate getFecha() {
		return fecha;
	}


	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}


	public float getCantidad() {
		return cantidad;
	}


	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}


	public String getConcepto() {
		return concepto;
	}


	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}


	@Override
	public String toString() {
		return "Movimiento [fecha=" + fecha + ", cantidad=" + cantidad + ", concepto=" + concepto + "]";
	}
	
	
	
	

}
