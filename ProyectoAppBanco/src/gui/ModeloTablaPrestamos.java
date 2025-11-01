package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import domain.Prestamo;

public class ModeloTablaPrestamos extends AbstractTableModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Prestamo> prestamos;
	
	public ModeloTablaPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}


	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return prestamos.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 3;
	}
	
	@Override
	public String getColumnName(int column) {
		String nombreColumna = "";
		switch(column) {
		case 0 -> nombreColumna = "CANTIDAD";
		case 1 -> nombreColumna = "PLAZO";
		case 2 -> nombreColumna = "INTERES";
		}
		return nombreColumna;
	}


	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Prestamo prestamo = prestamos.get(rowIndex);
		return switch (columnIndex) {
		case 0 -> prestamo.getCantidad();
		case 1 -> prestamo.getFechaFin();
		case 2 -> prestamo.getInteres();
		default -> null;
		};
		
	}

}
