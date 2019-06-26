/*
 * Categorie table model for join Query
 */

package model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CategoryTableModel extends AbstractTableModel {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int CATEGORY_ID_COL = 0;
	private static final int CATEGORY_NAME_COL = 1;

	private String[] columnNames = {"Id categorie", "Nom categorie"};
	
	private List<Categorie> categories;

	public CategoryTableModel(List<Categorie> theCategories) {
		categories = theCategories;
	} 
	
	@Override
	public int getColumnCount() {
		return columnNames.length; 
	}

	@Override
	public int getRowCount() {
		return categories.size(); 
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	} 

	@Override
	public Object getValueAt(int row, int col) {

		Categorie tempCategory = categories.get(row);

		switch (col) {
		case CATEGORY_ID_COL:
			return tempCategory.getId_cat();
		case CATEGORY_NAME_COL:
			return tempCategory.getNom_cat();
		default:
			return tempCategory.getNom_cat();
		} 
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	} 
}