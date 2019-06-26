/*
 * ProduitDetail model for join query
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
	private String raison_sociale;
	
	public ProductJoin(int num_prod, int code_barre, String libelle_produit, String nom_cat, String forme, int qtte_stock, int qtte_stock_alarme,
			float prix_vente, float prix_achat, String raison_sociale) {
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
		this.raison_sociale = raison_sociale;
	}
	

	public int getNum_prod() {
		return num_prod;
	}

	public void setNum_prod(int num_prod) {
		this.num_prod = num_prod;
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
	
	public float getPrix_achat() {
		return prix_achat;
	}
	public void setPrix_achat(int prix_achat) {
		this.prix_achat = prix_achat;
	}
	
	public float getPrix_vente() {
		return prix_vente;
	}
	public void setPrix_vente(int prix_vente) {
		this.prix_vente = prix_vente;
	}
	
	public String getRaison_sociale() {
		return raison_sociale;
	}

	public void setRaison_sociale(String raison_sociale) {
		this.raison_sociale = raison_sociale;
	}
	
	
	@Override
	public String toString() {
		return String
				.format("ProduitDetail [num_prod=%s, code_barre=%s, libelle_produit=%s, nom_cat=%s, forme=%s, qtte_stock=%s, qtte_stock_alarme=%s,"
						+ "prix_vente=%s, prix_achat=%s, raison_sociale=%s]",
						num_prod, code_barre, libelle_produit, nom_cat, forme, qtte_stock, qtte_stock_alarme, prix_vente, prix_achat, raison_sociale);
		
//				.format("ProduitDetail [libelle_produit=%s, nom_cat=%s, forme=%s, qtte_stock=%s]",
//						libelle_produit, nom_cat, forme, qtte_stock);
	} 
}
