/*
 * Units Window class
 */
package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;

import javax.swing.JDialog;

import java.awt.Toolkit;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import model.ListePharmaciens;
import model.Unit;
import model.UnitTableModel;

import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

import dao.SQLiteCon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class FournisseursFenetre extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// database connection declaration
	SQLiteCon conn;

	List<Unit> listeFour;

	// table
	private JTable tableListeFour;

	String newUnit = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					FournisseursFenetre dialog = new FournisseursFenetre();
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the dialog.
	 */
	public FournisseursFenetre() {
		getContentPane().setFocusTraversalKeysEnabled(false);

		// connect to database
		conn = new SQLiteCon();

		setIconImage(Toolkit.getDefaultToolkit().getImage(FournisseursFenetre.class.getResource("/view/logo_new.png")));
		setTitle("iste des pharmaciens");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 968, 700);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(164, 162, 644, 381);
		getContentPane().add(scrollPane);

		tableListeFour = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void changeSelection(int rowIndex, int columnIndex,
					boolean toggle, boolean extend) {
				// Always toggle on single selection
				super.changeSelection(rowIndex, columnIndex, !extend, extend);
			}
		};
		tableListeFour.setFocusable(false);

		scrollPane.setViewportView(tableListeFour);

		tableListeFour.setFillsViewportHeight(true);
		tableListeFour.setBackground(SystemColor.window);
		tableListeFour.setSelectionBackground(new Color(163, 193, 228));
		tableListeFour.setRequestFocusEnabled(false);
		tableListeFour.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JButton btnAdd = new JButton("Ajouter");
		btnAdd.setFocusPainted(false);
		btnAdd.setBackground(new Color(204, 204, 204));
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				// Add category
				ajouterUnFour();
			}
		});
		btnAdd.setBounds(32, 162, 88, 23);
		getContentPane().add(btnAdd);

		JButton btnRemove = new JButton("Remove");
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRemove.setBackground(new Color(204, 204, 204));
		btnRemove.setFocusPainted(false);
		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				removeUnit();
			}
		});
		btnRemove.setBounds(32, 192, 88, 23);
		getContentPane().add(btnRemove);

		JButton btnEdit = new JButton("Modifier");
		btnEdit.setBackground(new Color(204, 204, 204));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				updateUnit();
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEdit.setFocusPainted(false);
		btnEdit.setBounds(32, 222, 88, 23);
		getContentPane().add(btnEdit);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				OuvrirMenuPrincipal();
			}

			// Ouvrir le menu principal
			private void OuvrirMenuPrincipal() {
				
				MenuPrincipal menuPrincipal = new MenuPrincipal();
				menuPrincipal.getFrmMenuPrincipal().setVisible(true);
			}
		});
		btnRetour.setBounds(14, 13, 97, 25);
		getContentPane().add(btnRetour);
		
		JLabel lblListeDesFournisseurs = new JLabel("Liste des fournisseurs");
		lblListeDesFournisseurs.setHorizontalAlignment(SwingConstants.CENTER);
		lblListeDesFournisseurs.setForeground(new Color(165, 42, 42));
		lblListeDesFournisseurs.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblListeDesFournisseurs.setBounds(208, 75, 498, 25);
		getContentPane().add(lblListeDesFournisseurs);

		setLocationRelativeTo(null);

		getUnitsToTable();

	}

	// get all products to the table (join table query)
	private void getUnitsToTable() {

		try {

			listeFour = conn.getAllUnits();

			UnitTableModel model = new UnitTableModel(listeFour);

			tableListeFour.setModel(model);

			// remove/hide Id table
//			TableColumn myTableColumn0 = tableListeFour.getColumnModel()
//					.getColumn(0);
//			// tableCategories.getColumnModel().removeColumn(myTableColumn0);
//			myTableColumn0.setMaxWidth(0);
//			myTableColumn0.setMinWidth(0);
//			myTableColumn0.setPreferredWidth(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// add category
	private void ajouterUnFour() {
		// Ajouter un pharmacien
		AjouterUnFourFenetre ajouterUnFourFenetre = new AjouterUnFourFenetre();
		dispose();
		ajouterUnFourFenetre.setVisible(true);
		while (ajouterUnFourFenetre.isVisible()) {

		}
		getListeFourToTable();
	}
//		newUnit = JOptionPane.showInputDialog("New Unit name").trim();
//
//		// if not empty
//		if (!newUnit.equalsIgnoreCase("")) {
//
//			// check if exists
//			boolean unitExists = false;
//			for (int i = 0; i < listeFour.size(); i++) {
//				if (listeFour.get(i).getRaison_sociale().equalsIgnoreCase(newUnit)) {
//
//					unitExists = true;
//					break;
//				}
//			}
//
//			// if doesn't exist
//			if (!unitExists) {
//				try {
//					conn.insertUnitQuery(newUnit);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				getUnitsToTable();
//			} else {
//				JOptionPane.showMessageDialog(null, "This unit already exists.");
//			}
//		} else {
//			JOptionPane.showMessageDialog(null, "Name of unit can't be empty.");
//		}

	// get listeFour au tableau
	private void getListeFourToTable() {
		try {

			listeFour = conn.getAllUnits();

			UnitTableModel model = new UnitTableModel(listeFour);
			tableListeFour.setModel(model);

//			hideColumns();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// remove category
	private void removeUnit() {
		int idCol = 0;
		int nameCol = 1;

		// if row selected
		if (!(tableListeFour.getSelectedRow() == -1)) {

			int selectedRow = tableListeFour.getSelectedRow();

			String catId = tableListeFour.getValueAt(selectedRow, idCol)
					.toString();

			String catName = tableListeFour.getValueAt(selectedRow, nameCol)
					.toString();

			int reply = JOptionPane.showConfirmDialog(null,
					"Do you really want to remove this unit?", "Remove?",
					JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {

				try {
					conn.removeUnitQuery(catId, catName);

				} catch (Exception e) {
					e.printStackTrace();
				}

				// refresh view here
				getUnitsToTable();

			} else {
				// do nothing
			}

		} else {
			System.out.println("Nothing selected");
			JOptionPane
					.showMessageDialog(null,
							"In order to remove unit please select category row first.");
		}
	}

	// edit category
	private void updateUnit() {

		// if row selected
		if (!(tableListeFour.getSelectedRow() == -1)) {
			ModifierUnFourFenetre modifierUnFourFenetre = new ModifierUnFourFenetre();
			modifierUnFourFenetre.setVisible(true);
			
			int idCol = 0;
			int nameCol = 1;
			int selectedRow = tableListeFour.getSelectedRow();

			String id = tableListeFour.getValueAt(selectedRow, idCol)
					.toString();
			String currentUnit = tableListeFour.getValueAt(selectedRow,
					nameCol).toString();

			newUnit = JOptionPane.showInputDialog(
					"Please enter new name of this unit.", currentUnit);
			// if not empty
			if (!newUnit.equalsIgnoreCase("")) {

				// check if exists
				boolean unitExists = false;
				for (int i = 0; i < listeFour.size(); i++) {
					if (listeFour.get(i).getRaison_sociale()
							.equalsIgnoreCase(newUnit)) {
						unitExists = true;
						break;
					}
				}

				// if doesn't exist
				if (!unitExists
						|| (newUnit.equalsIgnoreCase(currentUnit))) {
					try {
						conn.updateUnitQuery(currentUnit, newUnit,
								id);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					getUnitsToTable();
				} else {
					JOptionPane.showMessageDialog(null,
							"This unit already exists.");
				}

			} else {
				JOptionPane.showMessageDialog(null,
						"Name of unit can't be empty.");
			}

		} else {
			JOptionPane.showMessageDialog(null, "Please select unit first.");
		}
	}
}
