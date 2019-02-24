package biblio.business;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Personne {
	private String nom;
	private String prenom;
	private Date dateNaissance;
	private String sexe;
	
	public Personne(String nom, String prenom, Date dateNaissance,
			String sexe) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.sexe = sexe;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public String getSexe() {
		return sexe;
	}

	@Override
	public String toString() {
		SimpleDateFormat dateFormater = new SimpleDateFormat("dd MMM yyyy");
		return "(Personne) nom: " + nom + "; prenom: " + prenom
				+ "; dateNaissance: " + dateFormater.format(dateNaissance) + "; sexe: " + sexe;
	}
	
	
}
