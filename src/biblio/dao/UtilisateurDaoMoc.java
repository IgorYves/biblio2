package biblio.dao;

import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;

import biblio.business.Adherent;
import biblio.business.Employe;
import biblio.business.EnumCategorieEmploye;
import biblio.business.Utilisateur;

public class UtilisateurDaoMoc {
	private HashMap<Integer, Utilisateur> utilisateurDB = new HashMap<>();
	
	public UtilisateurDaoMoc() {
		Utilisateur user0 = new Adherent(0, "Ivanoff", "Vasiliy", "Joe", "pass", 
				new GregorianCalendar(1980, 05, 25).getTime(), "m",
				"01.45.56.78.89", "06.10.20.30.40");
		utilisateurDB.put(0, user0);
		
		Utilisateur user1 = new Employe(1, "Tartempion", "Pierre", "Tart", "secret",
				new GregorianCalendar(1990, 06, 15).getTime(), "m",
				"487-578-DD-5", EnumCategorieEmploye.RESPONSABLE);
		utilisateurDB.put(1, user1);
		
		Utilisateur user2 = new Adherent(2, "Sarkozzi", "Cerdric", "Sarko", "123", 
				new GregorianCalendar(1985, 07, 21).getTime(), "m",
				"01.45.66.66.89", "06.15.25.35.45");
		utilisateurDB.put(2, user2);
		
		Utilisateur user3 = new Employe(3, "Tartempion", "Pierre", "Tart", "secret",
				new GregorianCalendar(1999, 02, 11).getTime(), "m",
				"425-514-DD-1", EnumCategorieEmploye.RESPONSABLE);
		utilisateurDB.put(3, user3);
		
	}

	public Utilisateur findByKey(int id) {
		return utilisateurDB.get(id);
	}

	public int insertUtilisateur(Utilisateur user) {
		if (user == null) return -1;
		int max = utilisateurDB.keySet().stream()
				.max(Comparator.comparing(Integer::valueOf)).get();
		user.setIdUtilisateur(++max);
		utilisateurDB.put(max, user);
		return max;
	}
	
	public void updateUtilisateur(Utilisateur user) {
		utilisateurDB.replace(user.getIdUtilisateur(), user);
	}
	
	public void deleteUtilisateur(Utilisateur user) {
		utilisateurDB.remove(user.getIdUtilisateur());
	}
	public void deleteUtilisateur(int id) {
		utilisateurDB.remove(id);
	}


	public static void main(String[] args) {
		UtilisateurDaoMoc userDAO = new UtilisateurDaoMoc();
		System.out.println(userDAO.findByKey(0));
		Utilisateur user4 = new Employe(4, "Vasechkin", "Ivan", "Vasya", "5555",
				new GregorianCalendar(2001, 01, 19).getTime(), "m",
				"722-617-DD-8", EnumCategorieEmploye.GESTIONNAIRE_DE_FONDS);
		int idNewUser = userDAO.insertUtilisateur(user4);
		System.out.println("(id new user : " + idNewUser + ") " + userDAO.findByKey(idNewUser));
		
	}

}
