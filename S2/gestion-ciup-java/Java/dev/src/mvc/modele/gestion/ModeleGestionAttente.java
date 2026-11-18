package mvc.modele.gestion;

import java.io.Serializable;
import java.util.ArrayList;

import mvc.modele.ciup.ModeleCiteInternationale;
import mvc.modele.utilisateur.ModeleEtudiant;

/**
 * Gère la liste d'attente des étudiants pour l'attribution des logements.
 * Cette classe assure :
 * - La gestion des étudiants en attente d'attribution
 * - L'ajout et la suppression d'étudiants de la liste
 * - L'attribution des maisons aux étudiants en attente
 */
public class ModeleGestionAttente implements Serializable {

	/** La cité internationale associée à cette gestion d'attente */
	private ModeleCiteInternationale cite;
	
	/** Liste des étudiants en attente d'attribution de logement */
	private ArrayList<ModeleEtudiant> listeAttente;

	/**
	 * Crée une nouvelle instance de gestion de la liste d'attente.
	 * Initialise une liste d'attente vide.
	 */
	public ModeleGestionAttente() {
		this.listeAttente = new ArrayList<>();
	}

	/**
	 * Associe une cité internationale à cette gestion d'attente.
	 * 
	 * @param cite La cité internationale à associer
	 */
	public void setCite(ModeleCiteInternationale cite) {
		this.cite = cite;
	}

	/**
	 * Récupère la cité internationale associée à cette gestion.
	 * 
	 * @return La cité internationale
	 */
	public ModeleCiteInternationale getCite() {
		return this.cite;
	}

	/**
	 * Ajoute un étudiant à la liste d'attente.
	 * Vérifie que l'étudiant n'est pas déjà dans la liste.
	 * 
	 * @param etudiant L'étudiant à ajouter à la liste d'attente
	 * @throws IllegalArgumentException Si l'étudiant est déjà dans la liste d'attente
	 */
	public void ajouterEnAttente(ModeleEtudiant etudiant) throws IllegalArgumentException {
		if (listeAttente.contains(etudiant)) {
			throw new IllegalArgumentException("L'étudiant est déjà dans la liste d'attente");
		}
		listeAttente.add(etudiant);
	}

	/**
	 * Supprime un étudiant de la liste d'attente.
	 * 
	 * @param etudiant L'étudiant à supprimer de la liste
	 * @throws IllegalArgumentException Si l'étudiant n'est pas dans la liste d'attente
	 */
	public void retirerDAttente(ModeleEtudiant etudiant) throws IllegalArgumentException {
		if (!listeAttente.contains(etudiant)) {
			throw new IllegalArgumentException("L'étudiant n'est pas dans la liste d'attente");
		}
		listeAttente.remove(etudiant);
	}

	/**
	 * Retourne une copie de la liste d'attente pour éviter la modification directe.
	 * 
	 * @return Une nouvelle liste contenant les étudiants en attente
	 */
	public ArrayList<ModeleEtudiant> getListeAttente() {
		return new ArrayList<>(listeAttente);
	}

	/**
	 * Affiche la liste des étudiants en attente dans la console.
	 * 
	 * @throws IllegalArgumentException Si la liste d'attente est vide
	 */
	public void afficherListeAttente() throws IllegalArgumentException {
		if (listeAttente.isEmpty()) {
			throw new IllegalArgumentException("La liste d'attente est vide");
		}
		for (ModeleEtudiant e : listeAttente) {
			System.out.println(e.getNom() + " " + e.getPrenom());
		}
	}

	/**
	 * Retire et retourne le premier étudiant de la liste d'attente.
	 * Cette méthode est utilisée lors de l'attribution d'une maison.
	 * 
	 * @return Le premier étudiant de la liste ou null si la liste est vide
	 */
	public ModeleEtudiant attribuerMaison() {
		if (listeAttente.isEmpty()) {
			return null;
		}
		ModeleEtudiant etudiant = listeAttente.get(0);
		listeAttente.remove(0);
		return etudiant;
	}
}