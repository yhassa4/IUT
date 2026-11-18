package mvc.vues.service;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import mvc.modele.service.ModeleMenu;
import mvc.modele.service.ModeleRestauration;
import mvc.modele.service.TypeRepas;

/**
 * Classe VueDetailMenu
 * Fenêtre permettant d'afficher les détails des menus d'un service de restauration donné.
 * Elle offre aussi la possibilité de filtrer les menus par type de repas (Vegan, Végétarien, Classique).
 */
public class VueDetailMenu extends JFrame {

	// Référence vers le modèle du menu (non utilisé ici mais potentiellement utile)
	ModeleMenu modeleMenu;

	// Composant Swing affichant la liste des noms de menus (partie gauche)
	JList<String> listeNomMenu;

	// Zone de texte pour afficher les informations détaillées du menu sélectionné
	JTextArea informationsMenu;

	/**
	 * Constructeur principal de la vue
	 * @param restau Le service de restauration auquel appartiennent les menus
	 * @param menusOriginaux La liste complète des menus à afficher
	 */
	public VueDetailMenu(ModeleRestauration restau, List<ModeleMenu> menusOriginaux) {
		// Configuration de la fenêtre
		setTitle("Détail des menus de " + restau.getNom());
		setSize(600, 350);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Format de date pour l'affichage des dates de menus
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		// Copie de la liste originale pour permettre le filtrage sans perte des données d'origine
		List<ModeleMenu> menusFiltres = new ArrayList<>(menusOriginaux);

		// Panneau principal contenant tous les éléments de la fenêtre
		JPanel mainPanel = new JPanel(new BorderLayout());

		// === Partie haute (NORTH) : menu déroulant de filtrage par type de repas ===
		JComboBox<TypeRepas> comboType = new JComboBox<>(TypeRepas.values());
		comboType.insertItemAt(null, 0);  // Ajout d'une option "Aucun filtre"
		comboType.setSelectedIndex(0);    // Sélection par défaut sur "Tous les types"
		JPanel topPanel = new JPanel();
		topPanel.add(new JLabel("Filtrer par type :"));
		topPanel.add(comboType);
		mainPanel.add(topPanel, BorderLayout.NORTH);

		// === Partie gauche (WEST) : liste des noms de menus ===
		DefaultListModel<String> listModel = new DefaultListModel<>();
		for (ModeleMenu m : menusFiltres) {
			listModel.addElement(m.getNom());  // Ajout des noms de menus à la liste
		}

		listeNomMenu = new JList<>(listModel);
		JScrollPane scrollGauche = new JScrollPane(listeNomMenu);
		scrollGauche.setPreferredSize(new Dimension(180, 300)); // Largeur fixe
		mainPanel.add(scrollGauche, BorderLayout.WEST);

		// === Partie droite (CENTER) : affichage des informations détaillées ===
		JPanel panelInfos = new JPanel();
		panelInfos.setLayout(new BoxLayout(panelInfos, BoxLayout.Y_AXIS));
		panelInfos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		JLabel titre = new JLabel("Menu proposé par : " + restau.getNom(), SwingConstants.CENTER);
		titre.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		panelInfos.add(titre);
		panelInfos.add(Box.createRigidArea(new Dimension(0, 10))); // Espacement vertical

		// Composants d'affichage des propriétés du menu sélectionné
		JLabel nomMenu = new JLabel();
		JLabel type = new JLabel();
		JLabel infos = new JLabel();
		JLabel date = new JLabel();

		// Alignement des composants à gauche
		nomMenu.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		type.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		infos.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		date.setAlignmentX(JLabel.LEFT_ALIGNMENT);

		// Ajout des composants dans le panneau d'infos
		panelInfos.add(nomMenu);
		panelInfos.add(type);
		panelInfos.add(infos);
		panelInfos.add(date);

		// Ajout du panneau de droite au panneau principal
		mainPanel.add(panelInfos, BorderLayout.CENTER);

		// Ajout final du panneau principal à la fenêtre
		add(mainPanel);

		// === Listener : sélection dans la liste de noms ===
		// Affiche les détails correspondants au menu sélectionné
		listeNomMenu.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int index = listeNomMenu.getSelectedIndex();
				if (index >= 0 && index < menusFiltres.size()) {
					ModeleMenu selected = menusFiltres.get(index);
					nomMenu.setText("Nom du menu : " + selected.getNom());
					type.setText("Type de repas : " + selected.getSonType().toString());
					infos.setText("Informations : " + selected.getInformations());
					date.setText("Date : " + selected.getSaDate().format(formatter));
				}
			}
		});

		// === Listener : changement de sélection dans la combo box de filtrage ===
		comboType.addActionListener(e -> {
			TypeRepas selectedType = (TypeRepas) comboType.getSelectedItem();

			// Mise à jour de la liste filtrée
			menusFiltres.clear();
			listModel.clear();

			for (ModeleMenu m : menusOriginaux) {
				if (selectedType == null || m.getSonType() == selectedType) {
					menusFiltres.add(m);
					listModel.addElement(m.getNom());
				}
			}

			// Réinitialisation de la sélection et de l'affichage
			if (!menusFiltres.isEmpty()) {
				listeNomMenu.setSelectedIndex(0);
			} else {
				nomMenu.setText("");
				type.setText("");
				infos.setText("");
				date.setText("");
			}
		});

		// Sélection initiale du premier menu (s'il en existe)
		if (!menusFiltres.isEmpty()) {
			listeNomMenu.setSelectedIndex(0);
		}
	}
}
