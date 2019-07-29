/*
 * In - ProduitsFenetre class
 */

package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import model.Categorie;
import model.MyRenderer;
import model.ProduitDetail;
import model.ProduitJoin;
import model.ProduitJoinTableModel;
import model.ProduitTableModel;

import java.awt.event.KeyAdapter;
import java.awt.Font;

import javax.swing.ListSelectionModel;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.JSeparator;

public class StockDispoFenetre extends JFrame {

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
	List<ProduitJoin> currentListProductJoin;

	// AddProduct Window declaration
	AjouterUnProduitFenetre ajouterUnProduitFenetre;

	// EditProduct Window declaration
	ModifierUnProduitFenetre modifierUnProduitFenetre;

	// Categories Window declaration
	CategoriesFenetre categoriesFenetre;

	// Units Window declaration
	FournisseursFenetre fournisseursFenetre;

	// database class declaration
	SQLiteCon conn;

	// fields, buttons, tables that need access
	JTable tableProduct;
	private JComboBox<String> comboBoxCategory;
	private JTextField textFieldSearch;
	private JPanel contentPane;

	/**
	 * Lancer l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					StockDispoFenetre frame = new StockDispoFenetre();
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
	public StockDispoFenetre() {

		// initialise connection
		conn = new SQLiteCon();

		createMenuBar();
		setResizable(false);

		setTitle("Stock disponible | Utilisateur : " + conn.currentUser);
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

		tableProduct = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
				// Always toggle on single selection
				super.changeSelection(rowIndex, columnIndex, !extend, extend);
			}
		};
		tableProduct.setFillsViewportHeight(true);
		tableProduct.setBackground(SystemColor.window);
		tableProduct.setSelectionBackground(new Color(163, 193, 228));
		tableProduct.setRequestFocusEnabled(false);

		tableProduct.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane.setViewportView(tableProduct);

		comboBoxCategory = new JComboBox(getCategoriesToCombo());

		// combobox highlighter color
		Object child = comboBoxCategory.getAccessibleContext().getAccessibleChild(0);
		BasicComboPopup popup = (BasicComboPopup) child;
		JList list = popup.getList();
		list.setSelectionBackground(new Color(204, 204, 204));

		comboBoxCategory.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent evt) {

				categoryFilter(evt);
			}
		});
		comboBoxCategory.setMaximumRowCount(20);
		comboBoxCategory.setBounds(177, 122, 125, 30);
		contentPane.add(comboBoxCategory);

		textFieldSearch = new JTextField();

		textFieldSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					searchBtn();
				}
			}
		});
		textFieldSearch.setToolTipText("ProduitDetail Name");
		textFieldSearch.setBounds(750, 122, 118, 30);
		contentPane.add(textFieldSearch);
		textFieldSearch.setColumns(10);

		// request focus
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				textFieldSearch.requestFocusInWindow();
			}
		});

		JButton btnSearch = new JButton("Rechercher");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnSearch.setBackground(new Color(204, 204, 204));
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				searchBtn();
			}
		});
		btnSearch.setFocusPainted(false);
		btnSearch.setBounds(880, 122, 118, 30);
		contentPane.add(btnSearch);

		JLabel lblNewLabel = new JLabel("Rechercher par libell\u00E9");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(607, 130, 138, 14);
		contentPane.add(lblNewLabel);

		JButton btnShowAll = new JButton("Tout afficher");
		btnShowAll.setBackground(new Color(204, 204, 204));
		btnShowAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				showwAll();
			}
		});
		btnShowAll.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnShowAll.setFocusPainted(false);
		btnShowAll.setBounds(1023, 121, 113, 30);
		contentPane.add(btnShowAll);

		JLabel labelLogo = new JLabel("");
		labelLogo.setIcon(null);
		labelLogo.setBounds(319, 516, 64, 64);
		contentPane.add(labelLogo);

		JLabel lblProduits = new JLabel("Stock disponible");
		lblProduits.setHorizontalAlignment(SwingConstants.CENTER);
		lblProduits.setForeground(new Color(165, 42, 42));
		lblProduits.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblProduits.setBounds(338, 57, 498, 25);
		contentPane.add(lblProduits);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1174, 30);
		contentPane.add(menuBar);

		JMenu menu = new JMenu("Param\u00E8tres");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.setFont(new Font("Tahoma", Font.PLAIN, 15));
		menuBar.add(menu);

		JMenuItem menuItem = new JMenuItem("Imprimer");
		menu.add(menuItem);

		JSeparator separator = new JSeparator();
		menu.add(separator);

		JMenuItem menuItem_1 = new JMenuItem("Fermer");
		menuItem_1.setToolTipText("Exit application");
		menuItem_1.setMnemonic(KeyEvent.VK_E);
		menu.add(menuItem_1);

		JMenu menu_1 = new JMenu("");
		menu_1.setMnemonic(KeyEvent.VK_F);
		menu_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		menuBar.add(menu_1);

		JSeparator separator_1 = new JSeparator();
		menu_1.add(separator_1);

		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				OuvrirMenuPrincipal();
			}
		});
		btnRetour.setBounds(39, 43, 97, 25);
		contentPane.add(btnRetour);

		JLabel lblFiltreParCatgorie = new JLabel("Filtrer par cat\u00E9gorie");
		lblFiltreParCatgorie.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFiltreParCatgorie.setForeground(Color.BLACK);
		lblFiltreParCatgorie.setBounds(40, 131, 125, 14);
		contentPane.add(lblFiltreParCatgorie);
		setLocationRelativeTo(null);

		getProductsJoin();
		// refreshTable();
	}

	// Ouvrir le menu principal
	private void OuvrirMenuPrincipal() {

		MenuPrincipal menuPrincipal = new MenuPrincipal();
		menuPrincipal.getFrmMenuPrincipal().setVisible(true);
	}

	// menu bar
	private void createMenuBar() {
	}

	/*
	 * Get data to the table and combobox
	 */

