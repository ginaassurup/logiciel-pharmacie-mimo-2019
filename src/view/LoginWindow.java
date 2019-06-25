/*
 * In - Login Window 
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

import javax.swing.ImageIcon;

import dao.SQLiteCon;

import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Window.Type;

public class LoginWindow {

	// database connection class declaration
	public SQLiteCon conn;
	
	// fields that need access and change
	private JTextField textFieldUserName;
	private JPasswordField passwordField;
	private JFrame frmInLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.frmInLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginWindow() {

		initialize();
		
		// connect to database
		conn = new SQLiteCon();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInLogin = new JFrame();
		frmInLogin.setResizable(false);
		frmInLogin.setTitle("Logiciel de Gestion de Pharmacie - Se connecter ");
		frmInLogin.setBounds(100, 100, 968, 500);
		frmInLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInLogin.setLocationRelativeTo(null);
		frmInLogin.getContentPane().setLayout(null);
		frmInLogin.getContentPane().setBackground(Color.WHITE);
		

		JLabel lblUserName = new JLabel("Identfiant");
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUserName.setForeground(Color.BLACK);
		lblUserName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUserName.setBounds(300, 181, 102, 14);
		frmInLogin.getContentPane().add(lblUserName);

		JLabel lblPassword = new JLabel("Mot de passe");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setForeground(Color.BLACK);
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(300, 241, 102, 14);
		frmInLogin.getContentPane().add(lblPassword);

		textFieldUserName = new JTextField();
		textFieldUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldUserName.setBounds(412, 174, 148, 30);
		frmInLogin.getContentPane().add(textFieldUserName);
		textFieldUserName.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordField.setBounds(412, 234, 148, 30);
		frmInLogin.getContentPane().add(passwordField);

		JButton btnLogin = new JButton("Se connecter");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLogin.setBackground(new Color(204, 204, 204));
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// verify user and password
				conn.login(SQLiteCon.myConn, textFieldUserName, passwordField);
				if(SQLiteCon.isConnected == true) {
					frmInLogin.dispose();
				}
			}
		});
		btnLogin.setBounds(412, 289, 148, 30);
		frmInLogin.getContentPane().add(btnLogin);

		// enter key
		frmInLogin.getRootPane().setDefaultButton(btnLogin);
		
		JLabel lblLogicielDePharmacie = new JLabel("Logiciel de pharmacie");
		lblLogicielDePharmacie.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogicielDePharmacie.setForeground(new Color(165, 42, 42));
		lblLogicielDePharmacie.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblLogicielDePharmacie.setBounds(330, 100, 261, 25);
		frmInLogin.getContentPane().add(lblLogicielDePharmacie);
		
	}
}
