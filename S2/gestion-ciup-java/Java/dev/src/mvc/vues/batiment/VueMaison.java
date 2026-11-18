package mvc.vues.batiment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import mvc.controleurs.ControleurBatiment;
import mvc.modele.batiment.*;
import mvc.modele.ciup.*;

/**
 * Vue représentant l'interface graphique de gestion des maisons de la cité universitaire.
 * Cette vue permet d'afficher la maison internationale en permanence en haut,
 * ainsi qu'une liste déroulante des autres maisons en dessous.
 */
public class VueMaison extends JPanel implements InterfaceVueBatiment {
	
	static public final String BOUTTON_MAISON = "LISTEMAISON";
	static public final String BOUTTON_CREER = "CREER_MAISON";
	static public final String BOUTTON_RETIRER = "RETIRER_MAISON";
	
	private ModeleCiteInternationale _modeleCite;
	private List<ModeleMaison> listeMaisons;
	private JPanel panelMaisons;
	private JScrollPane scrollPane;
	private JButton buttonDerouler;
	private JPanel panelListe;
	private JPanel panelGlobal;
	private JPanel panelMaisonInter;
	private JPanel panelBoutons;
	private JButton buttonCreer;
	private JButton buttonRetirer;
	
	/**
	 * Constructeur de la vue des maisons.
	 * Initialise l'interface graphique avec :
	 * - La maison internationale affichée en permanence en haut
	 * - Un bouton pour dérouler/enrouler la liste des autres maisons
	 * - Des boutons pour créer/retirer des maisons
	 * - Une liste déroulante des maisons (initialement cachée)
	 *
	 * @param modeleCite Le modèle de la cité internationale contenant les données des maisons
	 */
	public VueMaison(ModeleCiteInternationale modeleCite) {
		_modeleCite = modeleCite;
		this.listeMaisons = _modeleCite.getListeMaisons();
		
		setLayout(new BorderLayout());
		
		// Création du panel pour la maison internationale
		panelMaisonInter = new JPanel();
		panelMaisonInter.setLayout(new BorderLayout());
		panelMaisonInter.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		// Panel pour les boutons
		panelBoutons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonCreer = new JButton("Créer une maison");
		buttonRetirer = new JButton("Retirer une maison");
		buttonCreer.setActionCommand(BOUTTON_CREER);
		buttonRetirer.setActionCommand(BOUTTON_RETIRER);
		panelBoutons.add(buttonCreer);
		panelBoutons.add(buttonRetirer);
		
		// Initialisation des composants
		buttonDerouler = new JButton("Liste des Maisons ▼");
		buttonDerouler.setActionCommand(BOUTTON_MAISON);
		
		panelListe = new JPanel();
		panelListe.setLayout(new BorderLayout());
		panelListe.setVisible(false);
		
		// Panel pour contenir toutes les maisons
		panelMaisons = new JPanel();
		panelMaisons.setLayout(new BoxLayout(panelMaisons, BoxLayout.Y_AXIS));
		panelMaisons.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		// Scroll pane
		scrollPane = new JScrollPane(panelMaisons);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		// Configuration du panel global
		panelGlobal = new JPanel();
		panelGlobal.setLayout(new BorderLayout());
		
		// Ajout des composants dans l'ordre
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.add(buttonDerouler, BorderLayout.CENTER);
		headerPanel.add(panelBoutons, BorderLayout.EAST);
		
		// Mise à jour de l'affichage de la maison internationale
		afficherMaisonInternationale();
		
		// Configuration du panel de la liste
		panelListe.add(scrollPane, BorderLayout.CENTER);
		
		// Construction de la liste des autres maisons
		construireListe();
		
		// Assemblage final
		panelGlobal.add(headerPanel, BorderLayout.NORTH);
		panelGlobal.add(panelListe, BorderLayout.CENTER);
		
		// Ajout des panels principaux
		this.add(panelMaisonInter, BorderLayout.NORTH);
		this.add(panelGlobal, BorderLayout.CENTER);
		
		// S'assurer que les composants nécessaires sont visibles
		this.setVisible(true);
		panelMaisonInter.setVisible(true);
		panelGlobal.setVisible(true);
	}
	
	/**
	 * Affiche la maison internationale en haut de l'interface.
	 * Recherche la maison avec la nationalité "Internationale" dans la liste
	 * et crée un bandeau spécial pour l'afficher.
	 * Cette maison reste toujours visible, indépendamment de l'état de la liste déroulante.
	 */
	private void afficherMaisonInternationale() {
		panelMaisonInter.removeAll();
		ModeleMaison maisonInter = null;
		
		// Recherche de la maison internationale
		for (ModeleMaison maison : listeMaisons) {
			if (maison.getNationnalite().equalsIgnoreCase("Internationale")) {
				maisonInter = maison;
				break;
			}
		}
		
		if (maisonInter != null) {
			JPanel bandeau = creerBandeauMaison(maisonInter);
			panelMaisonInter.add(bandeau, BorderLayout.CENTER);
		}
		
		panelMaisonInter.revalidate();
		panelMaisonInter.repaint();
	}
	
