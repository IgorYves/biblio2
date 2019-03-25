package biblio.business;

import java.io.Serializable;
import java.util.Date;

public class EmpruntEnCours implements Serializable {
	private Date dateEmprunt;
	private Utilisateur emprunteur;
	private Exemplaire exemplaire;
	
	public EmpruntEnCours(Utilisateur emprunteur, Exemplaire exemplaire, Date dateEmprunt) 
			throws BiblioException 
	{
		super();
		this.dateEmprunt = dateEmprunt;
		this.emprunteur = emprunteur;
		this.exemplaire = exemplaire;
		
		if (this.emprunteur.isConditionsPretAcceptees()) {
			if (this.emprunteur != null) {
				this.emprunteur.addEmpruntEnCours(this);
			}
			if (this.exemplaire != null) {
				this.exemplaire.setEmpruntEnCours(this);
			}
		}
		else if (((new Date()).getTime() - dateEmprunt.getTime()) > (60*60*1000)) {
			if (this.emprunteur != null) {
				this.emprunteur.addEmpruntEnCours(this);
			}
			if (this.exemplaire != null) {
				this.exemplaire.setEmpruntEnCours(this);
			}
		}
		else {
			throw new BiblioException("Conditions de Prets ne sont pas acceptées");
		}
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
		long dayToday = new Date().getTime() 
						- (new Date().getTime())%(24*60*60*1000);
		long dayEmprunt = this.getDateEmprunt().getTime() 
						- this.getDateEmprunt().getTime()%(24*60*60*1000);
		int daysEmprunt = (int) ((dayToday - dayEmprunt) / (24*60*60*1000));
		return "[(Emprunt) dateEmprunt: " + dateEmprunt
				+ " (" + daysEmprunt + " jour(s))"
				+ "; emprunteur: " + emprunteur.getNom() 
				+ "; exemplaire: " + exemplaire.getIdExemplaire() 
				+ ", " + exemplaire.getAuteur() 
				+ ", " + exemplaire.getTitle() + "]";
	}
	
	
}
