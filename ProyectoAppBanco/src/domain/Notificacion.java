package domain;

import java.time.LocalDate;

public class Notificacion {

	private Cliente cliente;    	 // Cliente que recibe la notificación
	private String mensaje;          // Contenido del mensaje
	private LocalDate fechaEnvio;    // Cuándo se envió
	private String tipo;             // "Transacción", "Préstamo", "Aviso general"
	private boolean leido;           // Si el cliente ya la ha visto
	
	public Notificacion(Cliente cliente, String mensaje, LocalDate fechaEnvio, String tipo, boolean leido) {
		super();
		this.cliente = cliente;
		this.mensaje = mensaje;
		this.fechaEnvio = fechaEnvio;
		this.tipo = tipo;
		this.leido = leido;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public LocalDate getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(LocalDate fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}

	@Override
	public String toString() {
		return "Notificacion [cliente=" + cliente + ", mensaje=" + mensaje + ", fechaEnvio=" + fechaEnvio + ", tipo="
				+ tipo + ", leido=" + leido + "]";
	}
	
	
	
}
