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

import model.Categorie;
import model.LigneTicket;
import model.PharmacienDetail;
import model.ProductJoin;
import model.ProduitDetail;
import model.Ticket;
import model.Unit;
import view.MenuPrincipal;

public class SQLiteCon {

	public static Connection myConn;
	public static boolean isConnected;
	public static String currentUser = "";
	public static String currentPassword = "";
	private PreparedStatement myStmt;

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
			PreparedStatement pst = myConn.prepareStatement("PRAGMA foreign_keys = ON;");
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
			myRs = myStmt.executeQuery(
					"SELECT * FROM PharmacienDetail WHERE num_phar != 0 ORDER BY identifiant COLLATE NOCASE");

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
			myStmt = myConn.prepareStatement("select * from PharmacienDetail where identifiant like ?");

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
		// PharmacienDetail tempUser = new PharmacienDetail(id, userName, password,
		// firstName, surname);
		return tempUser;
	}

	// insert user
	public void insertUserQuery(String identifiant, String mdp, String prenom_phar, String nom_phar) throws Exception {

		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement(
					"INSERT INTO PharmacienDetail (identifiant, mdp, prenom_phar, nom_phar)" + "VALUES (?, ?, ?, ?)");

			myStmt.setString(1, identifiant);
			myStmt.setString(2, mdp);
			myStmt.setString(3, prenom_phar);
			myStmt.setString(4, nom_phar);

			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}
	}

	// Supprimer un pharmacien query
	public void supprimerUnPharQuery(String num_phar, String identifiant) throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn.prepareStatement("DELETE FROM PharmacienDetail WHERE num_phar = ? AND identifiant = ?");

			myStmt.setString(1, num_phar);
			myStmt.setString(2, identifiant);
			myStmt.execute();

		} catch (Exception e) {

		} finally {
			close(myStmt, null);

		}
	}

	// Mettre � jour le profil d'un pharmacien query
	public void majPharQuery(String num_phar, String identifiant, String mdp, String prenom_phar, String nom_phar)
			throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn.prepareStatement(
					"UPDATE PharmacienDetail SET identifiant = ?, mdp = ?, prenom_phar = ?, nom_phar = ?"
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

	// Modifier le mot de passe query
	public void modifierMdpQuery(String newPassword) throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn.prepareStatement("UPDATE PharmacienDetail SET mdp = ? WHERE identifiant = ?");

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
	 * Categorie Table Methods
	 */

	// gets the list of categories
	public List<Categorie> getAllCategories() throws Exception {

		List<Categorie> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM Categorie ORDER BY Categorie.nom_cat COLLATE NOCASE");

			while (myRs.next()) {
				Categorie tempCategory = convertRowToCategory(myRs);
				list.add(tempCategory);
			}

			return list;

		} finally {
			close(myStmt, myRs);
		}
	}

	// convert row to category
	private Categorie convertRowToCategory(ResultSet myRs) throws SQLException {

		int id_cat = myRs.getInt("id_cat");
		String nom_cat = myRs.getString("nom_cat");

		Categorie tempCategory = new Categorie(id_cat, nom_cat);

		return tempCategory;
	}

	// gets ID of catName
	public int getId_cat(String category) throws SQLException {

		List<Categorie> list = new ArrayList<>();
		Categorie tempCategory = null;

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM Categorie");

			while (myRs.next()) {
				tempCategory = convertRowToCategory(myRs);
				list.add(tempCategory);

				if (tempCategory.getNom_cat().equalsIgnoreCase(category)) {
					break;
				}
			}

			return tempCategory.getId_cat();

		} finally {
			close(myStmt, myRs);
		}
	}

	// insert category
	public void insertCategoryQuery(String nom_cat) throws Exception {

		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("INSERT INTO Categorie (nom_cat)" + "VALUES (?)");

			myStmt.setString(1, nom_cat);
			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}
	}

	// Supprimer une cat�gorie
	public void removeCategoryQuery(String id_cat, String nom_cat) throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn.prepareStatement("DELETE FROM Categorie WHERE id_cat = ? AND nom_cat = ?");

			myStmt.setString(1, id_cat);
			myStmt.setString(2, nom_cat);
			myStmt.execute();
			// JOptionPane.showMessageDialog(null, "Categorie removed");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "This category has products assigned so can't be removed.");
		} finally {
			close(myStmt, null);

		}
	}

	// update category
	public void updateCategoryQuery(String current_cat, String new_cat, String id_cat) throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn.prepareStatement("UPDATE Categorie SET nom_cat = ? WHERE id_cat = ?");

			myStmt.setString(1, new_cat);
			myStmt.setString(2, id_cat);

			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}
	}

	/*
	 * Unit Table methods
	 */

	// gets the list of listeFour
	public List<Unit> getAllUnits() throws Exception {

		List<Unit> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt
					.executeQuery("SELECT * FROM FournisseurDetail ORDER BY FournisseurDetail.id_four COLLATE NOCASE");

			while (myRs.next()) {
				Unit tempUnit = convertRowToUnit(myRs);
				list.add(tempUnit);
			}

			return list;

		} finally {
			close(myStmt, myRs);
		}
	}
	
	
