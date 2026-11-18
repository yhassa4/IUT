package mvc.controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import mvc.modele.gestion.ModeleGestionAttente;
import mvc.modele.utilisateur.ModeleEtudiant;
import mvc.vues.utilisateur.VueEtudiant;
import mvc.modele.ciup.ModeleCiteInternationale;
import mvc.modele.batiment.ModeleMaison;

/**
 * Contrôleur gérant les interactions entre la vue étudiant et le modèle de gestion.
 * Implémente le patron MVC pour la gestion des inscriptions d'étudiants.
 */
public class ControleurEtudiant implements ActionListener
{
	/** Action command pour l'inscription d'un étudiant */
	static public final String INSCRIRE_ACTION = "INSCRIRE";

	/** Modèle de gestion de la liste d'attente */
	private ModeleGestionAttente _modele;
	
	/** Vue pour l'interface utilisateur */
	private VueEtudiant _vue;
	
	/**
	 * Crée un nouveau contrôleur pour la gestion des étudiants.
	 * Initialise les liens entre le modèle et la vue.
	 * 
	 * @param modeleGestionAttente Le modèle de gestion des étudiants
	 * @param vue La vue associée à ce contrôleur
	 */
	public ControleurEtudiant(ModeleGestionAttente modeleGestionAttente, VueEtudiant vue)
	{
		_modele = modeleGestionAttente;
		_vue = vue;
		
		_vue.setEcouteur(this);
		updateList();
	}
	
	/**
	 * Gère les événements de l'interface utilisateur.
	 * Traite principalement l'inscription de nouveaux étudiants.
	 * 
	 * @param e L'événement déclenché par l'interface
	 */
	public void actionPerformed(ActionEvent e) 
	{		
		if (INSCRIRE_ACTION.equals(e.getActionCommand())) {
			String nom = _vue.getNom().getText();
			String prenom = _vue.getPrenom().getText();
			String nationalite = _vue.getNationalite().getText();
			String souhait = _vue.getSouhait().getText();
			
			// Vérification des champs obligatoires
			if (nom.isEmpty() || prenom.isEmpty() || nationalite.isEmpty()) {
				_vue.showError("Le nom, prénom et la nationalité sont obligatoires");
				return;
			}
			
			try {
				ModeleEtudiant etudiant = new ModeleEtudiant(nom, prenom, nationalite, souhait.isEmpty() ? null : souhait);
				ModeleCiteInternationale cite = _modele.getCite();
				
				try {
					if (cite.attribuerMaisonEtudiant(etudiant)) {
						System.out.println("Étudiant " + nom + " " + prenom + " assigné avec succès à " + etudiant.getMaison().getNom());
						_vue.clearFields();
						updateList();
					} else {
						_modele.ajouterEnAttente(etudiant);
						System.out.println("Étudiant " + nom + " " + prenom + " ajouté à la liste d'attente (aucune place disponible)");
						_vue.clearFields();
						updateList();
					}
				} catch (Exception ex) {
					// Si l'erreur contient le message de maison inexistante
					if (ex.getMessage() != null && ex.getMessage().contains("n'existe pas dans la cité")) {
						_vue.showError(ex.getMessage());
					} else {
						_vue.showError("Erreur lors de l'attribution de la maison: " + ex.getMessage());
					}
				}
				
			} catch (Exception ex) {
				_vue.showError(ex.getMessage());
			}
		}
	}
	
	/**
	 * Met à jour l'affichage des listes d'étudiants dans la vue.
	 * Récupère la liste d'attente et la liste des étudiants acceptés
	 * à partir du modèle et les transmet à la vue pour affichage.
	 */
	public void updateList()
	{
		ArrayList<ModeleEtudiant> listeAttente = _modele.getListeAttente();
		ArrayList<ModeleEtudiant> listeAcceptes = new ArrayList<>();
		
		try {
			ModeleCiteInternationale cite = _modele.getCite();
			for (ModeleMaison maison : cite.getListeMaisons()) {
				listeAcceptes.addAll(maison.getEtudiants());
			}
		} catch (Exception e) {
			System.err.println("Erreur lors de la récupération des étudiants acceptés: " + e.getMessage());
		}
		
		_vue.updateEtudiant(listeAttente, listeAcceptes);
	}
}
