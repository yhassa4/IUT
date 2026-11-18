package mvc.controleurs;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import mvc.modele.service.ModeleRestauration;
import mvc.modele.service.ModeleService;
import mvc.vues.service.InterfaceVueService;
import mvc.vues.service.VueCafeteria;
import mvc.vues.service.VueDetailMenu;
import mvc.vues.service.VueRestaurant;


/**
 * Classe abstraite qui joue le rôle de contrôleur dans le modèle MVC pour la gestion
 * des services de restauration (restaurants & cafétérias).
 *
 * Elle gère l'interaction entre :
 * - le modèle (ModeleService) qui contient les données des services de restauration
 * - la vue (InterfaceVueService), une interface qui permet d'afficher les services à l'utilisateur
 *
 * Cette classe implémente ActionListener pour écouter les actions utilisateurs sur les boutons principaux.
 */
public abstract class ControleurRestauration implements ActionListener{


	// Constantes représentant les commandes des boutons de la vue
	static public final String BOUTTON_RESTAU = "LISTERESTAU";
	static public final String BOUTTON_CAFET = "LISTECAFET";

	// Références vers le modèle et la vue associés à ce contrôleur
	protected ModeleService modeleService;
	protected InterfaceVueService vueService;



	/**
	 * Constructeur : initialise le modèle et la vue, et ajoute le contrôleur
	 * comme écouteur du bouton déroulant principal de la vue.
	 */
	public ControleurRestauration(ModeleService modeleService, InterfaceVueService vueService) {
		this.modeleService = modeleService;
		this.vueService = vueService;

		vueService.getButtonDerouler().addActionListener(this);

	}

	/**
	 * Méthode déclenchée lorsqu'une action utilisateur est détectée (clic sur bouton).
	 * Elle identifie quel bouton a été cliqué (restau ou cafet), et réagit en conséquence :
	 * - toggleVue : affiche ou masque la liste correspondante
	 * - initialiserEcouteurs : assigne des événements sur les éléments affichés
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(BOUTTON_RESTAU)) {
			toggleVue( "Restau' U");
			initialiserEcouteurs();
			vueService.redessiner();
		} else if(e.getActionCommand().equals(BOUTTON_CAFET)) {
			toggleVue( "Cafet' U");
			initialiserEcouteurs();
			vueService.redessiner();
		}

	}


	/**
	 * Parcourt tous les composants (ex: JPanels représentant les services) du panneau de liste
	 * et leur associe les événements utilisateurs (clic droit, double clic).
	 *
	 * Cette méthode détermine dynamiquement si la vue affiche une liste de restaurants ou de cafétérias.
	 */
	public void initialiserEcouteurs() {
		Component[] components = vueService.getPanelListe().getComponents();
		List<ModeleRestauration> listeCible;

		// Choix de la bonne liste selon le type concret de la vue
		if (vueService instanceof VueRestaurant) {
			listeCible = modeleService.getListeRestaurant();
		} else if (vueService instanceof VueCafeteria) {
			listeCible = modeleService.getListeCafeteria();
		} else {
			listeCible = Collections.emptyList();// Cas d’erreur ou vue inconnue
		}


		// Pour chaque composant (panel), on lie un gestionnaire d’événement
		for (int i = 0; i < components.length && i < listeCible.size(); i++) {
			if (components[i] instanceof JPanel) {
				JPanel panel = (JPanel) components[i];
				ModeleRestauration service = listeCible.get(i);
				ajouterGestionEvenements(panel, service);
				System.out.println("censer le faire");
			}
		}
	}




	/**
	 * Méthode utilitaire qui attache un MouseListener à un panel représentant un service.
	 * Elle gère deux cas :
	 * - clic droit : affiche un menu contextuel avec une option "Informations"
	 * - double clic : affiche les menus spécifiques du service sélectionné
	 */
	public void ajouterGestionEvenements(JPanel panelService, ModeleRestauration service) {
		panelService.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					// Création du menu contextuel
					JPopupMenu menu = new JPopupMenu();
					JMenuItem info = new JMenuItem("Informations");
					info.addActionListener(ev -> {
						afficherInformations(service);
						System.out.println("censer clique droit");
					});
					menu.add(info);
					menu.show(panelService, e.getX(), e.getY());
				} else if (e.getClickCount() == 2) {
					// Double clic -> afficher menus spécifiques
					System.out.println("censer double clique");
					afficherMenus(service);
				}
			}
		});
	}



	/**
	 * Méthode abstraite à implémenter dans les classes filles.
	 * Sert à afficher les informations d’un service donné.
	 */
	protected abstract void afficherInformations(ModeleRestauration service);

	/**
	 * Méthode abstraite à implémenter dans les classes filles.
	 * Sert à afficher les menus disponibles pour un service donné.
	 */
	protected void afficherMenus(ModeleRestauration service) {
		if (service != null) {
			VueDetailMenu vueDetail = new VueDetailMenu(service, service.getMenus());
			vueDetail.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "Aucun service de restauration sélectionné.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Gère l'affichage ou le masquage dynamique de la liste des services dans la vue.
	 * Change aussi le texte du bouton déroulant pour refléter l’état.
	 */
	protected void toggleVue(String nom) {
		boolean visible = !vueService.getVisibilityPanel();
		vueService.setVisibilityPanel(visible);
		vueService.setButtonText("Liste des " + nom + (visible ? " ▲" : " ▼"));
		System.out.println("bouton " + nom + (visible ? " activé" : " désactivé"));
	}

}
