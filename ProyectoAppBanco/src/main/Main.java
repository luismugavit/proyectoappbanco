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
				new Cliente(4, "A", "A", "A", "B", new ArrayList<Cuenta>()),
		};
		
		for(Cliente c : clientes) {
			
			listaClientes.add(c);
		}
	
		
		System.out.println(listaClientes.size());
		InterfazPrueba ventana = new InterfazPrueba();

	}

}
