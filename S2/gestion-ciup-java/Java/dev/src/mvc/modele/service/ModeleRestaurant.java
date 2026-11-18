package mvc.modele.service;
import java.io.Serializable;

/**
 * Classe concrète représentant un restaurant classique au sein de la Cité Internationale.
 * 
 * Elle hérite de la classe abstraite ModeleRestauration et spécialise certains comportements ou attributs
 * pour refléter les spécificités d’un "restaurant" (par opposition à une cafétéria par exemple).
 *
 * Un restaurant peut avoir une capacité maximale et peut imposer la réservation obligatoire.
 */
public class ModeleRestaurant extends ModeleRestauration  implements Serializable{
	
    /** Indique si une réservation est nécessaire pour accéder au restaurant (par défaut : true) */
	private boolean reservationObligatoire = true;
	
    /** Capacité maximale du restaurant (nombre de places assises, par défaut : 120) */
	private int capacite = 120;
	
	
    /**
     * Constructeur du restaurant, utilisant le constructeur de la superclasse pour définir le nom,
     * le type de service (fixé ici à RESTAURANT) et les horaires en semaine et le week-end.
     *
     * @param nom Nom du restaurant
     * @param horaireSemaine Horaires valables du lundi au vendredi
     * @param horaireWeekend Horaires valables les samedis et dimanches
     */
	public ModeleRestaurant(String nom, String horaireSemaine, String horaireWeekend) {
	    super(nom, TypeService.RESTAURANT, horaireSemaine, horaireWeekend);
	}

	
    /**
     * Accesseur pour savoir si la réservation est obligatoire.
     * Peut être utilisé pour adapter l’interface utilisateur ou informer les étudiants.
     * @return true si la réservation est obligatoire, false sinon
     */
	public boolean isReservationObligatoire() {
	    return reservationObligatoire;
	}

	
    /**
     * Définit si une réservation est requise ou non.
     * Permet de modifier dynamiquement cette contrainte si la politique change.
     * @param reservationObligatoire valeur true ou false
     */
	public void setReservationObligatoire(boolean reservationObligatoire) {
		this.reservationObligatoire = reservationObligatoire;
	}

	
    /**
     * Définit la capacité maximale du restaurant.
     * @param capacite Nombre de personnes que le restaurant peut accueillir
     */
	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}

	
    /**
     * Retourne la capacité maximale du restaurant.
     * @return capacité actuelle
     */
	public int getCapacite() {
	    return capacite;
	}



}