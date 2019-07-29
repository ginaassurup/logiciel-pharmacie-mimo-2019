/*
 * User table model for join Query
 */

package model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ListePharmaciens extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int USER_ID_COL = 0;
	private static final int PHARMACIENDETAIL_IDENTIFIANT_COL = 1;
	private static final int USER_PASSWORD_COL = 2;
	private static final int USER_FIRSTNAME_COL = 3;
	private static final int USER_SURNAME_COL = 4;

	private String[] columnNames = { "Numéro", "Identifiant", "Mot de passe",
			"Prénom", "Nom" };

	private List<PharmacienDetail> users;

	public ListePharmaciens(List<PharmacienDetail> theUsers) {
		users = theUsers;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return users.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		PharmacienDetail tempUser = users.get(row);

		switch (col) {
		case USER_ID_COL:
			return tempUser.getNum_phar();
		case PHARMACIENDETAIL_IDENTIFIANT_COL:
			return tempUser.getIdentifiant();
		case USER_PASSWORD_COL:
			return tempUser.getMdp();
		case USER_FIRSTNAME_COL:
			return tempUser.getPrenom_phar();
		case USER_SURNAME_COL:
			return tempUser.getNom_phar();

		default:
			return tempUser.getIdentifiant();
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}