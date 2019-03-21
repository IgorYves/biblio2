package biblio.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import biblio.business.BiblioException;
import biblio.business.EmpruntEnCours;
import biblio.business.Exemplaire;
import biblio.business.Utilisateur;

public class EmpruntEnCoursDB extends EmpruntEnCours {
	private int idExemplaire;
	private int idUtilisateur;
	public EmpruntEnCoursDB(Utilisateur emprunteur, Exemplaire exemplaire,
			Date dateEmprunt, int idExemplaire, int idUtilisateur)
			throws BiblioException {
		super(emprunteur, exemplaire, dateEmprunt);
		this.idExemplaire = idExemplaire;
		this.idUtilisateur = idUtilisateur;
	}
	
	public EmpruntEnCoursDB(int idUtilisateur, int idExemplaire, 
							Date dateEmprunt, Connection connection)
			throws BiblioException, SQLException, IOException {
		this((new UtilisateurDAO(connection)).findByKey(idUtilisateur), 
				(new ExemplaireDAO(connection)).findByKey(idExemplaire), 
				dateEmprunt, idExemplaire, idUtilisateur);
	}

	public int getIdExemplaire() {
		return idExemplaire;
	}

	public int getIdUtilisateur() {
		return idUtilisateur;
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}




	

}
