package mvc.vues.service;

import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import mvc.controleurs.ControleurRestauration;

/**
 * Vue composite qui regroupe deux interfaces de services :
 * une pour les cafétérias et une pour les restaurants.
 * Permet de les afficher l'une au-dessus de l'autre dans un layout vertical.
 */
public class VueDoubleService extends JPanel implements InterfaceVueService {

	// Vue représentant l'ensemble des cafétérias (composant de type VueCafeteria)
	// Permet d'afficher dynamiquement la liste des services de type cafétéria
	private InterfaceVueService vueCafet;

	// Vue représentant l'ensemble des restaurants (composant de type VueRestaurant)
	// Permet d'afficher dynamiquement la liste des services de type restaurant
	private InterfaceVueService vueRestau;



	/**
	 * Constructeur principal.
	 *
	 * @param vueCafet  Vue des cafétérias
	 * @param vueRestau Vue des restaurants
	 */
	public VueDoubleService(InterfaceVueService vueCafet, InterfaceVueService vueRestau) {
		this.vueCafet = vueCafet;
		this.vueRestau = vueRestau;

		setLayout(new GridLayout(2, 1));	// disposition verticale
		add((Component) vueCafet);
		add((Component) vueRestau);
	}


	/**
	 * Applique le contrôleur aux deux vues internes.
	 */
	@Override
	public void setEcouteur(ControleurRestauration controleurRestauration) {
		// TODO Auto-generated method stub

	}


	/**
	 * Redessine les deux vues.
	 */
	public void redessiner() {
		this.vueCafet.redessiner();
		this.vueRestau.redessiner();
	}


	/**
	 * Affiche ou masque la vue complète.
	 */
	public boolean getVisibilityPanel() {
		return this.isVisible();
	}
	public void setVisibilityPanel(boolean visibileOrNot) {
		this.setVisible(visibileOrNot);
	}

	public JPanel getPanel() {
		return (JPanel)this;
	}

	public boolean getVisibility() {
		return this.getPanel().isVisible();
	}
	public void setVisibility(boolean visibileOrNot) {
		this.getPanel().setVisible(visibileOrNot);
	}



	/**
	 * Méthodes non utilisées dans le cas d'une vue composite.
	 * Elles sont requises par l'interface, mais non pertinentes ici.
	 */
	@Override
	public JButton getButtonDerouler() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setButtonText(String text) {
		// TODO Auto-generated method stub

	}


	@Override
	public JPanel getPanelListe() {
		// TODO Auto-generated method stub
		return null;
	}


}
