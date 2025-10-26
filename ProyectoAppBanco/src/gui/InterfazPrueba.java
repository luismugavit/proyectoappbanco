package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import domain.Cliente;
import domain.Cuenta;

public class InterfazPrueba extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Cliente> listaClientes;
	private ModelTablaClientes modeloTabla;
	private JTable tablaClientes;
	
	
	private JPanel panelCont;
	public InterfazPrueba(ArrayList<Cliente> listaClientes, ArrayList<Cuenta> listaCuenta){
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Interfaz Banco");
		setSize(640,480);
		setLocationRelativeTo(null);
		this.listaClientes = listaClientes;
		
		crearTablaClientes(listaClientes);
		CardLayout card = new CardLayout();
		iniciarCardLayout(card);
		
		//Barra Menu y Opciones
		JMenuBar menuBarra = new JMenuBar();
		setJMenuBar(menuBarra);
		//Opciones relativas a los clientes del banco
		JMenu menuClientes = new JMenu("Clientes");
		JMenuItem itemMain = new JMenuItem("Main");
		
		menuBarra.add(menuClientes);
		menuBarra.add(itemMain);
		JMenuItem itemVerClientes = new JMenuItem("Tabla Clientes");
		JMenuItem itemCrearCliente = new JMenuItem("Crear cliente");
		JMenuItem itemOpcionesCliente = new JMenuItem("Opciones");
		menuClientes.add(itemVerClientes);
		menuClientes.add(itemCrearCliente);
		menuClientes.add(itemOpcionesCliente);
		itemVerClientes.addActionListener(e -> {
			
			card.show(panelCont, "tablaClientes");
		});
		
		itemMain.addActionListener(e -> card.show(panelCont, "inicio"));
		itemCrearCliente.addActionListener(e -> card.show(panelCont, "crearCliente"));
		
		//
		
		setVisible(true);
	}
	
	public void crearTablaClientes(ArrayList<Cliente> listaClientes) {
		
		modeloTabla = new ModelTablaClientes(listaClientes);
		tablaClientes = new JTable(modeloTabla);
		tablaClientes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
	}
	
	public void iniciarCardLayout(CardLayout card) {
		
		panelCont = new JPanel(card);
		//Panel Principal 
		JPanel main = new JPanel();
		main.add(new JLabel("Banco-Main"));
		
		//Panel de la tabla de clientes
		
		JPanel clientesPanel = new JPanel();
		JScrollPane scroller = new JScrollPane(tablaClientes);
		clientesPanel.add(scroller, BorderLayout.CENTER);
		
		//Panel de creación de clientes
		JPanel addClientePanel = new JPanel(new BorderLayout());
		addClientePanel.add(new JLabel("Add cliente", JLabel.CENTER), BorderLayout.NORTH);
		JPanel camposTextoPanel = new JPanel(new GridLayout(4, 2, 1,60));
		JTextField campoNombre = new JTextField();
		JTextField campoApellido1 = new JTextField();
		JTextField campoApellido2 = new JTextField();
		JTextField campoDNI = new JTextField();
		campoNombre.setMaximumSize(new Dimension(15,5));;
		camposTextoPanel.add(new JLabel("Nombre", JLabel.CENTER));
		camposTextoPanel.add(campoNombre);
		camposTextoPanel.add(new JLabel("Apellido_1", JLabel.CENTER));
		camposTextoPanel.add(campoApellido1);
		camposTextoPanel.add(new JLabel("Apellido_2", JLabel.CENTER));
		camposTextoPanel.add(campoApellido2);
		camposTextoPanel.add(new JLabel("DNI", JLabel.CENTER));
		camposTextoPanel.add(campoDNI);
		
		addClientePanel.add(camposTextoPanel);
		camposTextoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
	
		
		
		JPanel botones = new JPanel();
		
		JButton botonCrearCliente = new JButton("Crear Cliente");
		botones.add(botonCrearCliente);
		addClientePanel.add(botones, BorderLayout.SOUTH);
		
		botonCrearCliente.addActionListener(e -> {
			String nombreNuevoCliente = campoNombre.getText();
			String apellido1NuevoCliente = campoApellido1.getText();
			String apellido2NuevoCliente = campoApellido2.getText();
			String dniNuevoCliente = campoDNI.getText();
			Cliente newCliente = new Cliente(nombreNuevoCliente, apellido1NuevoCliente, apellido2NuevoCliente, dniNuevoCliente);
			listaClientes.add(newCliente);
			modeloTabla.fireTableDataChanged();
			
			
			System.out.println(nombreNuevoCliente);
			System.out.println(listaClientes.getLast());
		});
		
		
	
		
		panelCont.add(main, "inicio");
		panelCont.add(clientesPanel, "tablaClientes");
		panelCont.add(addClientePanel, "crearCliente");
		card.show(panelCont, "inicio");
		
		add(panelCont);
	}
	
	
	
	
	
}
