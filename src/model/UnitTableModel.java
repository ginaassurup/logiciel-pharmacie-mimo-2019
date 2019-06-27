/*
 * Unit table model for join Query
 */

package model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class UnitTableModel extends AbstractTableModel {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int UNIT_ID_COL = 0;
	private static final int UNIT_NAME_COL = 1;
	private static final int ADRESSE_FOUR_COL = 2;
	private static final int CODE_POSTAL_FOUR_COL = 3;
	private static final int VILLE_FOUR_COL = 4;

	private String[] columnNames = {"Numéro", "Raison sociale", "Adresse", "Code postale", "Ville"};
	
	private List<Unit> units;

	public UnitTableModel(List<Unit> theUnits) {
		units = theUnits;
	} 
	
	@Override
	public int getColumnCount() {
		return columnNames.length; 
	}

	@Override
	public int getRowCount() {
		return units.size(); 
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	} 

	@Override
	public Object getValueAt(int row, int col) {

		Unit tempUnit = units.get(row);

		switch (col) {
		case UNIT_ID_COL:
			return tempUnit.getId_four();
		case UNIT_NAME_COL:
			return tempUnit.getRaison_sociale();
		case ADRESSE_FOUR_COL:
			return tempUnit.getAdresse_four();
		case CODE_POSTAL_FOUR_COL:
			return tempUnit.getCode_postal_four();
		case VILLE_FOUR_COL:
			return tempUnit.getVille_four();

		default:
			return tempUnit.getRaison_sociale();
		} 
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	} 
}