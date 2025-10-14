package domain;

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


	public Cuenta(String numeroCuenta, float saldo, Cliente propietario) {
		super();
		this.numeroCuenta = numeroCuenta;
		this.saldo = saldo;
		this.propietario = propietario;
	}


	@Override
	public String toString() {
		return "Cuenta [numeroCuenta=" + numeroCuenta + ", saldo=" + saldo + ", propietario=" + propietario + "]";
	}
	
	
	
	

}
