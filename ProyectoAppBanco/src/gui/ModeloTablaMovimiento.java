package gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import domain.Movimiento;

public class ModeloTablaMovimiento extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Movimiento> listaMovimientos;
	private String[] columnasNames= {"Fecha", "Cantidad", "Concepto"};
	
	public ModeloTablaMovimiento(ArrayList<Movimiento> listaMovimientos) {
		this.listaMovimientos = listaMovimientos;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return listaMovimientos.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 3;
	}
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return columnasNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return listaMovimientos.get(rowIndex);
		
	}

}
