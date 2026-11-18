package mvc.controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mvc.modele.batiment.ModeleBatiment;
import mvc.modele.batiment.ModeleMaison;
import mvc.modele.ciup.ModeleCiteInternationale;
import mvc.vues.batiment.InterfaceVueBatiment;
import mvc.vues.batiment.VueMaison;

/**
 * Contrôleur gérant les interactions entre la vue des maisons et le modèle de la cité internationale.
 * Ce contrôleur gère :
 * - L'affichage/masquage de la liste des maisons
 * - La création de nouvelles maisons
 * - La suppression de maisons existantes
 * - L'affichage des détails d'une maison
 */
public class ControleurBatiment implements ActionListener {
    
    protected ModeleCiteInternationale modeleCite;
    protected VueMaison vueMaison;
    
    /**
     * Constructeur du contrôleur des maisons.
     * Initialise les références vers le modèle et la vue, et configure les écouteurs d'événements.
     *
     * @param modeleCite Le modèle de la cité internationale contenant les données des maisons
     * @param vueMaison La vue gérant l'interface graphique des maisons
     */
    public ControleurBatiment(ModeleCiteInternationale modeleCite, VueMaison vueMaison) {
        this.modeleCite = modeleCite;
        this.vueMaison = vueMaison;
        
        // Ajouter l'écouteur à la vue
        vueMaison.setEcouteur(this);
    }
    
    /**
     * Gère les événements provenant des boutons de la vue.
     * Les actions possibles sont :
     * - BOUTTON_MAISON : Afficher/masquer la liste des maisons
     * - BOUTTON_CREER : Ouvrir le formulaire de création d'une maison
     * - BOUTTON_RETIRER : Ouvrir le dialogue de suppression d'une maison
     *
     * @param e L'événement déclenché par l'action sur un bouton
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch (command) {
            case VueMaison.BOUTTON_MAISON:
                vueMaison.toggleVisibility();
                break;
            case VueMaison.BOUTTON_CREER:
                vueMaison.creerNouvelleMaison();
                break;
            case VueMaison.BOUTTON_RETIRER:
                vueMaison.retirerMaison();
                break;
        }
        
        vueMaison.redessiner();
    }
    
    /**
     * Affiche une boîte de dialogue contenant les détails d'une maison.
     * Les informations affichées comprennent :
     * - Le nom de la maison
     * - Le directeur
     * - La nationalité
     * - La localisation
     * - Le taux d'occupation (nombre d'étudiants/capacité)
     *
     * @param maison La maison dont on veut afficher les détails
     */
    protected void afficherDetailsMaison(ModeleMaison maison) {
        String message = String.format("Détails de la maison :\n" +
                "Nom : %s\n" +
                "Directeur : %s\n" +
                "Nationalité : %s\n" +
                "Localisation : %s\n" +
                "Occupation : %d/%d",
                maison.getNom(),
                maison.getDirecteur(),
                maison.getNationnalite(),
                maison.getLocalisation(),
                maison.getEtudiants().size(),
                maison.getNbChambre());
        
        JOptionPane.showMessageDialog(null, message, "Détails de " + maison.getNom(), 
                JOptionPane.INFORMATION_MESSAGE);
    }
} 