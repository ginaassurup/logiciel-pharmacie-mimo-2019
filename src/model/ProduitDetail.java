/*
 * ProduitDetail model
 */

package model;

public class ProduitDetail {
	private int num_prod;
	private int code_barre;
	private String libelle_produit;
	private int id_cat;
	private String forme;
	private int qtte_stock;
	private float prix_vente;
	private float prix_achat;
	
	public ProduitDetail(int num_prod, int code_barre, String libelle_produit, int id_cat, String forme, int qtte_stock, float prix_vente, float prix_achat) {
		super();
		this.num_prod = num_prod;
		this.code_barre = code_barre;
		this.libelle_produit = libelle_produit;
		this.id_cat = id_cat;
		this.forme = forme;
		this.qtte_stock = qtte_stock;
		this.prix_vente = prix_vente;
		this.prix_achat = prix_achat;
	}
	
	// Getters et setters
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
	public int getId_cat() {
		return id_cat;
	}
	public void setId_cat(int id_cat) {
		this.id_cat = id_cat;
	}
	public String getForme() {
		return forme;
	}
	public void setForme(String type) {
		this.forme = type;
	}
	public int getQtte_stock() {
		return qtte_stock;
	}
	public void setQtte_stock(int qtte_stock) {
		this.qtte_stock = qtte_stock;
	}
	
	public float Prix_achat() {
		return this.prix_achat;
	}
		
	public void setPrixAchat(float prix_achat) {
		this.prix_achat = prix_achat;
	}

	public float getPrix_vente() {
		return this.prix_vente;
	}
		
	public void setPrix_vente(float prix_vente) {
		this.prix_vente = prix_vente;
	}
	
	@Override
	public String toString() {
		return String
				.format("ProduitDetail [num_prod=%s, libelle_produit=%s, id_cat=%s, forme=%s, qtte_stock=%s]",
						num_prod, libelle_produit, id_cat, forme, qtte_stock);
	} 
}
