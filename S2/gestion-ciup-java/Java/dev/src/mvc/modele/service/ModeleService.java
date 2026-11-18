package mvc.modele.service;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


/**
 * Classe centrale de gestion des services de restauration.
 *
 * Cette classe permet de regrouper et gérer deux types de services :
 * - les cafétérias (listeCafeteria)
 * - les restaurants (listeRestaurant)
 *
 * Elle initialise les listes de services, puis génère automatiquement des menus
 * aléatoires pour chacun d'eux (exemples pour test ou affichage initial).
 *
 * ⚠️ Cette classe est utile pour centraliser tous les services de restauration
 * et faciliter leur gestion dans l'application (récupération, affichage, traitement...).
 */
public class ModeleService implements Serializable{

	/** Liste de toutes les cafétérias de la CIUP */
	private List<ModeleRestauration> listeCafeteria;

	/** Liste de tous les restaurants de la CIUP */
	private List<ModeleRestauration> listeRestaurant;

	/**
	 * Constructeur par défaut.
	 * Initialise les deux listes avec des données générées automatiquement,
	 * puis ajoute des menus factices à chaque service.
	 */
	public ModeleService() {
		this.listeCafeteria = InitializeCafet();
		this.listeRestaurant = InitializeRestau();
		genererMenusPourTousLesServices();

	}

	/**
	 * Génère des menus fictifs pour tous les services de restauration,
	 * aussi bien restaurants que cafétérias.
	 * Appelée automatiquement lors de la création de l'objet.
	 */
	private void genererMenusPourTousLesServices() {
		for (ModeleRestauration cafet : listeCafeteria) {
			ajouterMenusAleatoires(cafet);
		}
		for (ModeleRestauration restau : listeRestaurant) {
			ajouterMenusAleatoires(restau);
		}
	}

	/**
	 * Ajoute automatiquement 5 menus fictifs à un service donné.
	 * Chaque menu aura un nom, une info, une date différente et un type de repas.
	 *
	 * @param service le service (cafet ou restaurant) auquel on veut ajouter les menus
	 */
	private void ajouterMenusAleatoires(ModeleRestauration service) {
		for (int i = 1; i <= 5; i++) {
			String nomMenu = "Menu " + i + " de " + service.getNom();
			String infos = "Entrée + Plat + Dessert n°" + i;
			LocalDate date = LocalDate.now().plusDays(i); // Dates différentes
			TypeRepas type = switch (i % 3) {
			case 0 -> TypeRepas.CLASSIQUE;
			case 1 -> TypeRepas.VEGETARIEN;
			default -> TypeRepas.VEGAN;
			};

			ModeleMenu menu = new ModeleMenu(nomMenu, infos, date, type);
			service.ajouterMenu(menu);
		}
	}

	/**
	 * Initialise la liste des cafétérias disponibles avec des horaires spécifiques.
	 *
	 * @return une liste d'objets ModeleCafeteria (polymorphisme via ModeleRestauration)
	 */
	private List<ModeleRestauration > InitializeCafet() {
		return List.of(

				new ModeleCafeteria("Cafétéria Orsay", "08h00 - 14h00", "09h00 - 15h00"),
				new ModeleCafeteria("Cafétéria ENS", "08h30 - 13h30", "09h00 - 14h00"),
				new ModeleCafeteria("Cafétéria Jussieu", "09h00 - 15h00", "10h00 - 15h30"),
				new ModeleCafeteria("Cafétéria Curie", "08h00 - 13h00", "09h00 - 14h00"),
				new ModeleCafeteria("Cafétéria Sorbonne", "09h00 - 14h30", "10h00 - 15h00"),
				new ModeleCafeteria("Cafétéria Polytechnique", "08h30 - 14h30", "09h00 - 15h30"),
				new ModeleCafeteria("Cafétéria CentraleSupélec", "08h00 - 13h30", "09h30 - 15h00"),
				new ModeleCafeteria("Cafétéria Sciences Po", "08h45 - 14h00", "09h30 - 15h00"),
				new ModeleCafeteria("Cafétéria HEC", "08h00 - 13h00", "09h00 - 14h00"),
				new ModeleCafeteria("Cafétéria Mines Paris", "08h30 - 14h00", "09h00 - 15h00"),
				new ModeleCafeteria("Cafétéria ENSAE", "08h15 - 13h45", "09h00 - 14h30"),
				new ModeleCafeteria("Cafétéria Télécom Paris", "08h30 - 14h00", "09h15 - 15h15")

				);

	}

