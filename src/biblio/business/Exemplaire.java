package biblio.business;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Exemplaire implements Serializable {
	private int idExemplaire;
	private Date dateAchat;
	private String isbn;
	private EnumStatusExemplaire status;
	private String title;
	private String auteur;
	private EmpruntEnCours empruntEnCours;
	
	public Exemplaire() {}
	public Exemplaire(int idExemplaire, String title, String auteur, 
			Date dateAchat, String isbn, EnumStatusExemplaire status) {
		super();
		this.idExemplaire = idExemplaire;
		this.title = title;
		this.auteur = auteur;
		this.dateAchat = dateAchat;
		this.isbn = isbn;
		this.status = status;
	}
	
	public EnumStatusExemplaire getStatus() {
		return status;
	}
	public void setStatus(EnumStatusExemplaire status) {
		this.status = status;
	}
	public int getIdExemplaire() {
		return idExemplaire;
	}
	public Date getDateAchat() {
		return dateAchat;
	}
	public String getTitle() {
		return title;
	}
	public String getAuteur() {
		return auteur;
	}
	public String getIsbn() {
		return isbn;
	}
	public EmpruntEnCours getEmpruntEnCours() {
		return empruntEnCours;
	}
	public void setEmpruntEnCours(EmpruntEnCours empruntEnCours) {
		this.empruntEnCours = empruntEnCours;
		setStatus(EnumStatusExemplaire.PRETE);
	}
	
	@Override
	public String toString() {
		SimpleDateFormat dateFormater = new SimpleDateFormat("dd MMM yyyy");
		return "(Exemplaire) idExemplaire: " + idExemplaire + "; title: " + title + "; auteur: " + auteur 
				+ "; dateAchat: " + dateFormater.format(dateAchat) + "; isbn: " + isbn + "; status: " + status;
	}
	
	
	
	
}