	/**
	 * Construit la liste des maisons à afficher dans la partie déroulante.
	 * Exclut la maison internationale qui est déjà affichée en haut.
	 * Crée un bandeau pour chaque maison avec ses informations et ajoute
	 * un espace entre chaque bandeau pour une meilleure lisibilité.
	 */
	private void construireListe() {
		panelMaisons.removeAll();
		
		for (ModeleMaison maison : listeMaisons) {
			// Ne pas inclure la maison internationale dans la liste
			if (!maison.getNationnalite().equalsIgnoreCase("Internationale")) {
				JPanel bandeauMaison = creerBandeauMaison(maison);
				panelMaisons.add(bandeauMaison);
				panelMaisons.add(Box.createRigidArea(new Dimension(0, 10))); // Espace entre les bandeaux
			}
		}
		
		panelMaisons.revalidate();
		panelMaisons.repaint();
	}
	
	/**
	 * Crée un bandeau d'affichage pour une maison donnée.
	 * Le bandeau contient :
	 * - Le nom et la nationalité de la maison
	 * - Le taux d'occupation (nombre d'étudiants/capacité)
	 * - Un code couleur selon l'occupation (vert, orange, rouge)
	 * - Des effets de survol
	 * - Un menu contextuel accessible par clic droit
	 *
	 * @param maison La maison pour laquelle créer le bandeau
	 * @return JPanel Le bandeau configuré avec toutes les informations
	 */
	private JPanel creerBandeauMaison(ModeleMaison maison) {
		JPanel bandeau = new JPanel();
		bandeau.setLayout(new BorderLayout());
		bandeau.setPreferredSize(new Dimension(getWidth(), 80));
		bandeau.setBorder(BorderFactory.createCompoundBorder(
			new LineBorder(Color.GRAY),
			new EmptyBorder(10, 10, 10, 10)
		));
		
		// Panel gauche pour le nom et la nationalité
		JPanel infoPanel = new JPanel(new GridLayout(2, 1));
		JLabel nomLabel = new JLabel(maison.getNom());
		nomLabel.setFont(new Font("Arial", Font.BOLD, 14));
		JLabel nationaliteLabel = new JLabel("Nationalité : " + maison.getNationnalite());
		infoPanel.add(nomLabel);
		infoPanel.add(nationaliteLabel);
		
		// Panel droit pour l'occupation
		JPanel occupationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		int nbEtudiants = maison.getEtudiants().size();
		int capacite = maison.getNbChambre();
		JLabel occupationLabel = new JLabel(String.format("%d/%d places", nbEtudiants, capacite));
		occupationLabel.setFont(new Font("Arial", Font.BOLD, 12));
		
		// Couleur selon l'occupation
		if (nbEtudiants >= capacite) {
			occupationLabel.setForeground(Color.RED);
		} else if (nbEtudiants >= capacite * 0.8) {
			occupationLabel.setForeground(Color.ORANGE);
		} else {
			occupationLabel.setForeground(new Color(0, 150, 0)); // Vert
		}
		
		occupationPanel.add(occupationLabel);
		
		bandeau.add(infoPanel, BorderLayout.CENTER);
		bandeau.add(occupationPanel, BorderLayout.EAST);
		
		// Effet de survol
		bandeau.setBackground(Color.WHITE);
		bandeau.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				bandeau.setBackground(new Color(240, 240, 240));
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				bandeau.setBackground(Color.WHITE);
			}
		});
		
		// Menu contextuel
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem detailsItem = new JMenuItem("Afficher les détails");
		detailsItem.addActionListener(e -> afficherDetailsMaison(maison));
		popupMenu.add(detailsItem);
		
		bandeau.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				if (evt.isPopupTrigger()) {
					popupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
				}
			}
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				if (evt.isPopupTrigger()) {
					popupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
				}
			}
		});
		
		return bandeau;
	}
	
	/**
	 * Affiche une boîte de dialogue avec les détails complets d'une maison.
	 * Inclut toutes les informations disponibles :
	 * - Nom
	 * - Directeur
	 * - Nationalité
	 * - Localisation
	 * - Taux d'occupation
	 *
	 * @param maison La maison dont on veut afficher les détails
	 */
	private void afficherDetailsMaison(ModeleMaison maison) {
		String message = String.format("""
			Détails de la maison :
			Nom : %s
			Directeur : %s
			Nationalité : %s
			Localisation : %s
			Occupation : %d/%d places""",
			maison.getNom(),
			maison.getDirecteur(),
			maison.getNationnalite(),
			maison.getLocalisation(),
			maison.getEtudiants().size(),
			maison.getNbChambre());
		
		JOptionPane.showMessageDialog(this, message, 
			"Détails de " + maison.getNom(), 
			JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Ouvre une boîte de dialogue permettant de créer une nouvelle maison.
	 * Vérifie que le nom n'existe pas déjà et que les champs sont valides
	 * avant de créer la maison.
	 */
	public void creerNouvelleMaison() {
		JTextField nomField = new JTextField();
		JTextField nbChambreField = new JTextField();
		JTextField nationaliteField = new JTextField();
		JTextField directeurField = new JTextField();
		JTextField localisationField = new JTextField();
		
		Object[] message = {
			"Nom de la maison:", nomField,
			"Nombre de chambres:", nbChambreField,
			"Nationalité:", nationaliteField,
			"Directeur:", directeurField,
			"Localisation:", localisationField
		};
		
		int option = JOptionPane.showConfirmDialog(this, message, "Créer une nouvelle maison", 
			JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			
		if (option == JOptionPane.OK_OPTION) {
			try {
				String nom = nomField.getText().trim();
				int nbChambres = Integer.parseInt(nbChambreField.getText().trim());
				String nationalite = nationaliteField.getText().trim();
				String directeur = directeurField.getText().trim();
				String localisation = localisationField.getText().trim();
				
				// Vérifier si une maison avec ce nom existe déjà
				boolean exists = listeMaisons.stream()
					.anyMatch(m -> m.getNom().equalsIgnoreCase(nom));
				
				if (exists) {
					JOptionPane.showMessageDialog(this,
						"Une maison avec ce nom existe déjà.",
						"Erreur",
						JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				ModeleMaison nouvelleMaison = new ModeleMaison(nom, nbChambres, nationalite, 
					directeur, localisation, _modeleCite);
				_modeleCite.ajouterMaison(nouvelleMaison);
				
				// Mise à jour de l'affichage
				this.listeMaisons = _modeleCite.getListeMaisons();
				afficherMaisonInternationale();
				construireListe();
				
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this,
					"Le nombre de chambres doit être un nombre entier.",
					"Erreur",
					JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this,
					"Erreur lors de la création de la maison : " + e.getMessage(),
					"Erreur",
					JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Ouvre une boîte de dialogue permettant de retirer une maison existante.
	 * Vérifie que la maison existe avant de la supprimer.
	 */
	public void retirerMaison() {
		String nom = JOptionPane.showInputDialog(this,
			"Entrez le nom de la maison à retirer :",
			"Retirer une maison",
			JOptionPane.QUESTION_MESSAGE);
			
		if (nom != null && !nom.trim().isEmpty()) {
			ModeleMaison maisonARetirer = null;
			for (ModeleMaison maison : listeMaisons) {
				if (maison.getNom().equalsIgnoreCase(nom.trim())) {
					maisonARetirer = maison;
					break;
				}
			}
			
			if (maisonARetirer != null) {
				try {
					_modeleCite.supprimerMaison(maisonARetirer);
					this.listeMaisons = _modeleCite.getListeMaisons();
					afficherMaisonInternationale();
					construireListe();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this,
						"Erreur lors de la suppression de la maison : " + e.getMessage(),
						"Erreur",
						JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this,
					"Aucune maison trouvée avec ce nom.",
					"Erreur",
					JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Met à jour l'affichage complet de la vue.
	 * Actualise la liste des maisons depuis le modèle,
	 * rafraîchit l'affichage de la maison internationale
	 * et reconstruit la liste déroulante.
	 */
	@Override
	public void redessiner() {
		this.listeMaisons = _modeleCite.getListeMaisons();
		afficherMaisonInternationale();
		construireListe();
		if (panelGlobal != null) {
			panelGlobal.revalidate();
			panelGlobal.repaint();
		}
	}
	
	/**
	 * Configure les écouteurs d'événements pour les boutons de la vue.
	 *
	 * @param controleurBatiment Le contrôleur qui gère les actions sur les boutons
	 */
	@Override
	public void setEcouteur(ControleurBatiment controleurBatiment) {
		buttonDerouler.addActionListener(controleurBatiment);
		buttonCreer.addActionListener(controleurBatiment);
		buttonRetirer.addActionListener(controleurBatiment);
	}
	
	/**
	 * Retourne le conteneur principal de la vue.
	 *
	 * @return Container Le conteneur principal de la vue
	 */
	@Override
	public Container getPanel() {
		return this;
	}
	
	/**
	 * Bascule la visibilité de la liste déroulante des maisons.
	 * Met à jour le texte du bouton avec une flèche appropriée (▼ ou ▲)
	 * et force le rafraîchissement de l'affichage.
	 */
	public void toggleVisibility() {
		boolean visible = !panelListe.isVisible();
		panelListe.setVisible(visible);
		buttonDerouler.setText("Liste des Maisons " + (visible ? "▲" : "▼"));
		
		// Forcer le rafraîchissement
		if (panelGlobal != null) {
			panelGlobal.revalidate();
			panelGlobal.repaint();
		}
	}
}
