package biblio.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import biblio.business.BiblioException;
import biblio.business.Utilisateur;

public interface UtilisateurI {

	Utilisateur findByKey(int id)
			throws SQLException, BiblioException, IOException;

	List<Utilisateur> findAll()
			throws SQLException, BiblioException, IOException;

	HashMap<Integer, String> ListAllUsers()
			throws SQLException, BiblioException, IOException;

}