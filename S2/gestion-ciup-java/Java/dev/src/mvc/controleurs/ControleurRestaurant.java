package mvc.controleurs;

import javax.swing.JOptionPane;

import mvc.modele.service.ModeleRestauration;
import mvc.modele.service.ModeleService;
import mvc.vues.service.InterfaceVueService;
import mvc.vues.service.VueDetailMenu;
import mvc.vues.service.VueDetailRestauration;


/**
 * Contrôleur spécifique pour les restaurants universitaires.
 * Hérite de la classe abstraite ControleurRestauration, et fournit les implémentations concrètes
 * pour l’affichage des détails d’un restaurant et de ses menus.
 */
public class ControleurRestaurant extends ControleurRestauration{


	/**
	 * Constructeur : appelle le constructeur parent pour initialiser modèle et vue.
	 *
	 * @param modeleService Le modèle contenant les données des services de restauration
	 * @param vueService    La vue associée à ce contrôleur (VueRestaurant attendue)
	 */
	public ControleurRestaurant(ModeleService modeleService, InterfaceVueService vueService) {
		super(modeleService, vueService);
		// TODO Auto-generated constructor stub
	}


	// --------------------------------------------------------------------
	// Implémentation de la réaction au clic droit : affichage des infos
	// --------------------------------------------------------------------

	/**
	 * Affiche une fenêtre contenant les informations détaillées sur le restaurant sélectionné.
	 * Appelée lorsqu'on effectue un clic droit sur un panneau de restaurant.
	 *
	 * @param service Le restaurant sur lequel l’utilisateur a cliqué.
	 */
	@Override
	protected void afficherInformations(ModeleRestauration service) {
		if (service != null) {
			VueDetailRestauration vueDetail = new VueDetailRestauration(service);
			vueDetail.setVisible(true);
		} else {
			// Message si rien n’est sélectionné
			JOptionPane.showMessageDialog(null, "Aucun service de restauration sélectionné.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}


	// --------------------------------------------------------------------
	// Implémentation de la réaction au double-clic : afficher menus
	// --------------------------------------------------------------------

	/**
	 * Affiche une fenêtre listant les menus disponibles dans le restaurant sélectionné.
	 * Appelée lorsqu’on effectue un double clic sur un panneau de restaurant.
	 *
	 * @param service Le restaurant sélectionné
	 */
	@Override
	protected void afficherMenus(ModeleRestauration service) {
		if (service != null) {
			VueDetailMenu vueDetail = new VueDetailMenu(service, service.getMenus());
			vueDetail.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "Aucun service de restauration sélectionné.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}



}
