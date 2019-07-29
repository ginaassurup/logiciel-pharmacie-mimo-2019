/*
 * Categorie Table model pour la jointure Query
 */

package model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CategorieTableModel extends AbstractTableModel {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int CATEGORIE_ID_COL = 0;
	private static final int CATEGORIE_NOM_COL = 1;

	private String[] columnNames = {"Id catégorie", "Nom catégorie"};
	
	private List<Categorie> categories;

	public CategorieTableModel(List<Categorie> listCategories) {
		categories = listCategories;
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

		Categorie tempCategories = categories.get(row);

		switch (col) {
		case CATEGORIE_ID_COL:
			return tempCategories.getId_cat();
		case CATEGORIE_NOM_COL:
			return tempCategories.getNom_cat();
		default:
			return tempCategories.getNom_cat();
		} 
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	} 
}