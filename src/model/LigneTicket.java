
package model;

public class LigneTicket {
	
	private int id_ligne;
	private int num_ligne;
	private int num_prod;
	private int code_barre;
	private String libelle_produit;
	private float prix_vente;
	private int qtte_vendu;
	
	
	public LigneTicket(String libelle_produit) {
		super();
		this.libelle_produit = libelle_produit;
	}
	public int getId_ligne() {
		return id_ligne;
	}
	public void setId_ligne(int id_ligne) {
		this.id_ligne = id_ligne;
	}
	public int getNum_ligne() {
		return num_ligne;
	}
	public void setNum_ligne(int num_ligne) {
		this.num_ligne = num_ligne;
	}
	public int getNum_prod() {
		return num_prod;
	}
	public void setNum_prod(int num_prod) {
		this.num_prod = num_prod;
	}
	public int getCode_barre() {
		return code_barre;
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
	public int getQtte_vendu() {
		return qtte_vendu;
	}
	public void setQtte_vendu(int qtte_vendu) {
		this.qtte_vendu = qtte_vendu;
	}
	public float getPrix_vente() {
		return prix_vente;
	}
	public void setPrix_vente(float prix_vente) {
		this.prix_vente = prix_vente;
	}
	
	public float getMontant() {
		return this.prix_vente * this.qtte_vendu;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + num_prod;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LigneTicket other = (LigneTicket) obj;
		if (num_prod != other.num_prod)
			return false;
		return true;
	}
	
}