package main;

import java.util.ArrayList;


import domain.Cliente;
import domain.Cuenta;
import gui.InterfazPrueba;

public class Main {
	
	private static ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
	private static ArrayList<Cuenta> listaCuentas = new ArrayList<Cuenta>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Cliente[] clientes = {
				new Cliente(0, "A", "A", "A", "B", new ArrayList<Cuenta>()),
				new Cliente(1, "A", "A", "A", "B", new ArrayList<Cuenta>()),
				new Cliente(2, "A", "A", "A", "B", new ArrayList<Cuenta>()),
				new Cliente(3, "A", "A", "A", "B", new ArrayList<Cuenta>()),
				
		};
		
		for(Cliente c : clientes) {
			
			listaClientes.add(c);
		}
		
		Cuenta [] cuentas = {
				new Cuenta("ES01", (float) 1500.50, clientes[0]),
				new Cuenta("ES02", (float) 2435.45, clientes[1]),
				new Cuenta("ES03", (float) 956.32, clientes[2]),
				new Cuenta("ES04", (float) 10000.93, clientes[3]),
		};
		
		for (Cuenta cuenta : cuentas) {
			listaCuentas.add(cuenta);
		}
		
		System.out.println(listaClientes.size());
		InterfazPrueba ventana = new InterfazPrueba();

	}

}
