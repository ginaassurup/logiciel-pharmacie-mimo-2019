/*
 * Add ProduitDetail Window class
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
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import model.Categorie;
import model.ProduitJoin;
import model.FournisseurDetail;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AjouterLigneTicketFenetre extends JDialog {

	private static final long serialVersionUID = 1L;

	// database class declaration
	SQLiteCon conn;

	List<ProduitJoin> products;

	// fields that need access
	private final JPanel contentPanel = new JPanel();
//	public JTextField textFieldCodeBarre;
	public JTextField textFieldName;
	public JTextField textFieldType;
	public JTextField textFieldStock;
	public JTextField textFieldPrixVente;
	public JTextField textFieldPrixAchat;
	boolean click;
	JButton btnAddProduct;

	JComboBox<String> comboBoxCategory;
	JComboBox<String> comboBoxUnits;
	JTextField textFieldStockAlarm;
	private JTextField textFieldCodeBarre;

	/**
	 * Lancer l'application.
	 */
	public static void main(String[] args) {
		try {
			AjouterLigneTicketFenetre dialog = new AjouterLigneTicketFenetre();
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
	public AjouterLigneTicketFenetre() {

		// initialise database connection
		conn = new SQLiteCon();

		setModal(true);
		setResizable(false);
		setTitle("Ajouter un produit");
		setBounds(100, 100, 968, 700);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// contentPanel.setBackground(new Color(163, 193, 228));
		contentPanel.setBackground(Color.WHITE);

		textFieldName = new JTextField();
		textFieldName.setBounds(269, 175, 350, 30);
		contentPanel.add(textFieldName);
		textFieldName.setColumns(10);

		comboBoxCategory = new JComboBox(getCategoriesToCombo());
		comboBoxCategory.setBounds(269, 218, 239, 30);
		contentPanel.add(comboBoxCategory);
		
		// combobox highlighter color
		Object child = comboBoxCategory.getAccessibleContext().getAccessibleChild(0);
		BasicComboPopup popup = (BasicComboPopup)child;
		JList list = popup.getList();
		list.setSelectionBackground(new Color(204, 204, 204));

		textFieldType = new JTextField();
		textFieldType.setColumns(10);
		textFieldType.setBounds(269, 261, 350, 30);
		contentPanel.add(textFieldType);

		textFieldStock = new JTextField();
		textFieldStock.setColumns(10);
		textFieldStock.setBounds(269, 304, 350, 30);
		contentPanel.add(textFieldStock);

		JLabel lblName = new JLabel("Libell\u00E9");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setForeground(Color.BLACK);
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblName.setBounds(131, 175, 113, 30);
		contentPanel.add(lblName);

		JLabel lblCategory = new JLabel("Categorie");
		lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCategory.setForeground(Color.BLACK);
		lblCategory.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCategory.setBounds(131, 218, 113, 30);
		contentPanel.add(lblCategory);

		JLabel lblType = new JLabel("Forme");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblType.setForeground(Color.BLACK);
		lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblType.setBounds(131, 261, 113, 30);
		contentPanel.add(lblType);

		JLabel lblStock = new JLabel("Quantit\u00E9 stock");
		lblStock.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStock.setForeground(Color.BLACK);
		lblStock.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStock.setBounds(131, 304, 113, 30);
		contentPanel.add(lblStock);

		btnAddProduct = new JButton("Valider");
		btnAddProduct.setBackground(new Color(204, 204, 204));
		btnAddProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addProduct();
				OuvrirProduitsFenetre();
			}
		});
		btnAddProduct.setBounds(379, 549, 193, 40);
		contentPanel.add(btnAddProduct);

		JButton btnNewCat = new JButton("Nouveau");
		btnNewCat.setFocusPainted(false);
		btnNewCat.setBackground(new Color(204, 204, 204));
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
		comboBoxUnits.setBounds(269, 476, 239, 30);
				
		// combobox highlighter color
		Object childU = comboBoxUnits.getAccessibleContext().getAccessibleChild(0);
		BasicComboPopup popupU = (BasicComboPopup)childU;
		JList listU = popupU.getList();
		listU.setSelectionBackground(new Color(204, 204, 204));
		
		contentPanel.add(comboBoxUnits);

		JButton btnNewUnit = new JButton("Nouveau");
		btnNewUnit.setBackground(new Color(204, 204, 204));
		btnNewUnit.setFocusPainted(false);
		btnNewUnit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openUnits();
			}
		});
		btnNewUnit.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewUnit.setBounds(522, 476, 97, 30);
		contentPanel.add(btnNewUnit);

		JLabel lblUnits = new JLabel("FournisseurDetail");
		lblUnits.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUnits.setForeground(Color.BLACK);
		lblUnits.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUnits.setBounds(131, 476, 113, 30);
		contentPanel.add(lblUnits);

		textFieldStockAlarm = new JTextField();
		textFieldStockAlarm.setColumns(10);
		textFieldStockAlarm.setBounds(269, 347, 350, 30);
		contentPanel.add(textFieldStockAlarm);

		JLabel lblStockAlarm = new JLabel("Stock alarme");
		lblStockAlarm.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStockAlarm.setForeground(Color.BLACK);
		lblStockAlarm.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStockAlarm.setBounds(104, 347, 140, 30);
		contentPanel.add(lblStockAlarm);
		
		JLabel lblCodeBarre = new JLabel("Code barre");
		lblCodeBarre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodeBarre.setForeground(Color.BLACK);
		lblCodeBarre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodeBarre.setBounds(131, 127, 113, 30);
		contentPanel.add(lblCodeBarre);
		
		textFieldCodeBarre = new JTextField();
		textFieldCodeBarre.setColumns(10);
		textFieldCodeBarre.setBounds(269, 127, 350, 30);
		contentPanel.add(textFieldCodeBarre);
		
		JLabel lblAjouterUnProduit = new JLabel("Ajouter un produit");
		lblAjouterUnProduit.setHorizontalAlignment(SwingConstants.CENTER);
		lblAjouterUnProduit.setForeground(new Color(165, 42, 42));
		lblAjouterUnProduit.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAjouterUnProduit.setBounds(248, 83, 498, 25);
		contentPanel.add(lblAjouterUnProduit);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				OuvrirProduitsFenetre();
			}
		});
		btnRetour.setBounds(12, 13, 97, 25);
		contentPanel.add(btnRetour);
		
		JLabel lblPrixDeVente = new JLabel("Prix de vente");
		lblPrixDeVente.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrixDeVente.setForeground(Color.BLACK);
		lblPrixDeVente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrixDeVente.setBounds(131, 390, 113, 30);
		contentPanel.add(lblPrixDeVente);
		
		textFieldPrixVente = new JTextField();
		textFieldPrixVente.setColumns(10);
		textFieldPrixVente.setBounds(269, 390, 350, 30);
		contentPanel.add(textFieldPrixVente);
		
		JLabel lblPrixDachat = new JLabel("Prix d'achat");
		lblPrixDachat.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrixDachat.setForeground(Color.BLACK);
		lblPrixDachat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrixDachat.setBounds(131, 433, 113, 30);
		contentPanel.add(lblPrixDachat);
		
		textFieldPrixAchat = new JTextField();
		textFieldPrixAchat.setColumns(10);
		textFieldPrixAchat.setBounds(269, 433, 350, 30);
		contentPanel.add(textFieldPrixAchat);
		getContentPane().setBackground(new Color(163, 193, 228));
		setLocationRelativeTo(null);

	}

	private void OuvrirProduitsFenetre() {
		
		ProduitsFenetre produitsFenetre = new ProduitsFenetre();
		produitsFenetre.setVisible(true);
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

	// get all listeFour to comboBox
	private String[] getUnitsToCombo() {

		try {
			List<FournisseurDetail> fournisseurDetails = null;
			ArrayList<String> comboUnits = new ArrayList<String>();
			comboUnits.add("");
			fournisseurDetails = conn.getTousFour();

			for (int i = 0; i < fournisseurDetails.size(); i++) {
				comboUnits.add(fournisseurDetails.get(i).getRaison_sociale());
				System.out.println(comboUnits.get(i));
			}

			return comboUnits.toArray(new String[comboUnits.size()]);

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	// adds new product
	private void addProduct() {
		click = false;
		if (fieldsCheck()) {
			String code_barre = textFieldCodeBarre.getText().toString().trim();
			String libelle_produit = textFieldName.getText().toString().trim();
			String nom_cat = comboBoxCategory.getSelectedItem().toString().trim();
			String forme = textFieldType.getText().toString().trim();
			String qtte_stock = textFieldStock.getText().toString().trim();
			String qtte_stock_alarme = textFieldStockAlarm.getText().toString().trim();
			String prix_vente = textFieldPrixVente.getText().toString().trim();
			String prix_achat = textFieldPrixAchat.getText().toString().trim();
			String nom_four = comboBoxUnits.getSelectedItem().toString().trim();



			if (qtte_stock_alarme.equalsIgnoreCase("")) {
				qtte_stock_alarme = "0";
			}

			System.out.println(libelle_produit + " " + nom_cat + " " + forme + " " + qtte_stock + " " + qtte_stock_alarme + " " + prix_vente +  " " + prix_achat +  " " + nom_four);

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
			// if product name and type the same
			for (int i = 0; i < products.size(); i++) {
				if (products.get(i).getLibelle_produit().equalsIgnoreCase(libelle_produit)) {
					System.out.println("Exists " + products.get(i).getLibelle_produit()
							+ " " + libelle_produit);
					productExists = true;
					break;

				}
				
			}
			
			for (int i = 0; i < products.size(); i++) {
			
				if (products.get(i).getForme().equalsIgnoreCase(forme)) {
					typeExists = true;
					break;
				}
			
			}

			if(productExists & typeExists){
				bothExists = true;
			}
			
			if (!bothExists) {
				try {
					conn.insertProductQuery(code_barre, libelle_produit, nom_cat, forme,
							qtte_stock, qtte_stock_alarme, prix_vente, prix_achat, nom_four);
					
//					public void insertProductQuery(String code_barre, String libelle_produit, String nom_cat,
//							String forme, String qtte_stock, String qtte_stock_alarme, String prix_vente, String prix_achat, String nom_four)
					// refresh table
					click = true;
					setVisible(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				clearFields();
			} else {
				JOptionPane.showMessageDialog(null,
						"ProduitDetail with the same name and type exists.");
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
				|| !isNumeric(textFieldStock.getText()) ? true : false;
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

			if (str.equalsIgnoreCase("")) {
				str = "0";
			}

			if (Integer.parseInt(str) < 0) {
				return false;
			} else {
				return true;
			}

		} else {
			return false;
		}

	}

	// clears fields
	private void clearFields() {
		textFieldCodeBarre.setText("");
		textFieldName.setText("");
		comboBoxCategory.setSelectedIndex(0);
		textFieldType.setText("");
		textFieldStock.setText("");
		textFieldStockAlarm.setText("");
		textFieldPrixVente.setText("");
		textFieldPrixAchat.setText("");
		comboBoxUnits.setSelectedIndex(0);

	}

	// opens Categories window
	private void openCategories() {

		CategoriesFenetre categoriesFenetre = new CategoriesFenetre();
		categoriesFenetre.setVisible(true);

		// refreshes combobox after change
		SwingUtilities.invokeLater(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				@SuppressWarnings({ "rawtypes" })
				DefaultComboBoxModel model = new DefaultComboBoxModel(
						getCategoriesToCombo());
				comboBoxCategory.setModel(model);
				comboBoxCategory.setSelectedItem(categoriesFenetre.newCategorie);
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
