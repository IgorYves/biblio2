package biblio.business;

import java.util.Date;

public class EmpruntEnCours {
	private Date dateEmprunt;
	private Utilisateur emprunteur;
	private Exemplaire exemplaire;
	
	public EmpruntEnCours(Utilisateur emprunteur, Exemplaire exemplaire, Date dateEmprunt) {
		super();
		this.dateEmprunt = dateEmprunt;
		this.emprunteur = emprunteur;
		this.exemplaire = exemplaire;
		this.emprunteur.addEmpruntEnCours(this);
		this.exemplaire.setEmpruntEnCours(this);
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

	@Override
	public String toString() {
		return "[dateEmprunt: " + dateEmprunt + "; emprunteur: "
				+ emprunteur.getNom() 
				+ "; exemplaire: " + exemplaire.getAuteur() + ", " + exemplaire.getTitle() + "]";
	}
	
	
}
