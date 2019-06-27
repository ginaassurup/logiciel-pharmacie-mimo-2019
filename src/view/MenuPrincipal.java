package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.border.EmptyBorder;

import dao.SQLiteCon;

import javax.swing.JLabel;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.event.KeyEvent;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuPrincipal extends JFrame{

	private JFrame frmMenuPrincipal;
	// database class declaration
	SQLiteCon conn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal window = new MenuPrincipal();
					window.getFrmMenuPrincipal().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MenuPrincipal() {
		conn = new SQLiteCon();
		initialize();
	}

	/**
	 * @return the frmMenuPrincipal
	 */
	public JFrame getFrmMenuPrincipal() {
		return frmMenuPrincipal;
	}

	/**
	 * @param frmMenuPrincipal the frmMenuPrincipal to set
	 */
	public void setFrmMenuPrincipal(JFrame frmMenuPrincipal) {
		this.frmMenuPrincipal = frmMenuPrincipal;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrmMenuPrincipal(new JFrame());
		getFrmMenuPrincipal().setResizable(false);
		getFrmMenuPrincipal().setTitle("Menu principal | Utilisateur : " + conn.currentUser);
		getFrmMenuPrincipal().getContentPane().setBackground(SystemColor.text);
		getFrmMenuPrincipal().getContentPane().setLayout(null);
		
		JLabel lblMenuPrincipal = new JLabel("Menu principal");
		lblMenuPrincipal.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenuPrincipal.setForeground(new Color(165, 42, 42));
		lblMenuPrincipal.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMenuPrincipal.setBounds(231, 43, 498, 25);
		getFrmMenuPrincipal().getContentPane().add(lblMenuPrincipal);
		
		JButton button_1 = new JButton("Saisir un ticket");
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button_1.setPreferredSize(new Dimension(260, 60));
		button_1.setForeground(Color.BLACK);
		button_1.setBackground(Color.WHITE);
		button_1.setBounds(345, 148, 260, 40);
		getFrmMenuPrincipal().getContentPane().add(button_1);
		
		JButton btnInfoProduit = new JButton("Information sur un produit");
		btnInfoProduit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmMenuPrincipal.dispose();
				openMainWindow();
			}

			// opens listePhar window
			private void openMainWindow() {
				MainWindow mainWindow = new MainWindow();
				mainWindow.setVisible(true);
			}
		});
		btnInfoProduit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnInfoProduit.setPreferredSize(new Dimension(260, 60));
		btnInfoProduit.setForeground(Color.BLACK);
		btnInfoProduit.setBackground(Color.WHITE);
		btnInfoProduit.setBounds(345, 237, 260, 40);
		getFrmMenuPrincipal().getContentPane().add(btnInfoProduit);
		
		JButton button_3 = new JButton("Stock disponible");
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		button_3.setPreferredSize(new Dimension(260, 60));
		button_3.setForeground(Color.BLACK);
		button_3.setBackground(Color.WHITE);
		button_3.setBounds(345, 327, 260, 40);
		getFrmMenuPrincipal().getContentPane().add(button_3);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 962, 30);
		getFrmMenuPrincipal().getContentPane().add(menuBar);
		
		JMenu mnParamtres = new JMenu("Param\u00E8tres");
		mnParamtres.setFont(new Font("Tahoma", Font.PLAIN, 15));
		mnParamtres.setMnemonic(KeyEvent.VK_F);
		menuBar.add(mnParamtres);
		
		JMenuItem mntmImprimer = new JMenuItem("Imprimer");
		mnParamtres.add(mntmImprimer);
		
		JSeparator separator = new JSeparator();
		mnParamtres.add(separator);
		
		JMenuItem mntmSeDconnecter = new JMenuItem("Fermer");
		mntmSeDconnecter.setToolTipText("Exit application");
		mntmSeDconnecter.setMnemonic(KeyEvent.VK_E);
		mnParamtres.add(mntmSeDconnecter);
		
		JMenu mnSeDconnecter = new JMenu("");
		mnSeDconnecter.setMnemonic(KeyEvent.VK_F);
		mnSeDconnecter.setFont(new Font("Tahoma", Font.PLAIN, 15));
		menuBar.add(mnSeDconnecter);
		
		JSeparator separator_1 = new JSeparator();
		mnSeDconnecter.add(separator_1);
		
		JButton btnCommande = new JButton("Commandes");
		btnCommande.setPreferredSize(new Dimension(260, 60));
		btnCommande.setForeground(Color.BLACK);
		btnCommande.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCommande.setBackground(Color.WHITE);
		btnCommande.setBounds(33, 327, 260, 40);
		getFrmMenuPrincipal().getContentPane().add(btnCommande);
		
		JButton btnFournisseur = new JButton("Fournisseurs");
		// Si c'est l'admin, activer le boutton settings
		if (conn.currentUser.equalsIgnoreCase("admin")) {
			btnFournisseur.setEnabled(true);
		} else {
			btnFournisseur.setEnabled(false);
		}
		btnFournisseur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmMenuPrincipal.dispose();
				ouvrirUnitsWindow();
			}

			// opens listePhar window
			private void ouvrirUnitsWindow() {
				UnitsWindow unitsWindow = new UnitsWindow();
				unitsWindow.setVisible(true);
			}
		});
		
		btnFournisseur.setPreferredSize(new Dimension(260, 60));
		btnFournisseur.setForeground(Color.BLACK);
		btnFournisseur.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnFournisseur.setBackground(Color.WHITE);
		btnFournisseur.setBounds(33, 237, 260, 40);
		getFrmMenuPrincipal().getContentPane().add(btnFournisseur);
		
		JButton btnPharmaciens = new JButton("Pharmaciens");
		
		// Si c'est l'admin, activer le boutton settings
		if (conn.currentUser.equalsIgnoreCase("admin")) {
			btnPharmaciens.setEnabled(true);
		} else {
			btnPharmaciens.setEnabled(false);
		}
		btnPharmaciens.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmMenuPrincipal.dispose();
				openUsers();
			}

			// opens listePhar window
			private void openUsers() {
				FenetrePharmaciens fenetrePharmaciens = new FenetrePharmaciens();
				fenetrePharmaciens.setVisible(true);
			}
		});
		btnPharmaciens.setPreferredSize(new Dimension(260, 60));
		btnPharmaciens.setForeground(Color.BLACK);
		btnPharmaciens.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPharmaciens.setBackground(Color.WHITE);
		btnPharmaciens.setBounds(667, 237, 260, 40);
		getFrmMenuPrincipal().getContentPane().add(btnPharmaciens);
		
		JButton btnStatistiques = new JButton("Statistiques");
		btnStatistiques.setPreferredSize(new Dimension(260, 60));
		btnStatistiques.setForeground(Color.BLACK);
		btnStatistiques.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnStatistiques.setBackground(Color.WHITE);
		btnStatistiques.setBounds(667, 327, 260, 40);
		getFrmMenuPrincipal().getContentPane().add(btnStatistiques);
		
		JButton btnSeDconnecter = new JButton("Se d\u00E9connecter");
		btnSeDconnecter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmMenuPrincipal.dispose();
				OuvrirConnexionFenetre();
			}
		});
		btnSeDconnecter.setBounds(776, 45, 162, 25);
		getFrmMenuPrincipal().getContentPane().add(btnSeDconnecter);
		btnSeDconnecter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSeDconnecter.setBackground(Color.WHITE);
		getFrmMenuPrincipal().setBounds(100, 100, 968, 500);
		getFrmMenuPrincipal().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// Ouvrir le menu principal
	private void OuvrirConnexionFenetre() {
		
		ConnexionFenetre fenetre = new ConnexionFenetre();
		fenetre.frmConnexion.setVisible(true);
	}
}
