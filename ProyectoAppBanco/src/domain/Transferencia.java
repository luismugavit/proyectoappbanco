package domain;

import java.time.LocalDate;

/*
 * Se manda dinero de una cuenta a otra
 * Hereda de Movimiento
 */

public class Transferencia extends Movimiento {
	
	private Cuenta origen;
    private Cuenta destino;
    
    public Transferencia(LocalDate fecha, float cantidad, String concepto,Cuenta origen, Cuenta destino) {
		super(fecha, cantidad, concepto);
		this.origen = origen;
        this.destino = destino;
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
        return "Transferecnia [Fecha=" + fecha + ", Cantidad=" + cantidad + ", Origen=" + origen.getNumeroCuenta() + ", Dest=" + destino.getNumeroCuenta() + "]";
    }
}
