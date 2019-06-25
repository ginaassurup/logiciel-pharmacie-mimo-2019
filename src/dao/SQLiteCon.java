/*
 * SQLite class
 */

package dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Category;
import model.Product;
import model.ProductJoin;
import model.Unit;
import model.PharmacienDetail;
import view.MainWindow;
import view.MenuPrincipal;

public class SQLiteCon {

	public static Connection myConn;
	public static boolean isConnected;
	public static String currentUser = "";
	public static String currentPassword = "";

	// constructor that connects to database
	public SQLiteCon() {

		try {
			// gets name of the database from txt file
			Properties props = new Properties();
			props.load(new FileInputStream("settings.txt"));

			String db = props.getProperty("db");

			Class.forName("org.sqlite.JDBC");
			myConn = DriverManager.getConnection("jdbc:sqlite:" + db);
			System.out.println("Connected");

			// pragma on, and deal with no resultset
			PreparedStatement pst = myConn
					.prepareStatement("PRAGMA foreign_keys = ON;");
			boolean result = pst.execute();

			while (true) {
				if (result) {
					// ResultSet rs = pst.getResultSet();
					// Do something with resultset ...
				} else {
					int updateCount = pst.getUpdateCount();
					if (updateCount == -1) {
						// no more results
						break;
					}
					// Do something with update count ...
				}
				result = pst.getMoreResults();
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);

		}
	}

	/*
	 * User Table methods
	 */

	// login method that verifies user
	@SuppressWarnings("deprecation")
	public void login(Connection conn, JTextField user, JPasswordField pswd) {
		try {
			// declare query that checks if user exists
			String query = "SELECT  * FROM PharmacienDetail where identifiant=? AND mdp=?";
			// put into prepared statement
			PreparedStatement pst = conn.prepareStatement(query);

			pst.setString(1, user.getText());
			pst.setString(2, pswd.getText());

			// get result
			ResultSet rs = pst.executeQuery();

			int count = 0;

			// display result
			while (rs.next()) {
				count++;
			}

			// if match
			if (count == 1) {
				// JOptionPane.showMessageDialog(null, "User and Pass OK");
				// close login window
				isConnected = true;
				// frmInLogin.dispose();
				// open main window

				// assign current user and password (for managing listePhar)
				currentUser = user.getText();
				currentPassword = pswd.getText();

				// open main window
//				MainWindow mainWindow = new MainWindow();
//				mainWindow.setVisible(true);
				
				// open Menu principal
				MenuPrincipal menuPrincipal = new MenuPrincipal();
				menuPrincipal.getFrmMenuPrincipal().setVisible(true);
			}
			// if don't match
			else {
				JOptionPane.showMessageDialog(null, "Identifiant ou mot de passe incorrect");
			}

			rs.close();
			pst.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {

			try {

			} catch (Exception e2) {

			}
		}
	}

	// gets all listePhar to the list
	public List<PharmacienDetail> getAllUsers() throws Exception {

		List<PharmacienDetail> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt
					.executeQuery("SELECT * FROM PharmacienDetail WHERE num_phar != 1 ORDER BY identifiant COLLATE NOCASE");

			while (myRs.next()) {
				PharmacienDetail tempUser = convertRowToUser(myRs);
				list.add(tempUser);
			}

			return list;

		} finally {
			close(myStmt, myRs);
		}
	}

	// search listePhar
	public List<PharmacienDetail> searchUsers(String userName) throws Exception {
		List<PharmacienDetail> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			userName += "%";
			myStmt = myConn
					.prepareStatement("select * from PharmacienDetail where identifiant like ?");

			myStmt.setString(1, userName);

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				PharmacienDetail tempUser = convertRowToUser(myRs);
				list.add(tempUser);
			}

