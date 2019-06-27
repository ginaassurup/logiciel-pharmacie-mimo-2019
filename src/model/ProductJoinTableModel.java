/*
 * ProduitDetail table model for join query
 */

package model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ProductJoinTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int CODE_BARRE_COL = 0;
	private static final int LIBELLE_PRODUIT_COL = 1;
	private static final int CATEGORIE_COL = 2;
	private static final int FORME_COL = 3;
	private static final int QTTE_STOCK_COL = 4;
	private static final int QTTE_STOCK_ALARME_COL = 5;
	private static final int PRIX_VENTE_COL = 6;
	private static final int PRIX_ACHAT_COL = 7;	
	private static final int RAISON_SOCIALE_COL = 8;


	private String[] columnNames = { "Code barre","Libellé", "Catégorie", "Forme",
			"Stock", "Alarme", "Prix de vente",  "Prix d'achat", "Fournisseur"};
	
	private List<ProductJoin> productsJoin;

	public ProductJoinTableModel(List<ProductJoin> theProductsJoin) {
		productsJoin = theProductsJoin;
	} 
	
	@Override
	public int getColumnCount() {
		return columnNames.length; 
	}

	@Override
	public int getRowCount() {
		return productsJoin.size(); 
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	} 

	@Override
	public Object getValueAt(int row, int col) {

		ProductJoin tempProductJoin = productsJoin.get(row);

		switch (col) {
		
		case CODE_BARRE_COL:
			return tempProductJoin.getCode_barre();
		case LIBELLE_PRODUIT_COL:
			return tempProductJoin.getLibelle_produit();
		case CATEGORIE_COL:
			return tempProductJoin.getNom_cat();
		case FORME_COL:
			return tempProductJoin.getForme();
		case QTTE_STOCK_COL:
			return tempProductJoin.getQtte_stock();
		case QTTE_STOCK_ALARME_COL:
			return tempProductJoin.getQtte_stock_alarme();
		case PRIX_VENTE_COL:
			return tempProductJoin.getPrix_vente();
		case PRIX_ACHAT_COL:
			return tempProductJoin.getPrix_achat();
		case RAISON_SOCIALE_COL:
			return tempProductJoin.getRaison_sociale();

		default:
			return tempProductJoin.getLibelle_produit();
		} 
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	} 

}
