package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellRenderer;

import db.GestorBD;
import domain.Cliente;
import domain.Cuenta;
import domain.Gasto;
import domain.Ingreso;
import domain.Movimiento;
import domain.Prestamo;
import domain.Transferencia;

public class InterfazPrueba extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
	private ArrayList<Cuenta> listaCuentas = new ArrayList<Cuenta>();
	private ArrayList<Movimiento> registroMovimientos = new ArrayList<Movimiento>();
	private ModelTablaClientes modeloTabla; 
	private ModeloTablaCuentas1 modeloTablaCuentas;
	private JTable tablaClientes; 	//Tabla que muestra todos los clientes del banco ID/Nombre/Saldo_Total
	private JTable tablaCuentas;
	private JPanel panelCont;		//Panel contenedor 
	private CardLayout card;		//CardLayout permite alternar entre Paneles como si fuesen pestañas sin necesidad de abrir ventanas nuevas.
	private JScrollPane scroller;
	private int filaSelec = -1;
	private JLabel lblTotalClientes, lblTotalCuentas, lblCapitalTotal;
	private JLabel numeroClientes;
	private JLabel numeroCuentas;
	private JTextField txtFiltro;
	private GestorBD gestorBD = new GestorBD();
	private ArrayList<Cliente> listafiltro;
	
	public InterfazPrueba(){
		
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Interfaz Banco");
		setSize(640,480);
		setLocationRelativeTo(null);
		
		
		
		
		this.listaClientes = gestorBD.loadClientes();
		//System.out.println(listaClientes.get(0).getListaCuentas().size());
		this.listaCuentas = gestorBD.loadCuentas(listaClientes);
		
		//System.out.println(listaClientes.get(1).getListaCuentas().size());
		this.registroMovimientos = gestorBD.loadMovimientos(listaCuentas);
		//System.out.println(registroMovimientos);
		
		//Movimientos de prueba
		registroMovimientos.add(new Ingreso(LocalDate.now(), 1000.0f, "prueba", listaCuentas.get(0)));
		registroMovimientos.add(new Ingreso(LocalDate.now(), 1000.0f, "prueba", listaCuentas.get(0)));
		registroMovimientos.add(new Gasto(LocalDate.now(), 500.0f, "prueba", listaCuentas.get(0)));
		//iniciar CardLayout
		card = new CardLayout();
		iniciarCardLayout(card);
		
		//Barra Menu y Opciones
		JMenuBar menuBarra = new JMenuBar();
		setJMenuBar(menuBarra);
		crearOpcionesMenu(menuBarra);
		
		//setVisible(true);
		
		this.addWindowListener((WindowListener) new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        
		        
		        System.exit(0);  // termina la app
		    }
		});
	}
	
	public void crearOpcionesMenu(JMenuBar menuBarra) {
		//Opciones relativas a los clientes del banco
			JMenu menuClientes = new JMenu("Opciones");
			
			JMenuItem itemMain = new JMenuItem("Main");
			//ImageIcon homeImg = new ImageIcon("ProyectoAppBanco/src/resources/home.png");
			
			itemMain.setIcon(redimensionarIconoHQ("ProyectoAppBanco/src/resources/home.png", 20, 20));
			itemMain.setOpaque(true);
			
			
			menuBarra.add(menuClientes);
			menuBarra.add(itemMain);
			JMenuItem itemVerClientes = new JMenuItem("Tabla Clientes");
			JMenuItem itemVerCuentas = new JMenuItem("Tabla Cuentas");
			JMenuItem itemCrearCliente = new JMenuItem("Crear cliente");
			//JMenuItem itemOpcionesCliente = new JMenuItem("Opciones");
			JMenuItem itemGrafica = new JMenuItem("Acciones");
			
			menuClientes.add(itemVerClientes);
			menuClientes.add(itemVerCuentas);
			menuClientes.add(itemCrearCliente);
			//menuClientes.add(itemOpcionesCliente);
			menuClientes.add(itemGrafica);
			
			//Al clickar en el boton correspondiente a una pestaña esta se abre con card.show( panelCont, "identificador")
			itemVerClientes.addActionListener(e -> card.show(panelCont, "tablaClientes"));
			itemVerCuentas.addActionListener(e -> card.show(panelCont, "tablaCuentas"));
			itemMain.addActionListener(e -> {actualizarDashboard(); card.show(panelCont, "inicio");});
			itemCrearCliente.addActionListener(e -> card.show(panelCont, "crearCliente"));
			itemGrafica.addActionListener(e-> card.show(panelCont, "grafica"));
			//
	}
	
	public void crearTablaClientes(ArrayList<Cliente> listaClientes) {
		
		
		
		
		modeloTabla = new ModelTablaClientes(listaClientes);
		tablaClientes = new JTable(modeloTabla);
		tablaClientes.setRowHeight(24);
		tablaClientes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(150);
		tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(258);
		tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(200);
		
		
		

		
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
		scroller.setBorder(BorderFactory.createEmptyBorder());

		
	}
	
	public void crearTablaCuentas(ArrayList<Cuenta> listaCuentas) {
		
		modeloTablaCuentas = new ModeloTablaCuentas1(listaCuentas);
		tablaCuentas = new JTable(modeloTablaCuentas);
		tablaCuentas.setRowHeight(24);
		//tablaCuentas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablaCuentas.getColumnModel().getColumn(0).setPreferredWidth(150);
		tablaCuentas.getColumnModel().getColumn(1).setPreferredWidth(258);
		tablaCuentas.getColumnModel().getColumn(2).setPreferredWidth(200);
		tablaCuentas.getTableHeader().setPreferredSize(new Dimension(tablaCuentas.getPreferredSize().width, 32));
		TableCellRenderer renderer = (table, value, isSelected, hasFocus, row, column) -> {
			
			JLabel result = new JLabel();
			
			if (value instanceof String) {
				result.setText(value.toString());
			}else {
				result.setText(String.valueOf(value));
			
			}
			
			
			result.setHorizontalAlignment(SwingConstants.CENTER);
			
			if(row % 2 == 0) {
				result.setBackground(new Color(235, 238, 255));
			}else {
				result.setBackground(Color.WHITE);
			}
			
			result.setFont(new Font("Arial" , Font.PLAIN, 14));
			
			
			result.setOpaque(true);
			return result;
			
			
		};
		
		TableCellRenderer headerRenderer  = (table, value, isSelected, hasFocus, row, column) -> {
			JLabel result = new JLabel();
			
			result.setText(value.toString());
			result.setHorizontalAlignment(SwingConstants.CENTER);
			result.setBackground(new Color(24, 5, 92));
			result.setForeground(Color.white);
			result.setOpaque(true);
			result.setFont(new Font("Arial", Font.BOLD, 14));
			return result;
			
			
		};
		
		tablaCuentas.setDefaultRenderer(Object.class, renderer);
		tablaCuentas.getTableHeader().setDefaultRenderer(headerRenderer);
		MouseAdapter mouseAdapter = new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				int fila = tablaCuentas.rowAtPoint(e.getPoint());
				tabCliente(fila);
				panelCont.add(tabCliente(fila), "ClienteSeleccionado");
				card.show(panelCont, "ClienteSeleccionado");
			}
			
			@Override
	    	public void mouseMoved(MouseEvent e) {
	    		// TODO Auto-generated method stub
	    		
	    	}
		};
		
		tablaCuentas.addMouseListener(mouseAdapter);
		scroller = new JScrollPane(tablaCuentas);
		scroller.setBorder(BorderFactory.createEmptyBorder());
				
	}
	
	public JPanel tabCliente(int fila) {
		
		JPanel panelVistaCliente = new JPanel(new BorderLayout());
		Cliente cliente;
		if(txtFiltro.getText().equals("")) {
			cliente = listaClientes.get(fila);
		}else {
			cliente = listafiltro.get(fila);
		}
		Color azulOscuro = new Color(24, 5, 92);
		Color fondo = new Color(88, 81, 180);
		Color color2 = new Color(255, 196, 0);
		
		TableCellRenderer headerRendererCommon = (table, value, isSelected, hasFocus, row, column) -> {
			JLabel result = new JLabel();
			result.setText(value.toString());
			result.setHorizontalAlignment(SwingConstants.CENTER);
			result.setBackground(azulOscuro);
			result.setForeground(Color.white);
			result.setOpaque(true);
			result.setFont(new Font("Arial", Font.BOLD, 14));
			return result;
		};
		
		TableCellRenderer cellRendererCommon = (table, value, isSelected, hasFocus, row, column) -> {
			JLabel result = new JLabel();
			if (value instanceof String) {
				result.setText(value.toString());
			} else {
				result.setText(String.valueOf(value));
			}
			result.setHorizontalAlignment(SwingConstants.CENTER);
			
			if(row % 2 == 0) {
				result.setBackground(new Color(235, 238, 255));
			} else {
				result.setBackground(Color.WHITE);
			}
			
			if(table.getModel() instanceof ModeloTablaCuentas1 && row == filaSelec) {
				result.setBackground(new Color(155, 129, 248));
			}
			
			result.setFont(new Font("Arial" , Font.PLAIN, 14));
			result.setOpaque(true);
			return result;
		};
		
		//TITULO NOMBRE CLIENTE
		JPanel pNombre = new JPanel(new BorderLayout());
		pNombre.setBackground(new Color(24, 5, 92)); // Azul corporativo
		pNombre.setPreferredSize(new Dimension(800, 40));
		pNombre.setOpaque(true);
		
		JLabel nombre = new JLabel("  "+cliente.getApellido1() + " " +cliente.getApellido2() + ", " + cliente.getNombre() ); 
		nombre.setFont(new Font("Arial", Font.BOLD, 18));
		nombre.setForeground(Color.WHITE);
		
		JLabel dni = new JLabel("DNI: "+cliente.getDni() +"     " ); 
		dni.setFont(new Font("Arial", Font.PLAIN, 16));
		dni.setForeground(Color.WHITE);
		
		pNombre.add(nombre);
		pNombre.add(dni, BorderLayout.EAST);
		panelVistaCliente.add(pNombre, BorderLayout.NORTH);


		JPanel info = new JPanel(new GridLayout(2,3,0,0));

		//PANEL SALDO TOTAL
		JPanel panelSaldoTotal = new JPanel(new BorderLayout());
		panelSaldoTotal.setBackground(getBackground());
		panelSaldoTotal.setOpaque(true);
		JLabel labelSaldo = new JLabel(" SALDO TOTAL ");
		labelSaldo.setHorizontalAlignment(SwingConstants.CENTER);
		labelSaldo.setBackground(color2);
		labelSaldo.setOpaque(true);
		labelSaldo.setFont(new Font("Arial", Font.BOLD, 20));
		labelSaldo.setForeground(azulOscuro);
		
		JLabel saldoTotal = new JLabel(cliente.getSaldoTotal() + " €", JLabel.CENTER);
		saldoTotal.setFont(new Font("Arial", Font.BOLD, 30));
		saldoTotal.setForeground(azulOscuro);
			
		panelSaldoTotal.add(labelSaldo, BorderLayout.NORTH);
		panelSaldoTotal.add(saldoTotal);
		info.add(panelSaldoTotal);

		//PANEL TABLA MOVIMIENTOS DEL CLIENTE
		
		JPanel panelMovs = new JPanel(new BorderLayout());
		
		JLabel labelMovs = new JLabel(" MOVIMIENTOS "); 
		labelMovs.setHorizontalAlignment(SwingConstants.CENTER);
		labelMovs.setBackground(color2);
		labelMovs.setOpaque(true);
		labelMovs.setFont(new Font("Arial", Font.BOLD, 20));
		labelMovs.setForeground(azulOscuro);
		
		ArrayList<Movimiento> listaMovimientos = new ArrayList<Movimiento>();
		for(Movimiento mov : registroMovimientos) {
			if(!(mov instanceof Transferencia)) {
				if(mov instanceof Gasto) {
					if(((Gasto)mov).origen().getPropietario().equals(cliente)) {
						listaMovimientos.add(mov);
					}
				}else {
					if(((Ingreso)mov).getDestino().getPropietario().equals(cliente)) {
						listaMovimientos.add(mov);
					}
				}
			}
		}
		
		ModeloTablaMovimiento modeloMovs = new ModeloTablaMovimiento(listaMovimientos);
		JTable tablaMovimientosCliente = new JTable(modeloMovs);
		
		tablaMovimientosCliente.setShowGrid(false);
		tablaMovimientosCliente.setRowHeight(20);
		tablaMovimientosCliente.setBackground(getBackground());
		
		TableCellRenderer rendererMovs = (table, value, isSelected, hasFocus, row, column) -> {
			JLabel result = new JLabel();
			
			if(value instanceof Gasto) {
				result.setBackground(new Color(224, 103, 103));
				if ( column == 1) {
					result.setBackground(new Color(255, 207, 207));
				}
			}else if(value instanceof Ingreso) {
				result.setBackground(new Color(124, 230, 124));
				if (column == 1) {
					result.setBackground(new Color(207, 255, 207));
				}
			}
			
			if(column == 0) {
				result.setText(((Movimiento)value).getFecha().toString());
			}else if(column == 1) {
				result.setText(((Movimiento)value).getCantidad()+"");
				
			}else {
				result.setText(((Movimiento)value).getConcepto());
			}
			
			result.setHorizontalAlignment(SwingConstants.CENTER);
			result.setOpaque(true);
			return result;
			
		};
		tablaMovimientosCliente.setDefaultRenderer(Object.class, rendererMovs);
		// APLICAR CABECERA AZUL
		tablaMovimientosCliente.getTableHeader().setDefaultRenderer(headerRendererCommon);
		tablaMovimientosCliente.getTableHeader().setPreferredSize(new Dimension(tablaMovimientosCliente.getPreferredSize().width, 32));
		panelMovs.add(labelMovs, BorderLayout.NORTH);
		
		// AÑADIDO: SCROLL PANE PARA MOVIMIENTOS (ESTO HACE QUE SE VEA EL HEADER)
		JScrollPane scrollMovs = new JScrollPane(tablaMovimientosCliente);
		scrollMovs.setBorder(BorderFactory.createEmptyBorder());
		panelMovs.add(scrollMovs);
		
		
		info.add(panelMovs);
		
		//PANEL TABLA CUENTAS DEL CLIENTE
		
				JPanel panelTablaCuentas = new JPanel(new BorderLayout());
				
				JLabel labelCuentas = new JLabel(" CUENTAS "); 
				labelCuentas.setHorizontalAlignment(SwingConstants.CENTER);
				labelCuentas.setBackground(color2);
				labelCuentas.setOpaque(true);
				labelCuentas.setFont(new Font("Arial", Font.BOLD, 20));
				labelCuentas.setForeground(azulOscuro);
				
				ModeloTablaCuentas1 modeloCuentas1 = new ModeloTablaCuentas1(cliente.getListaCuentas());
				JTable tablaCuentasC = new JTable(modeloCuentas1);
				tablaCuentasC.setBackground(getBackground());
				tablaCuentasC.setRowHeight(24);
				
				tablaCuentasC.setDefaultRenderer(Object.class, cellRendererCommon);
				tablaCuentasC.getTableHeader().setDefaultRenderer(headerRendererCommon);
				tablaCuentasC.getTableHeader().setPreferredSize(new Dimension(tablaCuentasC.getPreferredSize().width, 32));

				//tablaCuentasC.getTableHeader().setDefaultRenderer(headerRenderer);
				
				JButton btnAddCuenta = new JButton("Nueva cuenta");
				
				
				tablaCuentasC.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						filaSelec = tablaCuentasC.rowAtPoint(e.getPoint());
						tablaCuentasC.repaint();
					}
				});
				
				
				
				btnAddCuenta.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						//System.out.println("ES-"+(listaCuentas.size()+1));
						Cuenta newCuenta = new Cuenta("ES"+(listaCuentas.size()+1),cliente);
						cliente.addCuenta(newCuenta);
						listaCuentas.add(newCuenta);
						gestorBD.insertarCuentasBD(newCuenta);
						//System.out.println(listaCuentas.getLast().getNumeroCuenta());
						JOptionPane.showMessageDialog(null, "Cuenta " + newCuenta.getNumeroCuenta()+ " añadida con éxito", "Cuenta añadida", JOptionPane.INFORMATION_MESSAGE);
						tablaCuentasC.repaint();
						tablaCuentas.repaint();
						modeloTablaCuentas.fireTableDataChanged();
					}
				});
				
				JScrollPane scrollCuentas = new JScrollPane(tablaCuentasC);
				scrollCuentas.setBorder(BorderFactory.createEmptyBorder());
				panelTablaCuentas.setBackground(fondo);
				panelTablaCuentas.add(btnAddCuenta, BorderLayout.SOUTH);
				panelTablaCuentas.add(scrollCuentas); // <--- AÑADE EL SCROLL, NO LA TABLA SOLA
				panelTablaCuentas.add(labelCuentas, BorderLayout.NORTH);

				info.add(panelTablaCuentas);
				
		// PANEL BOTONES DE OPERACIONES

		JPanel panelBotonesCuenta = new JPanel(new GridLayout(2,3));
		
		
			
		//panelBotonesCuenta.setBackground(fondo);
		panelBotonesCuenta.setOpaque(true);
		JButton btnIngresar = new JButton("Ingresar");
		JButton btnGastar = new JButton("Gastar");
		JButton btnSimular = new JButton("Simular Inversión");

			// JButton btnTransferir = new JButton("Transferir");

		panelBotonesCuenta.add(btnIngresar);
		panelBotonesCuenta.add(btnGastar);
		panelBotonesCuenta.add(btnSimular);
		panelBotonesCuenta.add(btnAddCuenta);
		
		
		panelVistaCliente.add(panelBotonesCuenta, BorderLayout.SOUTH);
		//info.add(panelBotonesCuenta);		
		
		//PANEL PRESTAMOS
		
		JPanel panelPrestamos = new JPanel(new BorderLayout());
		JLabel labelPrestamos = new JLabel(" PRESTAMOS ");
		labelPrestamos.setHorizontalAlignment(SwingConstants.CENTER);
		labelPrestamos.setBackground(color2);
		labelPrestamos.setOpaque(true);
		labelPrestamos.setFont(new Font("Arial", Font.BOLD, 20));
		labelPrestamos.setForeground(azulOscuro);
		
		panelPrestamos.add(labelPrestamos, BorderLayout.NORTH);
		
		JLabel deudaTotal = new JLabel("Deuda Total: " + cliente.getDeudaTotal() + " euros", JLabel.CENTER);
		deudaTotal.setFont(new Font("Arial", Font.BOLD, 16));
		deudaTotal.setBackground(Color.RED);
		deudaTotal.setOpaque(true);
		
		
		
		ModeloTablaPrestamos modeloPrestamos = new ModeloTablaPrestamos(cliente.getPrestamos());
		JTable tablaPrestamos = new JTable(modeloPrestamos);
		tablaPrestamos.setRowHeight(24);
		
		tablaPrestamos.setDefaultRenderer(Object.class, cellRendererCommon);
		tablaPrestamos.getTableHeader().setDefaultRenderer(headerRendererCommon);
		tablaPrestamos.getTableHeader().setPreferredSize(new Dimension(tablaPrestamos.getPreferredSize().width, 32));
		
		JScrollPane scrollPrestamos = new JScrollPane(tablaPrestamos);
		scrollPrestamos.setBorder(BorderFactory.createEmptyBorder());	
		
		panelPrestamos.add(scrollPrestamos);
		info.add(panelPrestamos);
		
		
		panelVistaCliente.add(info);
		
		
		JButton btnModificar = new JButton("Modificar Datos");	    
		panelBotonesCuenta.add(btnModificar);
	    
		// Listener para el BOTÓN MODIFICAR
		
	    btnModificar.addActionListener(e -> {
	        
	        JPanel panelEdicion = new JPanel(new GridLayout(4, 2, 5, 5));
	        JTextField txtNombre = new JTextField(cliente.getNombre());
	        JTextField txtAp1 = new JTextField(cliente.getApellido1());
	        JTextField txtAp2 = new JTextField(cliente.getApellido2());
	        JTextField txtDni = new JTextField(cliente.getDni());

	        panelEdicion.add(new JLabel("Nombre:"));
	        panelEdicion.add(txtNombre);
	        panelEdicion.add(new JLabel("Apellido 1:"));
	        panelEdicion.add(txtAp1);
	        panelEdicion.add(new JLabel("Apellido 2:"));
	        panelEdicion.add(txtAp2);
	        panelEdicion.add(new JLabel("DNI:"));
	        panelEdicion.add(txtDni);
	        
	        int resultado = JOptionPane.showConfirmDialog(this, panelEdicion, "Modificar Cliente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

	        if (resultado == JOptionPane.OK_OPTION) {
	            
	            if (txtNombre.getText().isEmpty() || txtDni.getText().isEmpty()) {
	                JOptionPane.showMessageDialog(this, "Nombre y DNI son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            
	            cliente.setNombre(txtNombre.getText());
	            cliente.setApellido1(txtAp1.getText());
	            cliente.setApellido2(txtAp2.getText());
	            cliente.setDni(txtDni.getText());

	            
	            boolean exito = GestorBD.UpdateCliente(cliente);

	            if (exito) {
	                JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
	                
	                
	                nombre.setText((cliente.getNombre() + " " + cliente.getApellido1() + " " + cliente.getApellido2()));
	                modeloTabla.fireTableDataChanged(); 
	                panelVistaCliente.revalidate();
	                panelVistaCliente.repaint();
	            } else {
	                JOptionPane.showMessageDialog(this, "Error al actualizar en la base de datos.", "Error BD", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });
		
		
		
	
        
        JButton btnPrestamo = new JButton("Solicitar Préstamo");
        panelBotonesCuenta.add(btnPrestamo); 

        // Listener para el BOTÓN PRESTAMO
        btnPrestamo.addActionListener(e -> {
            
            int filaSel = tablaCuentasC.getSelectedRow();
            if (filaSel == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona una cuenta para recibir el dinero.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Cuenta cuentaDestino = cliente.getListaCuentas().get(filaSel);

            
            JPanel panelInputs = new JPanel(new GridLayout(3, 2, 5, 5));
            
            JTextField txtCantidad = new JTextField();
            JTextField txtInteres = new JTextField("5.0"); 
            JTextField txtPlazo = new JTextField("12");    
            
            panelInputs.add(new JLabel("Cantidad (€):"));
            panelInputs.add(txtCantidad);
            panelInputs.add(new JLabel("Interés Anual (%):"));
            panelInputs.add(txtInteres);
            panelInputs.add(new JLabel("Plazo (meses):"));
            panelInputs.add(txtPlazo);

            
            int resultado = JOptionPane.showConfirmDialog(null, panelInputs, "Solicitud de Préstamo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            
            if (resultado == JOptionPane.OK_OPTION) {
                try {
                    double cantidad = Double.parseDouble(txtCantidad.getText());
                    double interes = Double.parseDouble(txtInteres.getText());
                    int meses = Integer.parseInt(txtPlazo.getText());

                    if (cantidad <= 0 || meses <= 0) {
                        JOptionPane.showMessageDialog(this, "La cantidad y el plazo deben ser positivos.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    
                    boolean exito = cliente.solicitarPrestamo(cantidad, interes, meses, cuentaDestino);

                    if (exito) {
                        
                        Prestamo nuevoP = cliente.getPrestamos().get(cliente.getPrestamos().size() - 1);
                        
                        gestorBD.insertarPrestamoBD(nuevoP);

                        Movimiento movPrestamo = new Ingreso(LocalDate.now(), (float)cantidad, "Préstamo Concedido", cuentaDestino);
                        registroMovimientos.add(movPrestamo);
                        
                        gestorBD.insertarIngresoBD((Ingreso)movPrestamo);
                        
                        gestorBD.UpdateCuenta(cuentaDestino);

                        modeloCuentas1.fireTableDataChanged();
                        modeloPrestamos.fireTableDataChanged();
                        saldoTotal.setText(cliente.getSaldoTotal() + " €");
                        deudaTotal.setText("Deuda Total: " + String.format("%.2f", cliente.getDeudaTotal()) + " euros");

                        JOptionPane.showMessageDialog(this, "¡Préstamo concedido e ingresado en cuenta!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Por favor, introduce números válidos.", "Error formato", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
		
		// Listener para el BOTÓN INGRESAR
		btnIngresar.addActionListener(e->{
			int filaSel = tablaCuentasC.getSelectedRow();
			if (filaSel == -1) { // Si no selecciona la cuenta
				JOptionPane.showMessageDialog(this, "Por favor, selecciona una cuenta primero.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Cuenta cuentaSeleccionada = cliente.getListaCuentas().get(filaSel);
			String sCantidad = JOptionPane.showInputDialog(this, "Cantidad a ingresar:", "Ingreso", JOptionPane.PLAIN_MESSAGE);
			String concepto = JOptionPane.showInputDialog(this, "Concepto:", "Ingreso", JOptionPane.PLAIN_MESSAGE);
			Movimiento newMov = new Ingreso(LocalDate.now(), Float.parseFloat(sCantidad), concepto, cuentaSeleccionada);
			registroMovimientos.add(newMov);
			listaMovimientos.add(newMov);
			
			try {
				float cantidad = Float.parseFloat(sCantidad);
				cuentaSeleccionada.ingreso(cantidad, concepto); //Llama al método de Cuenta
				gestorBD.insertarIngresoBD((Ingreso)newMov);
				gestorBD.UpdateCuenta(((Ingreso)newMov).getDestino());
				// Refrescar la tabla de cuentas y el saldo total
				modeloCuentas1.fireTableDataChanged(); //fireTableDataChanged: indica que el contenido ha cambiado y tiene que redibujarse 
				saldoTotal.setText(cliente.getSaldoTotal() + " €"); // Actualiza el saldo
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Cantidad no válida.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			//System.out.println(((Ingreso)newMov).getDestino().getSaldo());
			tablaMovimientosCliente.repaint();
		});
		
		//Listener para el Boton gastar
		btnGastar.addActionListener(e -> {
			int filaSel = tablaCuentasC.getSelectedRow();
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
					Movimiento newMov = new Gasto(LocalDate.now(), Float.parseFloat(sCantidad), concepto, cuentaSeleccionada);
					registroMovimientos.add(newMov);
					listaMovimientos.add(newMov);
					gestorBD.insertarGastoBD((Gasto)newMov);
					 
					gestorBD.UpdateCuenta(((Gasto)newMov).getOrigen());
					//System.out.println(registroMovimientos.getLast());
					modeloCuentas1.fireTableDataChanged();
					saldoTotal.setText(cliente.getSaldoTotal() + " €");
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Cantidad no válida.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			tablaMovimientosCliente.repaint();
			
		});
		
		// Boton para Recursividad, simular capital futura con 5% de interes
	    btnSimular.addActionListener(e -> {
	        try {
	            String sCapital = JOptionPane.showInputDialog(this, "Capital inicial a invertir (€):", "Calculadora Recursiva", JOptionPane.QUESTION_MESSAGE);
	            String sAnios = JOptionPane.showInputDialog(this, "¿A cuántos años?", "Calculadora Recursiva", JOptionPane.QUESTION_MESSAGE);

	            if (sCapital != null && sAnios != null) {
	                float capital = Float.parseFloat(sCapital);
	                int anios = Integer.parseInt(sAnios);
	                float interesFijo = 5.0f; // 5% de interés

	                float beneficioFinal = calcularBeneficioRecursivo(capital, interesFijo, anios);


	                JOptionPane.showMessageDialog(this, 
	                    "Cálculo realizado:\n" +
	                    "Invirtiendo " + capital + "€ al " + interesFijo + "% durante " + anios + " años,\n" +
	                    "obtendrás: " + String.format("%.2f", beneficioFinal) + " €",
	                    "Resultado Inversión", JOptionPane.INFORMATION_MESSAGE);
	            }
	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(this, "Por favor, introduce números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    });
		
		
		
		return panelVistaCliente;
		
		
		
	}
	
	public JPanel tabCrearCliente() {
		
		JPanel addClientePanel = new JPanel(new BorderLayout());
		
		JPanel ptituloCrearCliente = new JPanel(new BorderLayout());
		ptituloCrearCliente.setBackground(new Color(24, 5, 92)); // Azul corporativo
		ptituloCrearCliente.setPreferredSize(new Dimension(800, 40));
		
		JLabel titulo = new JLabel(" INTRODUZCA DATOS DEL NUEVO CLIENTE "); 
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setFont(new Font("Arial", Font.PLAIN, 22));
		titulo.setForeground(Color.WHITE);
		ptituloCrearCliente.add(titulo);
		
		//Contenido del Panel
		addClientePanel.add(ptituloCrearCliente, BorderLayout.NORTH);
		JPanel camposTextoPanel = new JPanel(new GridLayout(4, 1, 10,10));
		
		
		JTextField campoNombre = new JTextField();
		JTextField campoApellido1 = new JTextField();
		JTextField campoApellido2 = new JTextField();
		JTextField campoDNI = new JTextField();
		
		
		// Panel Nombre
		JPanel panelNombre = new JPanel(new BorderLayout());
		panelNombre.setBorder(BorderFactory.createCompoundBorder(
		        BorderFactory.createTitledBorder("Nombre"),
		        BorderFactory.createEmptyBorder(10, 15, 15, 15) // top, left, bottom, right
		));
		panelNombre.add(campoNombre, BorderLayout.CENTER);

		// Panel Apellido 1
		JPanel panelApellido1 = new JPanel(new BorderLayout());
		panelApellido1.setBorder(BorderFactory.createCompoundBorder(
		        BorderFactory.createTitledBorder("Apellido 1"),
		        BorderFactory.createEmptyBorder(10, 15, 15, 15)
		));
		panelApellido1.add(campoApellido1, BorderLayout.CENTER);

		// Panel Apellido 2
		JPanel panelApellido2 = new JPanel(new BorderLayout());
		panelApellido2.setBorder(BorderFactory.createCompoundBorder(
		        BorderFactory.createTitledBorder("Apellido 2"),
		        BorderFactory.createEmptyBorder(10, 15, 15, 15)
		));
		panelApellido2.add(campoApellido2, BorderLayout.CENTER);

		// Panel DNI
		JPanel panelDNI = new JPanel(new BorderLayout());
		panelDNI.setBorder(BorderFactory.createCompoundBorder(
		        BorderFactory.createTitledBorder("DNI"),
		        BorderFactory.createEmptyBorder(10, 15, 15, 15)
		));
		panelDNI.add(campoDNI, BorderLayout.CENTER);
		
		
        camposTextoPanel.add(panelNombre);
        camposTextoPanel.add(panelApellido1);
        camposTextoPanel.add(panelApellido2);
        camposTextoPanel.add(panelDNI);
		
		
		camposTextoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		addClientePanel.add(camposTextoPanel);
		
		JPanel botones = new JPanel();
		
		JButton botonCrearCliente = new JButton("CREAR CLIENTE");
		botonCrearCliente.setOpaque(true);
		botonCrearCliente.setFont(new Font("Arial", Font.BOLD, 16));
		botonCrearCliente.setForeground(Color.black);
		botonCrearCliente.setBackground(new Color(235, 238, 255));
		
		botonCrearCliente.setPreferredSize(new Dimension(640,40));
		botones.add(botonCrearCliente);
		addClientePanel.add(botonCrearCliente, BorderLayout.SOUTH);
		
		botonCrearCliente.addActionListener(e -> {
			String nombreNuevoCliente = campoNombre.getText();
			String apellido1NuevoCliente = campoApellido1.getText();
			String apellido2NuevoCliente = campoApellido2.getText();
			String dniNuevoCliente = campoDNI.getText();
			Cliente newCliente = new Cliente(nombreNuevoCliente, apellido1NuevoCliente, apellido2NuevoCliente, dniNuevoCliente);
			listaClientes.add(newCliente);
			gestorBD.insertarClientesEnBD(newCliente);
			modeloTabla.fireTableDataChanged();
			actualizarDashboard();
			tablaClientes.repaint();
			JOptionPane.showMessageDialog(null, "El cliente se ha añadido con éxito.", "Cliente añadido", JOptionPane.INFORMATION_MESSAGE);
		});
		
		
		
		return addClientePanel;
	}
	
	
	public JPanel crearMainPanel() {
		JPanel main = new JPanel(new BorderLayout());
		main.setBackground(Color.WHITE);

		JPanel pNorte = new JPanel(new BorderLayout());
		pNorte.setBackground(new Color(24, 5, 92)); // Azul corporativo
		pNorte.setPreferredSize(new Dimension(800, 60));
		
		JLabel titulo = new JLabel("  DEUSTOBANK PRINCIPAL"); 
		titulo.setFont(new Font("Arial", Font.BOLD, 24));
		titulo.setForeground(Color.WHITE);
		
		
		JLabel lblReloj = new JLabel("00:00:00  "); 
		lblReloj.setFont(new Font("Arial", Font.BOLD, 18));
		lblReloj.setForeground(Color.WHITE);
		
		javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
			java.time.LocalDateTime ahora = java.time.LocalDateTime.now(); // La hora actual 
			String hora = ahora.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")); // Horas : Minutos : Segundos
			lblReloj.setText(hora + "  ");
		});
		timer.start();

		pNorte.add(titulo, BorderLayout.WEST);
		pNorte.add(lblReloj, BorderLayout.EAST);
		
		main.add(pNorte, BorderLayout.NORTH); 

		
		int numCuentas = 0;
		double dineroTotal = 0;
		
		for(Cliente c : listaClientes) {
			numCuentas = numCuentas + c.getListaCuentas().size();
			dineroTotal = dineroTotal + c.getSaldoTotal();
		}

		
		JPanel pCentro = new JPanel(new GridLayout(2, 1, 10, 10)); 
		pCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
		
		JPanel pStats = new JPanel(new GridLayout(1, 3, 20, 0)); 
		
		
		JPanel pStat1 = new JPanel(new GridLayout(2,1));
		pStat1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pStat1.setBackground(new Color(230, 230, 255)); 
		JLabel l1 = new JLabel("CLIENTES", JLabel.CENTER);
		lblTotalClientes = new JLabel("0", JLabel.CENTER); 
		lblTotalClientes.setFont(new Font("Arial", Font.BOLD, 30));
		pStat1.add(lblTotalClientes);
		pStat1.add(l1);
		
		
		JPanel pStat2 = new JPanel(new GridLayout(2,1));
		pStat2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pStat2.setBackground(new Color(230, 255, 230)); 
		JLabel l2 = new JLabel("CUENTAS", JLabel.CENTER);
		lblTotalCuentas = new JLabel("0", JLabel.CENTER);
		lblTotalCuentas.setFont(new Font("Arial", Font.BOLD, 30));
		pStat2.add(lblTotalCuentas);
		pStat2.add(l2);
		
		
		JPanel pStat3 = new JPanel(new GridLayout(2,1));
		pStat3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pStat3.setBackground(new Color(255, 255, 230)); 
		JLabel l3 = new JLabel("CAPITAL TOTAL", JLabel.CENTER);
		lblCapitalTotal = new JLabel("0.00 €", JLabel.CENTER);
		lblCapitalTotal.setFont(new Font("Arial", Font.BOLD, 24));
		pStat3.add(lblCapitalTotal);
		pStat3.add(l3);
		actualizarDashboard();
		
		pStats.add(pStat1);
		pStats.add(pStat2);
		pStats.add(pStat3);
		
		
		
		JPanel pBotones = new JPanel(new GridLayout(1, 2, 40, 0));
		pBotones.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
		pBotones.setBackground(getBackground());
		
		Color azulCorporativoTexto = new Color(24, 5, 92);
		
		Icon iconoClientes = redimensionarIconoHQ("ProyectoAppBanco/src/resources/Deustoclientespng.png", 80, 80);		
		Icon iconoGraficas = redimensionarIconoHQ("ProyectoAppBanco/src/resources/Deustobankgrafica.png", 80, 80);	
		
		
		JButton b1 = new JButton("GESTIONAR CLIENTES", iconoClientes);
		b1.setFont(new Font("Arial", Font.BOLD, 16));
		b1.setForeground(azulCorporativoTexto);
		b1.setBackground(Color.LIGHT_GRAY);
		b1.setBorderPainted(false);
		b1.setContentAreaFilled(false);
		b1.setFocusPainted(false); // Quitar el borde 3D de los botones
		b1.setOpaque(false);
		b1.setVerticalTextPosition(SwingConstants.BOTTOM);
		b1.setHorizontalTextPosition(SwingConstants.CENTER);
		
		JButton b2 = new JButton("VER GRÁFICAS", iconoGraficas);
		b2.setFont(new Font("Arial", Font.BOLD, 18));
		b2.setForeground(azulCorporativoTexto);
		b2.setBackground(getBackground());
		b2.setFocusPainted(false);
		b2.setContentAreaFilled(false);
		b2.setBorderPainted(false);
		b2.setOpaque(true);
		b2.setVerticalTextPosition(SwingConstants.BOTTOM);
		b2.setHorizontalTextPosition(SwingConstants.CENTER);
		
		b1.addActionListener(e -> card.show(panelCont, "tablaClientes")); // CardLayout para tabla de clientes
		b2.addActionListener(e -> card.show(panelCont, "grafica")); // CardLayout para inversiones
		
		pBotones.add(b1);
		pBotones.add(b2);
		
		
		pCentro.add(pStats);
		pCentro.add(pBotones);
		
		main.add(pCentro, BorderLayout.CENTER); 

		return main;
	}
	
	public void actualizarDashboard() {
		int numClientes = listaClientes.size();
		int numCuentas = 0;
		double dineroTotal = 0;
		
		for(Cliente c : listaClientes) {
			numCuentas += c.getListaCuentas().size();
			dineroTotal += c.getSaldoTotal();
		}
		
		if (lblTotalClientes != null) lblTotalClientes.setText(String.valueOf(numClientes));
		if (lblTotalCuentas != null) lblTotalCuentas.setText(String.valueOf(numCuentas));
		if (lblCapitalTotal != null) lblCapitalTotal.setText(String.format("%,.2f €", dineroTotal));
		if (numeroClientes != null) numeroClientes.setText(" Nº de Clientes: " + listaClientes.size() + "   ");
		if (numeroCuentas != null) numeroCuentas.setText(" Nº de Cuentas: " + listaCuentas.size() + "   ");

	}
	
	public JPanel tabTablaClientes() {
		
		
		JPanel pTitulo = new JPanel(new BorderLayout());
		pTitulo.setBackground(new Color(24, 5, 92)); // Azul corporativo
		pTitulo.setPreferredSize(new Dimension(800, 40));
		
		JLabel titulo = new JLabel(" CLIENTES DEUSTOBANK "); 
		titulo.setFont(new Font("Arial", Font.BOLD, 18));
		titulo.setForeground(Color.WHITE);
		
		JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		panelFiltro.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		panelFiltro.setPreferredSize(new Dimension(400, 50));
		panelFiltro.setBackground(new Color(235, 238, 255));
		JLabel filtroTitulo = new JLabel("Búsqueda por nombre");
		
		txtFiltro = new JTextField(20);
		
		
		txtFiltro.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				filtrarClientes();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				filtrarClientes();
				tablaClientes.repaint();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		panelFiltro.add(filtroTitulo);
		panelFiltro.add(txtFiltro);
		
		numeroClientes = new JLabel(" Nº de Clientes: " + listaClientes.size() + "   "); 
		numeroClientes.setFont(new Font("Arial", Font.PLAIN, 18));
		numeroClientes.setForeground(Color.WHITE);
		
		pTitulo.add(numeroClientes, BorderLayout.EAST);
		pTitulo.add(titulo, BorderLayout.WEST);
		
		JPanel panelTablaClientes = new JPanel(new BorderLayout());
		
		crearTablaClientes(listaClientes);
		JPanel panelBotones = new JPanel(new FlowLayout());
		
		//Image imagen = ((ImageIcon) icono).getImage().getScaledInstance(40, 50, Image.SCALE_DEFAULT);
		
		
		Icon icono2 = redimensionarIconoHQ("ProyectoAppBanco/src/resources/addUser.png", 40, 40);
		Icon icono1 = redimensionarIconoHQ("ProyectoAppBanco/src/resources/sortasc.png", 40, 40);
		
		JButton botonAddCliente = new JButton(icono2);
		//botonAddCliente.setIcon(new ImageIcon());
		botonAddCliente.setOpaque(true);
		botonAddCliente.setBackground(new Color(235, 238, 255));
		botonAddCliente.addActionListener(e -> card.show(panelCont, "crearCliente") );
		
		
		JButton botonSort = new JButton(icono1);
		botonSort.setBackground(new Color(235, 238, 255));
		botonSort.addActionListener(e -> {
			
			if(txtFiltro.getText().equals("")) {
				ordenarPorSaldoRecursivo(listaClientes, listaClientes.size());
				tablaClientes.repaint();
				modeloTabla.fireTableDataChanged();
			}else {
				ordenarPorSaldoRecursivo(listafiltro, listafiltro.size());
				tablaClientes.repaint();
			}
			
			
		});
		
		botonAddCliente.setOpaque(true);
		
		
		
		
		panelBotones.add(botonSort);
		panelBotones.add(botonAddCliente);
		panelBotones.add(panelFiltro);
		
		panelTablaClientes.add(pTitulo, BorderLayout.NORTH);
		panelTablaClientes.add(scroller);
		panelTablaClientes.add(panelBotones, BorderLayout.SOUTH);
		
		
		
		return panelTablaClientes;
	}
	
	public JPanel tabTablaCuentas() {
		JPanel panelTablaCuentas = new JPanel(new BorderLayout());
		
		JPanel pTitulo = new JPanel(new BorderLayout());
		pTitulo.setBackground(new Color(24, 5, 92)); // Azul corporativo
		pTitulo.setPreferredSize(new Dimension(800, 40));
		
		numeroCuentas = new JLabel(" Nº de Cuentas: " + listaCuentas.size() + "   "); 
		numeroCuentas.setFont(new Font("Arial", Font.PLAIN, 18));
		numeroCuentas.setForeground(Color.WHITE);
		pTitulo.add(numeroCuentas, BorderLayout.EAST);
		
		JLabel titulo = new JLabel(" CUENTAS DEUSTOBANK "); 
		titulo.setFont(new Font("Arial", Font.BOLD, 18));
		titulo.setForeground(Color.WHITE);
		
		pTitulo.add(titulo);
		panelTablaCuentas.add(pTitulo, BorderLayout.NORTH);
		
		crearTablaCuentas(listaCuentas);
		JPanel panelBotones = new JPanel();
		JButton botonAddCuenta = new JButton("Añadir Cuenta");
		
		botonAddCuenta.addActionListener(e -> card.show(panelCont, "TablaCuentas"));
		panelBotones.add(botonAddCuenta);
		
		panelTablaCuentas.add(scroller);
		//panelTablaCuentas.add(panelBotones, BorderLayout.SOUTH);

		
		return panelTablaCuentas;
	}
	
	public JPanel tabGrafica() {
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel tituloPanel = new JPanel(new BorderLayout());
		
		
		JLabel titulo = new JLabel("ACCIONES DEUSTOBANK");
		titulo.setFont(new Font("Arial", Font.BOLD, 20));
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		
		titulo.setForeground(Color.white);
		
		
		
		tituloPanel.add(titulo);
		tituloPanel.setBackground(new Color(24, 5, 92));
		tituloPanel.setOpaque(true);
		tituloPanel.setPreferredSize(new Dimension(800,60));
		
		panel.add(tituloPanel, BorderLayout.NORTH);
		panel.add(new GraficaAcciones());
		return panel;
	}
	
	public void iniciarCardLayout(CardLayout card) {
		
		panelCont = new JPanel(card);
		
		//Panel Principal 
		
		JPanel main = crearMainPanel();
		
		
		//Panel de la tabla de clientes
		
		JPanel panelTablaClientes = tabTablaClientes();
		
			
		//Panel de creación de clientes
		
		JPanel addClientePanel = tabCrearCliente();
		
		//Panel tabla cuentas
		
		JPanel panelTablaCuentas = tabTablaCuentas();
	
		//Panel Grafica (hilos)
		
		JPanel panelGrafica = tabGrafica();
		
		//Añadir paneles al panel contenedor
		
		panelCont.add(main, "inicio");
		panelCont.add(panelTablaClientes, "tablaClientes");
		panelCont.add(addClientePanel, "crearCliente");
		panelCont.add(panelTablaCuentas, "tablaCuentas");
		panelCont.add(panelGrafica, "grafica");
		card.show(panelCont, "inicio");
		
		add(panelCont);
	}
	
	
	//IAG: Método para redimensionar imagenes sin mucho pixelado, Generado con chatGPT.
	public static ImageIcon redimensionarIconoHQ(String ruta, int ancho, int alto) {
	    ImageIcon iconoOriginal = new ImageIcon(ruta);
	    Image imagenOriginal = iconoOriginal.getImage();

	    BufferedImage imagenEscalada = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = imagenEscalada.createGraphics();

	    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	    g2d.drawImage(imagenOriginal, 0, 0, ancho, alto, null);
	    g2d.dispose();

	    return new ImageIcon(imagenEscalada);
	}
	
	
	public float calcularBeneficioRecursivo(float capitalActual, float interesAnual, int aniosRestantes) {
		// Caso Base
		if (aniosRestantes == 0) {
			return capitalActual;
		}
		// Caso Recursivo
		float nuevoCapital = capitalActual + (capitalActual * (interesAnual / 100));
		return calcularBeneficioRecursivo(nuevoCapital, interesAnual, aniosRestantes - 1);
		
		
	}
	
	
	public static void ordenarPorSaldoRecursivo(ArrayList<Cliente> lista, int n) {
		
		if(n <= 1) {
			//System.out.println("Ordenada");
		}else {
			
			for(int i = 0 ; i < n; i ++) {
				if(i+1 < lista.size()) {
					if(lista.get(i).getSaldoTotal() < lista.get(i+1).getSaldoTotal()) {
						Cliente auxiliar = lista.get(i);
						lista.set(i, lista.get(i+1));
						lista.set(i+1, auxiliar);
						
					}
				}
				
			}
			
			ordenarPorSaldoRecursivo(lista, n-1);
		}
		
	}
	
	public void filtrarClientes() {
		
		listafiltro = new ArrayList<Cliente>();
		for(Cliente cl: listaClientes) {
			String fullName = cl.getNombre() + " " + cl.getApellido1() + " " + cl.getApellido2();
			
			
			if(fullName.contains(txtFiltro.getText())) {
				//System.out.println(fullName);
				listafiltro.add(cl);
			};
			
		}
		
		//modeloTabla = new ModelTablaClientes(listafiltro);
		//ModelTablaClientes modeloFiltro = new ModelTablaClientes(listafiltro);
		modeloTabla = new ModelTablaClientes(listafiltro);
		tablaClientes.setModel(modeloTabla);
		//tablaClientes.setModel(modeloTabla);
		
		
		tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(150);
		tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(258);
		tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(200);
		
	}
}
