package mvc.modele.service;


import java.io.Serializable;
import java.time.LocalDate;


/**
 * Classe représentant un menu proposé dans un service de restauration
 * (restaurant ou cafétéria) pour une date donnée.
 *
 * Elle hérite de ObservableArrayList, ce qui permet d’utiliser une logique de notification
 * lorsqu’elle est utilisée dans des contextes graphiques (JavaFX, etc.).
 *
 * Chaque menu contient :
 * - un nom
 * - des informations supplémentaires (par exemple : allergènes, plats)
 * - une date à laquelle il est servi
 * - un type de repas (petit déjeuner, déjeuner, dîner, etc.)
 */
public class ModeleMenu implements Serializable{
    /** Nom du menu (ex: "Menu végétarien", "Menu du jour", etc.) */
    private String nom;

    /** Informations complémentaires sur le menu (composition, allergènes, etc.) */
    private String informations;

    /** Date à laquelle ce menu est proposé */
    private LocalDate saDate;

    /** Type de repas (valeur de l'énumération TypeRepas) */
    private TypeRepas sonType;

    /**
     * Constructeur du menu.
     * 
     * @param nom Nom du menu
     * @param informations Informations associées (contenu, remarques, etc.)
     * @param date Date du jour où ce menu est servi
     * @param type Type de repas (défini par une énumération)
     */

	public ModeleMenu(String nom, String informations, LocalDate date, TypeRepas type) {
	    this.nom = nom;
	    this.informations = informations;
	    saDate = date;
	    sonType = type;
	}

	
	// ----- Accesseurs et mutateurs (getters / setters) -----
	
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getInformations() {
		return informations;
	}

	public void setInformations(String informations) {
		this.informations = informations;
	}

	public LocalDate getSaDate() {
		return saDate;
	}

	public void setSaDate(LocalDate saDate) {
		this.saDate = saDate;
	}

	public TypeRepas getSonType() {
		return sonType;
	}

	public void setSonType(TypeRepas sonType) {
		this.sonType = sonType;
	}
	
    /**
     * Représentation textuelle du menu, utile pour l’affichage console ou débogage.
     * @return Chaîne formatée contenant le nom, le type, la date et les informations du menu.
     */
	@Override
	public String toString() {
	    return nom + " [" + sonType + "] - " + saDate + "\nInformations : " + informations ;
	}


}
