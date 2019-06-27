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
import model.PharmacienDetail;

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

	List<PharmacienDetail> listePhar;

	JTextField textFieldIdentifiant;
	JPasswordField mdpField;
	JTextField textFieldPrenom;
	JTextField textFieldNom;
	JPasswordField mdpField2;
	
	String mdpActuel;

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
		setTitle("Modifier le profil d' un fournisseur");
		setBounds(100, 100, 968, 600);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.WHITE);

		textFieldIdentifiant = new JTextField();
		textFieldIdentifiant.setBounds(427, 155, 180, 30);
		getContentPane().add(textFieldIdentifiant);
		textFieldIdentifiant.setColumns(10);

		mdpField = new JPasswordField();
		mdpField.setBounds(427, 202, 180, 30);
		getContentPane().add(mdpField);

		textFieldPrenom = new JTextField();
		textFieldPrenom.setColumns(10);
		textFieldPrenom.setBounds(427, 290, 180, 30);
		getContentPane().add(textFieldPrenom);

		textFieldNom = new JTextField();
		textFieldNom.setColumns(10);
		textFieldNom.setBounds(427, 333, 180, 30);
		getContentPane().add(textFieldNom);

		JButton btnValider = new JButton("Valider");
		btnValider.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				majPhar();
				OuvrirFenetrePharmaciens();
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

		mdpField2 = new JPasswordField();
		mdpField2.setBounds(427, 247, 180, 30);
		getContentPane().add(mdpField2);

		JLabel lbIdentifiant = new JLabel("Identifiant*:");
		lbIdentifiant.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbIdentifiant.setForeground(Color.BLACK);
		lbIdentifiant.setHorizontalAlignment(SwingConstants.RIGHT);
		lbIdentifiant.setBounds(301, 162, 116, 14);
		getContentPane().add(lbIdentifiant);

		JLabel lbMdp = new JLabel("Mot de passe*:");
		lbMdp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbMdp.setForeground(Color.BLACK);
		lbMdp.setHorizontalAlignment(SwingConstants.RIGHT);
		lbMdp.setBounds(301, 209, 116, 14);
		getContentPane().add(lbMdp);

		JLabel lbConfirmerMdp = new JLabel("Confirmer le mot de passe*:");
		lbConfirmerMdp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbConfirmerMdp.setForeground(Color.BLACK);
		lbConfirmerMdp.setHorizontalAlignment(SwingConstants.RIGHT);
		lbConfirmerMdp.setBounds(213, 254, 204, 14);
		getContentPane().add(lbConfirmerMdp);

		JLabel lbPrenom = new JLabel("Pr\u00E9nom");
		lbPrenom.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbPrenom.setForeground(Color.BLACK);
		lbPrenom.setHorizontalAlignment(SwingConstants.RIGHT);
		lbPrenom.setBounds(248, 297, 169, 14);
		getContentPane().add(lbPrenom);

		JLabel lbNom = new JLabel("Nom");
		lbNom.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbNom.setForeground(Color.BLACK);
		lbNom.setHorizontalAlignment(SwingConstants.RIGHT);
		lbNom.setBounds(248, 340, 169, 14);
		getContentPane().add(lbNom);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				OuvrirFenetrePharmaciens();
			}
		});
		btnRetour.setBounds(12, 13, 97, 25);
		getContentPane().add(btnRetour);
		
		JLabel lblAjoutDunPharmacien = new JLabel("Modifier le profil du fournisseur");
		lblAjoutDunPharmacien.setHorizontalAlignment(SwingConstants.CENTER);
		lblAjoutDunPharmacien.setForeground(new Color(165, 42, 42));
		lblAjoutDunPharmacien.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAjoutDunPharmacien.setBounds(248, 83, 498, 25);
		getContentPane().add(lblAjoutDunPharmacien);
		setLocationRelativeTo(null);
	}

	// Ouvrir la fenetre de la liste des Pharmaciens
	private void OuvrirFenetrePharmaciens() {
		
		PharmaciensFenetre pharmaciensFenetre = new PharmaciensFenetre();
		pharmaciensFenetre.setVisible(true);
	}

	// Mettre à jour le profil du pharmacien
	private void majPhar() {

		if (!emptyFields()) {
			
			String userName = textFieldIdentifiant.getText().trim();
			String password1 = new String(mdpField.getPassword());
			@SuppressWarnings("unused")
			String password2 = new String(mdpField2.getPassword());
			String firstName = textFieldPrenom.getText().trim();
			String surname = textFieldNom.getText().trim();

			if (passwordMatch()) {

				if (!userName.equalsIgnoreCase("admin")) {
					
					try {
						conn.majPharQuery(mdpActuel, userName, password1, firstName, surname);
						System.out.println("updated");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "You cannot rename user into \"admin\".");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Password doesn't match.");
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"Make sure that all required fields are filled.");
		}

	}

	// checks if empty
	@SuppressWarnings("deprecation")
	private boolean emptyFields() {

		boolean userName, password1, password2;

		userName = textFieldIdentifiant.getText().trim().equalsIgnoreCase("") ? true
				: false;
		password1 = mdpField.getText().trim().equalsIgnoreCase("") ? true
				: false;
		password2 = mdpField2.getText().trim().equalsIgnoreCase("") ? true
				: false;

		if (!userName || !password1 || !password2) {
			return false;
		} else
			return true;
	}

	// checks if password match
	private boolean passwordMatch() {

		@SuppressWarnings("deprecation")
		boolean match = mdpField.getText().trim()
				.equals(mdpField2.getText().trim()) ? true : false;

		return match;
	}
}
