package mvc.modele.service;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import mvc.modele.batiment.ModeleMaisonInternationale;

/**
 * Classe abstraite représentant un service de restauration dans la Cité Internationale,
 * tel qu’un restaurant ou une cafétéria.
 *
 * Cette classe factorise les attributs et comportements communs à tous les types de services
 * de restauration : nom, horaires, menus, type, etc.
 * Elle permet de spécialiser ensuite le comportement via des sous-classes concrètes
 * comme ModeleRestaurant ou ModeleCafeteria.
 *
 * Elle implémente Serializable pour permettre sa sauvegarde/restauration dans des fichiers binaires.
 */
public abstract class ModeleRestauration  implements Serializable{

    /** Nom du service de restauration (ex: "Resto U Lila", "Cafétéria Eiffel") */
    protected String nom;

    /** Nom de la maison internationale à laquelle appartient ce service (ex: "Maison du Japon") */
    protected String nomMaisonInter;

    /** Liste des menus proposés par le service, actualisés chaque jour */
    protected ArrayList<ModeleMenu> menus;

    /** Type du service : soit RESTAURANT, soit CAFETERIA (permet de différencier les interfaces) */
    protected TypeService sonType;

    /** Horaires d’ouverture par jour de la semaine (lundi → "11h30 - 14h00", etc.) */
    protected Map<DayOfWeek, String> horairesParJour;

    /** Date du jour associée aux menus à afficher (utile pour filtrer le menu du jour) */
    protected LocalDate dateDuMenu;


    /**
     * Constructeur principal d’un service de restauration.
     * Initialise les horaires en les répartissant automatiquement selon les jours ouvrés
     * (lundi à vendredi) et le week-end (samedi/dimanche).
     *
     * @param nom Nom du service
     * @param sonType Type du service (CAFETERIA ou RESTAURANT)
     * @param horaireSemaine Horaire à appliquer du lundi au vendredi
     * @param horaireWeekend Horaire à appliquer le samedi et le dimanche
     */
	public ModeleRestauration (String nom, TypeService sonType, String horaireSemaine, String horaireWeekend) {
		this.nom = nom;
		//this.nomCite = saCite.getNom();
		this.sonType = sonType;
		this.menus = new ArrayList<>();
		this.horairesParJour = new HashMap<>();
		setHorairesParJour(horaireSemaine, horaireWeekend);
	}
	
	
    /**
     * Initialise les horaires d’ouverture du service pour chaque jour de la semaine.
     * Objectif : éviter de saisir manuellement les 7 jours pour chaque service.
     *
     * Si le jour est un samedi ou un dimanche, on applique l’horaire du week-end.
     * Sinon, on applique l’horaire de la semaine.
     *
     * Cette méthode est privée car elle ne doit être utilisée que lors de l’initialisation.
     */
	private void setHorairesParJour(String horaireSemaine, String horaireWeekend) {
		for (DayOfWeek jour : DayOfWeek.values()) {
			if (jour == DayOfWeek.SATURDAY || jour == DayOfWeek.SUNDAY) {
				horairesParJour.put(jour, horaireWeekend);
			} else {
				horairesParJour.put(jour, horaireSemaine);
			}
		}
	}
	
	
    /**
     * Retourne la map des horaires triés du lundi au dimanche.
     * 
     * Objectif : garantir un affichage cohérent et prévisible dans l’interface utilisateur.
     * Utilise un LinkedHashMap pour maintenir l’ordre après le tri.
     *
     * @return Map triée des horaires (lundi → dimanche)
     */
	public Map<DayOfWeek, String> getHorairesTries() {
	    return horairesParJour.entrySet().stream()
	        .sorted(Map.Entry.comparingByKey()) // tri du lundi au dimanche
	        .collect(Collectors.toMap(
	            Map.Entry::getKey,
	            Map.Entry::getValue,
	            (a, b) -> a,
	            LinkedHashMap::new
	        ));
	}
	
