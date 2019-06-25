/*
 * User model
 */

package model;

public class PharmacienDetail {
	private int num_phar;
	private String identifiant;
	private String mdp;
	private String prenom_phar;
	private String nom_phar;
//	private boolean statut_manager;
	
	// constructor
	public PharmacienDetail(int num_phar, String identifiant, String mdp, String prenom_phar,
			String nom_phar) {
//	public PharmacienDetail(int num_phar, String identifiant, String mdp, String prenom_phar,
//			String nom_phar, boolean statut_manager) {
		super();
		this.num_phar = num_phar;
		this.identifiant = identifiant;
		this.mdp = mdp;
		this.prenom_phar = prenom_phar;
		this.nom_phar = nom_phar;
//		this.statut_manager = statut_manager;
	}
	
	// getters and setters
	public int getNum_phar() {
		return num_phar;
	}
	public void setNum_phar(int id) {
		this.num_phar = id;
	}
	public String getIdentifiant() {
		return identifiant;
	}
	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public String getPrenom_phar() {
		return prenom_phar;
	}
	public void setPrenom_phar(String prenom_phar) {
		this.prenom_phar = prenom_phar;
	}
	public String getNom_phar() {
		return nom_phar;
	}
	public void setNom_phar(String nom_phar) {
		this.nom_phar = nom_phar;
	}
	
//	public boolean getStatut_manager() {
//		return statut_manager;
//	}
//	public void setStatut_manager(boolean statut_manager) {
//		this.statut_manager = statut_manager;
//	}

	
	@Override
	public String toString() {
		return String
				.format("PharmacienDetail [num_phar=%s, identifiant=%s, mdp=%s, prenom_phar=%s, nom_phar=%s]",
						num_phar, identifiant, mdp, prenom_phar, nom_phar);
//				.format("User [id=%s, userName=%s, password=%s, firstName=%s, surname=%s, statut=%s]",
//						num_phar, identifiant, mdp, prenom_phar, nom_phar, statut_manager);
	} 
}
