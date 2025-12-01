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
		clientes.add(new Cliente("A", "A", "A", "A"));
		clientes.add(new Cliente("B", "C", "A", "D"));
		clientes.add(new Cliente("C", "A", "A", "B"));
		clientes.add(new Cliente("D", "A", "B", "C"));
		
		for (Cliente cliente : clientes) {
		    gestor.insertCliente(cliente);
		        }
		
		Cuenta c1 = new Cuenta("A", 500, null);
		gestor.insertCuenta(c1, clientes.get(0));
		Cuenta c2 = new Cuenta("B", 700, null);
		gestor.insertCuenta(c2, clientes.get(1));
		
		Prestamo p1 = new Prestamo(null, 1000, 10, 2);
		gestor.insertPrestamo(p1, clientes.get(0));
		Prestamo p2 = new Prestamo(null, 500, 20, 1);
		gestor.insertPrestamo(p2, clientes.get(0));
		}
	}

