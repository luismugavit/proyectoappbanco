package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import java.util.Scanner;


import domain.Cliente;
import domain.Cuenta;

import domain.Prestamo;
import gui.InterfazPrueba;

public class Main {
	
	public static ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
	public static ArrayList<Cuenta> listaCuentas = new ArrayList<Cuenta>();
	public static ArrayList<Prestamo> listaPrestamos = new ArrayList<Prestamo>();

	
	public static void cargarClientes() {
		
		File f = new File("src/db/db_clientes.csv");
		
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
					
					ArrayList<Cuenta> accs = new ArrayList<Cuenta>();
					ArrayList<Prestamo> prest = new ArrayList<Prestamo>();
					Cliente cliente = new Cliente(id, name, a1, a2, dni, accs, prest );
					
					
					for(String numCuenta : cuentas) {
						String[] camposCuenta = numCuenta.split("=");
						String numero = camposCuenta[0];
						Float saldo = Float.parseFloat(camposCuenta[1]);
						Cuenta cuenta = new Cuenta(numero, saldo,cliente );
						listaCuentas.add(cuenta);
						cliente.addCuenta(cuenta);
					}
					
					listaClientes.add(cliente);
					
					
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			System.out.println("a");
		}
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		GestorBaseDatos db = new GestorBaseDatos();
//
//        // 1. Crear la tabla (solo la crea si no existe)
//        db.inicializarBaseDatos();
//
//        // 2. Insertar algunos datos de prueba
//        db.insertarCliente("Juan Perez", 1500.50);
//        db.insertarCliente("Ana Gomez", 2300.00);
//
//        // 3. Leer y mostrar en consola
//        System.out.println("Lista de clientes");
//        db.leerClientes();
		
		Cliente[] clientes = {
				new Cliente(11, "A", "A", "A", "B", new ArrayList<Cuenta>(), new ArrayList<Prestamo>()),
				new Cliente(12, "A", "A", "A", "B", new ArrayList<Cuenta>(), new ArrayList<Prestamo>()),
				new Cliente(13, "A", "A", "A", "B", new ArrayList<Cuenta>(), new ArrayList<Prestamo>()),
				new Cliente(14, "A", "A", "A", "B", new ArrayList<Cuenta>(), new ArrayList<Prestamo>()),
				
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
		
		for(int i= 0; i<clientes.length; i++) {
			clientes[i].getListaCuentas().add(cuentas[i]);
		}
		

		cargarClientes();
		System.out.println(listaClientes.size());
		InterfazPrueba ventana = new InterfazPrueba(listaClientes,listaCuentas);
		ventana.setVisible(true);
		
		
	}

}