    /**
     * Accesseur pour connaître le type du service.
     * @return le type de service RESTAURANT ou CAFETERIA.
     */
	public TypeService getSonType() {
		return sonType;
	}

	
    /**
     * Retourne la liste des menus correspondant à une date donnée.
     *
     * Utilise les streams Java pour filtrer tous les menus dont la date correspond exactement à la date passée.
     * Cette méthode est utile pour afficher uniquement les menus du jour ou d’un jour sélectionné.
     *
     * @param date Date à filtrer
     * @return Liste des menus pour cette date
     */
	public List<ModeleMenu> getMenusParDate(LocalDate date) {
	    return menus.stream()
	            .filter(menu -> menu.getSaDate().equals(date))
	            .toList(); // Java 16+
	}

	
    /**
     * Retourne l’horaire d’ouverture du service pour le jour correspondant à la date passée.
     * @param date Date utilisée pour extraire le jour de la semaine
     * @return Horaire sous forme de chaîne (ex : "11h30 - 14h00")
     */
	public String getHorairePourJour(LocalDate date) {
	    return horairesParJour.get(date.getDayOfWeek());
	}

	
    /**
     * Getter brut pour accéder à tous les menus associés au service.
     * @return liste de tous les menus
     */
	public ArrayList<ModeleMenu> getMenus() {
		return menus;
	}

	
    /**
     * Ajoute un menu à la liste des menus proposés par le service.
     * @param menu Menu à ajouter
     */
	public void ajouterMenu(ModeleMenu menu) {
		menus.add(menu);
	}

	
    /**
     * Supprime un menu spécifique de la liste des menus proposés.
     * @param menu Menu à supprimer
     */
	public void supprimerMenu(ModeleMenu menu) {
		menus.remove(menu);
	}
	
	
    /**
     * Affiche dans la console les menus correspondant à un certain type de repas.
     * Cette méthode ne retourne pas de résultat, elle sert uniquement à l’affichage (à usage test/dev).
     *
     * @param critere Type de repas (ex : DEJEUNER, DINER)
     */
	public void filtrerMenu(TypeRepas critere) {
		for (ModeleMenu plat : menus) {
			if (plat.getSonType() == critere) {
				System.out.println(plat);
			}
		}
	}
	
	
    /** Change le nom du service de restauration. */
	public void setNom(String nom) {
		this.nom = nom;
	}

	
    /**
     * Associe ce service à une maison internationale (via son nom).
     * Le lien est fait uniquement par le nom (et non une référence directe), ce qui simplifie la sérialisation.
     * @param MaisonInter Objet représentant la maison internationale
     */
	public void setMaisonInternationale(ModeleMaisonInternationale MaisonInter) {
		this.nomMaisonInter = MaisonInter.getNom();
	}


    /** @return le nom du service de restauration */
	public String getNom() {
		return nom;
	}

	
    /**
     * Retourne le nom de la maison internationale associée à ce service.
     * Lève une exception si le service n’est associé à aucune maison.
     *
     * @return nom de la maison
     * @throws Exception si non associé à une maison
     */
	public String getMaisonInternationale() throws Exception {
	    if (this.nom == null) {
	        throw new Exception("Ce restaurant n'est associé à aucune maison Internationale.");
	    }
	    return this.nomMaisonInter;
	}


    /**
     * Méthode abstraite redondante avec getSonType() — peut être utilisée dans certains cas
     * pour forcer la redéfinition dans les sous-classes (au besoin).
     * @return le type de service
     */
	public  TypeService getTypeService() { // enum CAFETERIA / RESTAURANT
		return sonType;
	}


    /** Retourne la map complète des horaires du service (non triée). */
	public Map<DayOfWeek, String> getHorairesParJour() {
		return this.horairesParJour;
	}
	
	
    /**
     * Redéfinition de la méthode equals pour comparer deux services de restauration par leur nom.
     * Cela permet de garantir l’unicité logique d’un service par son identifiant naturel (le nom).
     *
     * @param obj Objet à comparer
     * @return vrai si les noms sont égaux
     */
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    ModeleRestauration modele = (ModeleRestauration) obj;
	    return nom != null ? nom.equals(modele.nom) : modele.nom == null;
	}

	
    /**
     * Redéfinition de hashCode pour être cohérent avec equals().
     * Important si on utilise ModeleRestauration comme clé de Map ou dans des Set.
     * @return valeur de hachage basée sur le nom
     */
	@Override
	public int hashCode() {
	    return nom != null ? nom.hashCode() : 0;
	}

	


}
