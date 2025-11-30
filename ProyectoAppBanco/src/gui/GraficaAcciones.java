package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class GraficaAcciones extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Integer> valores = new ArrayList<>();
	private List<Integer> valores2 = new ArrayList<>();
    private Random random = new Random();
    private boolean dbankSi = false;
    private boolean otroSi = false;
   
	
    public GraficaAcciones() {
    	this.setLayout(new BorderLayout());
    	JPanel leyenda = new JPanel(new GridLayout(2,1,50,100));
    	leyenda.setBorder(new EmptyBorder(5,10,20,10));
    	
    	
    	
    	JPanel panelDBank = new JPanel(new BorderLayout());
    	JLabel dBank = new JLabel("DeustoBank");
    	dBank.setHorizontalAlignment(SwingConstants.CENTER);
    	dBank.setForeground(Color.WHITE);
    	dBank.setFont(new Font("Arial", Font.BOLD, 14));
    	panelDBank.setBackground(new Color(24, 5, 92));
    	panelDBank.setPreferredSize(new Dimension(100,80));
    	panelDBank.add(dBank);
    	
    	JPanel panelOtro = new JPanel(new BorderLayout());
    	JLabel otro = new JLabel("Santander");
    	otro.setHorizontalAlignment(SwingConstants.CENTER);
    	otro.setFont(new Font("Arial", Font.BOLD,14));
    	otro.setForeground(Color.white);
    	panelOtro.setBackground(Color.red);
    	panelOtro.add(otro);
    	
    	
    	panelDBank.addMouseListener(new MouseAdapter() {
    		
    		@Override
    		public void mouseClicked(MouseEvent e) {
    			// TODO Auto-generated method stub
    			if(dbankSi == true) {
    				dbankSi = false;
    				panelDBank.setBackground(new Color(24, 5, 92));
    		
    			}else {
    				
    				if(otroSi == true) {
    					panelOtro.setBackground(Color.red);
    					otroSi = false;
    				}
    				dbankSi = true;
    			
    				panelDBank.setBackground(Color.green);
    				repaint();
    			}
    		}
    		
    		@Override
    		public void mouseEntered(MouseEvent e) {
    			// TODO Auto-generated method stub
    			panelDBank.setBorder(new LineBorder(Color.LIGHT_GRAY, 5));
    		}
    		
    		@Override
    		public void mouseExited(MouseEvent e) {
    			// TODO Auto-generated method stub
    			panelDBank.setBorder(new EmptyBorder(0,0,0,0));
    		}
		});
    	
    	panelOtro.addMouseListener(new MouseAdapter() {
    		
    		@Override
    		public void mouseClicked(MouseEvent e) {
    			// TODO Auto-generated method stub
    			if(otroSi == true) {
    				otroSi = false;
    				panelOtro.setBackground(Color.red);
    		
    			}else {
    				
    				if(dbankSi == true) {
    					dbankSi = false;
    					panelDBank.setBackground(new Color(24, 5, 92));
    				}
    				otroSi = true;
    			
    				panelOtro.setBackground(Color.green);
    				repaint();
    			}
    		}
    		
    		@Override
    		public void mouseEntered(MouseEvent e) {
    			// TODO Auto-generated method stub
    			panelOtro.setBorder(new LineBorder(Color.LIGHT_GRAY, 5));
    		}
    		
    		@Override
    		public void mouseExited(MouseEvent e) {
    			// TODO Auto-generated method stub
    			panelOtro.setBorder(new EmptyBorder(0,0,0,0));
    		}
    		
		});
    	
    
    	
    	
    	leyenda.add(panelDBank);
    	leyenda.add(panelOtro);
    	this.add(leyenda, BorderLayout.EAST);
    	
    	
    	//valores de inicialización
    	valores.add(20);
    	valores.add(10);
    	valores.add(17);
    	valores.add(5);
    	valores.add(10);
    	valores.add(18);
    	valores.add(10);
    	valores.add(10);
    	valores.add(16);
    	valores.add(15);
    	//valores rojo
    	valores2.add(20);
    	valores2.add(3);
    	valores2.add(10);
    	valores2.add(14);
    	valores2.add(12);
    	valores2.add(16);
    	valores2.add(9);
    	valores2.add(4);
    	valores2.add(1);
    	valores2.add(5);

    	
    	Thread hilo = new Thread(() -> {
    		while(!Thread.currentThread().isInterrupted()) {
    			try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					
				}
    			
    			int valorNuevo = random.nextInt(10);
    			int valorNuevo2 = random.nextInt(10);
    			valores.add(valorNuevo);
    			valores2.add(valorNuevo2);
    			
    			if(valores.size() > 15) {
    				valores.remove(0);
    				valores2.remove(0);
    			}
    			
    			SwingUtilities.invokeLater(this::repaint);
    		}
    	});
    	hilo.start();
    }
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

        int padding = 50;
        int ancho = getWidth() - 2 * padding;
        int alto = getHeight() - 2 * padding;

        g2.setColor(Color.WHITE);
        g2.fillRect(padding, padding, ancho, alto);
        g2.setColor(Color.BLACK);
        g2.drawLine(padding, padding + alto, padding + ancho, padding + alto); // X
        g2.drawLine(padding, padding, padding, padding + alto); // Y
        

        int max = Collections.max(valores);
        int min = Collections.min(valores);
        int max2 = Collections.max(valores2);
        int min2 = Collections.min(valores2);
        int maxT = 0;
        int minT = 100;
        if(max > max2) {
        	maxT = max;
        }else {
        	maxT = max2;
        }
        
        if(min < min2) {
        	minT = min;
        }else {
        	minT = min2;
        }
        
        g2.setStroke(new BasicStroke(2));
        if (otroSi == false) {
        	
            g2.setColor(Color.BLUE);
            int n = valores.size();
            for (int i = 0; i < n - 1; i++) {
                int x1 = padding + i * ancho / (n - 1);
                int y1 = padding + alto - (valores.get(i) - minT) * alto / (maxT - minT);
                int x2 = padding + (i + 1) * ancho / (n - 1);
                int y2 = padding + alto - (valores.get(i + 1) - minT) * alto / (maxT - minT);
                g2.drawLine(x1, y1, x2, y2);
            }
		}
        
        
        
        if(dbankSi == false) {
        	g2.setColor(Color.red);
        	
    		
    		int m = valores2.size();
    		for (int i = 0; i < m - 1; i++) {
    		    int x1 = padding + i * ancho / (m - 1);
    		    int y1 = padding + alto - (valores2.get(i) - minT) * alto / (maxT - minT);
    		    int x2 = padding + (i + 1) * ancho / (m - 1);
    		    int y2 = padding + alto - (valores2.get(i + 1) - minT) * alto / (maxT - minT);
    		    g2.drawLine(x1, y1, x2, y2);
    		}
        }
		
        
        
	}
	
	public void actualizarGrafica() {
	    SwingUtilities.invokeLater(() -> {
	        int valorNuevo = random.nextInt(10);
	        valores.add(valorNuevo);

	        if (valores.size() > 15) {
	            valores.remove(0);
	        }

	        repaint(); 
	    });
	}

	
	

}
