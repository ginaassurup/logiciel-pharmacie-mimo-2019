/*
 * Edit ProduitDetail Window class
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboPopup;

import dao.SQLiteCon;

import java.awt.Toolkit;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import model.Categorie;
import model.ProductJoin;
import model.Unit;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditProductWindow extends JDialog {

	private static final long serialVersionUID = 1L;

	// database class declaration
	SQLiteCon conn;

	List<ProductJoin> products;

	String currentId;
	String currentProductName = "";
	String currentTypeName = "";

	// fields that need access
	private final JPanel contentPanel = new JPanel();
	JTextField textFieldName;
	JTextField textFieldType;
	JTextField textFieldStock;
	JComboBox<String> comboBoxCategory;
	JComboBox<String> comboBoxUnits;
	JTextField textFieldStockAlarm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EditProductWindow dialog = new EditProductWindow();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EditProductWindow() {

		// initialise database connection
		conn = new SQLiteCon();

		setModal(true);
		setResizable(false);
		setTitle("Modifier un produit");
		setBounds(100, 100, 968, 700);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.setBackground(Color.WHITE);

		textFieldName = new JTextField();
		textFieldName.setBounds(188, 40, 162, 20);
		contentPanel.add(textFieldName);
		textFieldName.setColumns(10);

		comboBoxCategory = new JComboBox(getCategoriesToCombo());
		comboBoxCategory.setBounds(188, 80, 81, 20);

		// combobox highlighter color
		Object child = comboBoxCategory.getAccessibleContext()
				.getAccessibleChild(0);
		BasicComboPopup popup = (BasicComboPopup) child;
		JList list = popup.getList();
		list.setSelectionBackground(new Color(204, 204, 204));
		contentPanel.add(comboBoxCategory);

		textFieldType = new JTextField();
		textFieldType.setColumns(10);
		textFieldType.setBounds(188, 120, 162, 20);
		contentPanel.add(textFieldType);

		textFieldStock = new JTextField();
		textFieldStock.setColumns(10);
		textFieldStock.setBounds(188, 160, 162, 20);
		contentPanel.add(textFieldStock);

		JLabel lblName = new JLabel("Name:");
		lblName.setForeground(Color.LIGHT_GRAY);
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblName.setBounds(116, 43, 62, 14);
		contentPanel.add(lblName);

		JLabel lblCategory = new JLabel("Categorie:");
		lblCategory.setForeground(Color.LIGHT_GRAY);
		lblCategory.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCategory.setBounds(116, 83, 62, 14);
		contentPanel.add(lblCategory);

		JLabel lblType = new JLabel("Type:");
		lblType.setForeground(Color.LIGHT_GRAY);
		lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblType.setBounds(116, 123, 62, 14);
		contentPanel.add(lblType);

		JLabel lblStock = new JLabel("Stock:");
		lblStock.setForeground(Color.LIGHT_GRAY);
		lblStock.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStock.setBounds(116, 163, 62, 14);
		contentPanel.add(lblStock);

		JButton btnEditProduct = new JButton("Update ProduitDetail");
		btnEditProduct.setBackground(new Color(204, 204, 204));
		btnEditProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				// update product
				updateProduct();
			}
		});
		btnEditProduct.setBounds(408, 489, 162, 23);
		contentPanel.add(btnEditProduct);

		JButton btnNewCat = new JButton("New");
		btnNewCat.setBackground(new Color(204, 204, 204));
		btnNewCat.setFocusPainted(false);
		btnNewCat.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewCat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				openCategories();

			}
		});
		btnNewCat.setBounds(279, 80, 71, 20);
		contentPanel.add(btnNewCat);

		comboBoxUnits = new JComboBox(getUnitsToCombo());
		comboBoxUnits.setBounds(188, 198, 81, 20);

		// combobox highlighter color
		Object childU = comboBoxUnits.getAccessibleContext()
				.getAccessibleChild(0);
		BasicComboPopup popupU = (BasicComboPopup) childU;
		JList listU = popupU.getList();
		listU.setSelectionBackground(new Color(204, 204, 204));

		contentPanel.add(comboBoxUnits);

		JButton btnNewUnit = new JButton("New");
		btnNewUnit.setFocusPainted(false);
		btnNewUnit.setBackground(new Color(204, 204, 204));
		btnNewUnit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openUnits();
			}
		});
		btnNewUnit.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewUnit.setBounds(279, 198, 71, 20);
		contentPanel.add(btnNewUnit);

		JLabel lblUnit = new JLabel("Unit:");
		lblUnit.setForeground(Color.LIGHT_GRAY);
		lblUnit.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUnit.setBounds(116, 201, 62, 14);
		contentPanel.add(lblUnit);

		textFieldStockAlarm = new JTextField();
		textFieldStockAlarm.setColumns(10);
		textFieldStockAlarm.setBounds(188, 237, 162, 20);
		contentPanel.add(textFieldStockAlarm);

		JLabel lblStockAlarm = new JLabel("Stock Alarm:");
		lblStockAlarm.setForeground(Color.LIGHT_GRAY);
		lblStockAlarm.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStockAlarm.setBounds(79, 240, 99, 14);
		contentPanel.add(lblStockAlarm);

		setLocationRelativeTo(null);

	}

	// get all categories to comboBox
	private String[] getCategoriesToCombo() {

		try {
			List<Categorie> categories = null;
			ArrayList<String> comboCategories = new ArrayList<String>();
			comboCategories.add("");
			categories = conn.getAllCategories();

			for (int i = 0; i < categories.size(); i++) {
				comboCategories.add(categories.get(i).getNom_cat());
				System.out.println(comboCategories.get(i));
			}

			return comboCategories.toArray(new String[comboCategories.size()]);

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	// gets all listeFour to combo
	private String[] getUnitsToCombo() {

		try {
			List<Unit> units = null;
			ArrayList<String> comboUnits = new ArrayList<String>();
			comboUnits.add("");
			units = conn.getAllUnits();

			for (int i = 0; i < units.size(); i++) {
				comboUnits.add(units.get(i).getRaison_sociale());
				System.out.println(comboUnits.get(i));
			}

			return comboUnits.toArray(new String[comboUnits.size()]);

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	// updates product
	private void updateProduct() {

		if (fieldsCheck()) {

			int reply = JOptionPane.showConfirmDialog(null,
					"Do you really want to update this product?", "Update?",
					JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {

				String newProdName = textFieldName.getText().toString().trim();
				String nom_cat = comboBoxCategory.getSelectedItem().toString()
						.trim();
				String forme = textFieldType.getText().toString().trim();
				String qtte_stock = textFieldStock.getText().toString()
						.trim();
				String nom_four = comboBoxUnits.getSelectedItem().toString()
						.trim();

				String qtte_stock_alarme = textFieldStockAlarm.getText().toString()
						.trim();

				if (qtte_stock_alarme.equalsIgnoreCase("")) {
					qtte_stock_alarme = "0";
				}

				System.out.println("current: " + currentProductName
						+ " ! new: " + newProdName);

				try {
					products = conn.getProductsJoin();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// check if exists
				boolean productExists = false;
				boolean typeExists = false;
				boolean bothExists = false;
				
				boolean nameChanged = false;
				
				boolean typeChanged = false;
				
				if (!currentProductName.equalsIgnoreCase(newProdName)){
					nameChanged = true;
				}

				if (!forme.equalsIgnoreCase(currentTypeName)){
					typeChanged = true;
				}
				
				for (int i = 0; i < products.size(); i++) {

					if (products.get(i).getLibelle_produit().equalsIgnoreCase(newProdName)) {

						productExists = true;
						if (products.get(i).getForme().equalsIgnoreCase(forme)) {
							typeExists = true;
							break;
						}

					}

				}

				// check type

				for (int i = 0; i < products.size(); i++) {

					

				}

				if ((productExists && typeExists)) {
					bothExists = true;
				}

				// if product with the same type exists
				if (!bothExists || !nameChanged && !typeChanged) {
					try {
						conn.updateProductQuery(currentId, currentProductName,
								newProdName, nom_cat, forme, qtte_stock,
								nom_four, qtte_stock_alarme);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					currentProductName = textFieldName.getText().toString()
							.trim();

					dispose();
				} else {
					JOptionPane.showMessageDialog(null,
							"ProduitDetail with the same name and type exists.");
				}
			} else {
				// do nothing
			}

		} else {

			JOptionPane
					.showMessageDialog(
							null,
							"Please fill up all the fields and make sure that \"Stock\" & \"Stock Alarm\" are numeric.");
		}
	}

	// checks if required fields are filled up
	private boolean fieldsCheck() {

		boolean name, category, type, stock, unit, stockAlarm;

		name = textFieldName.getText().trim().equalsIgnoreCase("") ? true
				: false;
		category = comboBoxCategory.getSelectedIndex() == 0 ? true : false;
		type = textFieldType.getText().trim().equalsIgnoreCase("") ? true
				: false;
		stock = textFieldStock.getText().trim().equalsIgnoreCase("")
				|| !isNumeric(textFieldStock.getText().trim()) ? true : false;
		unit = comboBoxUnits.getSelectedIndex() == 0 ? true : false;

		stockAlarm = !isNumeric(textFieldStockAlarm.getText()) ? true : false;

		if (name || type || category || stock || unit || stockAlarm) {
			return false;
		} else
			return true;
	}

	// checks if String is numeric
	private static boolean isNumeric(String str) {
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(str, pos);
		if (str.length() == pos.getIndex()) {

			if (Integer.parseInt(str) < 0) {
				return false;
			} else {
				return true;
			}

		} else {
			return false;
		}

	}

	// opens categories window
	private void openCategories() {
		System.out.println("new category");
		CategoriesWindow categoriesWindow = new CategoriesWindow();
		categoriesWindow.setVisible(true);

		// refreshes combobox after change
		SwingUtilities.invokeLater(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				@SuppressWarnings({ "rawtypes" })
				DefaultComboBoxModel model = new DefaultComboBoxModel(
						getCategoriesToCombo());
				comboBoxCategory.setModel(model);
				comboBoxCategory.setSelectedItem(categoriesWindow.newCategory);
			}
		});
	}

	// opens Units window
	private void openUnits() {

		FournisseursFenetre fournisseursFenetre = new FournisseursFenetre();
		fournisseursFenetre.setVisible(true);

		// refreshes combobox after change
		SwingUtilities.invokeLater(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				@SuppressWarnings({ "rawtypes" })
				DefaultComboBoxModel model = new DefaultComboBoxModel(
						getUnitsToCombo());
				comboBoxUnits.setModel(model);
				comboBoxUnits.setSelectedItem(fournisseursFenetre.newUnit);
			}
		});

	}
}