package biblio.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import biblio.business.EnumStatusExemplaire;
import biblio.business.Exemplaire;
import biblio.control.ExemplaireI;

public class ExemplaireDAO implements ExemplaireI {
	private Connection connection;
	
	public ExemplaireDAO() throws IOException {
		this(ConnectionFactory.getDbConnection());
	}
	public ExemplaireDAO(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public Exemplaire findByKey(int id) throws SQLException {
		connection.setAutoCommit(false);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT "
				+ "livre.titre, exemplaire.dateachat, exemplaire.isbn, exemplaire.status "
				+ "from exemplaire join livre on exemplaire.isbn = livre.isbn "
				+ "where exemplaire.idexemplaire = " + id);
		if (resultSet.next()) {
			return new Exemplaire(id, resultSet.getString(1), null, 
					new java.util.Date(resultSet.getDate(2).getTime()), 
					resultSet.getString(3), 
					EnumStatusExemplaire.valueOf(resultSet.getString(4)));
			//todo close statement
		}
		connection.rollback();
		return null;
	}
	
	@Override
	public List<Exemplaire> findAll() throws SQLException {
		connection.setAutoCommit(false);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT exemplaire.idexemplaire, "
				+ "livre.titre, exemplaire.dateachat, exemplaire.isbn, exemplaire.status "
				+ "from exemplaire join livre on exemplaire.isbn = livre.isbn");
		ArrayList<Exemplaire> exemplaires = new ArrayList<Exemplaire>();
		while (resultSet.next()) {
			exemplaires.add(new Exemplaire(resultSet.getInt(1), resultSet.getString(2), null, 
					new java.util.Date(resultSet.getDate(3).getTime()), 
					resultSet.getString(4), 
					EnumStatusExemplaire.valueOf(resultSet.getString(5))));
		}
		statement.close();
		return exemplaires;
	}
	
	@Override
	public List<Exemplaire> findAllDisponibles() throws SQLException {
		connection.setAutoCommit(false);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT exemplaire.idexemplaire, "
				+ "livre.titre, exemplaire.dateachat, exemplaire.isbn, exemplaire.status "
				+ "from exemplaire join livre on exemplaire.isbn = livre.isbn "
				+ "where exemplaire.status='DISPONIBLE'");
		ArrayList<Exemplaire> exemplaires = new ArrayList<Exemplaire>();
		while (resultSet.next()) {
			exemplaires.add(new Exemplaire(resultSet.getInt(1), resultSet.getString(2), null, 
					new java.util.Date(resultSet.getDate(3).getTime()), 
					resultSet.getString(4), 
					EnumStatusExemplaire.valueOf(resultSet.getString(5))));
		}
		statement.close();
		return exemplaires;
	}
	
	@Override
	public boolean updateExemplaireStatus(Exemplaire exemplaire) throws SQLException {
		connection.setAutoCommit(false);
		Statement statement = connection.createStatement();
		statement.executeUpdate("update exemplaire set status = '" + exemplaire.getStatus() + "'"
				+ " where idexemplaire = " + exemplaire.getIdExemplaire());
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

	
	
	public static void main(String[] args) throws IOException, SQLException {
		ExemplaireI exDAO = new ExemplaireDAO(ConnectionFactory.getDbConnection());
		System.out.println(exDAO.findByKey(1));
		System.out.println("----------------");
		System.out.println(exDAO.findAll());
		
	}
}
