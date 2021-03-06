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

public class TestAdherentTroisEmprunts {

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
		Exemplaire ex2 = exDAO.findByKey(1);
		System.out.println("ExemplaireDAO find by key (1) -> ex2 >>>> " + ex2);
		Exemplaire ex3 = exDAO.findByKey(2);
		System.out.println("ExemplaireDAO find by key (2) -> ex3 >>>> " + ex3);
		Exemplaire ex4 = exDAO.findByKey(3);
		System.out.println("ExemplaireDAO find by key (3) -> ex4 >>>> " + ex4);
		
		System.out.println();
		UtilisateurDaoMoc userDAO = new UtilisateurDaoMoc();

		Utilisateur ad1 = userDAO.findByKey(0);
		System.out.println("UtilisateurDAO find by key (0) -> ad1 >>>> " + ad1);
		
		System.out.println();
		
		try {
			System.out.println("ajout de 3 prets :");
			EmpruntEnCours empruntEnCours1 = new EmpruntEnCours(ad1, ex1, new Date());
			EmpruntEnCours empruntEnCours2 = new EmpruntEnCours(ad1, ex2, new Date());
			EmpruntEnCours empruntEnCours3 = new EmpruntEnCours(ad1, ex3, new Date());
		} catch (BiblioException e1) {
			e1.printStackTrace();
		}
		
		System.out.println(ad1.getEmpruntEnCours());
		System.out.println(ad1);
		System.out.println("isConditionsPretAcceptees : " + ad1.isConditionsPretAcceptees());
		System.out.println();
		try {
			System.out.println("essai d'ajout de 4-eme pret avec 3 prets en cours :");
			EmpruntEnCours empruntEnCours4 = new EmpruntEnCours(ad1, ex4, new Date());
		} catch (BiblioException e1) {
			e1.printStackTrace();
		}
		
		
		
	}

}
