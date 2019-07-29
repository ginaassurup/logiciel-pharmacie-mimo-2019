/*
 * Fenetre Liste des Pharmaciens
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

import model.PharmacienDetail;
import model.ListePharmaciens;

import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

import dao.SQLiteCon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class PharmaciensFenetre extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// database connection declaration
	SQLiteCon conn;

	List<PharmacienDetail> listePhar;
	String newUser = "";

	// table
	private JTable tableListePhar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					PharmaciensFenetre dialog = new PharmaciensFenetre();
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
	public PharmaciensFenetre() {
		setFont(new Font("Tahoma", Font.PLAIN, 14));
		getContentPane().setFocusTraversalKeysEnabled(false);

		// connect to database
		conn = new SQLiteCon();

		setIconImage(Toolkit.getDefaultToolkit().getImage(PharmaciensFenetre.class.getResource("/view/User.png")));
		setTitle("Liste des pharmaciens | Utilisateur : " + conn.currentUser);
		setModal(true);
		setBounds(100, 100, 968, 703);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(204, 118, 623, 290);
		getContentPane().add(scrollPane);

		tableListePhar = new JTable() {
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
		tableListePhar.setFocusable(false);

		scrollPane.setViewportView(tableListePhar);

		tableListePhar.setFillsViewportHeight(true);
		tableListePhar.setBackground(SystemColor.window);
		tableListePhar.setSelectionBackground(new Color(163, 193, 228));
		tableListePhar.setRequestFocusEnabled(false);
		tableListePhar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(PharmaciensFenetre.class
				.getResource("/view/User.png")));
		label.setBounds(56, 118, 95, 93);
		getContentPane().add(label);

		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.setFocusPainted(false);
		btnAjouter.setBackground(new Color(204, 204, 204));
		btnAjouter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAjouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Add user
				ajouterUnPhar();
			}
		});
		btnAjouter.setBounds(49, 209, 113, 23);
		getContentPane().add(btnAjouter);

		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSupprimer.setFocusPainted(false);
		btnSupprimer.setBackground(new Color(204, 204, 204));
		btnSupprimer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				supprimerUnPhar();
			}
		});
		btnSupprimer.setBounds(49, 239, 113, 23);
		getContentPane().add(btnSupprimer);

		JButton btnModifier = new JButton("Modifier");
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modifierUnPhar();
			}
		});
		btnModifier.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnModifier.setFocusPainted(false);
		btnModifier.setBackground(new Color(204, 204, 204));
		btnModifier.setBounds(49, 269, 113, 23);
		getContentPane().add(btnModifier);
		
		JLabel lbListeDesPharmaciens = new JLabel("Liste des pharmaciens");
		lbListeDesPharmaciens.setHorizontalAlignment(SwingConstants.CENTER);
		lbListeDesPharmaciens.setForeground(new Color(165, 42, 42));
		lbListeDesPharmaciens.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbListeDesPharmaciens.setBounds(231, 43, 498, 25);
		getContentPane().add(lbListeDesPharmaciens);
		
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
		
		btnRetour.setBounds(12, 13, 97, 25);
		getContentPane().add(btnRetour);

		setLocationRelativeTo(null);

		getListePharToTable();

	}

	// get listePhar au tableau
	private void getListePharToTable() {
		try {

			listePhar = conn.getAllUsers();

			ListePharmaciens model = new ListePharmaciens(listePhar);
			tableListePhar.setModel(model);

			hideMdpColumn();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Ajouter un pharmacien
	private void ajouterUnPhar() {
		AjouterUnPharFenetre ajouterUnPharFenetre = new AjouterUnPharFenetre();
		dispose();
		ajouterUnPharFenetre.setVisible(true);
		while (ajouterUnPharFenetre.isVisible()) {

		}
		getListePharToTable();
	}

	// Supprimer un pharmacien
	private void supprimerUnPhar() {
		int idCol = 0;
		int nameCol = 1;

		// Si une ligne est s�lectionn�e
		if (!(tableListePhar.getSelectedRow() == -1)) {

			int selectedRow = tableListePhar.getSelectedRow();

			String num_phar = tableListePhar.getValueAt(selectedRow, idCol)
					.toString();

			String identifiant = tableListePhar.getValueAt(selectedRow, nameCol)
					.toString();

			if (identifiant.equalsIgnoreCase("admin")) {
				JOptionPane.showMessageDialog(null,
						"Impossible de supprimer l'admin");
			} else {

				int reply = JOptionPane.showConfirmDialog(null,
						"Voulez-vous vraiment le supprimer ?", "Supprimer ?",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {

					try {
						conn.supprimerUnPharQuery(num_phar, identifiant);
					} catch (Exception e) {
						e.printStackTrace();
					}

					// Mise � jour la vue
					getListePharToTable();

				} else {
					// do nothing
				}
			}
		} else {
			System.out.println("Aucune ligne est s�lectionn�e");
			JOptionPane
					.showMessageDialog(null,
							"Veuillez s�lectionner un pharmacien � supprimer");
		}
	}

	// Modifier un pharmacien
	private void modifierUnPhar() {

		if (!(tableListePhar.getSelectedRow() == -1)) {
			ModifierUnPharFenetre modifierUnPharFenetre = new ModifierUnPharFenetre();

			// insert data from table to the fields
			int idCol = 0;
			int userNameCol = 1;
			int passwordCol = 2;
			int firstNameCol = 3;
			int surnameCol = 4;

			int selectedRow = tableListePhar.getSelectedRow();

			modifierUnPharFenetre.textFieldIdentifiant.setText(tableListePhar
					.getValueAt(selectedRow, userNameCol).toString().trim());
			modifierUnPharFenetre.mdpField.setText(tableListePhar
					.getValueAt(selectedRow, passwordCol).toString().trim());
			modifierUnPharFenetre.textFieldPrenom.setText(tableListePhar
					.getValueAt(selectedRow, firstNameCol).toString().trim());
			modifierUnPharFenetre.textFieldNom.setText(tableListePhar
					.getValueAt(selectedRow, surnameCol).toString().trim());

			modifierUnPharFenetre.mdpActuel = tableListePhar
					.getValueAt(selectedRow, idCol).toString().trim();
			dispose();
			modifierUnPharFenetre.setVisible(true);

			while (modifierUnPharFenetre.isVisible()) {

			}
			// Mise � jour la vue
			getListePharToTable();

		} else {
			JOptionPane.showMessageDialog(null,
					"Veuillez s�lectionner un pharmacien � modifier !");
		}
	}

	// hides columns
	private void hideMdpColumn() {
		TableColumn myTableColumn2 = tableListePhar.getColumnModel().getColumn(2);

		myTableColumn2.setMaxWidth(0);
		myTableColumn2.setMinWidth(0);
		myTableColumn2.setPreferredWidth(0);
	}

}
