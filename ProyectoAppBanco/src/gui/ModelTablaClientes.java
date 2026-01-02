package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import domain.Cliente;

public class ModelTablaClientes extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Cliente> clientes;
	
	public ModelTablaClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return clientes.size();
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
		case 0 -> nombreColumna = "ID";
		case 1 -> nombreColumna = "NOMBRE";
		case 2 -> nombreColumna = "SALDO_TOTAL";
		}
		return nombreColumna;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Cliente cliente = clientes.get(rowIndex);
		if(columnIndex == 0) {
			return cliente.getId();
		}else if (columnIndex == 1) {
			return cliente.getNombre();
		}else {
			return cliente.getSaldoTotal();
		}
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	
	public void addCliente(Cliente cliente) {
        clientes.add(cliente);
        //fireTableRowsInserted(clientes.size() - 1, clientes.size() - 1);
    }

}
