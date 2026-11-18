package mvc.vues.service;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JButton;
import mvc.controleurs.ControleurRestauration;

public interface InterfaceVueService {

	/**
	 * Redessine la vue (rafraîchissement graphique).
	 */
	void redessiner();

	/**
	 * Définit le contrôleur associé à cette vue.
	 * @param controleurRestauration le contrôleur à associer
	 */
	void setEcouteur(ControleurRestauration controleurRestauration);

	/**
	 * Retourne le bouton utilisé pour dérouler ou rétracter la liste.
	 */
	JButton getButtonDerouler();

	/**
	 * Retourne la visibilité actuelle du panneau.
	 */
	boolean getVisibilityPanel();

	/**
	 * Définit la visibilité du panneau (liste déroulante).
	 * @param visibleOrNot true si visible, false sinon
	 */
	void setVisibilityPanel(boolean visibleOrNot);

	/**
	 * Met à jour le texte du bouton déroulant.
	 * @param text le texte à afficher
	 */
	void setButtonText(String text);

	/**
	 * Définit la visibilité globale de la vue.
	 * @param visible true si visible, false sinon
	 */
	void setVisibility(boolean visible);

	/**
	 * Retourne la visibilité globale de la vue.
	 */
	boolean getVisibility();

	/**
	 * Retourne le panneau principal de la vue.
	 */
	Component getPanel();

	/**
	 * Retourne le conteneur contenant la liste des éléments de restauration.
	 */
	Container getPanelListe();
}

