/*
 * Connexion Fenetre
 */

package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import dao.SQLiteCon;

import java.awt.Font;

public class ConnexionFenetre {

	// Déclaration de la base de données
	public SQLiteCon conn;

	private JTextField textFieldIdentifiant;
	private JPasswordField mdpField;
	JFrame frmConnexion;

	/**
	 * Lancer l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ConnexionFenetre fenetre = new ConnexionFenetre();
					fenetre.frmConnexion.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConnexionFenetre() {

		initialize();

		// Connexion à la base de données
		conn = new SQLiteCon();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmConnexion = new JFrame();
		frmConnexion.setResizable(false);
		frmConnexion.setTitle("Logiciel de Gestion de Pharmacie - Se connecter ");
		frmConnexion.setBounds(100, 100, 968, 500);
		frmConnexion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmConnexion.setLocationRelativeTo(null);
		frmConnexion.getContentPane().setLayout(null);
		frmConnexion.getContentPane().setBackground(Color.WHITE);

		JLabel lbIdentifiant = new JLabel("Identfiant");
		lbIdentifiant.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbIdentifiant.setForeground(Color.BLACK);
		lbIdentifiant.setHorizontalAlignment(SwingConstants.RIGHT);
		lbIdentifiant.setBounds(300, 181, 102, 14);
		frmConnexion.getContentPane().add(lbIdentifiant);

		JLabel lbMdp = new JLabel("Mot de passe");
		lbMdp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbMdp.setForeground(Color.BLACK);
		lbMdp.setHorizontalAlignment(SwingConstants.RIGHT);
		lbMdp.setBounds(300, 241, 102, 14);
		frmConnexion.getContentPane().add(lbMdp);

		textFieldIdentifiant = new JTextField();
		textFieldIdentifiant.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldIdentifiant.setBounds(412, 174, 148, 30);
		frmConnexion.getContentPane().add(textFieldIdentifiant);
		textFieldIdentifiant.setColumns(10);

		mdpField = new JPasswordField();
		mdpField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mdpField.setBounds(412, 234, 148, 30);
		frmConnexion.getContentPane().add(mdpField);

		JButton btnSeConnecter = new JButton("Se connecter");
		btnSeConnecter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSeConnecter.setBackground(new Color(204, 204, 204));
		btnSeConnecter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// verify user and password
				conn.login(SQLiteCon.myConn, textFieldIdentifiant, mdpField);
				if (SQLiteCon.isConnected == true) {
					frmConnexion.dispose();
				}
			}
		});
		btnSeConnecter.setBounds(412, 289, 148, 30);
		frmConnexion.getContentPane().add(btnSeConnecter);

		// enter key
		frmConnexion.getRootPane().setDefaultButton(btnSeConnecter);

		JLabel lblLogicielDePharmacie = new JLabel("Logiciel de pharmacie");
		lblLogicielDePharmacie.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogicielDePharmacie.setForeground(new Color(165, 42, 42));
		lblLogicielDePharmacie.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblLogicielDePharmacie.setBounds(330, 100, 261, 25);
		frmConnexion.getContentPane().add(lblLogicielDePharmacie);

	}
}
