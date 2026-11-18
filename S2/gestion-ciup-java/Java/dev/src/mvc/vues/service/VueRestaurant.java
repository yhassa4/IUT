package mvc.vues.service;


import java.awt.Container;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import mvc.controleurs.ControleurRestauration;
import mvc.modele.service.ModeleRestaurant;
import mvc.modele.service.ModeleRestauration;


/**
 * Vue spécifique aux restaurants universitaires.
 * Hérite de VueServiceRestauration et personnalise l’affichage pour les restaurants.
 */
public class VueRestaurant extends VueServiceRestauration implements InterfaceVueService{

	// Constante pour identifier le bouton dans le contrôleur
	static public final String INSCRIRE_ACTION = "INSCRIRE";

	/**
	 * Constructeur pour initialiser la vue avec la liste des services de type restaurant.
	 *
	 * @param listeServices Liste des restaurants à afficher
	 */
	public VueRestaurant( List<ModeleRestauration> listeServices) {
		super("Restaurant", listeServices);
		buttonDerouler.setActionCommand(ControleurRestauration.BOUTTON_RESTAU);
	}


	/**
	 * Ajoute un contrôleur à la vue (pour le bouton déroulant).
	 *
	 * @param c Le contrôleur à associer
	 */
	@Override
	public void setEcouteur(ControleurRestauration c) {
		buttonDerouler.addActionListener(c);
	}


	/**
	 * Crée le composant Swing représentant un restaurant individuel.
	 *
	 * @param service Instance de ModeleRestauration (restaurant ici)
	 * @return JPanel contenant les informations du restaurant
	 */
	@Override
	protected JPanel creerComposantService(ModeleRestauration service) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel(service.getNom() + " (Capacité : " + ((ModeleRestaurant) service).getCapacite() + ")");
		panel.add(label);


		return panel;
	}


	/**
	 * Retourne le panneau contenant la liste des restaurants affichés.
	 *
	 * @return Le JPanel contenant tous les composants restaurant
	 */
	@Override
	public Container getPanelListe() {
		return (JPanel)super.getPanelList();
	}


}