	// get all products to the table (join table query)
	private void getProductsJoin() {

		try {
			List<ProduitJoin> productsJoin = null;
			productsJoin = conn.getProductsJoin();
			ProduitJoinTableModel model = new ProduitJoinTableModel(productsJoin);
			tableProduct.setModel(model);

			hideProductIdColumn();
			hidePrixAchatColumn();
			allignColumn();
			colourIfStockAlarm();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// hides product id column
	private void hideProductIdColumn() {

		TableColumn productIdColumn = tableProduct.getColumnModel().getColumn(0);
		productIdColumn.setMaxWidth(0);
		productIdColumn.setMinWidth(0);
		productIdColumn.setPreferredWidth(0);

	}

	// hides prix achat column
	private void hidePrixAchatColumn() {

		TableColumn prixAchatColumn = tableProduct.getColumnModel().getColumn(8);
		prixAchatColumn.setMaxWidth(0);
		prixAchatColumn.setMinWidth(0);
		prixAchatColumn.setPreferredWidth(0);

	}

	// allignment
	private void allignColumn() {
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
		tableProduct.getColumnModel().getColumn(4).setCellRenderer(leftRenderer);
	}

	// change colour if stockAlarm
	private void colourIfStockAlarm() {

		MyRenderer colorRenderer = new MyRenderer();
		tableProduct.getColumnModel().getColumn(5).setCellRenderer(colorRenderer);
	}

	// get all categories to comboBox
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

	// refreshes combobox after change
	public void refreshComboBox() {

		SwingUtilities.invokeLater(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				@SuppressWarnings({ "rawtypes" })
				DefaultComboBoxModel model = new DefaultComboBoxModel(getCategoriesToCombo());
				comboBoxCategory.setModel(model);
				comboBoxCategory.setSelectedItem(firstCatStr);
			}
		});
	}

	/*
	 * ProduitDetail search and category filter
	 */

	// search button method
	private void searchBtn() {

		try {
			String product = textFieldSearch.getText();
			currentProductSearch = product;
			List<ProduitJoin> productsJoin = null;

			if (product != null && product.trim().length() > 0) {
				productsJoin = conn.searchProductsJoinCat(product, firstCatStr);
			} else {
				productsJoin = conn.searchProductsJoinCat("", firstCatStr);
			}

			for (ProduitJoin temp : productsJoin) {
				System.out.println(temp);
			}

			ProduitJoinTableModel model = new ProduitJoinTableModel(productsJoin);

			tableProduct.setModel(model);
			hideProductIdColumn();
			hidePrixAchatColumn();
			allignColumn();
			colourIfStockAlarm();
			currentListProductJoin = productsJoin;

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// show all method
	private void showwAll() {
		textFieldSearch.setText("");
		searchBtn();
	}

	// filters category depending on item in combobox
	private void categoryFilter(ItemEvent evt) {
		Object item = evt.getItem();

		if (evt.getStateChange() == ItemEvent.SELECTED) {
			// Item was just selected
			try {

				firstCatStr = item.toString();

				System.out.println(firstCatStr);

				List<ProduitJoin> productsJoin = null;

				if (firstCatStr.equalsIgnoreCase("Toutes")) {
					productsJoin = conn.getProductsJoin();
				} else {
					productsJoin = conn.filterProductsByCat(firstCatStr);
				}

				for (ProduitJoin temp : productsJoin) {
					System.out.println(temp);
				}

				ProduitJoinTableModel model = new ProduitJoinTableModel(productsJoin);
				tableProduct.setModel(model);
				hideProductIdColumn();
				hidePrixAchatColumn();
				allignColumn();
				colourIfStockAlarm();
				currentListProductJoin = productsJoin;
				textFieldSearch.setText("");

			} catch (Exception e) {
				// TODO: handle exception
			}

		} else if (evt.getStateChange() == ItemEvent.DESELECTED) {
			// Item is no longer selected
		}
	}

	/*
	 * AUTRES FONCTIONS
	 */

	// Fonction rafraichir la table
	public void refreshTable() {

		try {
			if (firstCatStr.equalsIgnoreCase("Toutes")) {
				currentListProductJoin = conn.getProductsJoin();
			} else {
				currentListProductJoin = conn.filterProductsByCat(firstCatStr);
			}

			if (currentProductSearch != null && currentProductSearch.trim().length() > 0) {
				currentListProductJoin = conn.searchProductsJoinCat(currentProductSearch, firstCatStr);
			} else {
				currentListProductJoin = conn.searchProductsJoinCat("", firstCatStr);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ProduitJoinTableModel model = new ProduitJoinTableModel(currentListProductJoin);

		tableProduct.setModel(model);
		hideProductIdColumn();
		hidePrixAchatColumn();
		allignColumn();
		colourIfStockAlarm();
	}

	// get Tous les produits
	public void getProducts() {

		try {

			List<ProduitDetail> produitDetails = null;

			produitDetails = conn.getTousProduits();

			ProduitTableModel model = new ProduitTableModel(produitDetails);
			tableProduct.setModel(model);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
