/*
 * In - ProduitsFenetre class
 */

package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import dao.SQLiteCon;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import model.Categorie;
import model.MyRenderer;
import model.ProduitDetail;
import model.ProductJoin;
import model.ProductJoinTableModel;
import model.ProductTableModel;

import java.awt.event.KeyAdapter;
import java.awt.Font;

import javax.swing.ListSelectionModel;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.JSeparator;

public class ProduitsFenetre extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// first category in combo box
	String firstCatStr = "Toutes";

	// selected product
	String currentProductName;

	// current product search string
	String currentProductSearch;

	// current product search result
	List<ProductJoin> currentListProductJoin;

	// AddProduct Window declaration
	AjouterUnProduitFenetre ajouterUnProduitFenetre;

	// EditProduct Window declaration
	ModifierUnProduitFenetre modifierUnProduitFenetre;

	// Categories Window declaration
	CategoriesWindow categoriesWindow;

	// Units Window declaration
	FournisseursFenetre fournisseursFenetre;

	// database class declaration
	SQLiteCon conn;

	// fields, buttons, tables that need access
	JTable tableProduit;
	private JComboBox<String> comboBoxCategories;
	private JTextField textFieldRechercher;
	private JButton buttonPlus;
	private JButton btnMoins;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ProduitsFenetre frame = new ProduitsFenetre();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
	public ProduitsFenetre() {
		
		// initialiser la connection
		conn = new SQLiteCon();

		setResizable(false);

		setTitle("Produits | Utilisateur : " + conn.currentUser);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1180, 828);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(163, 193, 228));
		scrollPane.setBounds(39, 178, 1096, 473);
		contentPane.add(scrollPane);

		tableProduit = new JTable() {
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
		tableProduit.setFillsViewportHeight(true);
		tableProduit.setBackground(SystemColor.window);
		tableProduit.setSelectionBackground(new Color(163, 193, 228));
		tableProduit.setRequestFocusEnabled(false);

		tableProduit.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane.setViewportView(tableProduit);

		comboBoxCategories = new JComboBox(getCategoriesToCombo());

		// combobox highlighter color
		Object child = comboBoxCategories.getAccessibleContext().getAccessibleChild(0);
		BasicComboPopup popup = (BasicComboPopup)child;
		JList list = popup.getList();
		list.setSelectionBackground(new Color(204, 204, 204));
		
		
		comboBoxCategories.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent evt) {

				filtrerCategories(evt);
			}
		});
		comboBoxCategories.setMaximumRowCount(20);
		comboBoxCategories.setBounds(162, 122, 125, 30);
		contentPane.add(comboBoxCategories);

		textFieldRechercher = new JTextField();

		textFieldRechercher.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					btnRechercher();
				}
			}
		});
		textFieldRechercher.setToolTipText("ProduitDetail Name");
		textFieldRechercher.setBounds(750, 122, 118, 30);
		contentPane.add(textFieldRechercher);
		textFieldRechercher.setColumns(10);

		// request focus
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				textFieldRechercher.requestFocusInWindow();
			}
		});

		JButton btnRechercher = new JButton("Rechercher");
		btnRechercher.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRechercher.setBackground(new Color(204, 204, 204));
		btnRechercher.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnRechercher();
			}
		});
		btnRechercher.setFocusPainted(false);
		btnRechercher.setBounds(880, 122, 118, 30);
		contentPane.add(btnRechercher);

		JLabel lblNewLabel = new JLabel("Rechercher par libellé");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(607, 125, 138, 22);
		contentPane.add(lblNewLabel);

		JButton btnToutAfficher = new JButton("Tout afficher");
		btnToutAfficher.setBackground(new Color(204, 204, 204));
		btnToutAfficher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				toutAfficher();
			}
		});
		btnToutAfficher.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnToutAfficher.setFocusPainted(false);
		btnToutAfficher.setBounds(1023, 121, 113, 30);
		contentPane.add(btnToutAfficher);

		JLabel labelProduits = new JLabel("Gestion des produits");
		labelProduits.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelProduits.setForeground(new Color(165, 42, 42));
		labelProduits.setHorizontalAlignment(SwingConstants.CENTER);
		labelProduits.setBounds(127, 691, 244, 30);
		contentPane.add(labelProduits);

		JLabel labelStock = new JLabel("Stock");
		labelStock.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelStock.setForeground(new Color(165, 42, 42));
		labelStock.setHorizontalAlignment(SwingConstants.CENTER);
		labelStock.setBounds(974, 695, 75, 22);
		contentPane.add(labelStock);
		
		JLabel lblProduits = new JLabel("Gestion des Produits");
		lblProduits.setHorizontalAlignment(SwingConstants.CENTER);
		lblProduits.setForeground(new Color(165, 42, 42));
		lblProduits.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblProduits.setBounds(338, 57, 498, 25);
		contentPane.add(lblProduits);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1174, 30);
		contentPane.add(menuBar);
		
		JMenu menu = new JMenu("Paramètres");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.setFont(new Font("Tahoma", Font.PLAIN, 15));
		menuBar.add(menu);
		
		JMenuItem menuItemImprimer = new JMenuItem("Imprimer");
		menu.add(menuItemImprimer);
		
		JSeparator separator = new JSeparator();
		menu.add(separator);
		
		JMenuItem menuItemFermer = new JMenuItem("Fermer");
		menuItemFermer.setToolTipText("Exit application");
		menuItemFermer.setMnemonic(KeyEvent.VK_E);
		menu.add(menuItemFermer);

		JButton btnAjouterUnProduit = new JButton("Ajouter");
		btnAjouterUnProduit.setBounds(43, 722, 125, 30);
		contentPane.add(btnAjouterUnProduit);
		btnAjouterUnProduit.setBackground(new Color(204, 204, 204));
		btnAjouterUnProduit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				AjouterUnProduit();
			}
		});
		btnAjouterUnProduit.setFont(new Font("Tahoma", Font.PLAIN, 14));
				
		btnAjouterUnProduit.setFocusPainted(false);
						
		JButton btnSupprimerUnProduit = new JButton("Supprimer");
		btnSupprimerUnProduit.setBounds(180, 722, 125, 30);
		contentPane.add(btnSupprimerUnProduit);
		btnSupprimerUnProduit.setBackground(new Color(204, 204, 204));
		btnSupprimerUnProduit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				supprimerUnProduit();
			}
		});
		
		btnSupprimerUnProduit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSupprimerUnProduit.setFocusPainted(false);
	
		JButton btnModifierUnProduit = new JButton("Modifier");
		btnModifierUnProduit.setBounds(317, 722, 125, 30);
		contentPane.add(btnModifierUnProduit);
		btnModifierUnProduit.setBackground(new Color(204, 204, 204));
		btnModifierUnProduit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// edit product
				modifierUnProduit();

			}
		});
				
		btnModifierUnProduit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnModifierUnProduit.setFocusPainted(false);
		
		buttonPlus = new JButton("+");
		buttonPlus.setBounds(1007, 722, 125, 30);
		contentPane.add(buttonPlus);
		buttonPlus.setBackground(new Color(204, 204, 204));
		buttonPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ajouterStock();
			}
		});
		
		buttonPlus.setFocusPainted(false);
		
		btnMoins = new JButton("-");
		btnMoins.setBounds(880, 722, 125, 30);
		contentPane.add(btnMoins);
		btnMoins.setBackground(new Color(204, 204, 204));
		btnMoins.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				supprimerStock();
			}
		});
		btnMoins.setFocusPainted(false);
				
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				OuvrirMenuPrincipal();
			}
		});
		btnRetour.setBounds(39, 43, 97, 25);
		contentPane.add(btnRetour);
		
		JLabel lblFiltrerParCatgorie = new JLabel("Filtrer par catégorie");
		lblFiltrerParCatgorie.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFiltrerParCatgorie.setHorizontalAlignment(SwingConstants.LEFT);
		lblFiltrerParCatgorie.setForeground(Color.BLACK);
		lblFiltrerParCatgorie.setBounds(39, 124, 125, 25);
		contentPane.add(lblFiltrerParCatgorie);
		setLocationRelativeTo(null);

		getProductsJoin();
		// refreshTable();
	}

	// Ouvrir le menu principal
	private void OuvrirMenuPrincipal() {
		
		MenuPrincipal menuPrincipal = new MenuPrincipal();
		menuPrincipal.getFrmMenuPrincipal().setVisible(true);
	}
	

	/*
	 * Récupérer les données à la table et au comboBox
	 */

	// get all products to the table (join table query)
	private void getProductsJoin() {

		try {
			List<ProductJoin> productsJoin = null;
			productsJoin = conn.getProductsJoin();
			ProductJoinTableModel model = new ProductJoinTableModel(
					productsJoin);
			tableProduit.setModel(model);
			
//			hideProductIdColumn();
			allignColumn();
			couleurSiStockAlarme();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// hides product id column
	private void hideProductIdColumn() {

		TableColumn productIdColumn = tableProduit.getColumnModel()
				.getColumn(0);
		// tableCategories.getColumnModel().removeColumn(myTableColumn0);
		productIdColumn.setMaxWidth(0);
		productIdColumn.setMinWidth(0);
		productIdColumn.setPreferredWidth(0);

	}

	// allignment
	private void allignColumn() {
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
		tableProduit.getColumnModel().getColumn(4)
				.setCellRenderer(leftRenderer);
	}

	// Changer la couleur si Stock < StockAlarme
	private void couleurSiStockAlarme() {
	
		MyRenderer colorRenderer = new MyRenderer();
		tableProduit.getColumnModel().getColumn(5).setCellRenderer(colorRenderer);	
	}

	// get toutes les catégories au comboBox
	private String[] getCategoriesToCombo() {

		try {
			List<Categorie> categories = null;
			ArrayList<String> comboCategories = new ArrayList<String>();
			comboCategories.add("Toutes");
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

	// Rafraichir le combox après le changement
	public void refreshComboBox() {

		SwingUtilities.invokeLater(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				@SuppressWarnings({ "rawtypes" })
				DefaultComboBoxModel model = new DefaultComboBoxModel(
						getCategoriesToCombo());
				comboBoxCategories.setModel(model);
				comboBoxCategories.setSelectedItem(firstCatStr);
			}
		});
	}

	////////////////////////////////////////////////////
	/* RECHERCHER UN PRODUIT ET FILTRER PAR CATEGORIE */
	////////////////////////////////////////////////////

	// Fonction Rechercher un produit
	private void btnRechercher() {

		try {
			String produit = textFieldRechercher.getText();
			currentProductSearch = produit;
			List<ProductJoin> productsJoin = null;

			if (produit != null && produit.trim().length() > 0) {
				productsJoin = conn.searchProductsJoinCat(produit, firstCatStr);
			} else {
				productsJoin = conn.searchProductsJoinCat("", firstCatStr);
			}

			for (ProductJoin temp : productsJoin) {
				System.out.println(temp);
			}

			ProductJoinTableModel model = new ProductJoinTableModel(
					productsJoin);

			tableProduit.setModel(model);
			allignColumn();
			couleurSiStockAlarme();
			currentListProductJoin = productsJoin;

		} catch (Exception e) {
			
		}
	}

	// Fonction Tout Afficher
	private void toutAfficher() {
		textFieldRechercher.setText("");
		btnRechercher();
	}

	// Fonction Filtrer par catégorie
	private void filtrerCategories(ItemEvent evt) {
		Object item = evt.getItem();

		if (evt.getStateChange() == ItemEvent.SELECTED) {
			// Un item vient d'etre choisi
			try {

				firstCatStr = item.toString();

				System.out.println(firstCatStr);

				List<ProductJoin> productsJoin = null;

				if (firstCatStr.equalsIgnoreCase("Toutes")) {
					productsJoin = conn.getProductsJoin();
				} else {
					productsJoin = conn.filterProductsByCat(firstCatStr);
				}

				for (ProductJoin temp : productsJoin) {
					System.out.println(temp);
				}

				ProductJoinTableModel model = new ProductJoinTableModel(
						productsJoin);
				tableProduit.setModel(model);
				allignColumn();
				couleurSiStockAlarme();
				currentListProductJoin = productsJoin;
				textFieldRechercher.setText("");

			} catch (Exception e) {
				
			}

		} else if (evt.getStateChange() == ItemEvent.DESELECTED) {
			// Item is no longer selected
		}
	}

	///////////////////////////////////
	/* AJOUTER ET SUPPRIMER LE STOCK */
	///////////////////////////////////

	// Fonction Ajouter le stock
	@SuppressWarnings("static-access")
	private void ajouterStock() {
		int prodIdCol = 0;

		// Si une ligne est sélectionnée
		if (!(tableProduit.getSelectedRow() == -1)) {
			int selectedRow = tableProduit.getSelectedRow();
			System.out.println("Ligne de stock à maj: "+selectedRow);
			String prodId = tableProduit.getValueAt(selectedRow, prodIdCol)
					.toString().trim();
			//String prodName = tableProduit.getValueAt(selectedRow, prodCol)
					//.toString().trim();

			boolean numeric = false;
			int quantity = 0;

			JOptionPane inpOption = new JOptionPane();
			String strDialogResponse = "";

			do {
				// Shows a inputdialog
				strDialogResponse = inpOption
						.showInputDialog("Combien de quantité voulez-vous ajouter dans le stock ?");
				// if OK is pushed then (if not strDialogResponse is null)
				if (strDialogResponse != null) {

					try {
						quantity = Integer.parseInt(strDialogResponse.trim());
						numeric = true;
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null,
								"Veuillez saisir un nombre entier");
						numeric = false;
					}
				}// Si cliquer sur le bouton Annuler
				else {
					break;
				}
			} while (!numeric);

			try {
				conn.addStockQuery(prodId, quantity);

			} catch (Exception e) {
				e.printStackTrace();
			}

			// Rafraichir la view
			refreshTable();
		} else {

			JOptionPane.showMessageDialog(null,
					"Pour modifier le stock, veuillez choisir un produit !");
		}
	}

	// Supprimer le stock
	@SuppressWarnings("static-access")
	private void supprimerStock() {
		int prodIdCol = 0;
		int prodCol = 2;
		int prodStockCol = 5;
		int prodStockAlarmCol = 6;

		// Si une ligne est sélectionnée
		if (!(tableProduit.getSelectedRow() == -1)) {

			int selectedRow = tableProduit.getSelectedRow();

			String prodId = tableProduit.getValueAt(selectedRow, prodIdCol)
					.toString().trim();
			String prodName = tableProduit.getValueAt(selectedRow, prodCol)
					.toString().trim();

			String prodStock = tableProduit
					.getValueAt(selectedRow, prodStockCol).toString().trim();

			String prodStockAlarm = tableProduit
					.getValueAt(selectedRow, prodStockAlarmCol).toString()
					.trim();

			boolean numeric = false;
			int quantity = 0;
			JOptionPane inpOption = new JOptionPane();
			String strDialogResponse = "";

			do {
				// Shows a input dialog
				strDialogResponse = inpOption
						.showInputDialog("Combien de quantité voulez-vous supprimier du stock ?");
				// if OK is pushed then (if not strDialogResponse is null)
				if (strDialogResponse != null) {

					try {
						quantity = Integer.parseInt(strDialogResponse);
						numeric = true;
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null,
								"Veuillez saisir un nombre entier");
						numeric = false;
					}
				}// Si cliquer sur le bouton Annuler
				else {
					break;
				}
			} while (!numeric);

			try {
				conn.removeStockQuery(prodId, prodName, quantity, prodStock,
						prodStockAlarm);

			} catch (Exception e) {
				e.printStackTrace();
			}
			// Rafraichir la view
			refreshTable();
		} else {
			System.out.println("Aucun produit est selectionné");
			JOptionPane.showMessageDialog(null,
					"Pour modifier le stock, veuillez choisir un produit !");
		}
	}

	/////////////////////////////////////
	/* AJOUTER ET SUPPRIMER UN PRODUIT */
	/////////////////////////////////////

	// Ajouter un produit
	private void AjouterUnProduit() {

		// initialiser AjouterUnProduitFenetre
		ajouterUnProduitFenetre = new AjouterUnProduitFenetre();
		dispose();
		ajouterUnProduitFenetre.setVisible(true);
		ajouterUnProduitFenetre.textFieldName.setText("");
		ajouterUnProduitFenetre.textFieldType.setText("");
		ajouterUnProduitFenetre.textFieldStock.setText("");

		while (ajouterUnProduitFenetre.isVisible()) {

		}

		refreshTable();
		refreshComboBox();
	}

	// Supprimer un produit
	private void supprimerUnProduit() {
		int prodIdCol = 0;
		int prodNameCol = 1;

		// Si une ligne est sélectionnée
		if (!(tableProduit.getSelectedRow() == -1)) {

			int selectedRow = tableProduit.getSelectedRow();

			String num_prod = tableProduit.getValueAt(selectedRow, prodIdCol)
					.toString().trim();

			String code_barre = tableProduit.getValueAt(selectedRow, prodNameCol)
					.toString().trim();

			int reply = JOptionPane.showConfirmDialog(null,
					"Voulez-vous vraiment supprimer ce produit ?", "Supprimer ?",
					JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {

				try {
					conn.removeProductQuery(num_prod, code_barre);

				} catch (Exception e) {
					e.printStackTrace();
				}

				// refresh view here
				refreshTable();

				JOptionPane.showMessageDialog(null, "Un produit est supprimé avec succès");

			} else {
				// do nothing
			}

		} else {
			System.out.println("Aucun produit est selectionné");
			JOptionPane
					.showMessageDialog(null,
							"Pour supprimer un produit, veuillez choisir un produit !");
		}

	}

	// Modifier un produit
	private void modifierUnProduit() {
		if (!(tableProduit.getSelectedRow() == -1)) {
			modifierUnProduitFenetre = new ModifierUnProduitFenetre();
			dispose();
			int idCol = 0;
			int codebarreCol=1;
			int nameCol = 2;
			int catCol = 3;
			int typeCol = 4;
			int stockCol = 5;
			int stockAlarmCol = 6;
			int prixVenteCol = 7;
			int prixAchatCol = 8;
			int unitCol = 9;
			int selectedRow = tableProduit.getSelectedRow();

			//System.out.println(tableProduit.getValueAt(selectedRow, idCol));
		
			//String idStr = "" +tableProduit.getValueAt(selectedRow, idCol);
			
//			modifierUnProduitFenetre. = idStr;
//			modifierUnProduitFenetre.textFieldCodeBarre.setText();
			modifierUnProduitFenetre.num_prodActuel = tableProduit.getValueAt(selectedRow, idCol).toString().trim();
			modifierUnProduitFenetre.textFieldCodeBarre.setText(tableProduit.getValueAt(selectedRow, codebarreCol).toString().trim());
			modifierUnProduitFenetre.textFieldName.setText(tableProduit
					.getValueAt(selectedRow, nameCol).toString().trim());
			modifierUnProduitFenetre.comboBoxCategory.setSelectedItem(tableProduit
					.getValueAt(selectedRow, catCol));
			modifierUnProduitFenetre.textFieldType.setText(tableProduit
					.getValueAt(selectedRow, typeCol).toString().trim());
			modifierUnProduitFenetre.textFieldStock.setText(tableProduit
					.getValueAt(selectedRow, stockCol).toString().trim());
			modifierUnProduitFenetre.comboBoxUnits.setSelectedItem(tableProduit
					.getValueAt(selectedRow, unitCol));

			modifierUnProduitFenetre.textFieldStockAlarm.setText(tableProduit
					.getValueAt(selectedRow, stockAlarmCol).toString().trim());
			modifierUnProduitFenetre.textFieldPrixVente.setText(tableProduit
					.getValueAt(selectedRow, prixVenteCol).toString().trim());
			modifierUnProduitFenetre.textFieldPrixAchat.setText(tableProduit
					.getValueAt(selectedRow, prixAchatCol).toString().trim());
			
			currentProductName = modifierUnProduitFenetre.textFieldName.getText()
					.toString().trim();
			System.out.println("Produit choisi: " + currentProductName);

//			modifierUnProduitFenetre.currentProductName = currentProductName;
//			modifierUnProduitFenetre.currentTypeName = tableProduit.getValueAt(
//					selectedRow, typeCol).toString();

			modifierUnProduitFenetre.setVisible(true);
			while (modifierUnProduitFenetre.isVisible()) {

			}

			refreshTable();
			refreshComboBox();
		} else {
			JOptionPane.showMessageDialog(null,
					"Veuillez choisir le produit � modifier.");
		}
	}

	//////////////////////
	/* AUTRES FONCTIONS */
	//////////////////////

	// Fonction Rafraichir la view
	public void refreshTable() {

		try {
			if (firstCatStr.equalsIgnoreCase("Toutes")) {
				currentListProductJoin = conn.getProductsJoin();
			} else {
				currentListProductJoin = conn.filterProductsByCat(firstCatStr);
			}

			if (currentProductSearch != null
					&& currentProductSearch.trim().length() > 0) {
				currentListProductJoin = conn.searchProductsJoinCat(
						currentProductSearch, firstCatStr);
			} else {
				currentListProductJoin = conn.searchProductsJoinCat("",
						firstCatStr);
			}

		} catch (Exception e) {
			
			e.printStackTrace();
		}

		ProductJoinTableModel model = new ProductJoinTableModel(currentListProductJoin);

		tableProduit.setModel(model);
		allignColumn();
		couleurSiStockAlarme();
	}
	

	// Récupérer tous les produits à la table
	public void getTousProduits() {

		try {

			List<ProduitDetail> produitDetails = null;

			produitDetails = conn.getTousProduits();

			ProductTableModel model = new ProductTableModel(produitDetails);
			tableProduit.setModel(model);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
