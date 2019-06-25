/*
 * Categories Window class
 */
package com.dmtware.in.view;

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

import com.dmtware.in.dao.SQLiteCon;
import com.dmtware.in.model.Category;
import com.dmtware.in.model.CategoryTableModel;

import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class CategoriesWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// database connection declaration
	SQLiteCon conn;

	List<Category> categories;

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

		setIconImage(Toolkit.getDefaultToolkit().getImage(CategoriesWindow.class.getResource("/com/dmtware/in/view/logo_new.png")));
		setTitle("In - Categories");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 254, 242);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(56, 56, 56));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(115, 11, 120, 192);
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
		tableCategories.setFocusable(false);

		scrollPane.setViewportView(tableCategories);

		tableCategories.setFillsViewportHeight(true);
		tableCategories.setBackground(SystemColor.window);
		tableCategories.setSelectionBackground(new Color(163, 193, 228));
		tableCategories.setRequestFocusEnabled(false);
		tableCategories.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(CategoriesWindow.class.getResource("/com/dmtware/in/view/logo_new_64_no_bckg.png")));
		label.setBounds(22, 11, 72, 72);
		getContentPane().add(label);

		JButton btnAdd = new JButton("Add");
		btnAdd.setFocusPainted(false);
		btnAdd.setBackground(new Color(204, 204, 204));
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				// Add category
				addCategory();
			}
		});
		btnAdd.setBounds(14, 119, 88, 23);
		getContentPane().add(btnAdd);

		JButton btnRemove = new JButton("Remove");
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRemove.setBackground(new Color(204, 204, 204));
		btnRemove.setFocusPainted(false);
		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				removeCategory();
			}
		});
		btnRemove.setBounds(14, 149, 88, 23);
		getContentPane().add(btnRemove);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setBackground(new Color(204, 204, 204));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				updateCategory();
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEdit.setFocusPainted(false);
		btnEdit.setBounds(14, 179, 88, 23);
		getContentPane().add(btnEdit);

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

		newCategory = JOptionPane.showInputDialog("New category name").trim();

		// if not empty
		if (!newCategory.equalsIgnoreCase("")) {

			// check if exists
			boolean catExists = false;
			for (int i = 0; i < categories.size(); i++) {
				if (categories.get(i).getName().equalsIgnoreCase(newCategory)) {

					System.out.println("Exists " + categories.get(i).getName()
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
					"Do you really want to remove this category?", "Remove?",
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
							"In order to remove category please select category row first.");
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
					"Please enter new name of this category", currentCategory);
			// if not empty
			if (!newCategory.equalsIgnoreCase("")) {

				// check if exists
				boolean catExists = false;
				for (int i = 0; i < categories.size(); i++) {
					if (categories.get(i).getName()
							.equalsIgnoreCase(newCategory)) {

						System.out.println("Exists "
								+ categories.get(i).getName() + " "
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
			JOptionPane.showMessageDialog(null, "Please select category first.");
		}
	}
}
