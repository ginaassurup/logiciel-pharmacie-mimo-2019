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
	private static final int CODE_BARRE_COL = 2;
	private static final int LIBELLE_PRODUIT_COL = 3;
	private static final int PRIX_VENTE_COL = 4;
	private static final int QUANTITE_COL = 5;
	private static final int MONTANT_COL = 6;
	private static final int PRODUCT_COL = 1;

	private String[] columnNames = { "Numéro", "Produit", "Code barre", "Libellé", "Prix de vente", "Quantité",
			"Montant" };

	private List<LigneTicket> ligneTicket;

	public LigneTicketTableModel(List<LigneTicket> ligneTicket) {

		this.ligneTicket = ligneTicket;
	}

	public void addRow(LigneTicket row) {
		row.setNum_ligne(this.ligneTicket.size() + 1);
		this.ligneTicket.add(row);
		this.fireTableDataChanged();

		return;
	}

	public void removeRow() {
		int lastIndex = this.ligneTicket.size() - 1;
		this.ligneTicket.remove(lastIndex);
		this.fireTableDataChanged();
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
	public boolean isCellEditable(int row, int col) {
		if (col == CODE_BARRE_COL || col == LIBELLE_PRODUIT_COL || col == PRIX_VENTE_COL) {
			return false;
		}
		return true;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		LigneTicket ligne = ligneTicket.get(row);
		

		switch (col) {
		case CODE_BARRE_COL:
			ligne.setCode_barre((int) value);
			break;
		case LIBELLE_PRODUIT_COL:
			ligne.setLibelle_produit((String) value);
			break;
		case PRIX_VENTE_COL:
			ligne.setPrix_vente((float) value);
			break;
		case QUANTITE_COL:
			ligne.setQtte_vendu((int) value);
			break;
		case PRODUCT_COL:
			ligne.setProduct((ProductJoin) value);
			break;
		}
		
		fireTableCellUpdated(row, col);

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
		case PRODUCT_COL:
			return ligne.getProduct();

		default:
			return ligne.getLibelle_produit();
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		if (c == 1)
			return ProductJoin.class;
		return getValueAt(0, c).getClass();
	}

}
