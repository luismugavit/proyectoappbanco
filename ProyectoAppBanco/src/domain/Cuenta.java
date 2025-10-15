package domain;

import java.util.ArrayList;

public class Cuenta {
	
	
	private String numeroCuenta;
	private float saldo;
	private Cliente propietario;
	
	
	
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

	/**
	 * Representa cada una de las cuentas del banco, para crear una cuenta es necesario que se asocie un cliente a esta.
	 */
	public Cuenta(String numeroCuenta, float saldo, Cliente propietario) {
		super();
		this.numeroCuenta = numeroCuenta;
		this.saldo = saldo;
		this.propietario = propietario;
	}

	/**
	 * Las cuentas pueden hacer transacciones entre ellas. 
	 * 
	 *
	 */
	public void transaccion(Cuenta destino, float cantidad) {
		destino.setSaldo(destino.getSaldo()+cantidad);
	}

	@Override
	public String toString() {
		return "Cuenta [numeroCuenta=" + numeroCuenta + ", saldo=" + saldo + ", propietario=" + propietario + "]";
	}
	
	
	
	

}
