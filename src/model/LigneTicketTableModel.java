/*
 * ProduitDetail table model for join query
 */

package model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class LigneTicketTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int NUMERO_COL = 0;
	private static final int CODE_BARRE_COL = 1;
	private static final int LIBELLE_PRODUIT_COL = 2;
	private static final int PRIX_VENTE_COL = 3;
	private static final int QUANTITE_COL = 4;	
	private static final int MONTANT_COL = 5;


	private String[] columnNames = { "Numéro", "Code barre","Libellé", "Prix de vente",  "Quantité", "Montant"};
	
	private List<LigneTicket> ligneTicket;

	public LigneTicketTableModel(List<LigneTicket> ligneTicket) {
		this.ligneTicket = ligneTicket;
	} 
	
	@Override
	public int getColumnCount() {
		return columnNames.length; 
	}

	@Override
	public int getRowCount() {
		return ligneTicket.size(); 
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	} 

	@Override
	public Object getValueAt(int row, int col) {

		LigneTicket ligne = ligneTicket.get(row);

		switch (col) {
		case NUMERO_COL:
			return ligne.getNum_ligne();
		case CODE_BARRE_COL:
			return ligne.getCode_barre();
		case LIBELLE_PRODUIT_COL:
			return ligne.getLibelle_produit();
		case PRIX_VENTE_COL:
			return ligne.getPrix_vente();
		case QUANTITE_COL:
			return ligne.getQtte_vendu();
		case MONTANT_COL:
			return ligne.getMontant();

		default:
			return ligne.getLibelle_produit();
		} 
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	} 

}
