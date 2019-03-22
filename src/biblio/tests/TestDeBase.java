package biblio.tests;

import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import biblio.business.BiblioException;
import biblio.business.EmpruntEnCours;
import biblio.business.Exemplaire;
import biblio.business.Utilisateur;
import biblio.dao.ExemplaireDaoMoc;
import biblio.dao.UtilisateurDaoMoc;
import biblio.gui.BiblioFrame;

public class TestDeBase {
	static BiblioFrame biblioFrame;
	static String msg = "";
	public static void main(String[] args) {
		//SwingUtilities.invokeLater(BiblioFrame::new);
		SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					biblioFrame = new BiblioFrame();
					}
			}
		);
		
		
		JTextField username = new JTextField();
		JTextField password = new JPasswordField();
		Object[] message = {"Login (\"admin\") : ", username, "Mot de passe (\"secret\") : ", password};
		boolean autentifOK = false;
		boolean continu = true;
		int option;
		while (continu) {
			option = JOptionPane.showConfirmDialog(null, message,
					"Autentifiez-vous SVP", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				if (username.getText().equals("admin") && password.getText().equals("secret")) {
					System.out.println("Autentification est OK");
					biblioFrame.jLabel.setText("Autentification est OK");
					autentifOK = true;
					continu = false;
				} else {
					System.out.println("Autentification echouée");
					biblioFrame.jLabel.setText("Autentification echouée");
				}
			} else {
				System.out.println("Autentification annulée");
				biblioFrame.jLabel.setText("Autentification annulée");
				continu = false;
			}
		}
		System.out.println();
		
		if (autentifOK) {
			ExemplaireDaoMoc exDaoMoc = new ExemplaireDaoMoc();
			System.out.println("--------------- debut des tests");
	
			Exemplaire ex1 = exDaoMoc.findByKey(0);
			msg = "ExemplaireDaoMoc find by key (0) -> ex1 >>>> " + ex1;
			System.out.println(msg);
			biblioFrame.jLabel.setText(msg);
			
			Exemplaire ex2 = exDaoMoc.findByKey(1);
			msg = "ExemplaireDaoMoc find by key (1) -> ex2 >>>> " + ex2;
			System.out.println(msg);
			biblioFrame.jLabel.setText(msg);
			
			System.out.println();
			UtilisateurDaoMoc userDaoMoc = new UtilisateurDaoMoc();
	
			Utilisateur ad1 = userDaoMoc.findByKey(0);
			System.out.println("UtilisateurDaoMoc find by key (0) -> ad1 >>>> " + ad1);
	
			Utilisateur em2 = userDaoMoc.findByKey(1);
			System.out.println("UtilisateurDaoMoc find by key (1) -> em2 >>>> " + em2);
	
			try {
				EmpruntEnCours empruntEnCours1 = new EmpruntEnCours(ad1, ex1,
						new Date());
				EmpruntEnCours empruntEnCours2 = new EmpruntEnCours(em2, ex2,
						new Date());
			} catch (BiblioException e) {
				e.printStackTrace();
			}
	
			System.out.println();
	
			System.out.println(ad1);
			System.out.println(ad1.getEmpruntEnCours());
			System.out.println();
			System.out.println(em2);
			System.out.println(em2.getEmpruntEnCours());
		}
		
	}

}
