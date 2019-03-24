package biblio.control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
		
		UtilisateurI userDAO = new UtilisateurDAO(connection);
		HashMap<Integer, String> users = userDAO.ListAllUsers();
		Integer[] buttons2Users = new Integer[users.size()];
		Set<Integer> usersKeys = users.keySet();
		buttons2Users = usersKeys.toArray(buttons2Users);
		boutons = new String[users.size()];
		for (int i = 0; i < boutons.length; i++) {
			boutons[i] = buttons2Users[i] + users.get(buttons2Users[i]);
		}
		userRetour = jop.showOptionDialog(jop, 
				"Chosissez l'utilisateur qui va emprunter une livre", 
				"Choix emprunteur",
				jop.YES_NO_CANCEL_OPTION, //DEFAULT_OPTION, YES_NO_OPTION, YES_NO_CANCEL_OPTION, OK_CANCEL_OPTION
				jop.QUESTION_MESSAGE, //ERROR_MESSAGE, INFORMATION_MESSAGE, WARNING_MESSAGE, QUESTION_MESSAGE, PLAIN_MESSAGE
				null, boutons, boutons[0]);
		//x -> -1; index of boutons Array
		if(userRetour != jop.CLOSED_OPTION) {
			emprunteurId = buttons2Users[userRetour];
			System.out.println(emprunteurId);
			
			ExemplaireI exDAO = new ExemplaireDAO(connection);
			List<Exemplaire> exemplaires = exDAO.findAllDisponibles();
			Exemplaire[] exemplairesArr = new Exemplaire[exemplaires.size()];
			exemplairesArr = exemplaires.toArray(exemplairesArr);
			boutons = new String[exemplaires.size()];
			for (int i = 0; i < boutons.length; i++) {
				boutons[i] = exemplairesArr[i].getTitle() 
						+ " (" + exemplairesArr[i].getIdExemplaire() + ")";
			}
			userRetour = -1;
			if (boutons.length>0) {
				userRetour = jop.showOptionDialog(jop, 
					"Chosissez l'exemplaire à emprunter", "Choix livre",
					jop.YES_NO_CANCEL_OPTION, //DEFAULT_OPTION, YES_NO_OPTION, YES_NO_CANCEL_OPTION, OK_CANCEL_OPTION
					jop.QUESTION_MESSAGE, //ERROR_MESSAGE, INFORMATION_MESSAGE, WARNING_MESSAGE, QUESTION_MESSAGE, PLAIN_MESSAGE
					null, boutons, boutons[0]);
				//x -> -1; index of boutons Array
				if (userRetour != jop.CLOSED_OPTION) {
					exemplaireId = exemplairesArr[userRetour].getIdExemplaire();
					System.out.println(exemplaireId);
					
					EmpruntEnCoursI empruntEnCoursDAO = new EmpruntEnCoursDAO(connection);
					
					Utilisateur user1 = userDAO.findByKey(emprunteurId);
					System.out.println(user1);
					System.out.println(user1.isConditionsPretAcceptees());
					//System.exit(1);
					if (user1.isConditionsPretAcceptees()) {
						//System.exit(1);
						Exemplaire exemplaire1 = exDAO.findByKey(exemplaireId);
						EmpruntEnCours empruntEnCours = new EmpruntEnCours(user1, exemplaire1, new Date());
						empruntEnCoursDAO.insertEmpruntEnCours(empruntEnCours);
						user1.addEmpruntEnCours(empruntEnCours);
						exDAO.updateExemplaireStatus(exemplaire1);
						jop.showMessageDialog(jop, "Enregistrement est OK", "Utilisateur", jop.INFORMATION_MESSAGE);
					}
					else {
						jop.showMessageDialog(jop, "ERROR_MESSAGE : conditions de pret ne sont pas acceptées", 
								"Utilisateur", jop.ERROR_MESSAGE);
					}
				}
			}
			else {
				jop.showMessageDialog(jop, "Il n'y a plus des exemplaires à emprunter", 
						"Pas de livres", jop.ERROR_MESSAGE);
			}
		}
		
		EmpruntEnCoursI empruntEnCoursDAO = new EmpruntEnCoursDAO(connection);
		HashMap<Integer, String> empruntEnCours = empruntEnCoursDAO.ListAllEmpruntEnCours();
		Integer[] buttons2emprunts = new Integer[empruntEnCours.size()];
		Set<Integer> empruntEnCoursKeys = empruntEnCours.keySet();
		buttons2emprunts = empruntEnCoursKeys.toArray(buttons2emprunts);
		boutons = new String[empruntEnCours.size()];
		for (int i = 0; i < boutons.length; i++) {
			boutons[i] = "ex-" + buttons2emprunts[i] + " ; " + empruntEnCours.get(buttons2emprunts[i]);
		}
		
		if (boutons.length>0) {
			userRetour = jop.showOptionDialog(jop, 
					"Chosissez l'exemplaire de livre à retourner", 
					"Retour livre",
					jop.YES_NO_CANCEL_OPTION, //DEFAULT_OPTION, YES_NO_OPTION, YES_NO_CANCEL_OPTION, OK_CANCEL_OPTION
					jop.QUESTION_MESSAGE, //ERROR_MESSAGE, INFORMATION_MESSAGE, WARNING_MESSAGE, QUESTION_MESSAGE, PLAIN_MESSAGE
					null, boutons, boutons[0]);
			//x -> -1; index of boutons Array
			
			if(userRetour != jop.CLOSED_OPTION) {
				exemplaireId = buttons2emprunts[userRetour];
				System.out.println(exemplaireId);
				if (empruntEnCoursDAO.madeReturn(exemplaireId)) {
					jop.showMessageDialog(jop, "Enregistrement de retour est OK", "Retour livre", jop.INFORMATION_MESSAGE);
				}
				else {
					jop.showMessageDialog(jop, "Erreur de retour, rollback effectué", 
							"Erreur", jop.ERROR_MESSAGE);
				}
			}
		}
		else {
			jop.showMessageDialog(jop, "Il n'y a plus des exemplaires à retourner", 
					"Pas de livres", jop.ERROR_MESSAGE);
		}
		
		
		connection.commit();
		connection.close();
	}

}
