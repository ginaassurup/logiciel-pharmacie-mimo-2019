package model;

import java.util.HashSet;
import java.util.Set;

public class Ticket {
	private int id_ticket;
	private Set<LigneTicket> lignes;
	
	public Ticket() {
		lignes = new HashSet<LigneTicket>();
	}
	
	
	public int getId_ticket() {
		return id_ticket;
	}
	public void setId_ticket(int id_ticket) {
		this.id_ticket = id_ticket;
	}


	public void add (LigneTicket ligne) {
		this.lignes.add(ligne);
	}
	
	public void remove (LigneTicket ligne) {
		this.lignes.remove(ligne);
	}
	
	public float getTotal() {
		float total = 0;
		for (LigneTicket l: lignes) {
			total += l.getMontant();
		}
		return total;
	}

}
