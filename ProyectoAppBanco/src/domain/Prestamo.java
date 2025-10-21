package domain;

import java.time.LocalDate;

public class Prestamo {

	private Cliente cliente;         	// Cliente que solicita el préstamo
	private double cantidad;        	// Monto total del préstamo
	private double interes;      		// Interés anual (%)
	private LocalDate fechaInicio;      // Fecha en la que se otorgó
	private LocalDate fechaFin;         // Fecha estimada de finalización
	private String estado;           	// "Activo", "Pagado", "En mora", etc.
	
	public Prestamo(Cliente cliente, double cantidad, double interes, LocalDate fechaInicio, LocalDate fechaFin,
			String estado) {
		super();
		this.cliente = cliente;
		this.cantidad = cantidad;
		this.interes = interes;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estado = estado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public double getInteres() {
		return interes;
	}

	public void setInteres(double interes) {
		this.interes = interes;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Prestamo [cliente=" + cliente + ", cantidad=" + cantidad + ", interes=" + interes + ", fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + ", estado=" + estado + "]";
	}
	
	
}
