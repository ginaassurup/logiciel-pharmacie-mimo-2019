/*
 * In - ProduitsFenetre class
 */

package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import dao.SQLiteCon;
import model.LigneTicket;
import model.LigneTicketTableModel;
import model.ProductJoin;
import model.ProductJoinTableModel;
import model.ProductTableModel;
import model.ProduitDetail;
import model.Ticket;

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

	// Categories Window declaration
	CategoriesWindow categoriesWindow;

	// Units Window declaration
	FournisseursFenetre fournisseursFenetre;

	// database class declaration
	SQLiteCon conn;

	// fields, buttons, tables that need access
	private Ticket t = new Ticket();
	JTable tableTicket;
	private JPanel contentPane;
	private JTextField montantField;
	//private LigneTicket l = new LigneTicket();

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
		conn = new SQLiteCon();

		createMenuBar();
		setResizable(false);

		setTitle("Saisir un ticket | Utilisateur : " + conn.currentUser);
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

			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
				// Always toggle on single selection
				super.changeSelection(rowIndex, columnIndex, !extend, extend);
				//conn.removeStockQuery(1, 3, quantity, prodStock, prodStockAlarm);
				
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
		btnAddProduct.setBounds(39, 552, 97, 30);
		contentPane.add(btnAddProduct);
		btnAddProduct.setBackground(new Color(204, 204, 204));
		btnAddProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//conn.removeStockQuery(model.get, prodName, quantity, prodStock, prodStockAlarm);
				addRow();
			}
		});
		btnAddProduct.setFont(new Font("Tahoma", Font.PLAIN, 14));

		btnAddProduct.setFocusPainted(false);

		JButton btnRemoveProduct = new JButton("Supprimer");
		btnRemoveProduct.setBounds(148, 552, 97, 30);
		contentPane.add(btnRemoveProduct);
		btnRemoveProduct.setBackground(new Color(204, 204, 204));
		btnRemoveProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				removeRow();
			}
		});

		btnRemoveProduct.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRemoveProduct.setFocusPainted(false);

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

		montantField = new JTextField();
		montantField.setHorizontalAlignment(SwingConstants.CENTER);
		montantField.setForeground(new Color(178, 34, 34));
		montantField.setFont(new Font("Tahoma", Font.BOLD, 16));
		montantField.setEditable(false);
		montantField.setBounds(744, 552, 153, 39);
		contentPane.add(montantField);
		montantField.setColumns(10);
		montantField.setText(String.valueOf(t.getTotal()));

		JButton btnValider = new JButton("Valider");
		btnValider.setForeground(new Color(165, 42, 42));
		btnValider.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnValider.setBounds(744, 609, 153, 43);
		contentPane.add(btnValider);
		setLocationRelativeTo(null);
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				validateTicket();
				majMontantTicket();
				dispose();
			}
		});

		initTicket();
	}

	@SuppressWarnings({ "serial", "unchecked" })
	private void initTicket() {

//		//t.setName("Ticket No : " + t.getId_ticket());
//		try {
//		//	t = conn.createTicketQuery(t);
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

		LigneTicketTableModel model = new LigneTicketTableModel(t.getLignes());
		model.addRow(new LigneTicket(t.getId_ticket(), ""));
		

		tableTicket.setModel(model);
		tableTicket.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if(TableModelEvent.UPDATE == e.getType() && (e.getColumn() == 5 || e.getColumn() == 1))
				{
					montantField.setText(String.valueOf(t.getTotal()));
				}
				
			}
		});

		TableColumn codeBarreColumn = tableTicket.getColumnModel().getColumn(1);

		List<ProductJoin> productsJoin = null;
		try {
			productsJoin = conn.getProductsJoin();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JComboBox<ProductJoin> comboBox = new JComboBox<>();
		comboBox.setRenderer(new BasicComboBoxRenderer() {
			@SuppressWarnings("rawtypes")
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

				if (value != null) {
					ProductJoin item = (ProductJoin) value;
					setText(item.getLibelle_produit().toUpperCase());
				}

				if (index == -1) {
					ProductJoin item = (ProductJoin) value;
					if (item != null) {
						setText("" + item.getLibelle_produit());
					} else {
						setText("");
					}	
				}

				return this;
			}
		});
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("Liste de produit connected");
				}
			}
		});
		for (ProductJoin pj : productsJoin) {
			comboBox.addItem(pj);
		}

		codeBarreColumn.setCellEditor(new DefaultCellEditor(comboBox));
	}

	// Mettre � jour le montant du ticket apr�s avoir valid�
	private void majMontantTicket() {

		try {

			float montant_ticket = t.getTotal();
			int id_ticket = t.getId_ticket();
			String libelle = "Ticket No: " + t.getId_ticket();
			conn.majMontantTicketQuery(id_ticket, libelle, montant_ticket);

			System.out.println("updated");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Ouvrir le menu principal
	private void OuvrirMenuPrincipal() {

		MenuPrincipal menuPrincipal = new MenuPrincipal();
		menuPrincipal.getFrmMenuPrincipal().setVisible(true);
	}

	// menu bar
	private void createMenuBar() {
	}

	// allignment
	private void allignColumn() {
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
		tableTicket.getColumnModel().getColumn(4).setCellRenderer(leftRenderer);
	}

	/*
	 * Add and Remove product
	 */

	// add product
	private void addRow() {
		System.out.println("Add Row");
		
		((LigneTicketTableModel) tableTicket.getModel()).addRow(new LigneTicket(t.getId_ticket(), ""));
		montantField.setText(String.valueOf(t.getTotal()));
		
		//conn.removeStockQuery(, prodName, quantity, prodStock, prodStockAlarm);
		// refreshTable();
//		refreshComboBox();
	}

	private void validateTicket() {

		try {
			t = conn.createTicketQuery(t);
			conn.createTicketLigneQuery(t);
			System.out.println("Montant: " + t.getTotal());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// add product
	private void removeRow() {

		((LigneTicketTableModel) tableTicket.getModel()).removeRow();

		// refreshTable();
//		refreshComboBox();
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

			if (currentProductSearch != null && currentProductSearch.trim().length() > 0) {
				currentListProductJoin = conn.searchProductsJoinCat(currentProductSearch, firstCatStr);
			} else {

				currentListProductJoin = conn.searchProductsJoinCat("", firstCatStr);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ProductJoinTableModel model = new ProductJoinTableModel(currentListProductJoin);

		tableTicket.setModel(model);
		allignColumn();
//		colourIfStockAlarm();
	}

	// //////////////////////
	// /DEPRECATED METHODS///
	// //////////////////////

	// get all products to table
	public void getProducts() {

		try {

			List<ProduitDetail> produitDetails = null;

			produitDetails = conn.getTousProduits();

			ProductTableModel model = new ProductTableModel(produitDetails);
			tableTicket.setModel(model);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
