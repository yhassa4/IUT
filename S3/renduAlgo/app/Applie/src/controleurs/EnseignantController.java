package controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import Appli.main;
import Base.*;

public class EnseignantController {

    @FXML private Label lblTotalEtudiants, lblTotalGroupes, lblTotalPromos, lblMoyGroup;
    @FXML private TableView<Etudiant> tableEtudiants;
    @FXML private TableColumn<Etudiant, String> colNom, colPrenom, colMail, colGroupe, colMoyenne;
    @FXML private ComboBox<String> comboPromo, comboGroupe;
    @FXML private NavBarController navBarController;

    private ObservableList<Etudiant> listEtuE = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Liaison des colonnes simples (Nom, Prénom, Mail)
        colNom.setCellValueFactory(new PropertyValueFactory<>("nomEtu"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenomEtu"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("mailProEtu"));
        
        // Affichage combiné du Groupe (TD / TP)
        colGroupe.setCellValueFactory(cellData -> {
            Etudiant e = cellData.getValue();
            if (e == null || e.getGroup() == null) return new SimpleStringProperty("-");
            Group g = e.getGroup();
            String td = g.issetGroupTD() ? g.getGroupTD().getNomGroupTD() : "";
            String tp = g.issetGroupTP() ? g.getGroupTP().getNomGroupTP() : "";
            
            if (!td.isEmpty() && !tp.isEmpty()) return new SimpleStringProperty(td + " / " + tp);
            return new SimpleStringProperty(td.isEmpty() ? tp : td);
        });

        // Affichage de la moyenne formatée
        colMoyenne.setCellValueFactory(cellData -> {
            float moy = cellData.getValue().GetMoyenneGenerale();
            return new SimpleStringProperty(String.format("%.2f", moy));
        });

        chargerDonnees();

        // Gestion du filtrage lors du clic sur les ComboBox
        comboPromo.setOnAction(e -> appliquerFiltre());
        comboGroupe.setOnAction(e -> {
            appliquerFiltre();
            reloadMoy();
        });

        // Mise à jour de la barre de navigation selon le rôle
        Enseignant cUser = (Enseignant) main.getCurrentUser();
        if (!"ENSEIGNANT".equals(cUser.getRole())) {
            navBarController.updateNav(2);
        }
    }

    private void chargerDonnees() {
        Enseignant cUser = (Enseignant) main.getCurrentUser();
        
        // Récupère les noms des groupes gérés par cet enseignant
        ArrayList<String> nomsMesGroupes = new ArrayList<>();
        for (IGroup g : cUser.getGroup()) {
            if (g != null) {
                if (g instanceof GroupTD) nomsMesGroupes.add(((GroupTD) g).getNomGroupTD());
                if (g instanceof GroupTP) nomsMesGroupes.add(((GroupTP) g).getNomGroupTP());
            }
        }

        Set<String> nomsPromos = new HashSet<>();
        Set<String> nomsGroupesFiltre = new HashSet<>();
        ArrayList<Etudiant> listeTemporaire = new ArrayList<>();

        // Parcourt tous les étudiants pour ne garder que ceux du prof
        int tailleArbre = Etudiant.ListEtu.length();
        for (int i = 0; i < tailleArbre; i++) {
            Etudiant e = Etudiant.ListEtu.get(i);
            
            if (e != null && e.getGroup() != null) {
                boolean estEtudiant = false;

                // Vérifie si l'étudiant est dans un des TD ou TP du prof
                if (e.getGroup().issetGroupTD()) {
                    String nomTD = e.getGroup().getGroupTD().getNomGroup();
                    if (nomsMesGroupes.contains(nomTD)) {
                        estEtudiant = true;
                        nomsGroupesFiltre.add(nomTD);
                    }
                }
                if (!estEtudiant && e.getGroup().issetGroupTP()) {
                    String nomTP = e.getGroup().getGroupTP().getNomGroup();
                    if (nomsMesGroupes.contains(nomTP)) {
                        estEtudiant = true;
                        nomsGroupesFiltre.add(nomTP);
                    }
                }

                if (estEtudiant) {
                    listeTemporaire.add(e);
                    if (e.getPromotion() != null) nomsPromos.add(e.getPromotion().getNomPromo());
                }
            }
        }

        // Met à jour la table et les filtres (Promo/Groupe)
        listEtuE.setAll(listeTemporaire);
        tableEtudiants.setItems(listEtuE);
        comboPromo.setItems(FXCollections.observableArrayList(nomsPromos).sorted());
        comboGroupe.setItems(FXCollections.observableArrayList(nomsGroupesFiltre).sorted());
        lblTotalEtudiants.setText(String.valueOf(listEtuE.size()));
    }

    private void reloadMoy() {
        String groupeSelectionne = comboGroupe.getValue();
        if (groupeSelectionne == null || groupeSelectionne.isEmpty()) {
            lblMoyGroup.setText("0.00");
            return;
        }

        float somme = 0;
        int cpt = 0;

        // Calcule la moyenne des étudiants appartenant au groupe sélectionné
        for (Etudiant etu : listEtuE) {
            boolean appartient = false;
            if (etu.getGroup() != null) {
                if (etu.getGroup().issetGroupTD() && etu.getGroup().getGroupTD().getNomGroupTD().equals(groupeSelectionne)) appartient = true;
                else if (etu.getGroup().issetGroupTP() && etu.getGroup().getGroupTP().getNomGroupTP().equals(groupeSelectionne)) appartient = true;
            }

            if (appartient) {
                somme += etu.GetMoyenneGenerale();
                cpt++;
            }
        }
        lblMoyGroup.setText(cpt > 0 ? String.format("%.2f", somme / cpt) : "0.00");
    }
    
    private void appliquerFiltre() {
        String promoFiltre = comboPromo.getValue();
        String groupeFiltre = comboGroupe.getValue();

        // Filtre la liste affichée selon la promo et le groupe choisis
        ObservableList<Etudiant> listeFiltree = listEtuE.filtered(e -> {
            boolean correctPromo = (promoFiltre == null || promoFiltre.isEmpty()) || 
                                   (e.getPromotion() != null && e.getPromotion().getNomPromo().equals(promoFiltre));

            boolean correctGroupe = (groupeFiltre == null || groupeFiltre.isEmpty()) || 
                                    (e.getGroup().issetGroupTD() && e.getGroup().getGroupTD().getNomGroupTD().equals(groupeFiltre)) ||
                                    (e.getGroup().issetGroupTP() && e.getGroup().getGroupTP().getNomGroupTP().equals(groupeFiltre));

            return correctPromo && correctGroupe;
        });

        tableEtudiants.setItems(listeFiltree);
    }

    @FXML
    private void reinitialiserFiltres() {
        // Remet à zéro les ComboBox et la table
        comboPromo.setValue(null);
        comboGroupe.setValue(null);
        tableEtudiants.setItems(listEtuE);
        lblMoyGroup.setText("0.00");
    }
}