/*
 * Product table model
 */

package model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ProductTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PRODUCT_NAME_COL = 0;
	private static final int CATEGORY_COL = 1;
	private static final int TYPE_COL = 2;
	private static final int STOCK_COL = 3;

	private String[] columnNames = { "Product Name", "Category", "Type",
			"Stock" };
	
	private List<Product> products;

	public ProductTableModel(List<Product> theProducts) {
		products = theProducts;
	} 
	
	@Override
	public int getColumnCount() {
		return columnNames.length; 
	}

	@Override
	public int getRowCount() {
		return products.size(); 
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	} 

	@Override
	public Object getValueAt(int row, int col) {

		Product tempProduct = products.get(row);

		switch (col) {
		case PRODUCT_NAME_COL:
			return tempProduct.getLibelle_produit();
		case CATEGORY_COL:
			return tempProduct.getId_cat();
		case TYPE_COL:
			return tempProduct.getForme();
		case STOCK_COL:
			return tempProduct.getQtte_stock();
		default:
			return tempProduct.getLibelle_produit();
		} 
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	} 

}
