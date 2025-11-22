package domain;

import java.time.LocalDate;

public class Prestamo {
	private static int contadorId = 1;
	private int id;

	private Cliente cliente;         	// Cliente que solicita el préstamo
	private double cantidadSolicitada;        	// Cantidad total del préstamo
	private double cantidadPendiente;	// Cantidad pendiente por pagar
	private double interesAnual;      	// Interés anual (%)
	private int plazoMeses;				// Duración en meses
	private double cuotaMensual;		// Lo que paga cada mes
	private LocalDate fechaInicio;      // Fecha en la que se otorgó
	private LocalDate fechaFin;         // Fecha estimada de finalización
	private String estado;           	// "Activo", "Pagado", "En mora", etc.
	
	public Prestamo(Cliente cliente, double cantidadSolicitada, double interesAnual, LocalDate fechaInicio, LocalDate fechaFin,
			String estado, double cantidadPendiente, int plazoMeses, double cuotaMensual, int meses) {
		super();
		this.setId(contadorId++);
		this.cliente = cliente;
		this.cantidadSolicitada = cantidadSolicitada;
		this.interesAnual = interesAnual;
		this.plazoMeses = meses;
		this.fechaInicio = LocalDate.now();
		this.fechaFin = fechaInicio.plusMonths(meses); //Linea con ayuda de IA
		this.estado = "Activo";
		
		this.cuotaMensual = calcularCuotaMensual();
		
		this.cantidadPendiente = this.cuotaMensual * meses;
	}

	private double calcularCuotaMensual() {
		if (interesAnual == 0) {
			return cantidadSolicitada / plazoMeses;
		}
		
        double tasaMensual = (interesAnual / 100) / 12;
        double cuota = cantidadSolicitada * (tasaMensual * Math.pow(1 + tasaMensual, plazoMeses)) /		//Ayuda de IA para la formula
                (Math.pow(1 + tasaMensual, plazoMeses) - 1);
        return cuota;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public double getInteresAnual() {
		return interesAnual;
	}

	public void setInteresAnual(double interes) {
		this.interesAnual = interes;
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
		return "Prestamo [cliente=" + cliente + ", cantidadSolicitada=" + cantidadSolicitada + ", interesAnual=" + interesAnual + ", fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + ", estado=" + estado + "]";
	}

	public double getCantidadPendiente() {
		return cantidadPendiente;
	}

	public void setCantidadPendiente(double cantidadPendiente) {
		this.cantidadPendiente = cantidadPendiente;
	}
	
	public void realizarPago(double monto) {
        if (monto > 0 && estado.equals("Activo")) {
            this.cantidadPendiente -= monto;
            if (this.cantidadPendiente <= 0.01) { 
                this.cantidadPendiente = 0;
                this.estado = "Pagado";
            }
        }
    }

	public int getPlazoMeses() {
		return plazoMeses;
	}

	public void setPlazoMeses(int plazoMeses) {
		this.plazoMeses = plazoMeses;
	}

	public double getCuotaMensual() {
		return cuotaMensual;
	}

	public void setCuotaMensual(double cuotaMensual) {
		this.cuotaMensual = cuotaMensual;
	}

	public double getCantidadSolicitada() {
		return cantidadSolicitada;
	}

	public void setCantidadSolicitada(double cantidadSolicitada) {
		this.cantidadSolicitada = cantidadSolicitada;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
