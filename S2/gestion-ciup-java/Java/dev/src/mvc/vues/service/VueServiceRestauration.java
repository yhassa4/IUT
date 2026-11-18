package mvc.vues.service;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import mvc.modele.service.ModeleRestauration;
import mvc.modele.service.ModeleService;



/**
 * Classe abstraite représentant une vue générique de services de restauration.
 * Fournit une structure commune pour l'affichage des services tels que les restaurants
 * et cafétérias dans une interface graphique Swing.
 *
 * Les sous-classes doivent implémenter la méthode creerComposantService pour définir
 * l'apparence de chaque service.
 */
public abstract class VueServiceRestauration extends JPanel implements InterfaceVueService {

	// Bouton principal permettant d'afficher ou cacher la liste
	protected JButton buttonDerouler;

	// Contient les éléments graphiques représentant les services
	private JPanel panelListe;

	// Panel global englobant bouton + liste
	private JPanel panelGlobal;

	// Mapping entre un service et son composant graphique associé
	protected Map<ModeleRestauration, JPanel> mapServiceToPanel = new HashMap<>();

	// Nom du type de service (e.g., "Cafeteria", "Restaurant")
	private String nomService;

	// Liste des services à afficher
	protected List<ModeleRestauration> listeServices;

	/**
	 * Constructeur principal.
	 *
	 * @param nomService Le nom du service ("Restaurant" ou "Cafeteria")
	 * @param listeServices La liste des objets de type ModeleRestauration
	 */
	public VueServiceRestauration(String nomService, List<ModeleRestauration > listeServices) {
		this.nomService = nomService;
		this.listeServices = listeServices;

		// Initialisation du bouton d’action
		buttonDerouler = new JButton("Liste des " + nomService + " ▼");

		// Création et configuration de la liste
		panelListe = new JPanel();
		panelListe.setLayout(new GridLayout(0,5)); // disposition en grille
		panelListe.setVisible(false);

		construireListe(); // construction des composants à partir de la liste


		// Organisation des éléments dans le panel global
		panelGlobal = new JPanel();
		panelGlobal.setLayout(new BorderLayout());
		panelGlobal.add(buttonDerouler, BorderLayout.NORTH);
		panelGlobal.add(panelListe, BorderLayout.CENTER);

		// Mise en place du panel principal
		this.setLayout(new BorderLayout());
		this.add(panelGlobal, BorderLayout.CENTER);

	}

	/**
	 * Construit ou reconstruit l'affichage graphique des services à partir de la liste.
	 */
	protected void construireListe() {
		panelListe.removeAll();		// Nettoyage
		mapServiceToPanel.clear();  // Réinitialisation du mapping

		for (ModeleRestauration s : listeServices) {
			JPanel comp = creerComposantService(s);	// génération d'un panel spécifique
			mapServiceToPanel.put(s, comp);			// association dans la map
			panelListe.add(comp);					// ajout au panel principal
		}

		panelListe.revalidate();
		panelListe.repaint();
	}

	public Map<ModeleRestauration, JPanel> getMapServiceToPanel() {
		return mapServiceToPanel;
	}


	/**
	 * Méthode abstraite que les sous-classes doivent implémenter.
	 * Elle doit retourner un composant Swing (ex: JPanel, JLabel custom)
	 * qui représente un service et sur lequel on pourra détecter clic/double clic.
	 */
	protected abstract JPanel creerComposantService(ModeleRestauration service);


	// Permet de redessiner complètement l'interface globale
	public void redessiner() {
		panelGlobal.revalidate();
		panelGlobal.repaint();
	}


	// Accesseurs utilitaires


	public JButton getButtonDerouler() {
		return buttonDerouler;
	}
	public boolean getVisibilityPanel() {
		return this.panelListe.isVisible();
	}
	public void setVisibilityPanel(boolean visibileOrNot) {
		this.panelListe.setVisible(visibileOrNot);
	}
	public void setButtonText(String text) {
		this.buttonDerouler.setText(text);
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
	public JPanel getPanelList() {
		return (JPanel)this.panelListe;
	}
}
