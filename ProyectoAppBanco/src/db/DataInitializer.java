package db;


import java.util.ArrayList;

import domain.Cliente;
import domain.Cuenta;
import domain.Prestamo;

public class DataInitializer {

	GestorBDInitializer gestor = new GestorBDInitializer();
	public void InicializarBD() {

		gestor.crearTablas();

		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		clientes.add(new Cliente("Laura", "Perez", "Sanchez", "1000001A"));
		clientes.add(new Cliente("Miguel", "Hernandez", "Lopez", "1000001B"));
		clientes.add(new Cliente("Ana", "Martinez", "Ruiz", "1000001C"));
		clientes.add(new Cliente("Javier", "Gomez", "Navarro", "1000001D"));
		clientes.add(new Cliente("Elena", "Torres", "Moreno", "1000001E"));
		clientes.add(new Cliente("Sergio", "Vega", "Ortega", "1000001F"));
		clientes.add(new Cliente("Paula", "Ramirez", "Castro", "1000001G"));
		clientes.add(new Cliente("David", "Flores", "Molina", "1000001H"));
		clientes.add(new Cliente("Lucia", "Cruz", "Delgado", "1000001I"));
		clientes.add(new Cliente("Alberto", "Santos", "Ibanez", "1000001J"));
		clientes.add(new Cliente("Marta", "Rey", "Cabrera", "1000001K"));
		clientes.add(new Cliente("Daniel", "Prieto", "Aguilar", "1000001L"));
		clientes.add(new Cliente("Natalia", "Suarez", "Vidal", "1000001M"));
		clientes.add(new Cliente("Pablo", "Dominguez", "Rojas", "1000001N"));
		clientes.add(new Cliente("Raquel", "Leon", "Pascual", "1000001O"));
		clientes.add(new Cliente("Ivan", "Herrera", "Blanco", "1000001P"));
		clientes.add(new Cliente("Carmen", "Nieto", "Campos", "1000001Q"));
		clientes.add(new Cliente("Oscar", "Calvo", "Fuentes", "1000001R"));
		clientes.add(new Cliente("Beatriz", "Marin", "Salas", "1000001S"));
		clientes.add(new Cliente("Victor", "Arias", "Nunez", "1000001T"));


		for (Cliente cliente : clientes) {
		    gestor.insertCliente(cliente);
		        }
		
		ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
		Cuenta c1 = new Cuenta("ES1", 5423.75f, clientes.get(0));
		Cuenta c2 = new Cuenta("ES2", 1876.40f, clientes.get(1));
		Cuenta c3 = new Cuenta("ES3", 9234.12f, clientes.get(2));
		Cuenta c4 = new Cuenta("ES4", 351.90f, clientes.get(3));
		Cuenta c5 = new Cuenta("ES5", 7842.66f, clientes.get(4));
		Cuenta c6 = new Cuenta("ES6", 1299.50f, clientes.get(5));
		Cuenta c7 = new Cuenta("ES7", 9987.30f, clientes.get(6));
		Cuenta c8 = new Cuenta("ES8", 4672.18f, clientes.get(7));
		Cuenta c9 = new Cuenta("ES9", 812.77f, clientes.get(8));
		Cuenta c10 = new Cuenta("ES10", 6341.09f, clientes.get(9));
		Cuenta c11 = new Cuenta("ES11", 2750.44f, clientes.get(10));
		Cuenta c12 = new Cuenta("ES12", 9012.88f, clientes.get(11));
		Cuenta c13 = new Cuenta("ES13", 1567.23f, clientes.get(12));
		Cuenta c14 = new Cuenta("ES14", 7499.95f, clientes.get(13));
		Cuenta c15 = new Cuenta("ES15", 3888.60f, clientes.get(14));
		Cuenta c16 = new Cuenta("ES16", 620.35f, clientes.get(15));
		Cuenta c17 = new Cuenta("ES17", 8430.10f, clientes.get(16));
		Cuenta c18 = new Cuenta("ES18", 2145.99f, clientes.get(17));
		Cuenta c19 = new Cuenta("ES19", 5690.70f, clientes.get(18));
		Cuenta c20 = new Cuenta("ES20", 9901.55f, clientes.get(19));

		cuentas.add(c1);
		cuentas.add(c2);
		cuentas.add(c3);
		cuentas.add(c4);
		cuentas.add(c5);
		cuentas.add(c6);
		cuentas.add(c7);
		cuentas.add(c8);
		cuentas.add(c9);
		cuentas.add(c10);
		cuentas.add(c11);
		cuentas.add(c12);
		cuentas.add(c13);
		cuentas.add(c14);
		cuentas.add(c15);
		cuentas.add(c16);
		cuentas.add(c17);
		cuentas.add(c18);
		cuentas.add(c19);
		cuentas.add(c20);
		
		for(Cuenta c: cuentas) {
			gestor.insertCuenta(c);
		}
		

		
		

		Prestamo p1 = new Prestamo(null, 1000, 10, 2);
		gestor.insertPrestamo(p1, clientes.get(0));
		Prestamo p2 = new Prestamo(null, 500, 20, 1);
		gestor.insertPrestamo(p2, clientes.get(0));
		}
	}

