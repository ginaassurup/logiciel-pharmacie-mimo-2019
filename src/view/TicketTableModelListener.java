package view;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import model.LigneTicketTableModel;
import model.ProductJoin;

public class TicketTableModelListener implements TableModelListener {

	
	@Override
	public void tableChanged(TableModelEvent e) {
		System.out.println("change detected");
		if(TableModelEvent.UPDATE == e.getType() && e.getColumn() == 1)
		{
			LigneTicketTableModel model = (LigneTicketTableModel) e.getSource();
			ProductJoin p = (ProductJoin) model.getValueAt(e.getFirstRow(), e.getColumn());
			//model.setValueAt(p.getLibelle_produit(), e.getFirstRow(), 2);
		}

	}

}
