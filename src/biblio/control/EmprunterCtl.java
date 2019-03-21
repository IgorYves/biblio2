package biblio.control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import biblio.business.BiblioException;
import biblio.business.EmpruntEnCours;
import biblio.business.Exemplaire;
import biblio.business.Utilisateur;
import biblio.dao.ConnectionFactory;
import biblio.dao.EmpruntEnCoursDAO;
import biblio.dao.ExemplaireDAO;
import biblio.dao.UtilisateurDAO;

public class EmprunterCtl {
	
	public static void main(String[] args) 
			throws IOException, SQLException, BiblioException {
		Connection connection = ConnectionFactory.getDbConnection();
		connection.setAutoCommit(false);
		
		JOptionPane jop = new JOptionPane();
		int emprunteurId = 0;
		int exemplaireId = 0;
		int userRetour = 0;
		String[] boutons;
		
		UtilisateurDAO userDAO = new UtilisateurDAO(connection);
		List<Utilisateur> users = userDAO.findAll();
		Utilisateur[] usersArr = new Utilisateur[users.size()];
		usersArr = users.toArray(usersArr);
		boutons = new String[users.size()];
		for (int i = 0; i < boutons.length; i++) {
			boutons[i] = usersArr[i].getNom() + " " + usersArr[i].getPrenom() 
					+ " (" + usersArr[i].getIdUtilisateur() + ")";
		}
		userRetour = jop.showOptionDialog(jop, 
				"Chosissez l'utilisateur qui va emprunter une livre", 
				"Choix emprunteur",
				jop.YES_NO_CANCEL_OPTION, //DEFAULT_OPTION, YES_NO_OPTION, YES_NO_CANCEL_OPTION, OK_CANCEL_OPTION
				jop.QUESTION_MESSAGE, //ERROR_MESSAGE, INFORMATION_MESSAGE, WARNING_MESSAGE, QUESTION_MESSAGE, PLAIN_MESSAGE
				null, boutons, boutons[0]);
		//x -> -1; index of boutons Array
		if(userRetour == jop.CLOSED_OPTION)System.exit(1);
		emprunteurId = usersArr[userRetour].getIdUtilisateur();
		System.out.println(emprunteurId);
		
		ExemplaireDAO exDAO = new ExemplaireDAO(connection);
		List<Exemplaire> exemplaires = exDAO.findAllDisponibles();
		Exemplaire[] exemplairesArr = new Exemplaire[exemplaires.size()];
		exemplairesArr = exemplaires.toArray(exemplairesArr);
		boutons = new String[exemplaires.size()];
		for (int i = 0; i < boutons.length; i++) {
			boutons[i] = exemplairesArr[i].getTitle() 
					+ " (" + exemplairesArr[i].getIdExemplaire() + ")";
		}
		userRetour = jop.showOptionDialog(jop, 
				boutons.length>0?"Chosissez l'exemplaire à emprunter":
					"Il n'y a plus des exemplaires à emprunter", 
					"Choix livre",
				jop.YES_NO_CANCEL_OPTION, //DEFAULT_OPTION, YES_NO_OPTION, YES_NO_CANCEL_OPTION, OK_CANCEL_OPTION
				jop.QUESTION_MESSAGE, //ERROR_MESSAGE, INFORMATION_MESSAGE, WARNING_MESSAGE, QUESTION_MESSAGE, PLAIN_MESSAGE
				null, boutons, boutons[0]);
		//x -> -1; index of boutons Array
		if(userRetour == jop.CLOSED_OPTION)System.exit(1);
		exemplaireId = exemplairesArr[userRetour].getIdExemplaire();
		System.out.println(exemplaireId);
		
		EmpruntEnCoursDAO empruntEnCoursDAO = new EmpruntEnCoursDAO(connection);
		System.out.println(userDAO.findByKey(emprunteurId));
		
		EmpruntEnCours empruntEnCours = new EmpruntEnCours(userDAO.findByKey(emprunteurId), 
				exDAO.findByKey(exemplaireId), new Date());
		empruntEnCoursDAO.insertEmpruntEnCours(empruntEnCours);
		
		
		
		connection.commit();
		connection.close();
	}

}
