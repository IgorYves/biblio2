package biblio.business;

import java.util.ArrayList;
import java.util.Date;

public abstract class Utilisateur extends Personne {
	private int idUtilisateur;
	private String pwd;
	private String pseudonyme;
	private ArrayList<EmpruntEnCours> empruntsEnCours = new ArrayList<>();
	
	public Utilisateur(int idUtilisateur, String nom, String prenom, 
			String pseudonyme, String pwd, Date dateNaissance, String sexe) {
		super(nom, prenom, dateNaissance, sexe);
		this.idUtilisateur = idUtilisateur;
		this.pwd = pwd;
		this.pseudonyme = pseudonyme;
	}
	public int getIdUtilisateur() {
		return idUtilisateur;
	}
	public void setIdUtilisateur(int idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}
	public String getPwd() {
		return pwd;
	}
	public String getPseudonyme() {
		return pseudonyme;
	}
	
	public void addEmpruntEnCours(EmpruntEnCours ep) {
		empruntsEnCours.add(ep);
	}
	
	public ArrayList<EmpruntEnCours> getEmpruntEnCours() {
		return empruntsEnCours;
	}
	
	public int getNbEmpruntsEnCours() {
		return empruntsEnCours.size();
	}
	
	abstract public boolean isConditionsPretAcceptees();
	abstract public int getNbRetards();
	
	@Override
	public String toString() {
		return super.toString() + "; (Utilisateur) idUtilisateur: " + idUtilisateur + "; pseudonyme: " + pseudonyme 
				+ "; pwd: " + pwd + "; empruntsEnCours: " + empruntsEnCours;
	}
}
