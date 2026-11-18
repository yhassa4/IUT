package mvc.modele.service;

import java.io.Serializable;


/**
 * Classe concrète représentant une cafétéria dans la Cité Internationale.
 * 
 * Elle hérite de la classe abstraite ModeleRestauration et définit les spécificités propres aux cafétérias :
 * - fonctionnement en libre-service
 * - possibilité de vente à emporter
 * - tarif unitaire fixe pour chaque plat
 *
 * Cette classe permet donc de différencier une cafétéria d’un restaurant dans l’application.
 */
public class ModeleCafeteria extends ModeleRestauration implements Serializable{
	
    /** Indique si la cafétéria fonctionne en libre-service (par défaut : true) */
    private boolean selfService = true;

    /** Prix unitaire d’un plat dans la cafétéria (valeur par défaut : 2.50 €) */
    private double tarifUnitaire = 2.50;

    /** Indique si la cafétéria propose la vente à emporter (par défaut : true) */
    private boolean venteAEmporter = true;
	
    
    /**
     * Constructeur de la cafétéria, utilisant le constructeur parent.
     * Le type de service est automatiquement défini à CAFETERIA.
     *
     * @param nom Nom de la cafétéria
     * @param horaireSemaine Horaires du lundi au vendredi
     * @param horaireWeekend Horaires du week-end
     */
	public ModeleCafeteria(String nom, String horaireSemaine, String horaireWeekend) {
	    super(nom, TypeService.CAFETERIA, horaireSemaine, horaireWeekend);
	}
	
    /**
     * Définit si la vente à emporter est autorisée.
     * @param venteAEmporter true si vente à emporter possible, false sinon
     */
    public void setVenteAEmporter(boolean venteAEmporter) {
        this.venteAEmporter = venteAEmporter;
    }

    /**
     * Définit si la cafétéria fonctionne en self-service.
     * @param selfService true si self-service activé, false sinon
     */
    public void setSelfService(boolean selfService) {
        this.selfService = selfService;
    }

    /**
     * Définit le tarif unitaire des plats servis dans la cafétéria.
     * @param tarifUnitaire Prix unitaire en euros
     */
    public void setTarifUnitaire(double tarifUnitaire) {
        this.tarifUnitaire = tarifUnitaire;
    }

    /**
     * Retourne le tarif unitaire actuel.
     * @return Prix d’un plat
     */
    public double getTarifUnitaire() {
        return tarifUnitaire;
    }

    /**
     * Vérifie si la cafétéria propose la vente à emporter.
     * @return true si vente à emporter autorisée
     */
    public boolean isVenteAEmporter() {
        return venteAEmporter;
    }

    /**
     * Vérifie si la cafétéria fonctionne en self-service.
     * @return true si libre-service activé
     */
    public boolean isSelfService() {
        return selfService;
    }

}
