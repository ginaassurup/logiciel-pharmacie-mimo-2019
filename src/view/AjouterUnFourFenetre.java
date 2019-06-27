/*
 * Add User Window class
 */
package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JDialog;

import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import dao.SQLiteCon;
import model.Unit;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.awt.Font;

public class AjouterUnFourFenetre extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// database class declaration
	SQLiteCon conn;

	List<Unit> listePhar;

	private JTextField textFieldRaisonSociale;
	private JTextField textFieldAdresseFour;
	private JTextField textFieldCodePostalFour;
	private JTextField textFieldVilleFour;

	public JTextField getTextFieldRaisonSociale() {
		return textFieldRaisonSociale;
	}

	public void setTextFieldRaisonSociale(JTextField textFieldUserName) {
		this.textFieldRaisonSociale = textFieldUserName;
	}

	public JTextField getTextFieldAdresseFour() {
		return textFieldAdresseFour;
	}

	public void setTextFieldAdresseFour(JTextField passwordField) {
		this.textFieldAdresseFour = passwordField;
	}

	public JTextField getTextFieldCodePostalFour() {
		return textFieldCodePostalFour;
	}

	public void setTextFieldCodePostalFour(JTextField textFieldFirstName) {
		this.textFieldCodePostalFour = textFieldFirstName;
	}

	public JTextField getTextFieldVilleFour() {
		return textFieldVilleFour;
	}

	public void setTextFieldVilleFour(JTextField textFieldSurname) {
		this.textFieldVilleFour = textFieldSurname;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AjouterUnFourFenetre dialog = new AjouterUnFourFenetre();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public AjouterUnFourFenetre() {
		
		// initialise database connection
		conn = new SQLiteCon();

		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(AjouterUnFourFenetre.class.getResource("/view/User.png")));
		setModal(true);
		setTitle("Ajouter un pharmacien");
		setBounds(100, 100, 968, 600);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);

		textFieldRaisonSociale = new JTextField();
		textFieldRaisonSociale.setBounds(427, 155, 180, 30);
		getContentPane().add(textFieldRaisonSociale);
		textFieldRaisonSociale.setColumns(10);

		textFieldAdresseFour = new JTextField();
		textFieldAdresseFour.setBounds(427, 202, 180, 30);
		getContentPane().add(textFieldAdresseFour);

		textFieldCodePostalFour = new JTextField();
		textFieldCodePostalFour.setColumns(10);
		textFieldCodePostalFour.setBounds(427, 290, 180, 30);
		getContentPane().add(textFieldCodePostalFour);

		textFieldVilleFour = new JTextField();
		textFieldVilleFour.setColumns(10);
		textFieldVilleFour.setBounds(427, 333, 180, 30);
		getContentPane().add(textFieldVilleFour);

		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				addFour();
				OuvrirFenetreUnitsWindow();
			}
		});
		btnValider.setBounds(427, 389, 180, 30);
		btnValider.setBackground(new Color(204, 204, 204));
		getContentPane().add(btnValider);

		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(AjouterUnFourFenetre.class
				.getResource("/view/User.png")));
		label.setBounds(56, 118, 95, 93);
		getContentPane().add(label);

		JLabel lbIdentifiant = new JLabel("Raison sociale *");
		lbIdentifiant.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbIdentifiant.setForeground(Color.BLACK);
		lbIdentifiant.setHorizontalAlignment(SwingConstants.RIGHT);
		lbIdentifiant.setBounds(301, 162, 116, 14);
		getContentPane().add(lbIdentifiant);

		JLabel lbMdp = new JLabel("Adresse");
		lbMdp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbMdp.setForeground(Color.BLACK);
		lbMdp.setHorizontalAlignment(SwingConstants.RIGHT);
		lbMdp.setBounds(301, 209, 116, 14);
		getContentPane().add(lbMdp);

		JLabel lbPrenom = new JLabel("Code postal");
		lbPrenom.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbPrenom.setForeground(Color.BLACK);
		lbPrenom.setHorizontalAlignment(SwingConstants.RIGHT);
		lbPrenom.setBounds(248, 297, 169, 14);
		getContentPane().add(lbPrenom);

		JLabel lbNom = new JLabel("Ville");
		lbNom.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbNom.setForeground(Color.BLACK);
		lbNom.setHorizontalAlignment(SwingConstants.RIGHT);
		lbNom.setBounds(248, 340, 169, 14);
		getContentPane().add(lbNom);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				OuvrirFenetreUnitsWindow();
			}		
		});
		btnRetour.setBounds(12, 13, 97, 25);
		getContentPane().add(btnRetour);
		
		JLabel lblAjouterUnPharmacien = new JLabel("Ajouter un fournisseur");
		lblAjouterUnPharmacien.setHorizontalAlignment(SwingConstants.CENTER);
		lblAjouterUnPharmacien.setForeground(new Color(165, 42, 42));
		lblAjouterUnPharmacien.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAjouterUnPharmacien.setBounds(248, 83, 498, 25);
		getContentPane().add(lblAjouterUnPharmacien);
		setLocationRelativeTo(null);
	}

	// Ouvrir la fenetre de la liste des Pharmaciens
	private void OuvrirFenetreUnitsWindow() {
		
		FournisseursFenetre fournisseursFenetre = new FournisseursFenetre();
		fournisseursFenetre.setVisible(true);
	}

	// adds Fournisseurs
	private void addFour() {

		String raison_sociale = textFieldRaisonSociale.getText().trim();
		@SuppressWarnings("deprecation")
		String adresse_four = textFieldAdresseFour.getText().trim();
		String firstName = textFieldCodePostalFour.getText().trim();
		String surname = textFieldVilleFour.getText().trim();

		try {
			listePhar = conn.getAllUnits();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// if not empty
//		if (!emptyFields()) {
//			JOptionPane.showMessageDialog(null,
//					"Veuillez remplir les champs requis.");
//		}

	}

	// checks if empty
	@SuppressWarnings("deprecation")
	private boolean emptyFields() {

		boolean raison_sociale, adresse_four;

		raison_sociale = textFieldRaisonSociale.getText().trim().equalsIgnoreCase("") ? true
				: false;
		adresse_four = textFieldAdresseFour.getText().trim().equalsIgnoreCase("") ? true
				: false;

		if (!raison_sociale || !adresse_four ) {
			return false;
		} else
			return true;
	}
}