			return list;
		} finally {
			close(myStmt, myRs);
		}
	}

	// convert row to user
	private PharmacienDetail convertRowToUser(ResultSet myRs) throws SQLException {

		int num_phar = myRs.getInt("num_phar");
		String identifiant = myRs.getString("identifiant");
		String mdp = myRs.getString("mdp");
		String prenom_phar = myRs.getString("prenom_phar");
		String nom_phar = myRs.getString("nom_phar");
//		boolean statut_manager = myRs.getBoolean("statut_manager");
		
		PharmacienDetail tempUser = new PharmacienDetail(num_phar, identifiant, mdp, prenom_phar, nom_phar);
//		PharmacienDetail tempUser = new PharmacienDetail(num_phar, identifiant, mdp, prenom_phar, nom_phar, statut_manager);
		// PharmacienDetail tempUser = new PharmacienDetail(id, userName, password, firstName, surname);
		return tempUser;
	}

	// insert user
	public void insertUserQuery(String identifiant, String mdp,
			String prenom_phar, String nom_phar) throws Exception {

		PreparedStatement myStmt = null;

		try {
			myStmt = myConn
					.prepareStatement("INSERT INTO PharmacienDetail (identifiant, mdp, prenom_phar, nom_phar)"
							+ "VALUES (?, ?, ?, ?)");

			myStmt.setString(1, identifiant);
			myStmt.setString(2, mdp);
			myStmt.setString(3, prenom_phar);
			myStmt.setString(4, nom_phar);

			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}
	}

	// remove user query
	public void supprimerUnPharQuery(String num_phar, String identifiant)
			throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn
					.prepareStatement("DELETE FROM PharmacienDetail WHERE num_phar = ? AND identifiant = ?");

			myStmt.setString(1, num_phar);
			myStmt.setString(2, identifiant);
			myStmt.execute();

		} catch (Exception e) {

		} finally {
			close(myStmt, null);

		}
	}

	// update user
	public void updateUserQuery(String num_phar, String identifiant, String mdp,
			String prenom_phar, String nom_phar) throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn
					.prepareStatement("UPDATE PharmacienDetail SET identifiant = ?, mdp = ?, prenom_phar = ?, nom_phar = ?"
							+ "WHERE num_phar = ?");

			myStmt.setString(1, identifiant);
			myStmt.setString(2, mdp);
			myStmt.setString(3, prenom_phar);
			myStmt.setString(4, nom_phar);
			myStmt.setString(5, num_phar);

			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}
	}

	// change password
	public void changePasswordQuery(String newPassword) throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn
					.prepareStatement("UPDATE PharmacienDetail SET mdp = ? WHERE identifiant = ?");

			myStmt.setString(1, newPassword);
			myStmt.setString(2, currentUser);

			myStmt.executeUpdate();

			System.out.println("Password changed");
		} finally {
			close(myStmt, null);
		}

		currentPassword = newPassword;
	}

	/*
	 * Category Table Methods
	 */

	// gets the list of categories
	public List<Category> getAllCategories() throws Exception {

		List<Category> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt
					.executeQuery("SELECT * FROM Category ORDER BY Category.Name COLLATE NOCASE");

			while (myRs.next()) {
				Category tempCategory = convertRowToCategory(myRs);
				list.add(tempCategory);
			}

			return list;

		} finally {
			close(myStmt, myRs);
		}
	}

	// convert row to category
	private Category convertRowToCategory(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("Id");
		String name = myRs.getString("Name");

		Category tempCategory = new Category(id, name);

		return tempCategory;
	}

	// gets ID of catName
	public int getCategoryId(String category) throws SQLException {

		List<Category> list = new ArrayList<>();
		Category tempCategory = null;

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM Category");

			while (myRs.next()) {
				tempCategory = convertRowToCategory(myRs);
				list.add(tempCategory);

				if (tempCategory.getName().equalsIgnoreCase(category)) {
					break;
				}
			}

			return tempCategory.getId();

		} finally {
			close(myStmt, myRs);
		}
	}

	// insert category
	public void insertCategoryQuery(String catName) throws Exception {

		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("INSERT INTO Category (Name)"
					+ "VALUES (?)");

			myStmt.setString(1, catName);
			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}
	}

	// remove category query
	public void removeCategoryQuery(String catId, String catName)
			throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn
					.prepareStatement("DELETE FROM Category WHERE Id = ? AND Name = ?");

			myStmt.setString(1, catId);
			myStmt.setString(2, catName);
			myStmt.execute();
			// JOptionPane.showMessageDialog(null, "Category removed");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"This category has products assigned so can't be removed.");
		} finally {
			close(myStmt, null);

		}
	}

	// update category
	public void updateCategoryQuery(String currentCategory, String newCategory,
			String id) throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn
					.prepareStatement("UPDATE Category SET Name = ? WHERE Id = ?");

			myStmt.setString(1, newCategory);
			myStmt.setString(2, id);

			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}
	}

	/*
	 * Unit Table methods
	 */

	// gets the list of units
	public List<Unit> getAllUnits() throws Exception {

		List<Unit> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt
					.executeQuery("SELECT * FROM Unit ORDER BY Unit.Name COLLATE NOCASE");

			while (myRs.next()) {
				Unit tempUnit = convertRowToUnit(myRs);
				list.add(tempUnit);
			}

			return list;

		} finally {
			close(myStmt, myRs);
		}
	}

	// convert row to unit
	private Unit convertRowToUnit(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("Id");
		String name = myRs.getString("Name");

		Unit tempUnit = new Unit(id, name);

		return tempUnit;
	}

	// gets ID of unit Name
	public int getUnitId(String unit) throws SQLException {

		List<Unit> list = new ArrayList<>();
		Unit tempUnit = null;

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM Unit");

			while (myRs.next()) {
				tempUnit = convertRowToUnit(myRs);
				list.add(tempUnit);

				if (tempUnit.getName().equalsIgnoreCase(unit)) {
					break;
				}
			}

			return tempUnit.getId();

		} finally {
			close(myStmt, myRs);
		}
	}

	// insert unit
	public void insertUnitQuery(String unitName) throws Exception {

		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("INSERT INTO Unit (Name)"
					+ "VALUES (?)");

			myStmt.setString(1, unitName);
			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}
	}

	// remove unit query
	public void removeUnitQuery(String unitId, String unitName)
			throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn
					.prepareStatement("DELETE FROM Unit WHERE Id = ? AND Name = ?");

			myStmt.setString(1, unitId);
			myStmt.setString(2, unitName);
			myStmt.execute();
			// JOptionPane.showMessageDialog(null, "Category removed");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"This unit has products assigned so can't be removed.");
		} finally {
			close(myStmt, null);

		}
	}

	// update unit
	public void updateUnitQuery(String currentUnit, String newUnit, String id)
			throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn
					.prepareStatement("UPDATE Unit SET Name = ? WHERE Id = ?");

			myStmt.setString(1, newUnit);
			myStmt.setString(2, id);

			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}
	}

	/*
	 * Product Table methods
	 */

	// get all products - join table (category id- name)
	public List<ProductJoin> getProductsJoin() throws Exception {

		List<ProductJoin> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt
					.executeQuery("SELECT Product.Id, Product.Name, Category.Name as CatName, Product.Type, Product.Stock, Unit.Name as UnitName, Product.StockAlarm FROM Product INNER JOIN Category ON Product.Category=Category.Id INNER JOIN Unit ON Product.Unit=Unit.Id ORDER BY Product.Name COLLATE NOCASE");

			while (myRs.next()) {
				ProductJoin tempProductJoin = convertRowToProductJoin(myRs);
				list.add(tempProductJoin);
			}

			return list;

		} finally {
			close(myStmt, myRs);
		}
	}

	// convert row to join product
	private ProductJoin convertRowToProductJoin(ResultSet myRs)
			throws SQLException {

		int num_prod= myRs.getInt("num_prod");
		int code_barre = myRs.getInt("code_barre");
		String libelle_produit = myRs.getString("libelle_produit");
		String nom_cat = myRs.getString("nom_cat");
		String forme = myRs.getString("forme");
		int qtte_stock= myRs.getInt("qtte_stock");
		int qtte_stock_alarme= myRs.getInt("qtte_stock_alarme");
		float prix_vente= myRs.getFloat("prix_vente");
		float prix_achat= myRs.getFloat("prix_achat");
		String nom_four = myRs.getString("nom_four");

		ProductJoin tempProductJoin = new ProductJoin(num_prod,code_barre, libelle_produit, nom_cat, forme, qtte_stock, qtte_stock_alarme,
				prix_vente, prix_achat, nom_four);


		return tempProductJoin;
	}

	// filters products by category, used in combobox
	public List<ProductJoin> filterProductsByCat(String catName)
			throws Exception {
		List<ProductJoin> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// catName = "";
			myStmt = myConn
					.prepareStatement("SELECT Product.Id, Product.Name, Category.Name as CatName, Product.Type, Product.Stock, Unit.Name as UnitName, Product.StockAlarm FROM Product INNER JOIN Category ON Product.Category=Category.Id INNER JOIN Unit ON Product.Unit=Unit.Id WHERE Category.Name = ? ORDER BY Product.Name COLLATE NOCASE");

			myStmt.setString(1, catName);

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				ProductJoin tempProductJoin = convertRowToProductJoin(myRs);
				list.add(tempProductJoin);
			}

			return list;
		} finally {
			close(myStmt, myRs);
		}
	}

	// search method used for search button
	public List<ProductJoin> searchProductsJoinCat(String prodName, String cat)
			throws Exception {
		List<ProductJoin> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {

			if (cat.equalsIgnoreCase("All")) {
				System.out.println("If ALL");
				prodName += "%";
				myStmt = myConn
						.prepareStatement("SELECT Product.Id, Product.Name, Category.Name as CatName, Product.Type, Product.Stock, Unit.Name as UnitName, Product.StockAlarm FROM Product INNER JOIN Category ON Product.Category=Category.Id INNER JOIN Unit ON Product.Unit=Unit.Id WHERE Product.Name LIKE ? ORDER BY Product.Name COLLATE NOCASE");

				myStmt.setString(1, prodName);

			} else {
				prodName += "%";
				myStmt = myConn
						.prepareStatement("SELECT Product.Id, Product.Name, Category.Name as CatName, Product.Type, Product.Stock, Unit.Name as UnitName, Product.StockAlarm FROM Product INNER JOIN Category ON Product.Category=Category.Id INNER JOIN Unit ON Product.Unit=Unit.Id WHERE Category.Name = ? AND Product.Name LIKE ? ORDER BY Product.Name COLLATE NOCASE");

				myStmt.setString(1, cat);
				myStmt.setString(2, prodName);

			}

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				ProductJoin tempProductJoin = convertRowToProductJoin(myRs);
				list.add(tempProductJoin);
			}

			return list;
		} finally {
			close(myStmt, myRs);
		}
	}

	// insert product
	public void insertProductQuery(String prodName, String catName,
			String typeName, String quantityName, String unitName,
			String stockAlarm) throws Exception {

		PreparedStatement myStmt = null;

		// get ID of catName
		int catId = getCategoryId(catName);

		// get ID of unitName
		int unitId = getUnitId(unitName);

		try {

			myStmt = myConn
					.prepareStatement("INSERT INTO Product (Name, Category, Type, Stock, Unit, StockAlarm)"
							+ "VALUES (?, ?, ?, ?, ?, ?)");

			myStmt.setString(1, prodName);
			myStmt.setString(2, "" + catId);
			myStmt.setString(3, typeName);
			myStmt.setString(4, quantityName);
			myStmt.setString(5, "" + unitId);
			myStmt.setString(6, stockAlarm);

			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}

	}

	// remove product
	public void removeProductQuery(String prodId, String prodName)
			throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn
					.prepareStatement("DELETE FROM Product WHERE Id = ? AND Name = ?");

			myStmt.setString(1, prodId);
			myStmt.setString(2, prodName);

			myStmt.execute();
		} finally {
			close(myStmt, null);
		}
	}

	// remove all products
	public void removeAllQuery(String tableName) throws Exception {

		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("DELETE FROM " + tableName);
			myStmt.execute();
		} finally {
			close(myStmt, null);
		}
	}

	// update product
	public void updateProductQuery(String currentId, String currentProductName, String prodName,
			String catName, String typeName, String quantityName,
			String unitName, String stockAlarm) throws Exception {

		PreparedStatement myStmt = null;

		// get ID of prod and catName
		int catId = getCategoryId(catName);
		int unitId = getUnitId(unitName);

		System.out.println(currentId);

		try {

			myStmt = myConn
					.prepareStatement("UPDATE Product SET Name = ?, Category = ?, Type = ?, Stock = ?, Unit = ?, StockAlarm = ?"
							+ "WHERE Id = ?");

			myStmt.setString(1, prodName);
			myStmt.setString(2, "" + catId);
			myStmt.setString(3, typeName);
			myStmt.setString(4, quantityName);
			myStmt.setString(5, "" + unitId);
			myStmt.setString(6, stockAlarm);
			myStmt.setString(7, currentId);

			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}
	}

	// gets ID of prodName
	public int getProductId(String product) throws SQLException {

		List<Product> list = new ArrayList<>();
		Product tempProduct = null;

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM Product");

			while (myRs.next()) {
				tempProduct = convertRowToProduct(myRs);
				list.add(tempProduct);

				if (tempProduct.getLibelle_produit().equalsIgnoreCase(product)) {
					break;
				}
			}

			return tempProduct.getNum_prod();

		} finally {
			close(myStmt, myRs);
		}
	}

	// add stock
	public void addStockQuery(String prodId, String prodName, int quantity)
			throws Exception {

		PreparedStatement myStmt = null;

		String qString = "" + quantity;

		try {

			myStmt = myConn
					.prepareStatement("UPDATE Product SET Stock = (Product.Stock + ?) "
							+ "WHERE Id = ? AND Name = ?");

			myStmt.setString(1, qString);
			myStmt.setString(2, prodId);
			myStmt.setString(3, prodName);

			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}

	}

	// remove stock
	public void removeStockQuery(String prodId, String prodName, int quantity,
			String prodStock, String prodStockAlarm) throws Exception {

		int prodStockInt = Integer.parseInt(prodStock);
		int prodStockAlarmInt = Integer.parseInt(prodStockAlarm);

		if (prodStockInt - quantity < 0) {
			JOptionPane.showMessageDialog(null, "Your current stock of \""
					+ prodName + "\" is " + prodStockInt + ". You can't remove "
					+ quantity + ".");
		} else {

			if (prodStockInt - quantity <= prodStockAlarmInt) {
				JOptionPane.showMessageDialog(null,
						"The stock of this product after this operation will reach stock alarm: "
								+ prodStockAlarmInt
								+ ". Don't forget to order \"" + prodName + "\" soon.");
			}

			PreparedStatement myStmt = null;

			String qString = "" + quantity;

			try {

				myStmt = myConn
						.prepareStatement("UPDATE Product SET Stock = (Product.Stock - ?) "
								+ "WHERE Id = ? AND Name = ?");

				myStmt.setString(1, qString);
				myStmt.setString(2, prodId);
				myStmt.setString(3, prodName);

				myStmt.executeUpdate();
			} finally {
				close(myStmt, null);
			}
		}
	}

	/*
	 * Other methods
	 */

	// close connection
	private static void close(Connection myConn, Statement myStmt,
			ResultSet myRs) throws SQLException {

		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {

		}

		if (myConn != null) {
			myConn.close();
		}
	}

	// close when st and rs
	private void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);
	}

	// //////////////////////
	// /DEPRECATED METHODS///
	// //////////////////////

	// get all products
	public List<Product> getAllProducts() throws Exception {

		List<Product> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM Product");

			while (myRs.next()) {
				Product tempProduct = convertRowToProduct(myRs);
				list.add(tempProduct);
			}

			return list;

		} finally {
			close(myStmt, myRs);
		}
	}

	// convert row to product
	private Product convertRowToProduct(ResultSet myRs) throws SQLException {

		int num_prod= myRs.getInt("num_prod");
		int code_barre = myRs.getInt("code_barre");
		String libelle_produit = myRs.getString("libelle_produit");
		int id_cat = myRs.getInt("id_cat");
		String forme = myRs.getString("forme");
		int qtte_stock= myRs.getInt("qtte_stock");
		int qtte_stock_alarme= myRs.getInt("qtte_stock_alarme");
		float prix_vente= myRs.getFloat("prix_vente");
		float prix_achat= myRs.getFloat("prix_achat");

		Product tempProduct = new Product(num_prod, code_barre, libelle_produit, id_cat, forme, qtte_stock, prix_vente, prix_achat);

		return tempProduct;
	}

	// search products
	public List<Product> searcshProducts(String prodName) throws Exception {
		List<Product> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			prodName += "%";
			myStmt = myConn
					.prepareStatement("select * from Product where Name like ?");

			myStmt.setString(1, prodName);

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				Product tempProduct = convertRowToProduct(myRs);
				list.add(tempProduct);
			}

			return list;
		} finally {
			close(myStmt, myRs);
		}
	}
}