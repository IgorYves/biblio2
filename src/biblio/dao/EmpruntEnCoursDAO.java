package biblio.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import biblio.business.BiblioException;
import biblio.business.EmpruntEnCours;
import biblio.business.Exemplaire;
import biblio.business.Utilisateur;

public class EmpruntEnCoursDAO {
	private Connection connection;
	
	public EmpruntEnCoursDAO() throws IOException {
		this(ConnectionFactory.getDbConnection());
	}
	public EmpruntEnCoursDAO(Connection connection) {
		this.connection = connection;
	}
	
	public boolean insertEmpruntEnCours(Utilisateur user, Exemplaire exemplaire) 
			throws SQLException {
		connection.setAutoCommit(false);
		Statement statement = connection.createStatement();
		statement.executeUpdate("insert into empruntencours "
				+ "values (" + exemplaire.getIdExemplaire()
				+ ", " + user.getIdUtilisateur() + ", sysdate)");
		if (statement.getWarnings() == null) {
			statement.close();
			return true;
		}
		else {
			connection.rollback();
			statement.close();
			return false;
		}
	}
	
	public boolean insertEmpruntEnCours(EmpruntEnCours empruntEnCours) 
			throws SQLException {
		return insertEmpruntEnCours(empruntEnCours.getEmprunteur(), 
									empruntEnCours.getExemplaire());
	}
	
	public EmpruntEnCoursDB findByKey(int idExemplaire) 
			throws SQLException, BiblioException, IOException {
		connection.setAutoCommit(false);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * from empruntencours "
				+ "where idexemplaire = " + idExemplaire);
		if (resultSet.next()) {
			return new EmpruntEnCoursDB(resultSet.getInt("idexemplaire"), 
					resultSet.getInt("idutilisateur"), 
					new java.util.Date(resultSet.getDate("dateemprunt").getTime()),
					connection);
			//todo close statement
		}
		connection.rollback();
		return null;
	}
	
	public List<EmpruntEnCoursDB> findAll() throws SQLException, BiblioException, IOException {
		connection.setAutoCommit(false);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select IDEXEMPLAIRE from empruntencours");
		List<EmpruntEnCoursDB> empruntEnCoursDB = new ArrayList<>();
		ArrayList<Integer> idexemplaires = new ArrayList<>();
		while (resultSet.next()) {
			idexemplaires.add(resultSet.getInt(1));
		}
		for (Integer idexemplaire : idexemplaires) {
			empruntEnCoursDB.add(this.findByKey(idexemplaire));
		}
		
		statement.close();
		return empruntEnCoursDB;
	}
	
	public List<EmpruntEnCoursDB> findByUtilisateur(Utilisateur user) 
			throws SQLException, BiblioException, IOException {
		List<EmpruntEnCoursDB> empruntsEnCoursDB = new ArrayList<EmpruntEnCoursDB>();
		connection.setAutoCommit(false);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * from empruntencours "
				+ "where idutilisateur = " + user.getIdUtilisateur());
		while (resultSet.next()) {
			empruntsEnCoursDB.add(new EmpruntEnCoursDB(resultSet.getInt("idexemplaire"), 
					resultSet.getInt("idutilisateur"), 
					new java.util.Date(resultSet.getDate("datemprunt").getTime()),
					connection));
			//todo close statement
		}
		statement.close();
		return empruntsEnCoursDB;
	}
	
	public static void main(String[] args) {
		

	}

}
