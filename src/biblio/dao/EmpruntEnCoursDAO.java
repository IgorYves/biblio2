package biblio.dao;

import java.util.Date;

import biblio.business.BiblioException;
import biblio.business.EmpruntEnCours;
import biblio.business.Exemplaire;
import biblio.business.Utilisateur;

public class EmpruntEnCoursDAO extends EmpruntEnCours {
	private int idExemplaire;
	private int idUtilisateur;
	public EmpruntEnCoursDAO(Utilisateur emprunteur, Exemplaire exemplaire,
			Date dateEmprunt, int idExemplaire, int idUtilisateur)
			throws BiblioException {
		super(emprunteur, exemplaire, dateEmprunt);
		this.idExemplaire = idExemplaire;
		this.idUtilisateur = idUtilisateur;
	}



	public EmpruntEnCoursDAO(Utilisateur emprunteur, Exemplaire exemplaire,
			Date dateEmprunt) throws BiblioException {
		super(emprunteur, exemplaire, dateEmprunt);
		// TODO Auto-generated constructor stub
	}

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
