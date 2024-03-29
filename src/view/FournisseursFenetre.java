/*
 * Units Window class
 */
package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;

import javax.swing.JDialog;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import model.FournisseurDetail;
import model.ListeFournisseurs;

import javax.swing.JScrollPane;

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

	// Déclaration la base de données
	SQLiteCon conn;

	List<FournisseurDetail> listeFour;

	// table
	private JTable tableListeFour;

	String newUnit = "";

	/**
	 * Lancer l'application.
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
	@SuppressWarnings({ "static-access" })
	public FournisseursFenetre() {
		getContentPane().setFocusTraversalKeysEnabled(false);

		// Connexion à la base de données
		conn = new SQLiteCon();

		setTitle("Liste des fournisseurs | Utilisateur : " + conn.currentUser);
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
		btnAdd.setBounds(14, 162, 100, 23);
		getContentPane().add(btnAdd);

		JButton btnRemove = new JButton("Supprimer");
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRemove.setBackground(new Color(204, 204, 204));
		btnRemove.setFocusPainted(false);
		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				removeUnit();
			}
		});
		btnRemove.setBounds(14, 192, 100, 23);
		getContentPane().add(btnRemove);

		JButton btnEdit = new JButton("Modifier");
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEdit.setBackground(new Color(204, 204, 204));
		btnEdit.setFocusPainted(false);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				updateUnit();
			}
		});
		btnEdit.setBounds(14, 222, 100, 23);
		getContentPane().add(btnEdit);
		
		JLabel lblListeDesFournisseurs = new JLabel("Liste des fournisseurs");
		lblListeDesFournisseurs.setHorizontalAlignment(SwingConstants.CENTER);
		lblListeDesFournisseurs.setForeground(new Color(165, 42, 42));
		lblListeDesFournisseurs.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblListeDesFournisseurs.setBounds(208, 75, 498, 25);
		getContentPane().add(lblListeDesFournisseurs);
		
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
		
		

		setLocationRelativeTo(null);

		getUnitsToTable();

	}

	// get all fournisseurs to the table (join table query)
	private void getUnitsToTable() {

		try {

			listeFour = conn.getTousFour();

			ListeFournisseurs model = new ListeFournisseurs(listeFour);
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
//		newUnit = JOptionPane.showInputDialog("New FournisseurDetail name").trim();
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

			listeFour = conn.getTousFour();

			ListeFournisseurs model = new ListeFournisseurs(listeFour);
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

			String id_four = tableListeFour.getValueAt(selectedRow, idCol)
					.toString();

			String raison_sociale = tableListeFour.getValueAt(selectedRow, nameCol)
					.toString();

			int reply = JOptionPane.showConfirmDialog(null,
					"Voulez-vous vraiment supprimer ce fournisseur ?", "Supprimer",
					JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {

				try {
					conn.removeUnitQuery(id_four, raison_sociale);

				} catch (Exception e) {
					e.printStackTrace();
				}

				// refresh view here
				getUnitsToTable();

			} else {
				// do nothing
			}

		} else {
			System.out.println("Aucune ligne est sélectionnée");
			JOptionPane
					.showMessageDialog(null,
							"Veuillez sélectionner un fournisseur à supprimer");
		}
	}

	// Modifier un fournisseur
	private void updateUnit() {

		// if row selected
		if (!(tableListeFour.getSelectedRow() == -1)) {
			ModifierUnFourFenetre modifierUnFourFenetre = new ModifierUnFourFenetre();
			int idCol = 0;
			int raisonSocialeCol = 1;
			int adresseCol = 2;
			int codePostalCol = 3;
			int villeCol = 4;
			int selectedRow = tableListeFour.getSelectedRow();			
			
			modifierUnFourFenetre.id_four = tableListeFour.getValueAt(selectedRow, idCol).toString().trim();
			modifierUnFourFenetre.textFieldRaisonSociale.setText(tableListeFour
					.getValueAt(selectedRow, raisonSocialeCol).toString().trim());
			modifierUnFourFenetre.textFieldAdresseFour.setText(tableListeFour
					.getValueAt(selectedRow, adresseCol).toString().trim());
			modifierUnFourFenetre.textFieldCodePostalFour.setText(tableListeFour
					.getValueAt(selectedRow, codePostalCol).toString().trim());
			modifierUnFourFenetre.textFieldVilleFour.setText(tableListeFour
					.getValueAt(selectedRow, villeCol).toString().trim());
			
			
			System.out.println("Id_four choisi: "+modifierUnFourFenetre.id_four);
			
			dispose();
			modifierUnFourFenetre.setVisible(true);

			while (modifierUnFourFenetre.isVisible()) {

			}
			// Mise à jour la vue
			getListeFourToTable();
		} else {
			JOptionPane.showMessageDialog(null,
					"Veuillez sélectionner un fournisseur à modifier !");
		}
	}
}
