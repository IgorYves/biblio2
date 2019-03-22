package biblio.tests;

import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import biblio.business.BiblioException;
import biblio.business.EmpruntEnCours;
import biblio.business.Exemplaire;
import biblio.business.Utilisateur;
import biblio.dao.ExemplaireDaoMoc;
import biblio.dao.UtilisateurDaoMoc;

public class TestAdherentEnRetard {

	public static void main(String[] args) {
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
					autentifOK = true;
					continu = false;
				} else {
					System.out.println("Autentification echouée");
				}
			} else {
				System.out.println("Autentification annulée");
				continu = false;
			}
		}
		System.out.println();
		
		if (!autentifOK) System.exit(0);
		
		ExemplaireDaoMoc exDAO = new ExemplaireDaoMoc();
		System.out.println("--------------- debut des tests");
		
		Exemplaire ex1 = exDAO.findByKey(0);
		System.out.println("ExemplaireDAO find by key (0) -> ex1 >>>> " + ex1);
		
		System.out.println();
		UtilisateurDaoMoc userDAO = new UtilisateurDaoMoc();

		Utilisateur ad1 = userDAO.findByKey(0);
		System.out.println("UtilisateurDAO find by key (0) -> ad1 >>>> " + ad1);
		
		System.out.println();
		
		int daysBefore = 20;
		Date dateEnRetard = new Date((new Date()).getTime() - daysBefore*24*60*60*1000);
		
		try {
			EmpruntEnCours empruntEnCours1 = new EmpruntEnCours(ad1, ex1, dateEnRetard);
		} catch (BiblioException e1) {
			e1.printStackTrace();
		}
		
		System.out.println(ad1.getEmpruntEnCours());
		System.out.println(ad1);
		if (ad1.getNbRetards() > 0) {
			System.out.println("Utilisateur " + ad1.getNom() + ", retard(s) : " + ad1.getNbRetards());
			System.out.println("isConditionsPretAcceptees : " + ad1.isConditionsPretAcceptees());
		}
		System.out.println();
		
		Exemplaire ex2 = exDAO.findByKey(1);
		System.out.println("ExemplaireDAO find by key (1) -> ex2 >>>> " + ex2);
		System.out.println();
		try {
			System.out.println("essai d'ajout de deuxieme pret avec un pret en retard :");
			EmpruntEnCours empruntEnCours2 = new EmpruntEnCours(ad1, ex2, new Date());
		} catch (BiblioException e) {
			e.printStackTrace();
		}
		
	}

}
