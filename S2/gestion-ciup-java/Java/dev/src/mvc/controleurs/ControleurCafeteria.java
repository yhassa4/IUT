package mvc.controleurs;

import javax.swing.JOptionPane;

import mvc.modele.service.ModeleRestauration;
import mvc.modele.service.ModeleService;
import mvc.vues.service.InterfaceVueService;
import mvc.vues.service.VueDetailMenu;
import mvc.vues.service.VueDetailRestauration;

/**
 * Contrôleur dédié à la gestion des interactions avec les services de type Cafétéria.
 * Hérite de ControleurRestauration et implémente les actions spécifiques aux cafétérias.
 */
public class ControleurCafeteria extends ControleurRestauration{


	/**
	 * Constructeur : initialise le contrôleur avec son modèle et sa vue.
	 * Le listener sur le bouton déroulant est déjà géré par la classe mère.
	 *
	 * @param modeleService Le modèle contenant les services
	 * @param vueService    La vue correspondant aux cafétérias
	 */
	public ControleurCafeteria(ModeleService modeleService, InterfaceVueService vueService) {
		super(modeleService, vueService);
		// TODO Auto-generated constructor stub
	}


	// --------------------------------------------------------------------
	// Gestion du clic droit : Affichage des détails d’une cafétéria
	// --------------------------------------------------------------------
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




}
