package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GraficaAcciones extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Integer> valores = new ArrayList<>();
    private Random random = new Random();
    
	
    public GraficaAcciones() {
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

    	
    	Thread hilo = new Thread(() -> {
    		while(!Thread.currentThread().isInterrupted()) {
    			try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO: handle exception
				}
    			
    			int valorNuevo = random.nextInt(10);
    			valores.add(valorNuevo);
    			
    			if(valores.size() > 15) {
    				valores.remove(0);
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
        
        // Dibujar línea del gráfico
        g2.setColor(Color.BLUE);
        int n = valores.size();
        for (int i = 0; i < n - 1; i++) {
            int x1 = padding + i * ancho / (n - 1);
            int y1 = padding + alto - (valores.get(i) - min) * alto / (max - min);
            int x2 = padding + (i + 1) * ancho / (n - 1);
            int y2 = padding + alto - (valores.get(i + 1) - min) * alto / (max - min);
            g2.drawLine(x1, y1, x2, y2);
        }

	}
	
	public void actualizarGrafica() {
	    SwingUtilities.invokeLater(() -> {
	        int valorNuevo = random.nextInt(10);
	        valores.add(valorNuevo);

	        if (valores.size() > 15) {
	            valores.remove(0);
	        }

	        repaint(); // dispara paintComponent
	    });
	}

	
	

}
