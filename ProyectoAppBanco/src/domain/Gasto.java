package domain;

import java.time.LocalDate;

/*
 * Se retira dinero de una cuenta
 * Hereda de Movimiento
 */
public class Gasto extends Movimiento {
	
	private Cuenta origen;
	
	public Gasto(LocalDate fecha, float cantidad, String concepto, Cuenta origen) {
		super(fecha, cantidad, concepto);
		this.origen = origen;
	}
	
	public Cuenta origen() {
		return origen;
	}

	public Cuenta getOrigen() {
		return origen;
	}

	public void setOrigen(Cuenta origen) {
		this.origen = origen;
	}

	@Override
	public String toString() {
        return "Gasto [Fecha=" + fecha + ", Cantidad=" + cantidad + ", Origen=" + origen.getNumeroCuenta() + "]";
    }
	
	
	
	
}
