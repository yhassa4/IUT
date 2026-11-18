package mvc.modele.utilisateur;

import mvc.modele.batiment.ModeleMaison;
import mvc.modele.gestion.ModeleGestionAttente;

/**
 * Représente un étudiant dans le système de la Cité Internationale Universitaire de Paris.
 * Cette classe gère les informations personnelles de l'étudiant et son affectation à une maison.
 */
public class ModeleEtudiant {

	private ModeleMaison saMaison;
	private ModeleGestionAttente sonAttente;
	private String nom;
	private String prenom;
	private String nationnalite;
	private String souhaitMaison; // Préférence de maison de l'étudiant

	/**
	 * Crée un nouvel étudiant avec ses informations personnelles.
	 * 
	 * @param nom Le nom de famille de l'étudiant
	 * @param prenom Le prénom de l'étudiant
	 * @param nationnalite La nationalité de l'étudiant (utilisée pour l'attribution des maisons)
	 * @param souhaitMaison La maison souhaitée par l'étudiant (peut être null si pas de préférence)
	 */
	public ModeleEtudiant(String nom, String prenom, String nationnalite, String souhaitMaison) {
		this.nom = nom;
		this.prenom = prenom;
		this.nationnalite = nationnalite;
		this.souhaitMaison = souhaitMaison;
	}

	/**
	 * @return Le nom de famille de l'étudiant
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * @return Le prénom de l'étudiant
	 */
	public String getPrenom() {
		return this.prenom;
	}

	/**
	 * Récupère la maison attribuée à l'étudiant.
	 * 
	 * @return La maison où l'étudiant est affecté
	 * @throws Exception Si l'étudiant n'a pas encore de maison attribuée
	 */
	public ModeleMaison getMaison() throws Exception {
		if (this.saMaison == null) {
			throw new Exception("Cet étudiant n'a pas encore de maison attribuée.");
		}
		return this.saMaison;
	}

	/**
	 * @return La nationalité de l'étudiant
	 */
	public String getNationalite() {
		return this.nationnalite;
	}

	/**
	 * Récupère la préférence de maison de l'étudiant.
	 * Cette information est utilisée lors du processus d'attribution des logements.
	 * 
	 * @return La maison souhaitée ou null si l'étudiant n'a pas exprimé de préférence
	 */
	public String getSouhaitMaison() {
		return this.souhaitMaison;
	}

	/**
	 * Attribue une maison à l'étudiant.
	 * Cette méthode est appelée uniquement par le système d'attribution des logements.
	 * 
	 * @param maison La maison à attribuer à l'étudiant
	 */
	public void setMaison(ModeleMaison maison) {
		this.saMaison = maison;
	}
}