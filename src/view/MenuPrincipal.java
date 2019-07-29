package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;

import dao.SQLiteCon;

import javax.swing.JLabel;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuPrincipal extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frmMenuPrincipal;
	// database class declaration
	SQLiteCon conn;

	/**
	 * Lancer l'application.
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
//		conn = new SQLiteCon();
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
	@SuppressWarnings({ "static-access" })
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
		lblMenuPrincipal.setBounds(233, 89, 498, 25);
		getFrmMenuPrincipal().getContentPane().add(lblMenuPrincipal);
		
		JButton button_1 = new JButton("Saisir un ticket");
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button_1.setPreferredSize(new Dimension(260, 60));
		button_1.setForeground(Color.BLACK);
		button_1.setBackground(Color.WHITE);
		button_1.setBounds(147, 192, 260, 40);
		getFrmMenuPrincipal().getContentPane().add(button_1);
		
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmMenuPrincipal.dispose();
				ouvrirSaisirUnTicketFenetre();
			}

			// opens listePhar window
			private void ouvrirSaisirUnTicketFenetre() {
				SaisirUnTicketFenetre saisirUnTicketFenetre = new SaisirUnTicketFenetre();
				saisirUnTicketFenetre.setVisible(true);
			}
		});
		
		JButton btnInfoProduit = new JButton("Gestion des produits");
		btnInfoProduit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnInfoProduit.setPreferredSize(new Dimension(260, 60));
		btnInfoProduit.setForeground(Color.BLACK);
		btnInfoProduit.setBackground(Color.WHITE);
		btnInfoProduit.setBounds(350, 281, 260, 40);
		getFrmMenuPrincipal().getContentPane().add(btnInfoProduit);
		if (conn.currentUser.equalsIgnoreCase("admin")) {
			btnInfoProduit.setEnabled(true);
		} else {
			btnInfoProduit.setEnabled(false);
		}
		btnInfoProduit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmMenuPrincipal.dispose();
				openMainWindow();
			}

			// opens liste Produits window
			private void openMainWindow() {
				ProduitsFenetre produitsFenetre = new ProduitsFenetre();
				produitsFenetre.setVisible(true);
			}
		});
		
		JButton buttonStockDispo = new JButton("Stock disponible");
		buttonStockDispo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonStockDispo.setPreferredSize(new Dimension(260, 60));
		buttonStockDispo.setForeground(Color.BLACK);
		buttonStockDispo.setBackground(Color.WHITE);
		buttonStockDispo.setBounds(554, 192, 260, 40);
		getFrmMenuPrincipal().getContentPane().add(buttonStockDispo);
		buttonStockDispo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmMenuPrincipal.dispose();
				openStockDispo();
			}
			
			private void openStockDispo() {
				StockDispoFenetre stockWindow = new StockDispoFenetre();
				stockWindow.setVisible(true);
				}
			});
		
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
				FournisseursFenetre fournisseursFenetre = new FournisseursFenetre();
				fournisseursFenetre.setVisible(true);
			}
		});
		
		btnFournisseur.setPreferredSize(new Dimension(260, 60));
		btnFournisseur.setForeground(Color.BLACK);
		btnFournisseur.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnFournisseur.setBackground(Color.WHITE);
		btnFournisseur.setBounds(45, 281, 260, 40);
		getFrmMenuPrincipal().getContentPane().add(btnFournisseur);
		
		JButton btnPharmaciens = new JButton("Pharmaciens");
		
		// Si c'est l'admin, activer le bouton Pharmaciens
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
				PharmaciensFenetre pharmaciensFenetre = new PharmaciensFenetre();
				pharmaciensFenetre.setVisible(true);
			}
		});
		btnPharmaciens.setPreferredSize(new Dimension(260, 60));
		btnPharmaciens.setForeground(Color.BLACK);
		btnPharmaciens.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPharmaciens.setBackground(Color.WHITE);
		btnPharmaciens.setBounds(655, 281, 260, 40);
		getFrmMenuPrincipal().getContentPane().add(btnPharmaciens);
		
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
