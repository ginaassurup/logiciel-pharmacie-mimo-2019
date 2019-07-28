/*
 * Categories Window class
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

import model.Categorie;
import model.CategoryTableModel;

import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

import dao.SQLiteCon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class CategoriesWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// database connection declaration
	SQLiteCon conn;

	List<Categorie> categories;

	// table
	private JTable tableCategories;

	String newCategory = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					CategoriesWindow dialog = new CategoriesWindow();
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
	public CategoriesWindow() {
		getContentPane().setFocusTraversalKeysEnabled(false);

		// connect to database
		conn = new SQLiteCon();

		setTitle("Liste des cat�gories du produit | Utilisateur : " + conn.currentUser);
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 711, 483);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(193, 158, 439, 215);
		getContentPane().add(scrollPane);

		tableCategories = new JTable() {
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
		tableCategories.setLocation(194, 0);
		tableCategories.setFocusable(false);

		scrollPane.setViewportView(tableCategories);

		tableCategories.setFillsViewportHeight(true);
		tableCategories.setBackground(SystemColor.window);
		tableCategories.setSelectionBackground(new Color(163, 193, 228));
		tableCategories.setRequestFocusEnabled(false);
		tableCategories.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JButton btnAdd = new JButton("Ajouter");
		btnAdd.setFocusPainted(false);
		btnAdd.setBackground(new Color(204, 204, 204));
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				// Add category
				addCategory();
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

				removeCategory();
			}
		});
		btnRemove.setBounds(14, 192, 100, 23);
		getContentPane().add(btnRemove);

		JButton btnEdit = new JButton("Modifier");
		btnEdit.setBackground(new Color(204, 204, 204));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				updateCategory();
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEdit.setFocusPainted(false);
		btnEdit.setBounds(14, 222, 100, 23);
		getContentPane().add(btnEdit);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				OuvrirMainWindow();
			}

			// Ouvrir la gestion des produits
			private void OuvrirMainWindow() {
				
				MainWindow mainwindow = new MainWindow();
				mainwindow.setVisible(true);
			}
		});
		btnRetour.setBounds(14, 13, 97, 25);
		getContentPane().add(btnRetour);
		
		JLabel lblListeDesCatgories = new JLabel("Liste des cat\u00E9gories de produit");
		lblListeDesCatgories.setHorizontalAlignment(SwingConstants.CENTER);
		lblListeDesCatgories.setForeground(new Color(165, 42, 42));
		lblListeDesCatgories.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblListeDesCatgories.setBounds(193, 81, 439, 25);
		getContentPane().add(lblListeDesCatgories);

		setLocationRelativeTo(null);

		getCategoriesToTable();

	}

	// get all products to the table (join table query)
	private void getCategoriesToTable() {

		try {

			categories = conn.getAllCategories();

			CategoryTableModel model = new CategoryTableModel(categories);

			tableCategories.setModel(model);

			// remove/hide Id table
			TableColumn myTableColumn0 = tableCategories.getColumnModel()
					.getColumn(0);
			// tableCategories.getColumnModel().removeColumn(myTableColumn0);
			myTableColumn0.setMaxWidth(0);
			myTableColumn0.setMinWidth(0);
			myTableColumn0.setPreferredWidth(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// add category
	private void addCategory() {

		newCategory = JOptionPane.showInputDialog("Entrez le nom de la nouvelle cat�gorie que vous voulez ajouer").trim();

		// if not empty
		if (!newCategory.equalsIgnoreCase("")) {

			// check if exists
			boolean catExists = false;
			for (int i = 0; i < categories.size(); i++) {
				if (categories.get(i).getNom_cat().equalsIgnoreCase(newCategory)) {

					System.out.println("Exists " + categories.get(i).getNom_cat()
							+ " " + newCategory);
					catExists = true;
					break;
				}
			}

			// if doesn't exist
			if (!catExists) {
				try {
					conn.insertCategoryQuery(newCategory);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				getCategoriesToTable();
			} else {
				JOptionPane.showMessageDialog(null,
						"This category already exists");
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"Name of category can't be empty");
		}
	}

	// remove category
	private void removeCategory() {
		int idCol = 0;
		int nameCol = 1;

		// if row selected
		if (!(tableCategories.getSelectedRow() == -1)) {

			int selectedRow = tableCategories.getSelectedRow();

			String catId = tableCategories.getValueAt(selectedRow, idCol)
					.toString();

			String catName = tableCategories.getValueAt(selectedRow, nameCol)
					.toString();

			System.out.println(catId + " " + catName);

			int reply = JOptionPane.showConfirmDialog(null,
					"Voulez-vous vraiment supprimer cette cat�gorie ?", "Supprimer ?",
					JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {

				try {
					conn.removeCategoryQuery(catId, catName);

				} catch (Exception e) {
					e.printStackTrace();
				}

				// refresh view here
				getCategoriesToTable();

			} else {
				// do nothing
			}

		} else {
			System.out.println("Nothing selected");
			JOptionPane
					.showMessageDialog(null,
							"Veuillez choisir une cat�gorie � supprimer");
		}
	}

	// edit category
	private void updateCategory() {

		// if row selected
		if (!(tableCategories.getSelectedRow() == -1)) {

			int idCol = 0;
			int nameCol = 1;
			int selectedRow = tableCategories.getSelectedRow();

			String id = tableCategories.getValueAt(selectedRow, idCol)
					.toString();
			String currentCategory = tableCategories.getValueAt(selectedRow,
					nameCol).toString();

			newCategory = JOptionPane.showInputDialog(
					"Entrez le nouveau nom de cette cat�gorie", currentCategory);
			// if not empty
			if (!newCategory.equalsIgnoreCase("")) {

				// check if exists
				boolean catExists = false;
				for (int i = 0; i < categories.size(); i++) {
					if (categories.get(i).getNom_cat()
							.equalsIgnoreCase(newCategory)) {

						System.out.println("Exists "
								+ categories.get(i).getNom_cat() + " "
								+ newCategory);
						catExists = true;
						break;
					}
				}

				// if doesn't exist
				if (!catExists || (newCategory.equalsIgnoreCase(currentCategory))) {
					try {
						conn.updateCategoryQuery(currentCategory, newCategory,
								id);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					getCategoriesToTable();
				} else {
					JOptionPane.showMessageDialog(null,
							"This category already exists.");
				}

			} else {
				JOptionPane.showMessageDialog(null,
						"Name of category can't be empty.");
			}

		} else {
			JOptionPane.showMessageDialog(null, "Veuillez choisir une ligne de cat�gorie");
		}
	}
}
