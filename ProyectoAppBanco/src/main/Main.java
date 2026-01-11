package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import db.CheckDB;
import domain.Cliente;
import domain.Cuenta;

import domain.Prestamo;
import gui.InterfazPrueba;

public class Main {
	//listas de prueba previas a la implementacion de la BD
	public static ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
	public static ArrayList<Cuenta> listaCuentas = new ArrayList<Cuenta>();
	public static ArrayList<Prestamo> listaPrestamos = new ArrayList<Prestamo>();

	//Con csv para hacer pruebas
	public static void cargarClientesPrueba() {
		
		File f = new File("src/db/db_clientes.csv");
		
		
		try {
			Scanner sc = new Scanner(f);
			while(sc.hasNextLine()) {
				try {
					String linea = sc.nextLine();
					String[] campos = linea.split(";");
					
					int id = Integer.parseInt(campos[0]);
					String name = campos[1];
					String a2 = campos[3];
					String a1 = campos[2];
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
					
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
		
			System.out.println("a");
		}
		
	}
	
	
	public static void main(String[] args) {
		
		CheckDB check = new CheckDB();
		check.init("ProyectoAppBanco/src/resources/db/Banco2.db");
		InterfazPrueba ventanaBD = new InterfazPrueba();
	
		ventanaBD.setVisible(true);
	}

}
