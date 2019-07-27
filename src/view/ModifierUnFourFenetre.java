/*
 * Edit User class
 */
package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JDialog;

import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
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

public class ModifierUnFourFenetre extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// database class declaration
	SQLiteCon conn;

	List<Unit> listeFour;

	JTextField textFieldRaisonSociale;
	JTextField textFieldAdresseFour;
	JTextField textFieldCodePostalFour;
	JTextField textFieldVilleFour;

	String id_four;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModifierUnFourFenetre dialog = new ModifierUnFourFenetre();
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
	public ModifierUnFourFenetre() {
		getContentPane().setForeground(Color.BLACK);
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));

		// initialise database connection
		conn = new SQLiteCon();

		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ModifierUnFourFenetre.class.getResource("/view/User.png")));
		setModal(true);
		setTitle("Modifier le profil d'un fournisseur");
		setBounds(100, 100, 968, 600);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.WHITE);

		textFieldRaisonSociale = new JTextField();
		textFieldRaisonSociale.setBounds(427, 155, 180, 30);
		getContentPane().add(textFieldRaisonSociale);
		//textFieldRaisonSociale.setColumns(10);

		textFieldAdresseFour = new JTextField();
		textFieldAdresseFour.setBounds(427, 202, 180, 30);
		getContentPane().add(textFieldAdresseFour);
		//textFieldAdresseFour.setColumns(10);

		textFieldCodePostalFour = new JTextField();
		//textFieldCodePostalFour.setColumns(10);
		textFieldCodePostalFour.setBounds(427, 290, 180, 30);
		getContentPane().add(textFieldCodePostalFour);
		

		textFieldVilleFour = new JTextField();
		//textFieldVilleFour.setColumns(10);
		textFieldVilleFour.setBounds(427, 333, 180, 30);
		getContentPane().add(textFieldVilleFour);
		

		JButton btnValider = new JButton("Valider");
		btnValider.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				majFour();
				OuvrirFenetreFournisseurs();
			}
		});
		btnValider.setBounds(427, 389, 180, 30);
		btnValider.setBackground(new Color(204, 204, 204));
		getContentPane().add(btnValider);

		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(ModifierUnFourFenetre.class
				.getResource("/view/User.png")));
		label.setBounds(56, 118, 95, 93);
		getContentPane().add(label);

		JLabel lbRaisonSociale = new JLabel("Raison sociale *");
		lbRaisonSociale.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbRaisonSociale.setForeground(Color.BLACK);
		lbRaisonSociale.setHorizontalAlignment(SwingConstants.RIGHT);
		lbRaisonSociale.setBounds(301, 162, 116, 14);
		getContentPane().add(lbRaisonSociale);

		JLabel lbAdrFour = new JLabel("Adresse");
		lbAdrFour.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbAdrFour.setForeground(Color.BLACK);
		lbAdrFour.setHorizontalAlignment(SwingConstants.RIGHT);
		lbAdrFour.setBounds(301, 209, 116, 14);
		getContentPane().add(lbAdrFour);

		JLabel lbCPFour = new JLabel("Code postal");
		lbCPFour.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbCPFour.setForeground(Color.BLACK);
		lbCPFour.setHorizontalAlignment(SwingConstants.RIGHT);
		lbCPFour.setBounds(248, 297, 169, 14);
		getContentPane().add(lbCPFour);

		JLabel lbVilleFour = new JLabel("Ville");
		lbVilleFour.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbVilleFour.setForeground(Color.BLACK);
		lbVilleFour.setHorizontalAlignment(SwingConstants.RIGHT);
		lbVilleFour.setBounds(248, 340, 169, 14);
		getContentPane().add(lbVilleFour);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				OuvrirFenetreFournisseurs();
			}
		});
		btnRetour.setBounds(12, 13, 97, 25);
		getContentPane().add(btnRetour);
		
		JLabel lbModifFour = new JLabel("Modifier le profil du fournisseur");
		lbModifFour.setHorizontalAlignment(SwingConstants.CENTER);
		lbModifFour.setForeground(new Color(165, 42, 42));
		lbModifFour.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbModifFour.setBounds(248, 83, 498, 25);
		getContentPane().add(lbModifFour);
		setLocationRelativeTo(null);
	}

	// Ouvrir la fenetre de la liste des Pharmaciens
	private void OuvrirFenetreFournisseurs() {
		
		FournisseursFenetre fournisseursFenetre = new FournisseursFenetre();
		fournisseursFenetre.setVisible(true);
	}

	// Mettre à jour le profil du pharmacien
	private void majFour() {

		if (!emptyFields()) {
			
			
			String raison_sociale = textFieldRaisonSociale.getText().trim();
//			String password1 = new String(mdpField.getPassword());
//			@SuppressWarnings("unused")
//			String password2 = new String(mdpField2.getPassword());
			String adresse_four = textFieldAdresseFour.getText().trim();
			String code_postal_four = textFieldCodePostalFour.getText().trim();
			String ville_four = textFieldVilleFour.getText().trim();
	
			try {
				conn.majFourQuery(id_four, raison_sociale, adresse_four, code_postal_four, ville_four);
				System.out.println("updated");
				System.out.println("Id_four mise à jour: "+id_four);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dispose();
			//id_four = "";
		} else {
			JOptionPane.showMessageDialog(null,
					"Make sure that all required fields are filled.");
		}

	}

	// checks if empty
	//@SuppressWarnings("deprecation")
	private boolean emptyFields() {

		boolean raison_sociale, adresse_four, code_postal_four, ville_four;

		raison_sociale = textFieldRaisonSociale.getText().trim().equalsIgnoreCase("") ? true
				: false;
		adresse_four = textFieldAdresseFour.getText().trim().equalsIgnoreCase("") ? true
				: false;
		code_postal_four = textFieldCodePostalFour.getText().trim().equalsIgnoreCase("") ? true
				: false;
		ville_four = textFieldVilleFour.getText().trim().equalsIgnoreCase("") ? true
				: false;

		if (!raison_sociale || !adresse_four || !code_postal_four || !ville_four) {
			return false;
		} else
			return true;
	}

}
