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
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

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
		textFieldName.setBounds(269, 127, 350, 30);
		contentPanel.add(textFieldName);
		textFieldName.setColumns(10);

		comboBoxCategory = new JComboBox(getCategoriesToCombo());
		comboBoxCategory.setBounds(269, 218, 239, 30);

		// combobox highlighter color
		Object child = comboBoxCategory.getAccessibleContext()
				.getAccessibleChild(0);
		BasicComboPopup popup = (BasicComboPopup) child;
		JList list = popup.getList();
		list.setSelectionBackground(new Color(204, 204, 204));
		contentPanel.add(comboBoxCategory);

		textFieldType = new JTextField();
		textFieldType.setColumns(10);
		textFieldType.setBounds(269, 261, 350, 30);
		contentPanel.add(textFieldType);

		textFieldStock = new JTextField();
		textFieldStock.setColumns(10);
		textFieldStock.setBounds(269, 304, 350, 30);
		contentPanel.add(textFieldStock);

		JLabel lblName = new JLabel("Code barre");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setForeground(new Color(0, 0, 0));
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblName.setBounds(131, 127, 113, 30);
		contentPanel.add(lblName);

		JLabel lblCategory = new JLabel("libelle:");
		lblCategory.setForeground(Color.LIGHT_GRAY);
		lblCategory.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCategory.setBounds(343, 223, 62, 14);
		contentPanel.add(lblCategory);

		JLabel lblType = new JLabel("Categorie");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblType.setForeground(new Color(0, 0, 0));
		lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblType.setBounds(131, 218, 113, 30);
		contentPanel.add(lblType);

		JLabel lblStock = new JLabel("Forme");
		lblStock.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStock.setForeground(new Color(0, 0, 0));
		lblStock.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStock.setBounds(131, 261, 113, 30);
		contentPanel.add(lblStock);

		JButton btnEditProduct = new JButton("Valider");
		btnEditProduct.setBackground(new Color(204, 204, 204));
		btnEditProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				// update product
				updateProduct();
			}
		});
		btnEditProduct.setBounds(358, 551, 193, 40);
		contentPanel.add(btnEditProduct);

		JButton btnNewCat = new JButton("Nouveau");
		btnNewCat.setBackground(new Color(204, 204, 204));
		btnNewCat.setFocusPainted(false);
		btnNewCat.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewCat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				openCategories();

			}
		});
		btnNewCat.setBounds(522, 218, 97, 30);
		contentPanel.add(btnNewCat);

		comboBoxUnits = new JComboBox(getUnitsToCombo());
		comboBoxUnits.setBounds(269, 476, 233, 30);

		// combobox highlighter color
		Object childU = comboBoxUnits.getAccessibleContext()
				.getAccessibleChild(0);
		BasicComboPopup popupU = (BasicComboPopup) childU;
		JList listU = popupU.getList();
		listU.setSelectionBackground(new Color(204, 204, 204));

		contentPanel.add(comboBoxUnits);

		JButton btnNewUnit = new JButton("Nouveau");
		btnNewUnit.setFocusPainted(false);
		btnNewUnit.setBackground(new Color(204, 204, 204));
		btnNewUnit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openUnits();
			}
		});
		btnNewUnit.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewUnit.setBounds(522, 476, 97, 30);
		contentPanel.add(btnNewUnit);

		JLabel lblUnit = new JLabel("Quantit\u00E9 stock");
		lblUnit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUnit.setForeground(new Color(0, 0, 0));
		lblUnit.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUnit.setBounds(131, 304, 113, 30);
		contentPanel.add(lblUnit);

		textFieldStockAlarm = new JTextField();
		textFieldStockAlarm.setColumns(10);
		textFieldStockAlarm.setBounds(269, 347, 350, 30);
		contentPanel.add(textFieldStockAlarm);

		JLabel lblStockAlarm = new JLabel("Stock Alarme");
		lblStockAlarm.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStockAlarm.setForeground(new Color(0, 0, 0));
		lblStockAlarm.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStockAlarm.setBounds(131, 347, 113, 30);
		contentPanel.add(lblStockAlarm);
		
		JLabel lblModifierUnProduit = new JLabel("Modifier un Produit");
		lblModifierUnProduit.setForeground(new Color(153, 0, 0));
		lblModifierUnProduit.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblModifierUnProduit.setBounds(358, 70, 261, 25);
		contentPanel.add(lblModifierUnProduit);
		
		textField = new JTextField();
		textField.setBounds(269, 175, 350, 30);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(269, 390, 350, 30);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(269, 433, 350, 30);
		contentPanel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblPrixDeVente = new JLabel("Prix de vente");
		lblPrixDeVente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrixDeVente.setForeground(new Color(0, 0, 0));
		lblPrixDeVente.setBounds(158, 390, 86, 30);
		contentPanel.add(lblPrixDeVente);
		
		JLabel lblPrixDachat = new JLabel("Prix d'achat");
		lblPrixDachat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrixDachat.setForeground(new Color(0, 0, 0));
		lblPrixDachat.setBounds(167, 439, 77, 14);
		contentPanel.add(lblPrixDachat);
		
		JLabel lblFournisseur = new JLabel("Fournisseur");
		lblFournisseur.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFournisseur.setForeground(new Color(0, 0, 0));
		lblFournisseur.setBounds(167, 482, 77, 14);
		contentPanel.add(lblFournisseur);
		
		JLabel lblLibelle = new JLabel("Libell\u00E9");
		lblLibelle.setForeground(new Color(0, 0, 0));
		lblLibelle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLibelle.setBounds(204, 181, 40, 14);
		contentPanel.add(lblLibelle);

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
				String catName = comboBoxCategory.getSelectedItem().toString()
						.trim();
				String typeName = textFieldType.getText().toString().trim();
				String quantityName = textFieldStock.getText().toString()
						.trim();
				String unitName = comboBoxUnits.getSelectedItem().toString()
						.trim();

				String stockAlarm = textFieldStockAlarm.getText().toString()
						.trim();

				if (stockAlarm.equalsIgnoreCase("")) {
					stockAlarm = "0";
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

				if (!typeName.equalsIgnoreCase(currentTypeName)){
					typeChanged = true;
				}
				
				for (int i = 0; i < products.size(); i++) {

					if (products.get(i).getLibelle_produit().equalsIgnoreCase(newProdName)) {

						productExists = true;
						if (products.get(i).getForme().equalsIgnoreCase(typeName)) {
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
						//conn.updateProductQuery(currentId, currentProductName,
								//newProdName, catName, typeName, quantityName,
								//unitName, stockAlarm);

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