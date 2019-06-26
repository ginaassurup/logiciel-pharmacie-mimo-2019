/*
 * Unit model
 */

package model;

public class Unit {
	private int id_four;
	private String raison_sociale;
	private String adresse_four;
	private int code_postal_four;
	private String ville_four;
	
	// constructor
	public Unit(int id_four, String raison_sociale, String adresse_four, int code_postal_four, String ville_four) {
		super();
		this.id_four = id_four;
		this.raison_sociale = raison_sociale;
		this.adresse_four = adresse_four;
		this.code_postal_four = code_postal_four;
		this.ville_four = ville_four; 
	}	
	
	public int getId_four() {
		return id_four;
	}
	public void setId(int id_four) {
		this.id_four = id_four;
	}
	
	public String getRaison_sociale() {
		return raison_sociale;
	}
	public void setRaison_sociale(String raison_sociale) {
		this.raison_sociale = raison_sociale;
	}
	
	public String getAdresse_four() {
		return this.adresse_four;
	}
	public void setAdresse_four(String adresse_four) {
		this.adresse_four = adresse_four;
	}
	
	public int getCode_postal_four() {
		return this.code_postal_four;
	}
	public void setCode_postal_four(int code_postal_four) {
		this.code_postal_four = code_postal_four;
	}
	
	public String getVille_four() {
		return this.ville_four;
	}
	public void setVille_four(String ville_four) {
		this.ville_four = ville_four;
	}

	@Override
	public String toString() {
		
		return this.raison_sociale;
	}
	
	
}