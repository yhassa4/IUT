package controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import Appli.main;
import Base.Etudiant;

public class PromotionController {

    @FXML private TableView<Etudiant> tablePromotion;
    @FXML private TableColumn<Etudiant, String> colNom;
    @FXML private TableColumn<Etudiant, String> colPrenom;
    @FXML private TableColumn<Etudiant, String> colGenre;
    @FXML private TableColumn<Etudiant, String> colBac;
    @FXML private TableColumn<Etudiant, String> colMail;
    @FXML private TableColumn<Etudiant, String> colGroupe;
    
    @FXML private TextField searchFD;
    @FXML private Label lblCompteur;
    @FXML private NavBarController navBarController;

    private ObservableList<Etudiant> Listetu = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Liaison des colonnes 
        colNom.setCellValueFactory(new PropertyValueFactory<>("nomEtu"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenomEtu"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genreEtu"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("mailProEtu"));
        colBac.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().getBac() != null) {
                return new SimpleStringProperty(cellData.getValue().getBac().getNomBac());
            }
            return new SimpleStringProperty("-");
        });

        colGroupe.setCellValueFactory(cellData -> {
            Etudiant etu = cellData.getValue();
            if (etu == null || etu.getGroup() == null) return new SimpleStringProperty("Non affecté"); // SimpleStringProperty = string 

            String td = etu.getGroup().issetGroupTD() ? etu.getGroup().getGroupTD().getNomGroupTD() : "";
            String tp = etu.getGroup().issetGroupTP() ? etu.getGroup().getGroupTP().getNomGroupTP() : "";

            if (!td.isEmpty() && !tp.isEmpty()) return new SimpleStringProperty(td + " / " + tp);
            if (!td.isEmpty()) return new SimpleStringProperty(td);
            if (!tp.isEmpty()) return new SimpleStringProperty(tp);
            
            return new SimpleStringProperty("-");
        });

        //Chargement des données
        chargerTouteLaPromo();

        //recherche 
        FilteredList<Etudiant> ListetuFiltre = new FilteredList<>(Listetu, p -> true);
        searchFD.textProperty().addListener((observable, oldValue, newValue) -> {
        	ListetuFiltre.setPredicate(etudiant -> {
                if (newValue == null || newValue.isEmpty()) return true;
                
                String lowerCaseFilter = newValue.toLowerCase();
                if (etudiant.getNomEtu().toLowerCase().contains(lowerCaseFilter)) return true;
                if (etudiant.getPrenomEtu().toLowerCase().contains(lowerCaseFilter)) return true;
                return false;
            });
            lblCompteur.setText("Total : " + ListetuFiltre.size() + " étudiants");
        });

        tablePromotion.setItems(ListetuFiltre);
        lblCompteur.setText("Total : " + Listetu.size() + " étudiants");
        
        navBarController.updateNav(3);
    }

    private void chargerTouteLaPromo() {
    	Listetu.clear();
        if (Etudiant.ListEtu != null) {
            for (int i = 0; i < Etudiant.ListEtu.length(); i++) {
                Etudiant e = Etudiant.ListEtu.get(i);
                if (e != null) Listetu.add(e);
            }
        }
    }
}