package biblio.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import biblio.business.BiblioException;
import biblio.business.EmpruntEnCours;
import biblio.business.Exemplaire;
import biblio.business.Utilisateur;
import biblio.dao.BiblioDaoException;
import biblio.dao.EmpruntEnCoursDB;

public interface EmpruntEnCoursI {

	boolean insertEmpruntEnCours(Utilisateur user, Exemplaire exemplaire)
			throws SQLException;

	boolean insertEmpruntEnCours(EmpruntEnCours empruntEnCours)
			throws SQLException;

	EmpruntEnCoursDB findByKey(int idExemplaire)
			throws SQLException, BiblioException, IOException;

	List<EmpruntEnCoursDB> findAll()
			throws SQLException, BiblioException, IOException;

	HashMap<Integer, String> ListAllEmpruntEnCours()
			throws SQLException, BiblioException, IOException;

	List<EmpruntEnCoursDB> findByUtilisateur(Utilisateur user)
			throws SQLException, BiblioException, IOException;

	boolean madeReturn(int idExemplaire) throws SQLException, BiblioDaoException;

}