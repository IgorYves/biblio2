package biblio.dao;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import biblio.business.EnumStatusExemplaire;
import biblio.business.Exemplaire;

public class ExemplaireDAO {
	private HashMap<Integer, Exemplaire> exemplaireDB = new HashMap<>();
	
	public ExemplaireDAO() {
		exemplaireDB.put(0, new Exemplaire(0, "3 musketers", "dumas", 
				new Date(), "978-985-6020-09-7", EnumStatusExemplaire.DISPONIBLE));
		exemplaireDB.put(1, new Exemplaire(1, "miserables", "victor hugo", 
				new Date(), "978-966-7343-29-3", EnumStatusExemplaire.DISPONIBLE));
		exemplaireDB.put(2, new Exemplaire(2, "alise", "karoll", 
				new Date(), "978-966-2046-62-5", EnumStatusExemplaire.DISPONIBLE));
		exemplaireDB.put(3, new Exemplaire(3, "asterix", "depardieu", 
				new Date(), "978-966-2046-92-2", EnumStatusExemplaire.DISPONIBLE));
		exemplaireDB.put(4, new Exemplaire(4, "rubbit", "unknown", 
				new Date(), "978-1-56619-909-4", EnumStatusExemplaire.DISPONIBLE));

	}
	public Exemplaire findByKey(int id) {
		return exemplaireDB.get(id);
	}
	
	public int insertExemplaire(Exemplaire ex) {
		if (ex == null) return -1;
		int max = exemplaireDB.keySet().stream()
				.max(Comparator.comparing(Integer::valueOf)).get();
		exemplaireDB.put(++max, ex);
		return max;
	}
	
	public void updateExemplaire(Exemplaire ex) {
		exemplaireDB.replace(ex.getIdExemplaire(), ex);
	}
	
	public void deleteExemplaire(Exemplaire ex) {
		exemplaireDB.remove(ex.getIdExemplaire());
	}
	public void deleteExemplaire(int id) {
		exemplaireDB.remove(id);
	}
	
	public static void main(String[] args) {
		ExemplaireDAO exDAO = new ExemplaireDAO();
		System.out.println(exDAO.findByKey(0));
		int lastID = exDAO.insertExemplaire(new Exemplaire(0, "Title de livre", "Autor", new Date(), 
				"978-985-6020-09-7", EnumStatusExemplaire.DISPONIBLE));
		System.out.println("(insertExemplaire, lastID : " + lastID + ") " + exDAO.findByKey(lastID));
		
	}
}
