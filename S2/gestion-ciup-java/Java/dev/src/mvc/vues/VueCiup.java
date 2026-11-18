package mvc.vues;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mvc.controleurs.ControleurBatiment;
import mvc.controleurs.ControleurCafeteria;
import mvc.controleurs.ControleurCiup;
import mvc.controleurs.ControleurEtudiant;
import mvc.controleurs.ControleurRestaurant;
import mvc.controleurs.ControleurRestauration;
import mvc.modele.batiment.ModeleMaison;
import mvc.modele.ciup.ModeleCiteInternationale;
import mvc.modele.gestion.ModeleGestionAttente;
import mvc.modele.service.ModeleService;
import mvc.modele.utilisateur.ModeleEtudiant;
import mvc.vues.batiment.VueMaison;
import mvc.vues.service.InterfaceVueService;
import mvc.vues.service.VueCafeteria;
import mvc.vues.service.VueDoubleService;
import mvc.vues.service.VueRestaurant;
import mvc.vues.utilisateur.VueEtudiant;

import serialisation.GestionSauvegardeCite;
import serialisation.GestionSauvegardeEtudiant;
import serialisation.GestionSauvegardeRestauration;

public class VueCiup extends JPanel {

	private static final long serialVersionUID = 1L;
	
	//-----------------------------
	// Attributs
	//-----------------------------
	private InterfaceVueService testVue;
	private VueEtudiant vueEtudiant;
	private VueMaison vueMaison;
	private ModeleGestionAttente modeleGestionAttente;
	private ModeleCiteInternationale modeleCite;
	private ControleurBatiment controleurBatiment;

	// Composants graphiques 
	private JButton buttonM;
	private JButton buttonE;
	private JButton buttonS;
	private JPanel cardPanel;
	private CardLayout cardLayout;

	//-----------------------------
	// Constructeur
	//-----------------------------
	public VueCiup() throws ClassNotFoundException, IOException {
		setLayout(new BorderLayout());

		// Initialiser la partie bâtiment
		initBatiment();
		
		// Initialiser la partie service
		initService();
		
		// Creer les boutons
		buttonM = new JButton("Liste Maisons");
		buttonE = new JButton("Liste Etudiants");
		buttonS = new JButton("Liste Services");

		// Préparer la gestion des clics sur les boutons
		buttonM.setActionCommand(mvc.controleurs.ControleurCiup.BOUTON_MAISON);
		buttonE.setActionCommand(mvc.controleurs.ControleurCiup.BOUTON_ETUDIANT);
		buttonS.setActionCommand(mvc.controleurs.ControleurCiup.BOUTON_SERVICE);

		// Mettre en page
		JPanel buttons = new JPanel(new GridLayout(1, 3));
		buttons.add(buttonM);
		buttons.add(buttonE);
		buttons.add(buttonS);
		
		add(buttons, BorderLayout.NORTH);
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);

		cardPanel.add(vueMaison, "MAISONS");
		cardPanel.add(vueEtudiant, "ETUDIANTS");
		cardPanel.add((JPanel) testVue, "SERVICES");
		cardPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0)); // top, left, bottom, right

		add(cardPanel, BorderLayout.CENTER);
		
		// Afficher la vue des maisons par défaut
		cardLayout.show(cardPanel, "MAISONS");
		
		// Mettre à jour l'affichage
		redessiner();
	}
	
	/**
	 * Initialise la partie bâtiment de l'application
	 */
	private void initBatiment() throws ClassNotFoundException, IOException {
		// Initialiser le modèle de gestion des étudiants
		modeleGestionAttente = new ModeleGestionAttente();
		
		// Initialiser le modèle de la cité
		modeleCite = new ModeleCiteInternationale("Cité Internationale", modeleGestionAttente);
		modeleGestionAttente.setCite(modeleCite);

		// Créer la vue étudiant
		vueEtudiant = new VueEtudiant(modeleGestionAttente);
		vueEtudiant.setVisible(false);  // Caché par défaut

		// Créer la vue maison
		vueMaison = new VueMaison(modeleCite);
		
		// Créer et initialiser les contrôleurs
		controleurBatiment = new ControleurBatiment(modeleCite, vueMaison);
		new ControleurEtudiant(modeleGestionAttente, vueEtudiant);
	}
	
	public void afficherVue(String nomCarte) {
		cardLayout.show(cardPanel, nomCarte);
	}

	//-----------------------------
	// Mettre à jour l'affichage de la vue 
	//-----------------------------
	public void redessiner() {
		this.revalidate();
		this.repaint();
	}

	//-----------------------------
	// Accesseurs pour les boutons
	//-----------------------------
	public JButton getButtonM() {
		return buttonM;
	}

	public void setButtonM(JButton buttonM) {
		this.buttonM = buttonM;
	}

	public JButton getButtonE() {
		return buttonE;
	}

	public void setButtonE(JButton buttonE) {
		this.buttonE = buttonE;
	}

	public JButton getButtonS() {
		return buttonS;
	}

	public void setButtonS(JButton buttonS) {
		this.buttonS = buttonS;
	}

	public VueEtudiant getVueEtudiant() {
		return vueEtudiant;
	}

	public VueMaison getVueMaison() {
		return vueMaison;
	}

	public ModeleGestionAttente getModeleGestionAttente() {
		return modeleGestionAttente;
	}

	public void initService() throws ClassNotFoundException, IOException {
		ModeleService modeleService;
		if (GestionSauvegardeRestauration.existeSauvegarde()) {
			modeleService = GestionSauvegardeRestauration.charger();
		} else {
			modeleService = new ModeleService();
			GestionSauvegardeRestauration.sauvegarder(modeleService);
		}

		InterfaceVueService vueResto = new VueRestaurant(modeleService.getListeRestaurant());
		InterfaceVueService vueCafet = new VueCafeteria(modeleService.getListeCafeteria());

		ControleurRestauration controleurRestauration = new ControleurRestaurant(modeleService, vueResto);
		ControleurRestauration controleurCafeteria = new ControleurCafeteria(modeleService, vueCafet);

		// Créer la vue composite
		VueDoubleService vueDouble = new VueDoubleService(vueCafet, vueResto);
		
		this.testVue = vueDouble;
	}
}
