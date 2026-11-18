package mvc.modele.batiment;

import java.io.Serializable;
import java.util.*;

import mvc.modele.ciup.ModeleCiteInternationale;
import mvc.modele.utilisateur.ModeleEtudiant;



public class ModeleMaison implements ModeleBatiment, Serializable {

	protected ArrayList<ModeleEtudiant> listeEtudiants;
	private String nomDetaille;
	protected int nbChambre;
	private String nationnalite;
	private String directeur;
	private String localisation;
	private String saCite;


	public ModeleMaison(String nomDetaille, int nbChambre, String nationnalite, String directeur, String localisation, ModeleCiteInternationale saCite) {
		this.nomDetaille = nomDetaille;
		this.nbChambre = nbChambre;
		this.nationnalite = nationnalite;
		this.directeur = directeur;
		this.localisation = localisation;
		this.listeEtudiants = new ArrayList<>();
		this.saCite = saCite.getNom();
	}


	public String getNom() {
		return this.nomDetaille;
	}

	public int getNbChambre() {
		return this.nbChambre;
	}

	public String getNationnalite() {
		return this.nationnalite;
	}

	public String getDirecteur() {
		return this.directeur;
	}

	public String getLocalisation() {
		return this.localisation;
	}


	public void afficherEtudiants() throws IllegalArgumentException {
		if (this.listeEtudiants.isEmpty()) {
			throw new IllegalArgumentException("La liste des etudiants est vide");
		}else {
			for (ModeleEtudiant e : this.listeEtudiants) {
				System.out.println(e.getNom() + " " + e.getPrenom());
			}
		}
	}

	/**
	 * 
	 * @param etudiant
	 */
	public void ajouterEtudiant(ModeleEtudiant etudiant) throws IllegalArgumentException
	{
		if (!verificationPlace() || !listeEtudiants.contains(etudiant)) 
		{
			listeEtudiants.add(etudiant);
		} 
		else 
		{
			throw new IllegalArgumentException("Aucune place disponible ou alors l'etudiant est deja dans la maison");
		}
	}

	/**
	 * 
	 * @param etudiant
	 */
	public void supprimerEtudiant(ModeleEtudiant etudiant) throws IllegalArgumentException 
	{
		if (!listeEtudiants.contains(etudiant)) 
		{
			throw new IllegalArgumentException("L'etudiant n'est pas present dans la maison");
		} 
		else 
		{
			listeEtudiants.remove(etudiant);
		}
	}
	
	

	public boolean verificationPlace() {
		return this.listeEtudiants.size() < nbChambre;
	}

	public ArrayList<ModeleEtudiant> getEtudiants() {
	    return new ArrayList<>(this.listeEtudiants); // Pour les tests
	}

	
	
}