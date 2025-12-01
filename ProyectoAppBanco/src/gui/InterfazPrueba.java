package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.table.TableCellRenderer;

import db.GestorBD;
import domain.Cliente;
import domain.Cuenta;
import domain.Gasto;
import domain.Ingreso;
import domain.Movimiento;
import domain.Transferencia;

public class InterfazPrueba extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Cliente> listaClientes;
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

	
	public InterfazPrueba(ArrayList<Cliente> listaClientes, ArrayList<Cuenta> listaCuenta){
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Interfaz Banco");
		setSize(640,480);
		setLocationRelativeTo(null);
		this.listaClientes = listaClientes;
		this.listaCuentas = listaCuenta;

		//Movimientos de prueba
		registroMovimientos.add(new Ingreso(LocalDate.now(), 1000.0f, "prueba", listaCuenta.get(0)));
		registroMovimientos.add(new Ingreso(LocalDate.now(), 1000.0f, "prueba", listaCuenta.get(0)));
		registroMovimientos.add(new Gasto(LocalDate.now(), 500.0f, "prueba", listaCuenta.get(0)));
		//iniciar CardLayout
		card = new CardLayout();
		iniciarCardLayout(card);
		
		//Barra Menu y Opciones
		JMenuBar menuBarra = new JMenuBar();
		setJMenuBar(menuBarra);
		crearOpcionesMenu(menuBarra);
		
		//setVisible(true);
	}
	
	public void crearOpcionesMenu(JMenuBar menuBarra) {
		//Opciones relativas a los clientes del banco
			JMenu menuClientes = new JMenu("Opciones");
			
			JMenuItem itemMain = new JMenuItem("Main");
			itemMain.setIcon(new ImageIcon("resources/home.png"));
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
		tablaClientes.setRowHeight(20);
		tablaClientes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(50);
		tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(270);
		tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(200);
		
		TableCellRenderer renderer = (table, value, isSelected, hasFocus, row, column) -> {
			
			JLabel result = new JLabel();
			
			if (value instanceof String) {
				result.setText(value.toString());
			}else {
				result.setText(String.valueOf(value));
//				if(value instanceof Float){
//					
////					if( Float.valueOf((Float)value)< 0.0) {
////						result.setForeground(Color.red);
////					}else {
////						result.setForeground(Color.green);
////					}
//					
//				}
			}
			
			
			result.setHorizontalAlignment(SwingConstants.CENTER);
			
			if(row % 2 == 0) {
				result.setBackground(new Color(235, 238, 255));
			}else {
				result.setBackground(Color.WHITE);
			}
			
			
			
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
		tablaClientes.getTableHeader().setPreferredSize(new Dimension(tablaClientes.getPreferredSize().width,32));
		tablaClientes.setDefaultRenderer(Object.class, renderer);
		tablaClientes.getTableHeader().setDefaultRenderer(headerRenderer);
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
	
	public void crearTablaCuentas(ArrayList<Cuenta> listaCuentas) {
		
		modeloTablaCuentas = new ModeloTablaCuentas1(listaCuentas);
		tablaCuentas = new JTable(modeloTablaCuentas);
		tablaCuentas.setRowHeight(20);
		tablaCuentas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablaCuentas.getColumnModel().getColumn(0).setPreferredWidth(220);
		tablaCuentas.getColumnModel().getColumn(1).setPreferredWidth(270);

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
				
	}
	
	public JPanel tabCliente(int fila) {
		JPanel panelVistaCliente = new JPanel(new BorderLayout());
		Cliente cliente = listaClientes.get(fila);

		JLabel nombre = new JLabel((cliente.getNombre()+ " "+ cliente.getApellido1()+" "+cliente.getApellido2()).toUpperCase() );
		nombre.setFont(new Font("Arial", Font.BOLD, 18));


		JPanel info = new JPanel(new GridLayout(0,2,10,10));

		JLabel saldoTotal = new JLabel("Saldo Total: " + cliente.getSaldoTotal() + " euros", JLabel.CENTER);
		saldoTotal.setFont(new Font("Arial", Font.BOLD, 18));
		saldoTotal.setBackground(Color.LIGHT_GRAY);
		saldoTotal.setOpaque(true);
		info.add(saldoTotal);

		JLabel deudaTotal = new JLabel("Deuda Total: " + cliente.getDeudaTotal() + " euros", JLabel.CENTER);
		deudaTotal.setFont(new Font("Arial", Font.BOLD, 16));
		deudaTotal.setBackground(Color.RED);
		deudaTotal.setOpaque(true);
		
		ModeloTablaPrestamos modeloPrestamos = new ModeloTablaPrestamos(cliente.getPrestamos());
		JTable tablaPrestamos = new JTable(modeloPrestamos);
		JScrollPane scrollPrestamos = new JScrollPane(tablaPrestamos);
		scrollPrestamos.setBorder(BorderFactory.createTitledBorder("Préstamos Activos"));
	

		JPanel panelTablaCuentas = new JPanel(new BorderLayout());
		
		
		
		ModeloTablaCuentas1 modeloCuentas1 = new ModeloTablaCuentas1(cliente.getListaCuentas());
		JTable tablaCuentasC = new JTable(modeloCuentas1);
		
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
			
			if(row == filaSelec) {
				result.setBackground(new Color(155, 129, 248));
			}
			
			
			
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
		tablaCuentasC.setDefaultRenderer(Object.class, renderer);
		tablaCuentasC.getTableHeader().setDefaultRenderer(headerRenderer);
		
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
				Cuenta newCuenta = new Cuenta(cliente);
				cliente.addCuenta(newCuenta);
				listaCuentas.add(newCuenta);
				System.out.println(listaCuentas.getLast().getNumeroCuenta());
				JOptionPane.showMessageDialog(null, "Cuenta " + newCuenta.getNumeroCuenta()+ " añadida con éxito", "Cuenta añadida", JOptionPane.INFORMATION_MESSAGE);
				tablaCuentasC.repaint();
				tablaCuentas.repaint();
				modeloTablaCuentas.fireTableDataChanged();
			}
		});
		
