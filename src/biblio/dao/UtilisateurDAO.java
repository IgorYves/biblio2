package biblio.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import biblio.business.Adherent;
import biblio.business.Employe;
import biblio.business.EnumCategorieEmploye;
import biblio.business.Utilisateur;

public class UtilisateurDAO {
	private Connection connection;
	
	public UtilisateurDAO() throws IOException {
		this(ConnectionFactory.getDbConnection());
	}
	public UtilisateurDAO(Connection connection) {
		this.connection = connection;
	}
	
	

	public Utilisateur findByKey(int id) throws SQLException {
		connection.setAutoCommit(false);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from utilisateur "
				+ "left join adherent on utilisateur.IDUTILISATEUR=adherent.IDUTILISATEUR "
				+ "left join employe on utilisateur.IDUTILISATEUR = employe.IDUTILISATEUR "
				+ "where utilisateur.IDUTILISATEUR=" + id);
		Utilisateur user = null;
		if (resultSet.next()) {
			if ((resultSet.getString("CATEGORIEUTILISATEUR"))
					.equalsIgnoreCase("ADHERENT")) {
				user = new Adherent(id, resultSet.getString("nom"), 
					resultSet.getString("prenom"), 
					resultSet.getString("pwd"), 
					resultSet.getString("pseudonyme"), 
					new java.util.Date((resultSet.getDate("datenaissance")).getTime()),
					resultSet.getString("sexe"),
					resultSet.getString("telephone"));
			}
			else if ((resultSet.getString("CATEGORIEUTILISATEUR"))
					.equalsIgnoreCase("EMPLOYE")) {
				EnumCategorieEmploye categorieEmploye = EnumCategorieEmploye.BIBLIOTHECAIRE;
				switch ((resultSet.getString("categorieemploye")).toUpperCase()) {
					case "BIBLIOTHECAIRE" :
						categorieEmploye = EnumCategorieEmploye.BIBLIOTHECAIRE;
						break;
					case "RESPONSABLE" :
						categorieEmploye = EnumCategorieEmploye.RESPONSABLE;
						break;
					case "GESTIONNAIRE_DE_FONDS" :
						categorieEmploye = EnumCategorieEmploye.GESTIONNAIRE_DE_FONDS;
						break;
				}
				user = new Employe(id, resultSet.getString("nom"), 
						resultSet.getString("prenom"), 
						resultSet.getString("pwd"), 
						resultSet.getString("pseudonyme"), 
						new java.util.Date((resultSet.getDate("datenaissance")).getTime()),
						resultSet.getString("sexe"),
						resultSet.getString("codematricule"),
						categorieEmploye);
			}
			else {
				System.out.println(1 / 0);
			}
			statement.close();
			return user;
		}
		connection.rollback();
		return null;
	}

	public List<Utilisateur> findAll() throws SQLException {
		connection.setAutoCommit(false);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from utilisateur "
				+ "left join adherent on utilisateur.IDUTILISATEUR=adherent.IDUTILISATEUR "
				+ "left join employe on utilisateur.IDUTILISATEUR = employe.IDUTILISATEUR");
		List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		Utilisateur user = null;
		while (resultSet.next()) {
			if ((resultSet.getString("CATEGORIEUTILISATEUR"))
					.equalsIgnoreCase("ADHERENT")) {
				user = new Adherent(resultSet.getInt("IDUTILISATEUR"), 
					resultSet.getString("nom"), 
					resultSet.getString("prenom"), 
					resultSet.getString("pwd"), 
					resultSet.getString("pseudonyme"), 
					new java.util.Date((resultSet.getDate("datenaissance")).getTime()),
					resultSet.getString("sexe"),
					resultSet.getString("telephone"));
			}
			else if ((resultSet.getString("CATEGORIEUTILISATEUR"))
					.equalsIgnoreCase("EMPLOYE")) {
				EnumCategorieEmploye categorieEmploye = EnumCategorieEmploye.BIBLIOTHECAIRE;
				switch ((resultSet.getString("categorieemploye")).toUpperCase()) {
					case "BIBLIOTHECAIRE" :
						categorieEmploye = EnumCategorieEmploye.BIBLIOTHECAIRE;
						break;
					case "RESPONSABLE" :
						categorieEmploye = EnumCategorieEmploye.RESPONSABLE;
						break;
					case "GESTIONNAIRE_DE_FONDS" :
						categorieEmploye = EnumCategorieEmploye.GESTIONNAIRE_DE_FONDS;
						break;
				}
				user = new Employe(resultSet.getInt("IDUTILISATEUR"), 
						resultSet.getString("nom"), 
						resultSet.getString("prenom"), 
						resultSet.getString("pwd"), 
						resultSet.getString("pseudonyme"), 
						new java.util.Date((resultSet.getDate("datenaissance")).getTime()),
						resultSet.getString("sexe"),
						resultSet.getString("codematricule"),
						categorieEmploye);
			}
			else {
				System.out.println(1 / 0);
			}
			utilisateurs.add(user);
		}
		statement.close();
		return utilisateurs;
	}


	public static void main(String[] args) throws IOException, SQLException {
		UtilisateurDAO userDAO = new UtilisateurDAO();
		System.out.println(userDAO.findByKey(1));
		System.out.println(userDAO.findByKey(2));
		System.out.println("----------------");
		System.out.println(userDAO.findAll());
		
	}

}