	/**
	 * Initialise la liste des restaurants disponibles avec horaires déjeuner et dîner.
	 *
	 * @return une liste d'objets ModeleRestaurant (polymorphisme via ModeleRestauration)
	 */
	private List<ModeleRestauration > InitializeRestau() {
		return List.of(

				// Création des restaurants
				new ModeleRestaurant("Restau Orsay", "11h30 - 13h30", "12h00 - 14h00"),
				new ModeleRestaurant("Restau ENS", "11h45 - 13h15", "12h00 - 13h45"),
				new ModeleRestaurant("Restau AgroParisTech", "12h00 - 14h00", "12h30 - 14h30"),
				new ModeleRestaurant("Restau Jussieu", "11h00 - 13h00", "12h00 - 13h30"),
				new ModeleRestaurant("Restau Curie", "12h00 - 14h00", "12h30 - 14h00"),
				new ModeleRestaurant("Restau Polytechnique", "11h45 - 13h30", "18h00 - 20h00"),
				new ModeleRestaurant("Restau CentraleSupélec", "12h00 - 14h00", "19h00 - 21h00"),
				new ModeleRestaurant("Restau Sciences Po", "11h30 - 13h00", "18h30 - 20h30"),
				new ModeleRestaurant("Restau HEC", "12h15 - 14h15", "19h00 - 21h00"),
				new ModeleRestaurant("Restau Mines Paris", "11h45 - 13h45", "18h00 - 20h00"),
				new ModeleRestaurant("Restau Inconnito", "08h15 - 13h45", "09h00 - 14h30"),
				new ModeleRestaurant("Restau Paris", "08h30 - 14h00", "09h15 - 15h15")


				);
	}

	/**
	 * Accès à la liste des cafétérias.
	 *
	 * @return liste de cafétérias disponibles
	 */
	public List<ModeleRestauration> getListeCafeteria() {
		return listeCafeteria;
	}


	/**
	 * Accès à la liste des restaurants.
	 *
	 * @return liste de restaurants disponibles
	 */
	public List<ModeleRestauration> getListeRestaurant() {
		return listeRestaurant;
	}


	/**
	 * Ajoute un nouveau service de restauration (cafétéria ou restaurant) à la liste correspondante.
	 *
	 * @param service le service de restauration à ajouter
	 * @throws Exception si le service existe déjà ou si le type est inconnu
	 */
	public void ajouterServices(ModeleRestauration service) throws Exception {
		// Vérifie si le service est une cafétéria
		if (service instanceof ModeleCafeteria) {
			// Empêche les doublons
			if (listeCafeteria.contains(service)) {
				throw new Exception("La cafétéria existe déjà");
			}
			// Ajoute à la liste des cafétérias
			listeCafeteria.add((ModeleCafeteria) service);
			// Vérifie si le service est un restaurant
		} else if (service instanceof ModeleRestaurant) {
			if (listeRestaurant.contains(service)) {
				throw new Exception("Le restaurant existe déjà");
			}
			// Ajoute à la liste des restaurants
			listeRestaurant.add((ModeleRestaurant) service);
			// Cas de type non reconnu (ni cafétéria ni restaurant)
		} else {
			throw new Exception("Type de service inconnu");
		}
	}

	/**
	 * Supprime un service de restauration (cafétéria ou restaurant) des listes si présent.
	 *
	 * @param service le service de restauration à supprimer
	 * @throws Exception si le service n'existe pas ou si le type est inconnu
	 */
	public void supprimerServices(ModeleRestauration service) throws Exception {
		// Vérifie si le service est une cafétéria
		if (service instanceof ModeleCafeteria) {
			// Empêche la suppression si le service n'existe pas
			if (!listeCafeteria.contains(service)) {
				throw new Exception("La cafétéria n'existe pas");
			}
			// Supprime de la liste
			listeCafeteria.remove(service);
		} else if (service instanceof ModeleRestaurant) {
			if (!listeRestaurant.contains(service)) {
				throw new Exception("Le restaurant n'existe pas");
			}
			// Supprime de la liste
			listeRestaurant.remove(service);
			// Cas de type non reconnu
		} else {
			throw new Exception("Type de service inconnu");
		}
	}

}
