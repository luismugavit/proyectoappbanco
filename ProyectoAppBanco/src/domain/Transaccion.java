package domain;

import java.time.LocalDate;

public class Transaccion {
	
	private LocalDate fecha;
	private float cantidad;
	private Cuenta origen;
	private Cuenta destino;
	
	
	public Transaccion(LocalDate fecha, float cantidad, Cuenta origen, Cuenta destino) {
		super();
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.origen = origen;
		this.destino = destino;
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


	public Cuenta getOrigen() {
		return origen;
	}


	public void setOrigen(Cuenta origen) {
		this.origen = origen;
	}


	public Cuenta getDestino() {
		return destino;
	}


	public void setDestino(Cuenta destino) {
		this.destino = destino;
	}


	@Override
	public String toString() {
		return "Transaccion [fecha=" + fecha + ", cantidad=" + cantidad + ", origen=" + origen + ", destino=" + destino
				+ "]";
	}
	
	

}
