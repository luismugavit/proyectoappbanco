package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import domain.Cliente;
import domain.Cuenta;

public class InterfazPrueba extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Cliente> listaClientes;
	private JTable tablaClientes;
	private JPanel panelCont;
	
	public InterfazPrueba(ArrayList<Cliente> listaClientes, ArrayList<Cuenta> listaCuenta){
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Interfaz Banco");
		setSize(640,480);
		setLocationRelativeTo(null);
		this.listaClientes = listaClientes;
		
		tablaClientes = crearTablaClientes(listaClientes);
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
		itemVerClientes.addActionListener(e -> card.show(panelCont, "tablaClientes"));
		itemMain.addActionListener(e -> card.show(panelCont, "inicio"));
		
		//
		
	
		
		
		setVisible(true);
	}
	
	public JTable crearTablaClientes(ArrayList<Cliente> listaClientes) {
		
		ModelTablaClientes modeloTabla = new ModelTablaClientes(listaClientes);
		JTable tablaClientes = new JTable(modeloTabla);
		tablaClientes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		return tablaClientes;
	}
	
	public void iniciarCardLayout(CardLayout card) {
		
		panelCont = new JPanel(card);
		
		JPanel main = new JPanel();
		main.add(new JLabel("Banco-Main"));
		
		
		JPanel clientesPanel = new JPanel();
		JScrollPane scroller = new JScrollPane(tablaClientes);
		clientesPanel.add(scroller, BorderLayout.CENTER);
		
		
		panelCont.add(main, "inicio");
		panelCont.add(clientesPanel, "tablaClientes");
		
		card.show(panelCont, "inicio");
		
		add(panelCont);
	}
	
	
	
	
	
}
