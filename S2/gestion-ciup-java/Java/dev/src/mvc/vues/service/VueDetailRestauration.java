package mvc.vues.service;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.DayOfWeek;

import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mvc.modele.service.ModeleCafeteria;
import mvc.modele.service.ModeleRestaurant;
import mvc.modele.service.ModeleRestauration;

/**
 * Classe VueDetailRestauration
 * Cette fenêtre permet d’afficher tous les détails d’un service de restauration (restaurant ou cafétéria),
 * en adaptant dynamiquement les informations selon le type du service.
 */
public class VueDetailRestauration extends JFrame {

	// Référence vers le service de restauration concerné (restaurant ou cafétéria)
	ModeleRestauration service;

	/**
	 * Constructeur de la vue de détail
	 * @param resto Le modèle du service de restauration dont on veut afficher les détails
	 */
	public VueDetailRestauration(ModeleRestauration resto) {
		// Titre de la fenêtre = "Détail - [nom du service]"
		super("Détail - " + resto.getNom());

		// Stockage local du service pour d'éventuelles extensions
		service = resto;

		// Panneau principal, en layout vertical
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // marges internes

		// === Informations communes à tous les types de services ===
		panel.add(new JLabel("Nom : " + resto.getNom()));

		// Espacement vertical visuel
		panel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Titre de la section des informations spécifiques
		panel.add(new JLabel("Informations spécifiques :"));

		// === Affichage conditionnel selon le type de service ===

		// Si c'est un restaurant, on affiche la capacité et la réservation
		if (resto instanceof ModeleRestaurant r) {
			panel.add(new JLabel("Réservation obligatoire : " + (r.isReservationObligatoire() ? "Oui" : "Non")));
			panel.add(new JLabel("Capacité : " + r.getCapacite()));
		}
		// Si c'est une cafétéria, on affiche ses options spécifiques
		else if (resto instanceof ModeleCafeteria c) {
			panel.add(new JLabel("Self-service : " + (c.isSelfService() ? "Oui" : "Non")));
			panel.add(new JLabel("Tarif unitaire : " + c.getTarifUnitaire() + " €"));
			panel.add(new JLabel("Vente à emporter : " + (c.isVenteAEmporter() ? "Oui" : "Non")));
		}

		// Type général du service (enum TypeService)
		panel.add(new JLabel("Type de service : " +
				(resto.getSonType() != null ? resto.getSonType().toString() : "N/A")));

		// === Affichage des horaires du service ===
		Map<DayOfWeek, String> horaires = resto.getHorairesTries();
		if (!horaires.isEmpty()) {
			panel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacement vertical
			panel.add(new JLabel("Horaires d'ouverture :"));

			// Affichage jour par jour
			for (Map.Entry<DayOfWeek, String> entry : horaires.entrySet()) {
				panel.add(new JLabel(entry.getKey() + " : " + entry.getValue()));
			}
		}

		// === Finalisation de la fenêtre ===
		// Encapsule le panneau dans une JScrollPane (utile si beaucoup d'informations)
		JScrollPane scrollPane = new JScrollPane(panel);
		add(scrollPane, BorderLayout.CENTER);

		// Taille par défaut de la fenêtre
		setSize(400, 500);

		// Centre la fenêtre à l’écran
		setLocationRelativeTo(null);
	}
}

