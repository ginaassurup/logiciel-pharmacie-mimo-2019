/*
 * In - MainWindow class
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
import java.awt.Toolkit;

import model.Categorie;
import model.LigneTicket;
import model.LigneTicketTableModel;
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
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.JTextArea;

public class SaisirUnTicketFenetre extends JFrame {

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
	EditProductWindow editProductWindow;

	// Categories Window declaration
	CategoriesWindow categoriesWindow;

	// Units Window declaration
	FournisseursFenetre fournisseursFenetre;

	// database class declaration
	SQLiteCon conn;

	// fields, buttons, tables that need access
	JTable tableTicket;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					SaisirUnTicketFenetre frame = new SaisirUnTicketFenetre();
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
	public SaisirUnTicketFenetre() {
		
		// initialise connection
//		conn = new SQLiteCon();

		createMenuBar();
		setResizable(false);

		setTitle("Saisir un ticket | Utilisateur : ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 968, 700);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(163, 193, 228));
		scrollPane.setBounds(29, 156, 906, 383);
		contentPane.add(scrollPane);

		tableTicket = new JTable() {
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
		tableTicket.setFillsViewportHeight(true);
		tableTicket.setBackground(SystemColor.window);
		tableTicket.setSelectionBackground(new Color(163, 193, 228));
		tableTicket.setRequestFocusEnabled(false);

		tableTicket.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane.setViewportView(tableTicket);
//		BasicComboPopup popup = (BasicComboPopup)child;
//		JList list = popup.getList();
//		list.setSelectionBackground(new Color(204, 204, 204));

		// request focus
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
			}
		});
		
		JLabel labelLogo = new JLabel("");
		labelLogo.setIcon(null);
		labelLogo.setBounds(319, 516, 64, 64);
		contentPane.add(labelLogo);
		
		JLabel lblProduits = new JLabel("Saisir un ticket");
		lblProduits.setHorizontalAlignment(SwingConstants.CENTER);
		lblProduits.setForeground(new Color(165, 42, 42));
		lblProduits.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblProduits.setBounds(218, 57, 498, 25);
		contentPane.add(lblProduits);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 962, 30);
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

		JButton btnAddProduct = new JButton("Ajouter");
		btnAddProduct.setBounds(39, 550, 80, 30);
		contentPane.add(btnAddProduct);
		btnAddProduct.setBackground(new Color(204, 204, 204));
		btnAddProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				addProduct();
			}
		});
		btnAddProduct.setFont(new Font("Tahoma", Font.PLAIN, 10));
				
		btnAddProduct.setFocusPainted(false);
						
		JButton btnRemoveProduct = new JButton("Supprimer");
		btnRemoveProduct.setBounds(121, 550, 80, 30);
		contentPane.add(btnRemoveProduct);
		btnRemoveProduct.setBackground(new Color(204, 204, 204));
		btnRemoveProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				removeProduct();
			}
		});
		
		btnRemoveProduct.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnRemoveProduct.setFocusPainted(false);
	
		JButton btnEdit = new JButton("Modifier");
		btnEdit.setBounds(203, 550, 80, 30);
		contentPane.add(btnEdit);
		btnEdit.setBackground(new Color(204, 204, 204));
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// edit product
				editProduct();

			}
		});
				
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnEdit.setFocusPainted(false);
				
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				OuvrirMenuPrincipal();
			}
		});
		btnRetour.setBounds(10, 42, 97, 25);
		contentPane.add(btnRetour);
		
		JLabel label = new JLabel("Montant");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setForeground(new Color(165, 42, 42));
		label.setFont(new Font("Tahoma", Font.BOLD, 20));
		label.setBounds(635, 559, 97, 25);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(744, 552, 153, 39);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnValider = new JButton("Valider");
		btnValider.setBounds(744, 609, 153, 43);
		contentPane.add(btnValider);
		setLocationRelativeTo(null);

//		getProductsJoin();
		// refreshTable();
		
		initTicket();
	}

	private void initTicket() {
		List<LigneTicket> ligne = new ArrayList<>(1);
		ligne.add(new LigneTicket("test"));
		LigneTicketTableModel model = new LigneTicketTableModel(ligne);
		tableTicket.setModel(model);
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
			List<ProductJoin> productsJoin = null;
			productsJoin = conn.getProductsJoin();
			ProductJoinTableModel model = new ProductJoinTableModel(
					productsJoin);
			tableTicket.setModel(model);

			
//			hideProductIdColumn();
//			hideStockAlarmColumn();
			allignColumn();
			colourIfStockAlarm();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// hides product id column
	private void hideProductIdColumn() {

		TableColumn productIdColumn = tableTicket.getColumnModel()
				.getColumn(0);
		// tableCategories.getColumnModel().removeColumn(myTableColumn0);
		productIdColumn.setMaxWidth(0);
		productIdColumn.setMinWidth(0);
		productIdColumn.setPreferredWidth(0);

	}

	// hides stockAlarm column
	private void hideStockAlarmColumn() {

		// hides stockAlarm column
		TableColumn stockAlarmColumn = tableTicket.getColumnModel().getColumn(
				6);
		// tableCategories.getColumnModel().removeColumn(myTableColumn0);
		stockAlarmColumn.setMaxWidth(0);
		stockAlarmColumn.setMinWidth(0);
		stockAlarmColumn.setPreferredWidth(0);

	}

	// allignment
	private void allignColumn() {
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
		tableTicket.getColumnModel().getColumn(4)
				.setCellRenderer(leftRenderer);
	}

	// change colour if stockAlarm
	private void colourIfStockAlarm() {
	
		MyRenderer colorRenderer = new MyRenderer();
		tableTicket.getColumnModel().getColumn(4).setCellRenderer(colorRenderer);	
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

	/*
	 * Add and Remove product
	 */

	// add product
	private void addProduct() {

		// initialise AjouterUnProduitFenetre
		ajouterUnProduitFenetre = new AjouterUnProduitFenetre();
		dispose();
		ajouterUnProduitFenetre.setVisible(true);
		ajouterUnProduitFenetre.textFieldName.setText("");
		ajouterUnProduitFenetre.textFieldType.setText("");
		ajouterUnProduitFenetre.textFieldStock.setText("");

		while (ajouterUnProduitFenetre.isVisible()) {

		}

		refreshTable();
//		refreshComboBox();
	}

	// remove product
	private void removeProduct() {
		int prodIdCol = 0;
		int prodNameCol = 1;

		// if row selected
		if (!(tableTicket.getSelectedRow() == -1)) {

			int selectedRow = tableTicket.getSelectedRow();

			String num_prod = tableTicket.getValueAt(selectedRow, prodIdCol)
					.toString().trim();

			String libelle_produit = tableTicket.getValueAt(selectedRow, prodNameCol)
					.toString().trim();

			int reply = JOptionPane.showConfirmDialog(null,
					"Voulez-vous vraiment supprimer ce produit ?", "Remove ?",
					JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {

				try {
					conn.removeProductQuery(num_prod, libelle_produit);

				} catch (Exception e) {
					e.printStackTrace();
				}

				// refresh view here
				refreshTable();

				JOptionPane.showMessageDialog(null, "ProduitDetail removed.");

			} else {
				// do nothing
			}

		} else {
			System.out.println("Nothing selected");
			JOptionPane
					.showMessageDialog(null,
							"In order to remove product please select product row first.");
		}

	}

	// edit product
	private void editProduct() {
		if (!(tableTicket.getSelectedRow() == -1)) {
			editProductWindow = new EditProductWindow();

			int idCol = 0;
			int nameCol = 1;
			int catCol = 2;
			int typeCol = 3;
			int stockCol = 4;
			int unitCol = 5;
			int stockAlarmCol = 6;

			int selectedRow = tableTicket.getSelectedRow();

			System.out.println(tableTicket.getValueAt(selectedRow, idCol));
			
			String idStr = "" +tableTicket.getValueAt(selectedRow, idCol);
			
			editProductWindow.currentId = idStr;
			editProductWindow.textFieldName.setText(tableTicket
					.getValueAt(selectedRow, nameCol).toString().trim());
			editProductWindow.comboBoxCategory.setSelectedItem(tableTicket
					.getValueAt(selectedRow, catCol));
			editProductWindow.textFieldType.setText(tableTicket
					.getValueAt(selectedRow, typeCol).toString().trim());
			editProductWindow.textFieldStock.setText(tableTicket
					.getValueAt(selectedRow, stockCol).toString().trim());
			editProductWindow.comboBoxUnits.setSelectedItem(tableTicket
					.getValueAt(selectedRow, unitCol));

			editProductWindow.textFieldStockAlarm.setText(tableTicket
					.getValueAt(selectedRow, stockAlarmCol).toString().trim());

			currentProductName = editProductWindow.textFieldName.getText()
					.toString().trim();

			editProductWindow.currentProductName = currentProductName;
			editProductWindow.currentTypeName = tableTicket.getValueAt(
					selectedRow, typeCol).toString();

			editProductWindow.setVisible(true);
			while (editProductWindow.isVisible()) {

			}

			refreshTable();
//			refreshComboBox();
		} else {
			JOptionPane.showMessageDialog(null,
					"In order to edit product please select product row first.");
		}
	}

	/*
	 * Other methods
	 */

	// method that refreshes table after changing stock
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ProductJoinTableModel model = new ProductJoinTableModel(
				currentListProductJoin);

		tableTicket.setModel(model);
//		hideProductIdColumn();
//		hideStockAlarmColumn();
		allignColumn();
		colourIfStockAlarm();
	}

	// //////////////////////
	// /DEPRECATED METHODS///
	// //////////////////////

	// get all products to table
	public void getProducts() {

		try {

			List<ProduitDetail> produitDetails = null;

			produitDetails = conn.getAllProducts();

			ProductTableModel model = new ProductTableModel(produitDetails);
			tableTicket.setModel(model);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
