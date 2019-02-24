package biblio.tests;

import java.util.Date;

import biblio.business.EmpruntEnCours;
import biblio.business.Exemplaire;
import biblio.business.Utilisateur;
import biblio.dao.ExemplaireDAO;
import biblio.dao.UtilisateurDAO;

public class TestDeBase {

	public static void main(String[] args) {
		ExemplaireDAO exDAO = new ExemplaireDAO();
		System.out.println("--------------- debut des tests");
		
		Exemplaire ex1 = exDAO.findByKey(0);
		System.out.println("ExemplaireDAO find by key (0) -> ex1 >>>> " + ex1);
		
		Exemplaire ex2 = exDAO.findByKey(1);
		System.out.println("ExemplaireDAO find by key (1) -> ex2 >>>> " + ex2);
		
		System.out.println();
		UtilisateurDAO userDAO = new UtilisateurDAO();

		Utilisateur ad1 = userDAO.findByKey(0);
		System.out.println("UtilisateurDAO find by key (0) -> ad1 >>>> " + ad1);

		Utilisateur em2 = userDAO.findByKey(1);
		System.out.println("UtilisateurDAO find by key (1) -> em2 >>>> " + em2);
		
		EmpruntEnCours empruntEnCours1 = new EmpruntEnCours(ad1, ex1, new Date());
		EmpruntEnCours empruntEnCours2 = new EmpruntEnCours(em2, ex2, new Date());
		
		System.out.println();
		
		System.out.println(ad1);
		System.out.println(ad1.getEmpruntEnCours());
		System.out.println();
		System.out.println(em2);
		System.out.println(em2.getEmpruntEnCours());
		
	}

}
