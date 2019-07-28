package model;

import java.util.ArrayList;
import java.util.List;

public class Ticket {
	private int id_ticket;
	private String name;
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
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public float getTotal() {
		float total = 0;
		for (LigneTicket l: lignes) {
			total += l.getMontant();
		}
		return total;
	}

}
