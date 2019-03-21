package biblio.control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import biblio.dao.ConnectionFactory;
import biblio.dao.UtilisateurDAO;

public class EmprunterCtl {

	public static void main(String[] args) 
			throws IOException, SQLException {
		Connection connection = ConnectionFactory.getDbConnection();
		connection.setAutoCommit(false);
		
		UtilisateurDAO userDAO = new UtilisateurDAO();
		
		
		connection.close();
	}

}
