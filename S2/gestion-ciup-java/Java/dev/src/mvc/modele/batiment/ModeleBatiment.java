package mvc.modele.batiment;
import java.io.Serializable;

import mvc.modele.utilisateur.*;


public interface ModeleBatiment {

	public String getNationnalite();

	public String getDirecteur();

	public String getLocalisation();

	public void afficherEtudiants();

	/**
	 * 
	 * @param etudiant
	 */
	public void ajouterEtudiant(ModeleEtudiant etudiant) throws IllegalArgumentException;

	/**
	 * 
	 * @param etudiant
	 */
	public  void supprimerEtudiant(ModeleEtudiant etudiant) throws IllegalArgumentException;

	public boolean verificationPlace();

}