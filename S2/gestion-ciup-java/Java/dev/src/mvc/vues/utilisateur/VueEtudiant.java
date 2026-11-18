package mvc.vues.utilisateur;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import mvc.controleurs.ControleurEtudiant;
import mvc.modele.gestion.ModeleGestionAttente;
import mvc.modele.utilisateur.ModeleEtudiant;

/**
 * Vue représentant l'interface graphique de gestion des étudiants.
 * Cette vue permet :
 * - L'inscription de nouveaux étudiants
 * - L'affichage de la liste d'attente
 * - L'affichage des étudiants acceptés avec leur maison
 */
public class VueEtudiant extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	ModeleGestionAttente _modele;
	
	// Composants du formulaire d'inscription
	private JTextField nomField;
	private JTextField prenomField;
	private JTextField nationaliteField;
	private JTextField souhaitEtudiant;
	private JButton inscrireButton;
	private JButton effacerButton;
	
	// Modèles pour les listes d'affichage
	private DefaultListModel<String> listModelAttente;
	private DefaultListModel<String> listModelAcceptes;
	private JList<String> listeAttente;
	private JList<String> listeAcceptes;
	
	/**
	 * Crée une nouvelle vue pour la gestion des étudiants.
	 * Initialise l'interface graphique avec :
	 * - Un formulaire d'inscription
	 * - Une liste des étudiants en attente
	 * - Une liste des étudiants acceptés
	 * 
	 * @param modele Le modèle de gestion des étudiants
	 */
	public VueEtudiant(ModeleGestionAttente modele)
	{
		_modele = modele;
		
		setLayout(new BorderLayout());
		
		// Création du formulaire d'inscription
		JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
		formPanel.setBorder(BorderFactory.createTitledBorder("Inscription"));
		
		formPanel.add(new JLabel("Nom:"));
		nomField = new JTextField();
		formPanel.add(nomField);
		
		formPanel.add(new JLabel("Prénom:"));
		prenomField = new JTextField();
		formPanel.add(prenomField);
		
		formPanel.add(new JLabel("Nationalité:"));
		nationaliteField = new JTextField();
		formPanel.add(nationaliteField);
		
		formPanel.add(new JLabel("Souhait:"));
		souhaitEtudiant = new JTextField();
		formPanel.add(souhaitEtudiant);
		
		// Boutons d'action
		inscrireButton = new JButton("Inscrire");
		effacerButton = new JButton("Effacer");
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 0));
		buttonPanel.add(inscrireButton);
		buttonPanel.add(effacerButton);
		formPanel.add(buttonPanel);
		
		// Initialisation des listes
		listModelAttente = new DefaultListModel<>();
		listModelAcceptes = new DefaultListModel<>();
		
		listeAttente = new JList<>(listModelAttente);
		listeAcceptes = new JList<>(listModelAcceptes);
		
		// Configuration des panneaux de liste
		JPanel waitingListPanel = new JPanel(new BorderLayout());
		waitingListPanel.setBorder(BorderFactory.createTitledBorder("Liste d'attente"));
		waitingListPanel.add(new JScrollPane(listeAttente));
		
		JPanel acceptedListPanel = new JPanel(new BorderLayout());
		acceptedListPanel.setBorder(BorderFactory.createTitledBorder("Étudiants acceptés"));
		acceptedListPanel.add(new JScrollPane(listeAcceptes));
		
		// Organisation des composants
		JPanel listsPanel = new JPanel(new GridLayout(2, 1, 0, 10));
		listsPanel.add(waitingListPanel);
		listsPanel.add(acceptedListPanel);
		
		add(formPanel, BorderLayout.NORTH);
		add(listsPanel, BorderLayout.CENTER);
		
		// Configuration du bouton Effacer
		effacerButton.addActionListener(e -> clearFields());
	}
	
	/**
	 * Met à jour l'affichage des listes d'étudiants.
	 * 
	 * @param etudiantsAttente Liste des étudiants en attente
	 * @param etudiantsAcceptes Liste des étudiants acceptés avec leur maison
	 */
	public void updateEtudiant(List<ModeleEtudiant> etudiantsAttente, List<ModeleEtudiant> etudiantsAcceptes)
	{
		// Mise à jour de la liste d'attente
		listModelAttente.clear();
		if (etudiantsAttente != null) {
			for (ModeleEtudiant etudiant : etudiantsAttente) {
				listModelAttente.addElement(etudiant.getNom() + " " + etudiant.getPrenom() + " - " + etudiant.getNationalite());
			}
		}
		
		// Mise à jour de la liste des acceptés
		listModelAcceptes.clear();
		if (etudiantsAcceptes != null) {
			for (ModeleEtudiant etudiant : etudiantsAcceptes) {
				String maison = "";
				try {
					maison = " - " + etudiant.getMaison().getNom();
				} catch (Exception e) {
					// Ignore l'erreur si la maison n'est pas définie
				}
				listModelAcceptes.addElement(etudiant.getNom() + " " + etudiant.getPrenom() + " - " + etudiant.getNationalite() + maison);
			}
		}
	}
	
	/**
	 * @return Le champ de saisie du nom
	 */
	public JTextField getNom() {
		return nomField;
	}
	
	/**
	 * @return Le champ de saisie du prénom
	 */
	public JTextField getPrenom() {
		return prenomField;
	}
	
	/**
	 * @return Le champ de saisie de la nationalité
	 */
	public JTextField getNationalite() {
		return nationaliteField;
	}
	
	/**
	 * @return Le champ de saisie du souhait de maison
	 */
	public JTextField getSouhait() {
		return souhaitEtudiant;
	}
	
	/**
	 * Vide tous les champs du formulaire d'inscription.
	 * Replace le focus sur le champ du nom.
	 */
	public void clearFields() {
		nomField.setText("");
		prenomField.setText("");
		nationaliteField.setText("");
		souhaitEtudiant.setText("");
		nomField.requestFocus();
	}
	
	/**
	 * Affiche une boîte de dialogue d'erreur.
	 * 
	 * @param message Le message d'erreur à afficher
	 */
	public void showError(String message) {
		JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Configure l'écouteur d'événements pour le bouton d'inscription.
	 * 
	 * @param controleur Le contrôleur qui gère les événements
	 */
	public void setEcouteur(ControleurEtudiant controleur) {
		inscrireButton.setActionCommand(ControleurEtudiant.INSCRIRE_ACTION);
		inscrireButton.addActionListener(controleur);
	}
}
