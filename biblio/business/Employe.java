package biblio.business;

import java.util.Date;

public class Employe extends Utilisateur {
	private String codeMatricule;
	private EnumCategorieEmploye categorieEmploye;
	
	public Employe(int idUtilisateur, String nom, String prenom, 
			String pseudonyme, String pwd, Date dateNaissance, String sexe, 
			String codeMatricule, EnumCategorieEmploye categorieEmploye) {
		super(idUtilisateur, nom, prenom, pseudonyme, pwd, dateNaissance, sexe);
		this.codeMatricule = codeMatricule;
		this.categorieEmploye = categorieEmploye;
	}

	public String getCodeMatricule() {
		return codeMatricule;
	}

	public EnumCategorieEmploye getCategorieEmploye() {
		return categorieEmploye;
	}
	
	@Override
	public boolean isConditionsPretAcceptees() {
		return true;
	}
	
	@Override
	public int getNbRetards() {
		return 0;
	}

	@Override
	public String toString() {
		return super.toString() + "; (Employe) codeMatricule: " + codeMatricule 
				+ "; categorieEmploye: "	+ categorieEmploye;
	}
	
}
