package mvc.vues.gestion;

import java.awt.GridLayout;

import javax.swing.*;

import mvc.controleurs.GestionControleur;
import mvc.modele.gestion.ModeleGestionAttente;

public class GestionVue extends JPanel
{
	static public final String ACTION_INSCRIRE = "INSCRIRE";
	
	// Déclaration des composants
	JLabel inscriptionLabel;
	JButton inscrireButton;
	JTextField nomElèveTextField;
	JTextField prenomElèveTextField;
	JButton effacerButton;
	JLabel nom;
	JLabel prénom;
	JLabel nationalité;
	JTextField nationalitéEtudiant;
	
	
	
	ModeleGestionAttente _modele;
	
	public GestionVue(ModeleGestionAttente modele)
	{
		_modele = modele;
		
		// Mettre en page
		setLayout(new GridLayout(10, 2));
		
		
		// Créer un "panneau de contrôle"
		inscriptionLabel = new JLabel("Inscription des Elèves: ");
		nom = new JLabel("nom: ");
		prénom = new JLabel("prénom: ");
		nationalité = new JLabel("nationalité: ");
	
		// Initialise les composants
		nomElèveTextField = new JTextField("Nom de l'élève ");
		prenomElèveTextField = new JTextField("Prénom de l'élève ");
		inscrireButton = new JButton("Inscrire");	
		effacerButton = new JButton("Effacer");
		nationalitéEtudiant = new JTextField("nationalité Etudiant ");
		
		
		
		
		
		add(inscriptionLabel);
		add(nomElèveTextField);
		add(prenomElèveTextField);
		add(inscrireButton);
		add(effacerButton);
		add(nom);
		add(prénom);
		add(nationalité);
		add(nationalitéEtudiant);
		
		// Mettre à jour l'affichage
		updateEtudiant();
	}
	
	public void updateEtudiant()
	{
		
	}
	
	public JTextField getNom() {
		return nomElèveTextField;
	}
	
	public JTextField getPrenom() {
		return prenomElèveTextField;
	}
	
	
	public void setEcouteur(GestionControleur controleur) {
		inscrireButton.addActionListener(controleur);
		
	}

}
