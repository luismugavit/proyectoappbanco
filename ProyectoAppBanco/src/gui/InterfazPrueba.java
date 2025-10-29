package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
	private JTable tablaClientes; 	//Tabla que muestra todos los clientes del banco ID/Nombre/Saldo_Total
	private JPanel panelCont;		//Panel contenedor 
	private CardLayout card;		//CardLayout permite alternar entre Paneles como si fuesen pestañas sin necesidad de abrir ventanas nuevas.
	private JScrollPane scroller;
	
	
	public InterfazPrueba(ArrayList<Cliente> listaClientes, ArrayList<Cuenta> listaCuenta){
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Interfaz Banco");
		setSize(640,480);
		setLocationRelativeTo(null);
		this.listaClientes = listaClientes;
		
		//iniciar CardLayout
		card = new CardLayout();
		iniciarCardLayout(card);
		
		//Barra Menu y Opciones
		JMenuBar menuBarra = new JMenuBar();
		setJMenuBar(menuBarra);
		crearOpcionesMenu(menuBarra);
		
		setVisible(true);
	}
	
	public void crearOpcionesMenu(JMenuBar menuBarra) {
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
			
			//Al clickar en el boton correspondiente a una pestaña esta se abre con card.show( panelCont, "identificador")
			itemVerClientes.addActionListener(e -> card.show(panelCont, "tablaClientes"));
			itemMain.addActionListener(e -> card.show(panelCont, "inicio"));
			itemCrearCliente.addActionListener(e -> card.show(panelCont, "crearCliente"));
			
			//
	}
	
	public void crearTablaClientes(ArrayList<Cliente> listaClientes) {
		
		modeloTabla = new ModelTablaClientes(listaClientes);
		tablaClientes = new JTable(modeloTabla);

		scroller = new JScrollPane(tablaClientes);

		
	}
	
	public JPanel tabCrearCliente() {
		
		JPanel addClientePanel = new JPanel(new BorderLayout());
		//Contenido del Panel
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
		
		
		
		return addClientePanel;
	}
	
	public JPanel crearMainPanel() {
		JPanel main = new JPanel();
		main.add(new JLabel("Banco-Main"));
		return main;
	}
	
	public JPanel tabTablaClientes() {
		
		JPanel panelTablaClientes = new JPanel(new BorderLayout());
		
		crearTablaClientes(listaClientes);
		JPanel panelBotones = new JPanel();
		JButton botonAddCliente = new JButton("Añadir Cliente");
		
		botonAddCliente.addActionListener(e -> card.show(panelCont, "crearCliente") );
		panelBotones.add(botonAddCliente);
		
		

		panelTablaClientes.add(scroller);
		panelTablaClientes.add(panelBotones, BorderLayout.WEST);
		
		
		
		return panelTablaClientes;
	}
	
	
	public void iniciarCardLayout(CardLayout card) {
		
		panelCont = new JPanel(card);
		
		//Panel Principal 
		
		JPanel main = crearMainPanel();
		
		
		//Panel de la tabla de clientes
		
		JPanel panelTablaClientes = tabTablaClientes();
		
			
		//Panel de creación de clientes
		
		JPanel addClientePanel = tabCrearCliente();
		
	
		//Añadir paneles al panel contenedor
		panelCont.add(main, "inicio");
		panelCont.add(panelTablaClientes, "tablaClientes");
		panelCont.add(addClientePanel, "crearCliente");
		card.show(panelCont, "inicio");
		
		add(panelCont);
	}
}
