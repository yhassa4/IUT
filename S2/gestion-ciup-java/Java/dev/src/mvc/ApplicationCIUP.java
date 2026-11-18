package mvc;

import java.io.IOException;

import javax.swing.JFrame;

import mvc.controleurs.ControleurCiup;
import mvc.vues.VueCiup;
import serialisation.GestionSauvegardeCite;
import serialisation.GestionSauvegardeEtudiant;
import serialisation.GestionSauvegardeRestauration;

public class ApplicationCIUP 
{	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		// Supprimer les anciennes sauvegardes si nécessaire
		GestionSauvegardeCite.supprimerSauvegarde();
		GestionSauvegardeEtudiant.supprimerSauvegarde();
		GestionSauvegardeRestauration.supprimerSauvegarde();

		JFrame fenetre = new JFrame("CIUP - Gestion");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setSize(1600, 700);
		fenetre.setLocationRelativeTo(null);

		VueCiup vue = new VueCiup();
		ControleurCiup controleur = new ControleurCiup(vue);

		fenetre.add(vue);
		fenetre.setVisible(true);
	}
}