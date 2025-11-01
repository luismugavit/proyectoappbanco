package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.table.DefaultTableModel;

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
		
		MouseAdapter mouseAdapter = new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				int fila = tablaClientes.rowAtPoint(e.getPoint());
				tabCliente(fila);
				panelCont.add(tabCliente(fila), "ClienteSeleccionado");
				card.show(panelCont, "ClienteSeleccionado");
			}
			
			@Override
	    	public void mouseMoved(MouseEvent e) {
	    		// TODO Auto-generated method stub
	    		
	    	}
			
		};
		
		tablaClientes.addMouseListener(mouseAdapter);
		scroller = new JScrollPane(tablaClientes);

		
	}
	
	public JPanel tabCliente(int fila) {
		JPanel panelVistaCliente = new JPanel(new BorderLayout());
		Cliente cliente = listaClientes.get(fila);
		
		
		JLabel nombre = new JLabel((cliente.getNombre()+ " "+ cliente.getApellido1()+" "+cliente.getApellido2()).toUpperCase() );
		nombre.setFont(new Font("Arial", Font.BOLD, 18));
		
		//ArrayList<Cuenta>listaCuentas = cliente.getListaCuentas();
		
		JPanel info = new JPanel(new GridLayout(2,2,10,10));
		
		
		JLabel saldoTotal = new JLabel("Saldo Total: " + cliente.getSaldoTotal() + " euros", JLabel.CENTER);
		saldoTotal.setFont(new Font("Arial", Font.BOLD, 18));
		saldoTotal.setBackground(Color.LIGHT_GRAY);
		saldoTotal.setOpaque(true);
		
		
		
		info.add(saldoTotal);
	
		
		
		info.add(new JLabel("Informacion sobre movimientos (gastos/ingresos) y transacciones"));
		
		
		JPanel panelTablaCuentas = new JPanel(new BorderLayout());
		ModeloTablaCuentas1 modeloCuentas1 = new ModeloTablaCuentas1(cliente.getListaCuentas());
		JTable tablaCuentas = new JTable(modeloCuentas1);
		panelTablaCuentas.add(tablaCuentas);
		panelTablaCuentas.add(tablaCuentas.getTableHeader(), BorderLayout.NORTH);
		
		info.add(panelTablaCuentas);
		
		// Panel de botones de Operaciones
		JPanel panelBotonesCuenta = new JPanel();
		JButton btnIngresar = new JButton("Ingresar");
		JButton btnGastar = new JButton("Gastar");
		// JButton btnTransferir = new JButton("Transferir");
		panelBotonesCuenta.add(btnIngresar);
		panelBotonesCuenta.add(btnGastar);
		info.add(panelBotonesCuenta);
		
		
		panelVistaCliente.add(info);
		panelVistaCliente.add(nombre, BorderLayout.NORTH);
		
		// Listener para el BOTÓN INGRESAR
		btnIngresar.addActionListener(e->{
			int filaSel = tablaCuentas.getSelectedRow();
			if (filaSel == -1) { // Si no selecciona la cuenta
				JOptionPane.showMessageDialog(this, "Por favor, selecciona una cuenta primero.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Cuenta cuentaSeleccionada = cliente.getListaCuentas().get(filaSel);
			String sCantidad = JOptionPane.showInputDialog(this, "Cantidad a ingresar:", "Ingreso", JOptionPane.PLAIN_MESSAGE);
			String concepto = JOptionPane.showInputDialog(this, "Concepto:", "Ingreso", JOptionPane.PLAIN_MESSAGE);
			try {
				float cantidad = Float.parseFloat(sCantidad);
				cuentaSeleccionada.ingreso(cantidad, concepto); //Llama al método de Cuenta
				// Refrescar la tabla de cuentas y el saldo total
				modeloCuentas1.fireTableDataChanged(); //fireTableDataChanged: indica que el contenido ha cambiado y tiene que redibujarse 
				saldoTotal.setText("Saldo Total: " + cliente.getSaldoTotal() + " euros"); // Actualiza el saldo
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Cantidad no válida.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		//Listener para el Boton gastar
		btnGastar.addActionListener(e -> {
			int filaSel = tablaCuentas.getSelectedRow();
			if (filaSel == -1) {
				JOptionPane.showMessageDialog(this, "Por favor, selecciona una cuenta primero.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Cuenta cuentaSeleccionada = cliente.getListaCuentas().get(filaSel);
			String sCantidad = JOptionPane.showInputDialog(this, "Cantidad a retirar:", "Gasto", JOptionPane.PLAIN_MESSAGE);
			String concepto = JOptionPane.showInputDialog(this, "Concepto:", "Gasto", JOptionPane.PLAIN_MESSAGE);
			
			try {
				float cantidad = Float.parseFloat(sCantidad);
				if (!cuentaSeleccionada.gasto(cantidad, concepto)) { //Llama al método de Cuenta
					JOptionPane.showMessageDialog(this, "Fondos insuficientes.", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					// Refrescar la tabla de cuentas y el saldo total
					modeloCuentas1.fireTableDataChanged();
					saldoTotal.setText("Saldo Total: " + cliente.getSaldoTotal() + " euros"); //Actualiza el saldo
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Cantidad no válida.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		});
		
		
		
		return panelVistaCliente;
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
		
			JOptionPane.showMessageDialog(null, "El cliente se ha añadido con éxito.", "Cliente añadido", JOptionPane.INFORMATION_MESSAGE);
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
