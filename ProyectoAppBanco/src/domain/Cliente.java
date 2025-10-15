package domain;

import java.util.ArrayList;


/**
 * Representa un cliente del banco.
 * Contiene información personal y la lista de cuentas asociadas.
 */
public class Cliente {

	private int id;
	private static int cod = 0;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String dni;
	private ArrayList<Cuenta> listaCuentas;
	
	
	public Cliente(int id, String nombre, String apellido1, String apellido2, String dni,
			ArrayList<Cuenta> listaCuentas) {
		super();
		this.id = cod;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.dni = dni;
		this.listaCuentas = listaCuentas;
		cod++;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido1() {
		return apellido1;
	}


	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}


	public String getApellido2() {
		return apellido2;
	}


	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}


	public ArrayList<Cuenta> getListaCuentas() {
		return listaCuentas;
	}


	public void setListaCuentas(ArrayList<Cuenta> listaCuentas) {
		this.listaCuentas = listaCuentas;
	}


	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2
				+ ", dni=" + dni + ", listaCuentas=" + listaCuentas + "]";
	}
	
	/**
	 * Devuelve el saldo total de un cliente sumando el de todas sus cuentas.
	 */
	public float getSaldoTotal() {
		float saldo = 0.0f;
		for(Cuenta c : listaCuentas) {
			saldo += c.getSaldo();
		}
		return saldo;
	}
	
}
