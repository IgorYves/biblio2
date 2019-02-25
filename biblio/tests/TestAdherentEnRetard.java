package biblio.tests;

import java.util.Date;

import biblio.business.BiblioException;
import biblio.business.EmpruntEnCours;
import biblio.business.Exemplaire;
import biblio.business.Utilisateur;
import biblio.dao.ExemplaireDAO;
import biblio.dao.UtilisateurDAO;

public class TestAdherentEnRetard {

	public static void main(String[] args) {
		ExemplaireDAO exDAO = new ExemplaireDAO();
		System.out.println("--------------- debut des tests");
		
		Exemplaire ex1 = exDAO.findByKey(0);
		System.out.println("ExemplaireDAO find by key (0) -> ex1 >>>> " + ex1);
		
		System.out.println();
		UtilisateurDAO userDAO = new UtilisateurDAO();

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
