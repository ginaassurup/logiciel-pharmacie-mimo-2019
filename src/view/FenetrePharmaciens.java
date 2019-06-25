/*
 * Users Window class
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
import java.awt.Window.Type;
import java.awt.Dialog.ModalityType;
import javax.swing.SwingConstants;

public class FenetrePharmaciens extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// database connection declaration
	SQLiteCon conn;

	List<PharmacienDetail> users;
	String newUser = "";

	// table
	private JTable tableUsers;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					FenetrePharmaciens dialog = new FenetrePharmaciens();
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
	public FenetrePharmaciens() {
		setFont(new Font("Tahoma", Font.PLAIN, 14));
		getContentPane().setFocusTraversalKeysEnabled(false);

		// connect to database
		conn = new SQLiteCon();

		setIconImage(Toolkit.getDefaultToolkit().getImage(FenetrePharmaciens.class.getResource("/view/User.png")));
		setTitle("Logiciel Gestion de pharmacie - Users");
		setModal(true);
		setBounds(100, 100, 968, 500);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(204, 118, 623, 290);
		getContentPane().add(scrollPane);

		tableUsers = new JTable() {
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
		tableUsers.setFocusable(false);

		scrollPane.setViewportView(tableUsers);

		tableUsers.setFillsViewportHeight(true);
		tableUsers.setBackground(SystemColor.window);
		tableUsers.setSelectionBackground(new Color(163, 193, 228));
		tableUsers.setRequestFocusEnabled(false);
		tableUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(FenetrePharmaciens.class
				.getResource("/view/User.png")));
		label.setBounds(56, 118, 95, 93);
		getContentPane().add(label);

		JButton btnAdd = new JButton("Ajouter");
		btnAdd.setFocusPainted(false);
		btnAdd.setBackground(new Color(204, 204, 204));
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Add user
				addUser();
			}
		});
		btnAdd.setBounds(49, 209, 113, 23);
		getContentPane().add(btnAdd);

		JButton btnRemove = new JButton("Supprimer");
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRemove.setFocusPainted(false);
		btnRemove.setBackground(new Color(204, 204, 204));
		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				removeUser();
			}
		});
		btnRemove.setBounds(49, 239, 113, 23);
		getContentPane().add(btnRemove);

		JButton btnEdit = new JButton("Modifier");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				updateUser();
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEdit.setFocusPainted(false);
		btnEdit.setBackground(new Color(204, 204, 204));
		btnEdit.setBounds(49, 269, 113, 23);
		getContentPane().add(btnEdit);
		
		JLabel lblListeDesPharmaciens = new JLabel("Liste des pharmaciens");
		lblListeDesPharmaciens.setHorizontalAlignment(SwingConstants.CENTER);
		lblListeDesPharmaciens.setForeground(new Color(165, 42, 42));
		lblListeDesPharmaciens.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblListeDesPharmaciens.setBounds(231, 43, 498, 25);
		getContentPane().add(lblListeDesPharmaciens);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				OuvrirMenuPrincipal();
			}

			// opens users window
			private void OuvrirMenuPrincipal() {
				
				MenuPrincipal menuPrincipal = new MenuPrincipal();
				menuPrincipal.getFrmMenuPrincipal().setVisible(true);
			}
		});
		
		btnRetour.setBounds(12, 13, 97, 25);
		getContentPane().add(btnRetour);

		setLocationRelativeTo(null);

		getUsersToTable();

	}

	// get all users to a table
	private void getUsersToTable() {
		try {

			users = conn.getAllUsers();

			ListePharmaciens model = new ListePharmaciens(users);
			tableUsers.setModel(model);

//			hideColumns();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// add user
	private void addUser() {
		AddUserWindow addUserWindow = new AddUserWindow();
		addUserWindow.setVisible(true);
		while (addUserWindow.isVisible()) {

		}
		getUsersToTable();
	}

	// remove user
	private void removeUser() {
		int idCol = 0;
		int nameCol = 1;

		// if row selected
		if (!(tableUsers.getSelectedRow() == -1)) {

			int selectedRow = tableUsers.getSelectedRow();

			String userId = tableUsers.getValueAt(selectedRow, idCol)
					.toString();

			String userName = tableUsers.getValueAt(selectedRow, nameCol)
					.toString();

			if (userName.equalsIgnoreCase("admin")) {
				JOptionPane.showMessageDialog(null,
						"Administrator account can't be removed.");
			} else {

				int reply = JOptionPane.showConfirmDialog(null,
						"Do you really want to remove this user?", "Remove?",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {

					try {
						conn.removeUserQuery(userId, userName);
					} catch (Exception e) {
						e.printStackTrace();
					}

					// refresh view here
					getUsersToTable();

				} else {
					// do nothing
				}
			}
		} else {
			System.out.println("Nothing selected");
			JOptionPane
					.showMessageDialog(null,
							"In order to remove user please select category row first.");
		}
	}

	// edit user
	private void updateUser() {

		if (!(tableUsers.getSelectedRow() == -1)) {
			EditUserWindow editUserWindow = new EditUserWindow();

			// insert data from table to the fields
			int idCol = 0;
			int userNameCol = 1;
			int passwordCol = 2;
			int firstNameCol = 3;
			int surnameCol = 4;

			int selectedRow = tableUsers.getSelectedRow();

			editUserWindow.textFieldUserName.setText(tableUsers
					.getValueAt(selectedRow, userNameCol).toString().trim());
			editUserWindow.passwordField.setText(tableUsers
					.getValueAt(selectedRow, passwordCol).toString().trim());
			editUserWindow.textFieldFirstName.setText(tableUsers
					.getValueAt(selectedRow, firstNameCol).toString().trim());
			editUserWindow.textFieldSurname.setText(tableUsers
					.getValueAt(selectedRow, surnameCol).toString().trim());

			editUserWindow.currentId = tableUsers
					.getValueAt(selectedRow, idCol).toString().trim();
			editUserWindow.setVisible(true);

			while (editUserWindow.isVisible()) {

			}
			// refresh table
			getUsersToTable();

		} else {
			JOptionPane.showMessageDialog(null,
					"In order to edit please select user first.");
		}
	}

	// hides columns
	private void hideColumns() {
		// remove/hide Id table
		TableColumn myTableColumn0 = tableUsers.getColumnModel().getColumn(0);
		TableColumn myTableColumn2 = tableUsers.getColumnModel().getColumn(2);

		// tableCategories.getColumnModel().removeColumn(myTableColumn0);
		myTableColumn0.setMaxWidth(0);
		myTableColumn0.setMinWidth(0);
		myTableColumn0.setPreferredWidth(0);

		myTableColumn2.setMaxWidth(0);
		myTableColumn2.setMinWidth(0);
		myTableColumn2.setPreferredWidth(0);
	}
}
