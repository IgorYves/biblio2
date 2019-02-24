package biblio.business;

import java.util.Date;

public class EmpruntArchive {
	private Date dateRestitutionEff;
	private Date dateEmprunt;
	private Utilisateur emprunteur;
	private Exemplaire exemplaire;
	
	public EmpruntArchive(Date dateRestitutionEff, Date dateEmprunt,
			Utilisateur emprunteur, Exemplaire exemplaire) {
		super();
		this.dateRestitutionEff = dateRestitutionEff;
		this.dateEmprunt = dateEmprunt;
		this.emprunteur = emprunteur;
		this.exemplaire = exemplaire;
	}

	public Date getDateRestitutionEff() {
		return dateRestitutionEff;
	}

	public Date getDateEmprunt() {
		return dateEmprunt;
	}

	public Utilisateur getEmprunteur() {
		return emprunteur;
	}

	public Exemplaire getExemplaire() {
		return exemplaire;
	}
	
	
	
	
}
