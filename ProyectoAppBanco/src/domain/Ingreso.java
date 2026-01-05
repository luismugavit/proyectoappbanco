package domain;

import java.time.LocalDate;

/*
 * Ingresa sinero a una Cuenta
 * Hereda de Movimiento
 */

public class Ingreso extends Movimiento {
	
	private Cuenta destino;

	public Ingreso(LocalDate fecha, float cantidad, String concepto, Cuenta destino) {
		super(fecha, cantidad, concepto);
		this.destino = destino;
	}
	
	public Cuenta getDestino() {
		return destino;
	}
	

	public void setDestino(Cuenta destino) {
		this.destino = destino;
	}

	@Override
	public String toString() {
        return "INGRESO [Fecha=" + fecha + ", Cantidad=" + cantidad + ", Destino=" + destino.getNumeroCuenta() + "]";
    }
	
	
	

}