//	public List<PharmacienDetail> getAllUsers() throws Exception {
//
//		List<PharmacienDetail> list = new ArrayList<>();
//
//		Statement myStmt = null;
//		ResultSet myRs = null;
//
//		try {
//			myStmt = myConn.createStatement();
//			myRs = myStmt.executeQuery(
//					"SELECT * FROM PharmacienDetail WHERE num_phar != 1 ORDER BY identifiant COLLATE NOCASE");
//
//			while (myRs.next()) {
//				PharmacienDetail tempUser = convertRowToUser(myRs);
//				list.add(tempUser);
//			}
//
//			return list;
//
//		} finally {
//			close(myStmt, myRs);
//		}
//	}

	// convert row to unit
	private Unit convertRowToUnit(ResultSet myRs) throws SQLException {

		int id_four = myRs.getInt("id_four");
		String raison_sociale = myRs.getString("raison_sociale");
		String adresse_four = myRs.getString("adresse_four");
		int code_postal_four = myRs.getInt("code_postal_four");
		String ville_four = myRs.getString("ville_four");

		Unit tempUnit = new Unit(id_four, raison_sociale, adresse_four, code_postal_four, ville_four);

		return tempUnit;
	}

	// gets ID of unit Name
	public int getId_four(String unit) throws SQLException {

		List<Unit> list = new ArrayList<>();
		Unit tempUnit = null;

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM FournisseurDetail");

			while (myRs.next()) {
				tempUnit = convertRowToUnit(myRs);
				list.add(tempUnit);

				if (tempUnit.getRaison_sociale().equalsIgnoreCase(unit)) {
					break;
				}
			}

			return tempUnit.getId_four();

		} finally {
			close(myStmt, myRs);
		}
	}

	// Ajouter un Fournisseur
	public void insertFourQuery(String raison_sociale, String adresse_four, String code_postal_four, String ville_four)
			throws Exception {

		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement(
					"INSERT INTO FournisseurDetail (raison_sociale, adresse_four, code_postal_four, ville_four)"
							+ "VALUES (?, ?, ?, ?)");

			myStmt.setString(1, raison_sociale);
			myStmt.setString(2, adresse_four);
			myStmt.setString(3, code_postal_four);
			myStmt.setString(4, ville_four);
			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}
	}

	// remove unit query
	public void removeUnitQuery(String id_four, String raison_sociale) throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn.prepareStatement("DELETE FROM FournisseurDetail WHERE id_four = ? AND raison_sociale = ?");

			myStmt.setString(1, id_four);
			myStmt.setString(2, raison_sociale);
			myStmt.execute();
			// JOptionPane.showMessageDialog(null, "Categorie removed");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ce fournisseur est attach� � un produit. Impossible de le supprimer !");
		} finally {
			close(myStmt, null);

		}
	}

	// update unit
	public void majFourQuery(String num_four, String raison_sociale, String adresse_four, String code_postal_four, String ville_four) throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn.prepareStatement("UPDATE FournisseurDetail SET raison_sociale = ?, adresse_four = ?, code_postal_four = ?, ville_four = ? WHERE id_four = ?");

			myStmt.setString(1, raison_sociale);
			myStmt.setString(2, adresse_four);
			myStmt.setString(3, code_postal_four);
			myStmt.setString(4, ville_four);
			myStmt.setString(5, num_four);

			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}
	}
	

	/*
	 * ProduitDetail Table methods
	 */

	// get all products - join table (category id- name)
	public List<ProductJoin> getProductsJoin() throws Exception {

		List<ProductJoin> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(
					"SELECT ProduitDetail.num_prod, ProduitDetail.code_barre, ProduitDetail.libelle_produit, Categorie.nom_cat as nom_cat, "
							+ "ProduitDetail.forme, ProduitDetail.qtte_stock, ProduitDetail.prix_vente, ProduitDetail.prix_achat, FournisseurDetail.raison_sociale as raison_sociale, "
							+ "ProduitDetail.qtte_stock_alarme FROM ProduitDetail INNER JOIN Categorie ON ProduitDetail.id_cat=Categorie.id_cat INNER JOIN FournisseurDetail ON ProduitDetail.id_four=FournisseurDetail.id_four ORDER BY ProduitDetail.num_prod COLLATE NOCASE");

//			myRs = myStmt
//					.executeQuery("SELECT Product.Id, Product.Name, Categorie.Name as CatName, Product.Type, Product.Stock, Unit.Name as UnitName, Product.StockAlarm FROM Product INNER JOIN Categorie ON Product.Category=Categorie.Id INNER JOIN Unit ON Product.Unit=Unit.Id ORDER BY Product.Name COLLATE NOCASE");

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
	private ProductJoin convertRowToProductJoin(ResultSet myRs) throws SQLException {

		int num_prod = myRs.getInt("num_prod");
		int code_barre = myRs.getInt("code_barre");
		String libelle_produit = myRs.getString("libelle_produit");
		String nom_cat = myRs.getString("nom_cat");
		String forme = myRs.getString("forme");
		int qtte_stock = myRs.getInt("qtte_stock");
		int qtte_stock_alarme = myRs.getInt("qtte_stock_alarme");
		float prix_vente = myRs.getFloat("prix_vente");
		float prix_achat = myRs.getFloat("prix_achat");
		String raison_sociale = myRs.getString("raison_sociale");

		ProductJoin tempProductJoin = new ProductJoin(num_prod, code_barre, libelle_produit, nom_cat, forme, qtte_stock,
				qtte_stock_alarme, prix_vente, prix_achat, raison_sociale);

		return tempProductJoin;
	}

	// filters products by category, used in combobox
	public List<ProductJoin> filterProductsByCat(String nom_cat) throws Exception {
		List<ProductJoin> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// catName = "";
			myStmt = myConn.prepareStatement(
					"SELECT ProduitDetail.num_prod, ProduitDetail.code_barre, ProduitDetail.libelle_produit, Categorie.nom_cat as nom_cat, "
							+ "ProduitDetail.forme, ProduitDetail.qtte_stock, ProduitDetail.prix_vente, ProduitDetail.prix_achat, FournisseurDetail.raison_sociale as raison_sociale, "
							+ "ProduitDetail.qtte_stock_alarme FROM ProduitDetail INNER JOIN Categorie ON ProduitDetail.id_cat=Categorie.id_cat INNER JOIN FournisseurDetail "
							+ "ON ProduitDetail.id_four=FournisseurDetail.id_four WHERE Categorie.nom_cat = ? ORDER BY ProduitDetail.libelle_produit COLLATE NOCASE");

//			myStmt = myConn
//					.prepareStatement("SELECT Product.Id, Product.Name, Category.Name as CatName, Product.Type, Product.Stock, Unit.Name as UnitName, Product.StockAlarm FROM Product INNER JOIN Category ON Product.Category=Category.Id INNER JOIN Unit ON Product.Unit=Unit.Id WHERE Category.Name = ? ORDER BY Product.Name COLLATE NOCASE");

			myStmt.setString(1, nom_cat);

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
	public List<ProductJoin> searchProductsJoinCat(String libelle_produit, String nom_cat) throws Exception {
		List<ProductJoin> list = new ArrayList<>();

		myStmt = null;
		ResultSet myRs = null;

		try {

			if (nom_cat.equalsIgnoreCase("Toutes")) {
				System.out.println("If ALL");
				libelle_produit += "%";

				myStmt = myConn.prepareStatement(
						"SELECT ProduitDetail.num_prod, ProduitDetail.code_barre, ProduitDetail.libelle_produit, Categorie.nom_cat as nom_cat, "
								+ "ProduitDetail.forme, ProduitDetail.qtte_stock, ProduitDetail.prix_vente, ProduitDetail.prix_achat, FournisseurDetail.raison_sociale as raison_sociale, "
								+ "ProduitDetail.qtte_stock_alarme FROM ProduitDetail INNER JOIN Categorie ON ProduitDetail.id_cat=Categorie.id_cat INNER JOIN FournisseurDetail ON ProduitDetail.id_four=FournisseurDetail.id_four WHERE ProduitDetail.libelle_produit LIKE ? ORDER BY ProduitDetail.num_prod COLLATE NOCASE");

//				myStmt = myConn
//						.prepareStatement("SELECT ProduitDetail.Id, ProduitDetail.Name, Categorie.Name as CatName, ProduitDetail.Type, ProduitDetail.Stock, Unit.Name as UnitName, ProduitDetail.StockAlarm FROM ProduitDetail INNER JOIN Categorie ON ProduitDetail.Category=Categorie.Id INNER JOIN Unit ON ProduitDetail.Unit=Unit.Id WHERE ProduitDetail.Name LIKE ? ORDER BY ProduitDetail.Name COLLATE NOCASE");

				myStmt.setString(1, libelle_produit);

			} else {
				libelle_produit += "%";
				myStmt = myConn.prepareStatement(
						"SELECT ProduitDetail.num_prod, ProduitDetail.code_barre, ProduitDetail.libelle_produit, Categorie.nom_cat as nom_cat, "
								+ "ProduitDetail.forme, ProduitDetail.qtte_stock, ProduitDetail.prix_vente, ProduitDetail.prix_achat, FournisseurDetail.raison_sociale as raison_sociale, "
								+ "ProduitDetail.qtte_stock_alarme FROM ProduitDetail INNER JOIN Categorie ON ProduitDetail.id_cat=Categorie.id_cat INNER JOIN FournisseurDetail ON ProduitDetail.id_four=FournisseurDetail.id_four WHERE Categorie.nom_cat = ? AND ProduitDetail.libelle_produit LIKE ? ORDER BY ProduitDetail.num_prod COLLATE NOCASE");

//				myStmt = myConn
//						.prepareStatement("SELECT ProduitDetail.Id, ProduitDetail.Name, Categorie.Name as CatName, ProduitDetail.Type, ProduitDetail.Stock, Unit.Name as UnitName, ProduitDetail.StockAlarm FROM ProduitDetail INNER JOIN Categorie ON ProduitDetail.Category=Categorie.Id INNER JOIN Unit ON ProduitDetail.Unit=Unit.Id WHERE Categorie.Name = ? AND ProduitDetail.Name LIKE ? ORDER BY ProduitDetail.Name COLLATE NOCASE");

				myStmt.setString(1, nom_cat);
				myStmt.setString(2, libelle_produit);

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
	
	// Cr�er un Ticket query	
	public Ticket createTicketQuery(Ticket t) throws SQLException {
		
		try {
			myStmt = myConn.prepareStatement(
					"INSERT INTO Ticket (libelle, montant_ticket)"
							+ "VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);

//			myStmt.setInt(1, t.getId_ticket());
			myStmt.setString(1, t.getName());
			myStmt.setFloat(2, t.getTotal());
			System.out.println("Montant: " + t.getTotal() );
			
			myStmt.executeUpdate();
			
			try (ResultSet generatedKeys = myStmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	t.setId_ticket(generatedKeys.getInt(1));
	                return t;
	            }
	            else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }
		} finally {
			close(myStmt, null);
		}
	}
	
	//	Mettre � jour le montant d'un ticket
	public void majMontantTicketQuery(int id_ticket, String libelle, float montant_ticket)
			throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn.prepareStatement(
					"UPDATE Ticket SET montant_ticket = ?, libelle = ?"
							+ "WHERE id_ticket = ?");

			myStmt.setFloat(1, montant_ticket);
			myStmt.setString(2, libelle);
			myStmt.setInt(3, id_ticket);

			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}
	}
	
	//	Cr�er une Ligne de ticket query
	public void createTicketLigneQuery(Ticket t) throws SQLException {
		myStmt = myConn.prepareStatement(
				"INSERT INTO LigneTicket (num_prod, qtte_vendu, montant_ligne, id_ticket)"
		
						+ "VALUES (?,?,?,?)");
		
		PreparedStatement myStmt2 = myConn.prepareStatement(
				"UPDATE ProduitDetail SET qtte_stock = ?  WHERE num_prod = ?");
		try {
			for(LigneTicket l : t.getLignes())
			{
			

			myStmt.setInt(1, l.getNum_prod());
			myStmt.setInt(2, l.getQtte_vendu());
			myStmt.setFloat(3, l.getMontant());
			myStmt.setInt(4, t.getId_ticket());
			
			
			myStmt.executeUpdate();
			
			// 
			int remainsStock = l.getProduct().getQtte_stock() - l.getQtte_vendu();
			myStmt2.setInt(1, remainsStock);
			myStmt2.setInt(2, l.getNum_prod());
			
			myStmt2.executeUpdate();
			
			
			}
			
			return;
		} finally {
			close(myStmt, null);
		}
	}

	// Ajouter un produit
	public void insertProductQuery(String code_barre, String libelle_produit, String nom_cat, String forme,
			String qtte_stock, String qtte_stock_alarme, String prix_vente, String prix_achat, String nom_four)
			throws Exception {

		PreparedStatement myStmt = null;

		// get ID of catName
		int id_cat = getId_cat(nom_cat);

		// get ID of unitName
		int id_four = getId_four(nom_four);

		try {
			myStmt = myConn.prepareStatement(
					"INSERT INTO ProduitDetail (code_barre, libelle_produit, id_cat, forme, qtte_stock, qtte_stock_alarme, prix_vente, prix_achat, id_four)"
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

//			myStmt = myConn
//					.prepareStatement("INSERT INTO Product (Name, Category, Type, Stock, Unit, StockAlarm)"
//							+ "VALUES (?, ?, ?, ?, ?, ?)");

			myStmt.setString(1, code_barre);
			myStmt.setString(2, libelle_produit);
			myStmt.setString(3, "" + id_cat);
			myStmt.setString(4, forme);
			myStmt.setString(5, qtte_stock);
			myStmt.setString(6, qtte_stock_alarme);
			myStmt.setString(7, prix_vente);
			myStmt.setString(8, prix_achat);
			myStmt.setString(9, "" + id_four);

			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}

	}

	// remove product
	public void removeProductQuery(String num_prod, String code_barre) throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn.prepareStatement("DELETE FROM ProduitDetail WHERE num_prod = ? AND code_barre = ?");

			myStmt.setString(1, num_prod);
			myStmt.setString(2, code_barre);

			myStmt.execute();
		} catch (Exception e) {

		} finally {
			close(myStmt, null);
		}
	}

//	public void supprimerUnPharQuery(String num_phar, String identifiant)
//			throws Exception {
//
//		PreparedStatement myStmt = null;
//
//		try {
//
//			myStmt = myConn
//					.prepareStatement("DELETE FROM PharmacienDetail WHERE num_phar = ? AND identifiant = ?");
//
//			myStmt.setString(1, num_phar);
//			myStmt.setString(2, identifiant);
//			myStmt.execute();
//
//		} catch (Exception e) {
//
//		} finally {
//			close(myStmt, null);
//
//		}
//	}

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
	
	public void updateProductQuery(String num_prod, String code_barre, String libelle_produit, String nom_cat,
			String forme, String qtte_stock, String qtte_stock_alarme, String prix_vente, String prix_achat, String nom_four) throws Exception {
		

		PreparedStatement myStmt = null;

		// get ID of catName
		int id_cat = getId_cat(nom_cat);
	
		// get ID of unitName
		int id_four = getId_four(nom_four);

		System.out.println(num_prod);
				
		try {

//			myStmt = myConn
//					.prepareStatement("UPDATE Product SET Name = ?, Category = ?, Type = ?, Stock = ?, Unit = ?, StockAlarm = ?"
//							+ "WHERE Id = ?");

			myStmt = myConn.prepareStatement(
					"UPDATE ProduitDetail SET code_barre=?, libelle_produit = ?, id_cat = ?, forme = ?, qtte_stock = ?, id_four = ?, qtte_stock_alarme = ?, prix_vente = ?, prix_achat = ?"
							+ "WHERE num_prod = ?");

			myStmt.setString(1, code_barre);
			myStmt.setString(2, libelle_produit);
			myStmt.setString(3, "" + id_cat);
			myStmt.setString(4, forme);
			myStmt.setString(5, qtte_stock);
			myStmt.setString(6, "" + id_four);
			myStmt.setString(7, qtte_stock_alarme);
			myStmt.setString(8, prix_vente);
			myStmt.setString(9, prix_achat);
			myStmt.setString(10, num_prod);
			
//			myStmt.setString(1, prodName);
//			myStmt.setString(2, "" + catId);
//			myStmt.setString(3, typeName);
//			myStmt.setString(4, quantityName);
//			myStmt.setString(5, "" + unitId);
//			myStmt.setString(6, stockAlarm);
//			myStmt.setString(7, currentId);
			System.out.println("Update Product Query");
			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}
	}

	// gets ID of prodName
	public int getProductId(String product) throws SQLException {

		List<ProduitDetail> list = new ArrayList<>();
		ProduitDetail tempProduct = null;

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM ProduitDetail");

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
	//public void addStockQuery(String prodId, String prodName, int quantity) throws Exception {
	public void addStockQuery(String prodId, int quantity) throws Exception {
		PreparedStatement myStmt = null;

		//String qString = "" + quantity;

		try {

			myStmt = myConn.prepareStatement(
					//"UPDATE ProduitDetail SET qtte_stock = ProduitDetail.qtte_stock + ? " + "WHERE num_prod = ? AND libelle_produit = ?");
			"UPDATE ProduitDetail SET qtte_stock = ProduitDetail.qtte_stock + ? " + "WHERE num_prod = ? ");
			myStmt.setInt(1, quantity);
			myStmt.setString(2, prodId);
			//myStmt.setString(3, prodName);

			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}

	}

	// remove stock
	public void removeStockQuery(String prodId, String prodName, int quantity, String prodStock, String prodStockAlarm)
			throws Exception {

		int prodStockInt = Integer.parseInt(prodStock);
		int prodStockAlarmInt = Integer.parseInt(prodStockAlarm);

		if (prodStockInt - quantity < 0) {
			JOptionPane.showMessageDialog(null, "Your current stock of \"" + prodName + "\" is " + prodStockInt
					+ ". You can't remove " + quantity + ".");
		} else {

			if (prodStockInt - quantity <= prodStockAlarmInt) {
				JOptionPane.showMessageDialog(null,
						"The stock of this product after this operation will reach stock alarm: " + prodStockAlarmInt
								+ ". Don't forget to order \"" + prodName + "\" soon.");
			}

			PreparedStatement myStmt = null;

			//String qString = "" + quantity;

			try {

				myStmt = myConn.prepareStatement(
						//"UPDATE ProduitDetail SET Stock = (ProduitDetail.Stock - ?) " + "WHERE Id = ? AND Name = ?");
				"UPDATE ProduitDetail SET qtte_stock = (ProduitDetail.qtte_stock - ?) " + "WHERE num_prod = ?");

				myStmt.setInt(1, quantity);
				myStmt.setString(2, prodId);
				//myStmt.setString(3, prodName);

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
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException {

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
	public List<ProduitDetail> getTousProduits() throws Exception {

		List<ProduitDetail> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM ProduitDetail");

			while (myRs.next()) {
				ProduitDetail tempProduct = convertRowToProduct(myRs);
				list.add(tempProduct);
			}

			return list;

		} finally {
			close(myStmt, myRs);
		}
	}

	// convert row to product
	private ProduitDetail convertRowToProduct(ResultSet myRs) throws SQLException {

		int num_prod = myRs.getInt("num_prod");
		int code_barre = myRs.getInt("code_barre");
		String libelle_produit = myRs.getString("libelle_produit");
		int id_cat = myRs.getInt("id_cat");
		String forme = myRs.getString("forme");
		int qtte_stock = myRs.getInt("qtte_stock");
//		int qtte_stock_alarme= myRs.getInt("qtte_stock_alarme");
		float prix_vente = myRs.getFloat("prix_vente");
		float prix_achat = myRs.getFloat("prix_achat");

		ProduitDetail tempProduct = new ProduitDetail(num_prod, code_barre, libelle_produit, id_cat, forme, qtte_stock,
				prix_vente, prix_achat);

		return tempProduct;
	}

	// search products
	public List<ProduitDetail> searcshProducts(String prodName) throws Exception {
		List<ProduitDetail> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			prodName += "%";
			myStmt = myConn.prepareStatement("select * from ProduitDetail where Name like ?");

			myStmt.setString(1, prodName);

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				ProduitDetail tempProduct = convertRowToProduct(myRs);
				list.add(tempProduct);
			}

			return list;
		} finally {
			close(myStmt, myRs);
		}
	}
}
