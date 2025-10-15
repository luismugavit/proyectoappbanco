package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import domain.Cliente;
import domain.Cuenta;
import gui.InterfazPrueba;

public class Main {
	
	private static ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
	private static ArrayList<Cuenta> listaCuentas = new ArrayList<Cuenta>();

	
	public void cargarClientes() {
		File f = new File("/db/db_clientes.csv");
		
		try {
			Scanner sc = new Scanner(f);
			while(sc.hasNextLine()) {
				try {
					String linea = sc.nextLine();
					String[] campos = linea.split(";");
					
					int id = Integer.parseInt(campos[0]);
					String name = campos[1];
					String a1 = campos[2];
					String a2 = campos[3];
					String dni = campos[4];
					String[] cuentas = campos[5].split(",");
					
					ArrayList<Cuenta> listaCuentas = new ArrayList<Cuenta>();
					for(String numCuenta : cuentas) {
						listaCuentas.add(null);
					}
					
					Cliente cliente = new Cliente(id, name, a1, a2, dni, listaCuentas);
					
					
					
					
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (FileNotFoundException e) {
			// TODO: handle exception
		}
		
	}
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
		
		System.out.println(listaClientes.get(1).getId());
		System.out.println(listaClientes.size());
		//InterfazPrueba ventana = new InterfazPrueba();

	}

}
