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
	private String[] columnas = {"ID", "Monto", "Cuota Mes", "Pendiente", "Estado"};
	
	public ModeloTablaPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}


	@Override
	public int getRowCount() {
		return prestamos.size();
	}

	@Override
	public int getColumnCount() {
		return columnas.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return columnas[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Prestamo prestamo = prestamos.get(rowIndex);
		return switch (columnIndex) {
        case 0 -> prestamo.getId();
        case 1 -> String.format("%.2f €", prestamo.getCantidadSolicitada());
        case 2 -> String.format("%.2f €", prestamo.getCuotaMensual());
        case 3 -> String.format("%.2f €", prestamo.getCantidadPendiente());
        case 4 -> prestamo.getEstado();
        default -> null;
		};
		
	}
	
	public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
        fireTableDataChanged();
    }

}
