package mvc.vues.service;

import java.awt.Container;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mvc.controleurs.ControleurRestauration;
import mvc.modele.service.ModeleCafeteria;
import mvc.modele.service.ModeleRestauration;


/**
 * VueCafeteria est une vue spécialisée pour l'affichage des cafétérias.
 * Hérite de VueServiceRestauration, et permet l'affichage des informations spécifiques
 * aux ModeleCafeteria (tarif, self-service, etc.).
 *
 * Utilisée dans le cadre du patron MVC (couche Vue).
 */
public class VueCafeteria extends VueServiceRestauration implements InterfaceVueService{


	// Constante pour identifier le bouton dans le contrôleur
	public static final String BOUTTON_CAFET = "LISTECAFET";


	/**
	 * Constructeur — initialise la vue avec une liste de cafétérias.
	 *
	 * @param listeServices Liste des services de type cafétéria
	 */
	public VueCafeteria(List<ModeleRestauration > listeServices) {
		super("Cafeteria", listeServices);
		buttonDerouler.setActionCommand(ControleurRestauration.BOUTTON_CAFET);	
	}

	/**
	 * Attache le contrôleur aux composants interactifs de la vue.
	 *
	 * @param c Le contrôleur à associer
	 */
	@Override
	public void setEcouteur(ControleurRestauration c) {
		buttonDerouler.addActionListener(c);
	}


	/**
	 * Crée un composant graphique représentant un service de restauration.
	 * Spécialisation pour ModeleCafeteria.
	 *
	 * @param service Le service de restauration (doit être une cafétéria)
	 * @return JPanel contenant les infos affichées
	 */
	@Override
	protected JPanel creerComposantService(ModeleRestauration service) {
		JPanel panel = new JPanel();
		ModeleCafeteria cafet = (ModeleCafeteria) service;
		JLabel label = new JLabel(service.getNom() 
				+ " (Tarif : " + cafet.getTarifUnitaire() + "€, "
				+ (cafet.isSelfService() ? "Self-service" : "Service au comptoir") + ")");
		panel.add(label);

		return panel;
	}

	/**
	 * Renvoie le conteneur principal affichant la liste des cafétérias.
	 * Permet une meilleure compatibilité dans l’affichage graphique.
	 *
	 * @return Container affichant la liste
	 */
	@Override
	public Container getPanelListe() {
		return (JPanel)super.getPanelList();
	}

}
