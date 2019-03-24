package biblio.control;

import java.sql.SQLException;
import java.util.List;

import biblio.business.Exemplaire;

public interface ExemplaireI {

	Exemplaire findByKey(int id) throws SQLException;

	List<Exemplaire> findAll() throws SQLException;

	List<Exemplaire> findAllDisponibles() throws SQLException;

	boolean updateExemplaireStatus(Exemplaire exemplaire) throws SQLException;

}