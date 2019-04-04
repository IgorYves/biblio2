package biblio.control;

import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import biblio.business.BiblioException;
import biblio.business.EmpruntEnCours;
import biblio.business.Exemplaire;
import biblio.business.Utilisateur;
import biblio.dao.BiblioDaoException;
import biblio.dao.ConnectionFactory;
import biblio.dao.EmpruntEnCoursDAO;
import biblio.dao.ExemplaireDAO;
import biblio.dao.UtilisateurDAO;
import biblio.gui.BiblioMainFrame;
import biblio.gui.BiblioMainFrame2;

public class EmprunterCtl {
	
	static JOptionPane jop = new JOptionPane();
	int emprunteurId = 0;
	int exemplaireId = 0;
	int userRetour = 0;
	String userRetourString = null;
	String[] boutons;
	String[] options;
	
	public void enregistrerEmprunt() throws IOException, SQLException, BiblioException {
		Connection connection = ConnectionFactory.getDbConnection();
		connection.setAutoCommit(false);
		
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
						user1.addNewEmpruntEnCours(empruntEnCours);
						exDAO.updateExemplaireStatus(exemplaire1);
						jop.showMessageDialog(jop, "Enregistrement est OK", 
								"Utilisateur", jop.INFORMATION_MESSAGE);
					}
					else {
						jop.showMessageDialog(jop, "ERROR_MESSAGE : conditions de pret ne sont pas acceptées", 
								"Utilisateur", jop.ERROR_MESSAGE);
					}
				}
				else {
					jop.showMessageDialog(jop, "Action annulée", 
							"Info", jop.INFORMATION_MESSAGE);
				}
			}
			else {
				jop.showMessageDialog(jop, "Il n'y a plus des exemplaires à emprunter", 
						"Pas de livres", jop.ERROR_MESSAGE);
			}
		}
		else {
			jop.showMessageDialog(jop, "Action annulée", 
					"Info", jop.INFORMATION_MESSAGE);
		}
		
		connection.commit();
		connection.close();
	}
	public void enregistrerEmprunt(int emprunteurId, int exemplaireId) 
			throws SQLException, BiblioException, IOException, BiblioDaoException {
		Connection connection = ConnectionFactory.getDbConnection();
		connection.setAutoCommit(false);
		UtilisateurI userDAO = new UtilisateurDAO(connection);
		Utilisateur user1 = userDAO.findByKey(emprunteurId);
		if (user1 == null) {
			jop.showMessageDialog(jop, "ERROR_MESSAGE : user not found", 
					"Utilisateur", jop.ERROR_MESSAGE);
			throw new BiblioDaoException("user not found");
		}
		System.out.println(user1);
		System.out.println(user1.isConditionsPretAcceptees());
		if (user1.isConditionsPretAcceptees()) {
			ExemplaireI exDAO = new ExemplaireDAO(connection);
			Exemplaire exemplaire1 = exDAO.findByKey(exemplaireId);
			if (exemplaire1 == null) {
				jop.showMessageDialog(jop, "ERROR_MESSAGE : exemplaire not found", 
						"Exemplaire", jop.ERROR_MESSAGE);
				throw new BiblioDaoException("exemplaire not found");
			}
			EmpruntEnCours empruntEnCours = new EmpruntEnCours(user1, exemplaire1, new Date());
			EmpruntEnCoursI empruntEnCoursDAO = new EmpruntEnCoursDAO(connection);
			empruntEnCoursDAO.insertEmpruntEnCours(empruntEnCours);
			user1.addNewEmpruntEnCours(empruntEnCours);
			exDAO.updateExemplaireStatus(exemplaire1);
			jop.showMessageDialog(jop, "Enregistrement est OK", 
					"Utilisateur", jop.INFORMATION_MESSAGE);
		}
		else {
			jop.showMessageDialog(jop, "ERROR_MESSAGE : conditions de pret ne sont pas acceptées !", 
					"Utilisateur", jop.ERROR_MESSAGE);
		}
		
		connection.commit();
		connection.close();
	}
	
	
	public void enregistrerRetour() throws IOException, SQLException, BiblioException, HeadlessException, BiblioDaoException {
		Connection connection = ConnectionFactory.getDbConnection();
		connection.setAutoCommit(false);
		
		EmpruntEnCoursI empruntEnCoursDAO = new EmpruntEnCoursDAO(connection);
		HashMap<Integer, String> empruntEnCours = empruntEnCoursDAO.ListAllEmpruntEnCours();
		HashMap<String, Integer> empruntString2idExemplaire = new HashMap<>();
		Set<Integer> empruntEnCoursKeys = empruntEnCours.keySet();
		options = new String[empruntEnCoursKeys.size()];
		Integer current = null;
		Iterator iterator = empruntEnCoursKeys.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			current = (Integer) iterator.next();
			options[i] = "ex-" + current + " ; " + empruntEnCours.get(current);
			empruntString2idExemplaire.put(options[i], current);
			i++;
		}
		
		if (options.length>0) {
			userRetourString = (String)jop.showInputDialog(jop, 
					"Chosissez l'exemplaire de livre à retourner", "Retour livre", 
					jop.QUESTION_MESSAGE,
					null,//icone
					options, options[0]);
			
			if(userRetourString != null) {
				exemplaireId = empruntString2idExemplaire.get(userRetourString);
				System.out.println(exemplaireId);
				if (empruntEnCoursDAO.madeReturn(exemplaireId)) {
					jop.showMessageDialog(jop, "Enregistrement de retour est OK", 
							"Retour livre", jop.INFORMATION_MESSAGE);
				}
				else {
					jop.showMessageDialog(jop, "Erreur de retour, rollback effectué", 
							"Erreur", jop.ERROR_MESSAGE);
				}
			}
			else {
				jop.showMessageDialog(jop, "Action annulée", 
						"Info", jop.INFORMATION_MESSAGE);
			}
		}
		else {
			jop.showMessageDialog(jop, "Il n'y a plus des exemplaires à retourner", 
					"Pas de livres", jop.ERROR_MESSAGE);
		}
		
		connection.commit();
		connection.close();
	}
	public void enregistrerRetour(int exemplaireId) 
			throws IOException, SQLException, HeadlessException, BiblioDaoException {
		Connection connection = ConnectionFactory.getDbConnection();
		connection.setAutoCommit(false);
		EmpruntEnCoursI empruntEnCoursDAO = new EmpruntEnCoursDAO(connection);
		if (empruntEnCoursDAO.madeReturn(exemplaireId)) {
			jop.showMessageDialog(jop, "Enregistrement de retour est OK", 
					"Retour livre", jop.INFORMATION_MESSAGE);
		}
		else {
			jop.showMessageDialog(jop, "Erreur de retour, rollback effectué", 
					"Erreur", jop.ERROR_MESSAGE);
		}
		
		connection.commit();
		connection.close();
	}
	
	public static List<Utilisateur> getAllUtilisateurs() 
			throws IOException, SQLException, BiblioException {
		Connection connection = ConnectionFactory.getDbConnection();
		return getAllUtilisateurs(connection);
	}
	public static List<Utilisateur> getAllUtilisateurs(Connection connection) 
			throws IOException, SQLException, BiblioException {
		connection.setAutoCommit(false);
		UtilisateurI userDAO = new UtilisateurDAO(connection);
		List<Utilisateur> users = userDAO.findAll();
		connection.commit();
		connection.close();
		return users;
	}
	public static HashMap<Integer, String> listAllUtilisateurs() 
			throws IOException, SQLException, BiblioException {
		Connection connection = ConnectionFactory.getDbConnection();
		connection.setAutoCommit(false);
		UtilisateurI userDAO = new UtilisateurDAO(connection);
		HashMap<Integer, String> users = userDAO.ListAllUsers();
		return users;
	}
	
	public static List<Exemplaire> getAllExemplaires() throws IOException, SQLException {
		Connection connection = ConnectionFactory.getDbConnection();
		return getAllExemplaires(connection);
	}
	public static List<Exemplaire> getAllExemplaires(Connection connection) 
			throws IOException, SQLException {
		connection.setAutoCommit(false);
		ExemplaireI exDAO = new ExemplaireDAO(connection);
		List<Exemplaire> exemplaires = exDAO.findAll();
		connection.commit();
		connection.close();
		return exemplaires;
	}
	
	public static HashMap<Integer, String> listAllEmpruntEnCoursDB() 
			throws IOException, SQLException, BiblioException {
		Connection connection = ConnectionFactory.getDbConnection();
		return listAllEmpruntEnCoursDB(connection);
	}
	public static HashMap<Integer, String> listAllEmpruntEnCoursDB(Connection connection) 
			throws SQLException, BiblioException, IOException {
		connection.setAutoCommit(false);
		EmpruntEnCoursI empruntEnCoursDAO = new EmpruntEnCoursDAO(connection);
		HashMap<Integer, String> empruntsEnCoursDB = empruntEnCoursDAO.ListAllEmpruntEnCours();
		connection.commit();
		connection.close();
		return empruntsEnCoursDB;
	}
	
	public static boolean getAutorisation() {
		JTextField username = new JTextField();
		JTextField password = new JPasswordField();
		Object[] message = {"Login (\"biblio\") : ", username, 
							"Mot de passe (\"secret\") : ", password};
		boolean autentifOK = false;
		boolean continu = true;
		int option;
		while (continu) {
			option = JOptionPane.showConfirmDialog(null, message,
					"Autentifiez-vous SVP", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				if (username.getText().equals("biblio") && password.getText().equals("secret")) {
					System.out.println("Autentification est OK");
					continu = false;
					return true;
				} else {
					jop.showMessageDialog(jop, "Autentification echouée", 
										"Info", jop.INFORMATION_MESSAGE);
					System.out.println("Autentification echouée");
				}
			} else {
				jop.showMessageDialog(jop, "Action annulée", "Info", jop.INFORMATION_MESSAGE);
				System.out.println("Autentification annulée");
				continu = false;
				return false;
			}
		}
		
		return false;
	}

	public static String getUserString(int id) throws SQLException, BiblioException, IOException {
		Connection connection = ConnectionFactory.getDbConnection();
		connection.setAutoCommit(false);
		UtilisateurI userDAO = new UtilisateurDAO(connection);
		connection.commit();
		connection.close();
		//return userDAO.findByKey(id).toString();
		Utilisateur user = userDAO.findByKey(id);
		return null;
	}
	
	public static void main(String[] args) 
			throws IOException, SQLException, BiblioException, HeadlessException, BiblioDaoException {
		EmprunterCtl emprunterCtl = new EmprunterCtl();
		
		System.out.println(listAllEmpruntEnCoursDB());
		
		String[] boutons = {"Swing v2.1 beta", "Swing v2.0 stable avec Jop", "Console v1.0"};
		emprunterCtl.userRetour = emprunterCtl.jop.showOptionDialog(emprunterCtl.jop, 
				"Quelle interface voulez vous utiliser ?", "Choix d'interface",
				emprunterCtl.jop.YES_NO_CANCEL_OPTION, //DEFAULT_OPTION, YES_NO_OPTION, YES_NO_CANCEL_OPTION, OK_CANCEL_OPTION
				emprunterCtl.jop.INFORMATION_MESSAGE, //ERROR_MESSAGE, INFORMATION_MESSAGE, WARNING_MESSAGE, QUESTION_MESSAGE, PLAIN_MESSAGE
						null, boutons, boutons[0]);
		//x -> -1; index of boutons Array
		System.out.println(emprunterCtl.userRetour);
		if (emprunterCtl.userRetour == 0) {//Swing (V2.0)
			SwingUtilities.invokeLater(()->{
				if (getAutorisation()) {
					try {
						new BiblioMainFrame2(getAllUtilisateurs(), 
											getAllExemplaires(), 
											null);
					} catch (IOException | SQLException | BiblioException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					jop.showMessageDialog(jop, "Autentification requise", 
							"Info", jop.INFORMATION_MESSAGE);
				}
			});
		}
		else if (emprunterCtl.userRetour == 1) {//Swing avec Jop
			SwingUtilities.invokeLater(()->{
				if (getAutorisation()) {
					try {
						new BiblioMainFrame(getAllUtilisateurs(), 
											getAllExemplaires(), 
											listAllEmpruntEnCoursDB());
					} catch (IOException | SQLException | BiblioException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					jop.showMessageDialog(jop, "Autentification requise", 
							"Info", jop.INFORMATION_MESSAGE);
				}
			});
		}
		else if (emprunterCtl.userRetour == 2) {//concole
			if (getAutorisation()) {
				emprunterCtl.enregistrerEmprunt();
				emprunterCtl.enregistrerRetour();
			}else {
				jop.showMessageDialog(jop, "Autentification requise", 
						"Info", jop.INFORMATION_MESSAGE);
			}
		}
		else {
			emprunterCtl.jop.showMessageDialog(emprunterCtl.jop, "Action annulée", 
					"Info", emprunterCtl.jop.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}
}
