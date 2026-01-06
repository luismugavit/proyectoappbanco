package gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


import domain.Cuenta;

public class ModeloTablaCuentas1 extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Cuenta> cuentas;

	public ModeloTablaCuentas1(ArrayList<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return cuentas.size();
	}
	@Override
	public String getColumnName(int column) {
		String nombreColumna = "";
		switch(column) {
		case 0 -> nombreColumna = "NUMERO_CUENTA";
		case 1 -> nombreColumna = "SALDO";
		case 2 -> nombreColumna = "PROPIETARIO";
		}
		return nombreColumna;
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		
		
		Cuenta cuenta = cuentas.get(rowIndex);
		if(columnIndex == 0) {
			return cuenta.getNumeroCuenta();
		}else if (columnIndex == 1){
			return cuenta.getSaldo();
		}else {
			return cuenta.getPropietario().getDni();
		}
	
	}

	
	
	
}
