package model;

import java.util.ArrayList;
import java.util.List;

public class Ticket {
	private int id_ticket;
	private String name;
	private float montant_ticket;
	private List<LigneTicket> lignes;
	
	public Ticket() {
		lignes = new ArrayList<LigneTicket>();
	}
	
	
	public int getId_ticket() {
		return id_ticket;
	}
	public void setId_ticket(int id_ticket) {
		this.id_ticket = id_ticket;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getMontant_ticket() {
		return this.getTotal();
	}


	public void setMontant_ticket(float montant_ticket) {
		this.montant_ticket = this.getTotal();
	}


	public void add (LigneTicket ligne) {
		this.lignes.add(ligne);
	}
	
	public void remove (LigneTicket ligne) {
		this.lignes.remove(ligne);
	}
	
	
	
	public List<LigneTicket> getLignes() {
		return lignes;
	}


	public void setLignes(List<LigneTicket> lignes) {
		this.lignes = lignes;
	}
	

	public float getTotal() {
		float total = 0;
		for (LigneTicket l: lignes) {
			total += l.getMontant();
		}
		return total;
	}

}
