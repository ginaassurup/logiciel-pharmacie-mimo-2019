/*
 * Product model for join query
 */

package model;

public class ProductJoin {
	private int num_prod;
	private int code_barre;
	private String libelle_produit;
	private String nom_cat;
	private String forme;
	private int qtte_stock;
	private int qtte_stock_alarme;
	private float prix_vente;
	private float prix_achat;
	private String nom_four;
	
	public ProductJoin(int num_prod, int code_barre, String libelle_produit, String nom_cat, String forme, int qtte_stock, int qtte_stock_alarme,
			float prix_vente, float prix_achat, String nom_four) {
		super();
		this.num_prod = num_prod;
		this.code_barre = code_barre;
		this.libelle_produit = libelle_produit;
		this.nom_cat = nom_cat;
		this.forme = forme;
		this.qtte_stock = qtte_stock;
		this.qtte_stock_alarme = qtte_stock_alarme;
		this.prix_vente = prix_vente;
		this.prix_achat = prix_achat;
		this.nom_four = nom_four;
	}
	

	public int getNum_prod() {
		return num_prod;
	}

	public void setNum_prod(int id) {
		this.num_prod = id;
	}
	
	public int getCode_barre() {
		return this.code_barre;
	}
		
	public void setCode_barre(int code_barre) {
		this.code_barre = code_barre;
	}
	
	public String getLibelle_produit() {
		return libelle_produit;
	}

	public void setLibelle_produit(String libelle_produit) {
		this.libelle_produit = libelle_produit;
	}
	
	public String getNom_cat() {
		return nom_cat;
	}

	public void setNom_cat(String nom_cat) {
		this.nom_cat = nom_cat;
	}

	public String getForme() {
		return forme;
	}

	public void setForme(String forme) {
		this.forme = forme;
	}
	
	public int getQtte_stock() {
		return qtte_stock;
	}
	public void setQtte_stock(int qtte_stock) {
		this.qtte_stock = qtte_stock;
	}
	
	public int getQtte_stock_alarme() {
		return qtte_stock_alarme;
	}
	public void setQtte_stock_alarme(int qtte_stock_alarme) {
		this.qtte_stock_alarme = qtte_stock_alarme;
	}
	
	public String getNom_four() {
		return nom_four;
	}

	public void setNom_four(String nom_four) {
		this.nom_four = nom_four;
	}
	
	
	@Override
	public String toString() {
		return String
				.format("Product [libelle_produit=%s, nom_cat=%s, forme=%s, qtte_stock=%s]",
						libelle_produit, nom_cat, forme, qtte_stock);
	} 
}
