package controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import Appli.main;
import Base.Etudiant;

public class GroupesController {

    // Déclaration des éléments graphiques liés au fichier FXML
    @FXML private TableView<Etudiant> tableEtudiant;
    @FXML private TableColumn<Etudiant, String> colNom;
    @FXML private TableColumn<Etudiant, String> colPrenom;
    @FXML private TableColumn<Etudiant, String> colGenre;
    @FXML private TableColumn<Etudiant, String> colBac;
    @FXML private TableColumn<Etudiant, String> colMail;
    @FXML private TableColumn<Etudiant, String> colGroupe;
    
    @FXML private ComboBox<String> groupeCombo;
    @FXML private Label lblCompteur;
    @FXML private NavBarController navBarController;

    // Liste pour stocker tous les étudiants récupérés
    private ObservableList<Etudiant> tousLesEtudiants = FXCollections.observableArrayList();
    
    @FXML
    public void initialize() {    
        
        // Récupère l'utilisateur connecté pour identifier ses groupes
        Etudiant cUser = (Etudiant)main.getCurrentUser(); 
        ArrayList<String> options = new ArrayList<>();
        
        // Ajoute le nom du groupe TD de l'utilisateur s'il existe
        if(cUser != null && cUser.getGroup() != null) {
            if(cUser.getGroup().issetGroupTD()){
                String nomTD = cUser.getGroup().getGroupTD().getNomGroupTD();
                if(!options.contains(nomTD)) options.add(nomTD);
            }
            // Ajoute le nom du groupe TP de l'utilisateur s'il existe
            if(cUser.getGroup().issetGroupTP()){
                String nomTP = cUser.getGroup().getGroupTP().getNomGroupTP();
                if(!options.contains(nomTP)) options.add(nomTP);
            }
        }
        
        // Remplit le menu déroulant avec les noms des groupes trouvés
        groupeCombo.getItems().setAll(options);
        
        // Gère l'affichage si aucun groupe n'est disponible
        if (options.isEmpty()) {
            groupeCombo.setPromptText("Aucun groupe assigné");
        } else {
            groupeCombo.setValue(options.get(0)); // Sélectionne le premier par défaut
        }
        
        // Lie les colonnes classiques aux attributs simples de l'étudiant
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genreEtu"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("mailProEtu"));
        
        // Récupère le nom du Bac via une propriété personnalisée
        colBac.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().getBac() != null) {
                return new SimpleStringProperty(cellData.getValue().getBac().getNomBac());
            }
            return new SimpleStringProperty("-");
        });

        // Détermine quel nom de groupe afficher dans le tableau selon la sélection
        colGroupe.setCellValueFactory(cellData -> {
            Etudiant etu = cellData.getValue();
            String selection = groupeCombo.getValue();
            
            if (etu == null || etu.getGroup() == null || selection == null) {
                return new SimpleStringProperty("-");
            }

            // Vérifie si l'étudiant de la ligne correspond au TD sélectionné
            if (etu.getGroup().issetGroupTD() && selection.equals(etu.getGroup().getGroupTD().getNomGroupTD())) {
                return new SimpleStringProperty(etu.getGroup().getGroupTD().getNomGroupTD());
            } 
            // Vérifie si l'étudiant de la ligne correspond au TP sélectionné
            if (etu.getGroup().issetGroupTP() && selection.equals(etu.getGroup().getGroupTP().getNomGroupTP())) {
                return new SimpleStringProperty(etu.getGroup().getGroupTP().getNomGroupTP());
            }

            return new SimpleStringProperty("-");
        });

        // Charge les données et définit l'action lors du changement de sélection
        chargerDonnees();
        
        groupeCombo.setOnAction(e -> {
            appliquerFiltre(); // Filtre la liste quand on change de groupe
            tableEtudiant.refresh(); 
        });
        
        // Applique le filtre de départ si des options existent
        if (!options.isEmpty()) {
            appliquerFiltre();
        }
        
       navBarController.updateNav(2); // Met à jour l'indicateur de navigation
    }

    // Remplit la liste locale à partir de la liste globale des étudiants
    private void chargerDonnees() {
        tousLesEtudiants.clear();
        if (Etudiant.ListEtu != null) {
            for (int i = 0; i < Etudiant.ListEtu.length(); i++) {
                Etudiant e = Etudiant.ListEtu.get(i);
                if (e != null) {
                    tousLesEtudiants.add(e);
                }
            }
        }
    }

    // Filtre les étudiants affichés selon le groupe choisi dans le ComboBox
    private void appliquerFiltre() {
        String selection = groupeCombo.getValue();
        
        // Si rien n'est sélectionné, on vide le tableau
        if (selection == null || selection.isEmpty()) {
            tableEtudiant.setItems(FXCollections.observableArrayList());
            lblCompteur.setText("Aucun groupe sélectionné");
            return;
        }
        
        ObservableList<Etudiant> filtres = FXCollections.observableArrayList();

        // Parcourt tous les étudiants pour trouver ceux qui appartiennent au groupe sélectionné
        for (Etudiant e : tousLesEtudiants) {
            if (e == null || e.getGroup() == null) continue;

            // Vérifie la correspondance avec le TD
            boolean matchTD = e.getGroup().issetGroupTD() && 
                              e.getGroup().getGroupTD().getNomGroupTD().equals(selection);
            
            // Vérifie la correspondance avec le TP
            boolean matchTP = e.getGroup().issetGroupTP() && 
                              e.getGroup().getGroupTP().getNomGroupTP().equals(selection);
            
            if (matchTD || matchTP) {
                filtres.add(e); // Ajoute l'étudiant s'il fait partie du groupe
            }
        }
        
        // Met à jour le tableau et le texte du compteur
        tableEtudiant.setItems(filtres);
        lblCompteur.setText("Nombre d'étudiants : " + filtres.size());
    }
}