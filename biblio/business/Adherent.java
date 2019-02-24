package biblio.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Adherent extends Utilisateur {
	private String[] telephone;
	private static int nmMaxPrets = 3;
	private static int dureeMaxPrets = 15;
	
	public Adherent(int idUtilisateur, String nom, String prenom, 
			String pseudonyme, String pwd, Date dateNaissance, String sexe, 
			String ... telephone) {
		super(idUtilisateur, nom, prenom, pseudonyme, pwd, dateNaissance, sexe);
		this.telephone = telephone;
	}

	public String[] getTelephone() {
		return telephone;
	}

	@Override
	public boolean isConditionsPretAcceptees() {
		if (getNbRetards() > 0)
			return false;
		if (getEmpruntEnCours().size() >= nmMaxPrets)
			return false;
		return true;
	}
	
	@Override
	public int getNbRetards() {
		ArrayList<EmpruntEnCours> empruntsEnCours = getEmpruntEnCours();
		int dayToday = (int) ((new Date().getTime()) 
							- (new Date().getTime())%(24*60*60*1000));
		return (int) empruntsEnCours.stream()
					.filter((e)-> dureeMaxPrets < 
							(int) (dayToday - (e.getDateEmprunt().getTime()
									- e.getDateEmprunt().getTime()%(24*60*60*1000)))
							)
					.count();
	}

	@Override
	public String toString() {
		return super.toString() + "; (Adherent) telephone: " + Arrays.toString(telephone);
	}
	
}
