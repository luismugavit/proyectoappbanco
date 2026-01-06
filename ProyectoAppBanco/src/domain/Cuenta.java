package domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cuenta {
	
	
	private String numeroCuenta;
	private float saldo;
	private Cliente propietario;
	private ArrayList<Movimiento> historial;
	private static int id = 1;
	
	public Cuenta(Cliente cliente) {
		this.numeroCuenta = "ES-" + id;
		this.saldo = 0.0f;
		this.propietario = cliente;
		this.historial = new ArrayList<Movimiento>();
		id++;
	}
	
	public Cuenta(String numeroCuenta, Cliente cliente) {
		this.numeroCuenta = numeroCuenta;
		this.saldo = 0.0f;
		this.propietario = cliente;
		this.historial = new ArrayList<Movimiento>();
		id++;
	}
	
	public String getNumeroCuenta() {
		return numeroCuenta;
	}


	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}


	public float getSaldo() {
		return saldo;
	}


	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}


	public Cliente getPropietario() {
		return propietario;
	}


	public void setPropietario(Cliente propietario) {
		this.propietario = propietario;
	}
	

	public ArrayList<Movimiento> getHistorial() {
		return historial;
	}


	public void setHistorial(ArrayList<Movimiento> historial) {
		this.historial = historial;
	}


	/**
	 * Representa cada una de las cuentas del banco, para crear una cuenta es necesario que se asocie un cliente a esta.
	 */
	public Cuenta(String numeroCuenta, float saldo, Cliente propietario) {
		super();
		this.numeroCuenta = numeroCuenta;
		this.saldo = saldo;
		this.propietario = propietario;
		this.historial = new ArrayList<>();
	}

	/**
	 * Las cuentas pueden hacer transacciones entre ellas. 
	 * 
	 *
	 */
	public void transaccion(Cuenta destino, float cantidad) {
		destino.setSaldo(destino.getSaldo()+cantidad);
	}
	
	public void ingreso(float cantidad, String concepto) {
		if (cantidad <= 0) { //No se pueden ingresar cantidades negativas
			return;
		}
		this.saldo+=cantidad; //Actualizamos el saldo
		Ingreso ing = new Ingreso(LocalDate.now(), cantidad, concepto, this);
		this.historial.add(ing);
	}
	
	public boolean gasto(float cantidad, String concepto) {
		if (cantidad <= 0) {
			return false;
		}
		if (this.saldo < cantidad) {
			return false; //No hay fondos
		}
		
		this.saldo -= cantidad; //Actualizamos el saldo con el gasto
		Gasto g = new Gasto(LocalDate.now(), cantidad, concepto, this);
		this.historial.add(g);
		return true;
	}
	
	public boolean transferencia(Cuenta destino, float cantidad, String concepto ) {
		if (cantidad <= 0) {
			return false;
		}
        if (this.saldo < cantidad) {
            return false; 
        }
        
//        Ejecutamos el gasto en la Cuenta
        this.saldo -= cantidad;
        Transferencia t = new Transferencia(LocalDate.now(), cantidad, concepto, this, destino);
        this.historial.add(t);
        
//        Ejecutamos el Ingreso en la Cuenta
//        Llama al método ingreso() de la otra cuenta para que también actualice su saldo y registre el movimiento
        destino.ingreso(cantidad, "Transferencia de " + this.propietario.getNombre());
        
		return true;
	}

	@Override
	public String toString() {
		return "Cuenta [numeroCuenta=" + numeroCuenta + ", saldo=" + saldo + ", propietario=" + propietario.getDni() + "]";
	}
	
	
	
	

}