//		JScrollPane scroll = new JScrollPane(tablaCuentasC);
		
		panelTablaCuentas.add(btnAddCuenta, BorderLayout.SOUTH);
		panelTablaCuentas.add(tablaCuentasC);
		panelTablaCuentas.add(tablaCuentasC.getTableHeader(), BorderLayout.NORTH);

		info.add(panelTablaCuentas);

		//info.add(new JLabel("Informacion sobre movimientos (gastos/ingresos) y transacciones"));
		
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
		
		
		
		
		
		
		
		// Panel de botones de Operaciones

		JPanel panelBotonesCuenta = new JPanel();
		JButton btnIngresar = new JButton("Ingresar");
		JButton btnGastar = new JButton("Gastar");
		JButton btnSimular = new JButton("Simular Inversión");

		// JButton btnTransferir = new JButton("Transferir");

		panelBotonesCuenta.add(btnIngresar);
		panelBotonesCuenta.add(btnGastar);
		panelBotonesCuenta.add(btnSimular);
		info.add(panelBotonesCuenta);
		info.add(scrollPrestamos);
		
		info.add(tablaMovimientosCliente);
		
		
		
		
		
		panelVistaCliente.add(info);
		panelVistaCliente.add(nombre, BorderLayout.NORTH);
		
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

	            
	            boolean exito = gestorBD.UpdateCliente(cliente);

	            if (exito) {
	                JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
	                
	                
	                nombre.setText((cliente.getNombre() + " " + cliente.getApellido1() + " " + cliente.getApellido2()).toUpperCase());
	                modeloTabla.fireTableDataChanged(); 
	                panelVistaCliente.revalidate();
	                panelVistaCliente.repaint();
	            } else {
	                JOptionPane.showMessageDialog(this, "Error al actualizar en la base de datos.", "Error BD", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });
		
		
		
		// Ayuda de IA para algun apartado de prestamos
        
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
                        
                        modeloCuentas1.fireTableDataChanged(); // Refrescar tabla cuentas
                        modeloPrestamos.fireTableDataChanged();
                        saldoTotal.setText("Saldo Total: " + cliente.getSaldoTotal() + " euros"); // Refrescar saldo
                        deudaTotal.setText("Deuda Total: " + String.format("%.2f", cliente.getDeudaTotal()) + " euros"); // Refrescar deuda
                        
                        Movimiento movPrestamo = new Ingreso(LocalDate.now(), (float)cantidad, "Préstamo Concedido", cuentaDestino);
                        registroMovimientos.add(movPrestamo);

                        JOptionPane.showMessageDialog(this, "¡Préstamo concedido y dinero ingresado!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
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
				// Refrescar la tabla de cuentas y el saldo total
				modeloCuentas1.fireTableDataChanged(); //fireTableDataChanged: indica que el contenido ha cambiado y tiene que redibujarse 
				saldoTotal.setText("Saldo Total: " + cliente.getSaldoTotal() + " euros"); // Actualiza el saldo
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Cantidad no válida.", "Error", JOptionPane.ERROR_MESSAGE);
			}
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
					System.out.println(registroMovimientos.getLast());
					modeloCuentas1.fireTableDataChanged();
					saldoTotal.setText("Saldo Total: " + cliente.getSaldoTotal() + " euros"); //Actualiza el saldo
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
		
		
		
		JPanel pBotones = new JPanel(new GridLayout(1, 2, 20, 0));
		pBotones.setBorder(BorderFactory.createTitledBorder("Accesos directos"));
		
		JButton b1 = new JButton("GESTIONAR CLIENTES");
		b1.setFont(new Font("Arial", Font.BOLD, 16));
		
		JButton b2 = new JButton("VER GRÁFICAS");
		b2.setFont(new Font("Arial", Font.BOLD, 16));
		
		
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
	}
	
	public JPanel tabTablaClientes() {
		
		JPanel panelTablaClientes = new JPanel(new BorderLayout());
		
		crearTablaClientes(listaClientes);
		JPanel panelBotones = new JPanel();
		
		//Image imagen = ((ImageIcon) icono).getImage().getScaledInstance(40, 50, Image.SCALE_DEFAULT);
		
		
		Icon icono2 = redimensionarIconoHQ("src/resources/addUser.png", 60, 80);
		
		JButton botonAddCliente = new JButton(icono2);
		//botonAddCliente.setIcon(new ImageIcon());
		botonAddCliente.setOpaque(true);
		
		botonAddCliente.addActionListener(e -> card.show(panelCont, "crearCliente") );
		panelBotones.add(botonAddCliente);
		
		

		panelTablaClientes.add(scroller);
		panelTablaClientes.add(panelBotones, BorderLayout.WEST);
		
		
		
		return panelTablaClientes;
	}
	
	public JPanel tabTablaCuentas() {
		JPanel panelTablaCuentas = new JPanel(new BorderLayout());
		
		crearTablaCuentas(listaCuentas);
		JPanel panelBotones = new JPanel();
		JButton botonAddCuenta = new JButton("Añadir Cuenta");
		
		botonAddCuenta.addActionListener(e -> card.show(panelCont, "TablaCuentas"));
		panelBotones.add(botonAddCuenta);
		
		panelTablaCuentas.add(scroller);
		panelTablaCuentas.add(panelBotones, BorderLayout.WEST);

		
		return panelTablaCuentas;
	}
	
	public JPanel tabGrafica() {
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel tituloPanel = new JPanel(new BorderLayout());
		
		
		JLabel titulo = new JLabel("ACCIONESS DEUSTOBANK");
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

}
