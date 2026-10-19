package controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

import Appli.main;

public class NavBarController {

    @FXML private HBox buttonContainer;
    private ArrayList<Button> Listbutton = new ArrayList<Button>();
    
    // style de base 
    private static final String styleBase = "-fx-background-color: transparent; " +
                       "-fx-text-fill: #555555; " +
                       "-fx-font-size: 15px; " + 
                       "-fx-font-weight: bold; " +
                       "-fx-padding: 12 25; " + 
                       "-fx-cursor: hand; " +
                       "-fx-border-color: transparent; " +
                       "-fx-border-width: 0 0 4 0;"; 

    // style actif/survole
    private static final String styleFocus = "-fx-background-color: rgba(165, 0, 68, 0.05); " + 
                        "-fx-text-fill: #a50044; " +
                        "-fx-border-color: #a50044; " + 
                        "-fx-border-width: 0 0 4 0;";

    @FXML
    public void initialize() {
        String role = main.getMainController().getCurrentRole(); 
        buttonContainer.getChildren().clear();
        System.out.println(role);
        if ("etudiant".equals(role.toLowerCase())) {//cas User = etudiant
            ajouterBouton("Vue d'ensemble", "VueinfoEtudiant.fxml");
            ajouterBouton("Mes Notes", "VueNotesEtudiant.fxml");
            ajouterBouton("Mes Groupes", "Vuemongroup.fxml");
            ajouterBouton("Ma Promotion", "VuePromotion.fxml");
            
        }
        else if ("ENSEIGNANT".toUpperCase().equals(role.toLowerCase())) { 
        	ajouterBouton("Vue d'ensemble", "VueEnsembleEnseignant.fxml");
        }
        else if ("RESPONSABLE SEMESTRE".equals(role.toUpperCase()) || "RESPONSABLE D’ANNÉE".equals(role.toUpperCase())|| "RESPONSABLE DE FILIÈRE".equals(role.toUpperCase())) {
            ajouterBouton("Vue d'ensemble", "VueEnsembleResponsable.fxml");
            ajouterBouton("Gestion des Notes", "VueEtudiant.fxml");
            ajouterBouton("Mes Cours", "VueEnsembleEnseignant.fxml");
            ajouterBouton("Gestion des sondages", "GestionSondageVue.fxml");
            ajouterBouton("Gestion des groupes", "GestionGroupesVue.fxml");
           
        }
        else if ("RESPONSABLE DE FORMATION".equals(role.toUpperCase())) {
            ajouterBouton("Vue d'ensemble", "VueEnsembleResponsable.fxml");
            ajouterBouton("Gestion des Notes", "VueEtudiant.fxml");
            ajouterBouton("Mes Cours", "VueEnsembleEnseignant.fxml");
            ajouterBouton("Gestion des sondages", "GestionSondageView.fxml");
            ajouterBouton("Gestion des groupes", "GestionGroupesView.fxml");
            ajouterBouton("Gestion des enseignants", "GestionEnseignant.fxml");
           
        }
    }
    
    //methode pour render le bon navbar , c est du bidoullage mais les autres methode que j ai trouver etait trop complexe 
    public void updateNav(int page) { 
	    for (int i = 0; i < Listbutton.size(); i++) {
	        Button boutonEnCours = Listbutton.get(i); 
	        
	        if (i == page) {
	            boutonEnCours.setStyle(styleFocus); 
	        } else {
	            boutonEnCours.setStyle(styleBase); 
	        }
	    }
    }

 // methode pour ajouter les bouton au navBar et bind leur actions
    private void ajouterBouton(String texte, String fxmlCible) {
        Button b = new Button(texte.toUpperCase());
        Listbutton.add(b);

        b.setOnAction(e -> {
            Appli.main.getMainController().chargerPage(fxmlCible);
        });
        
        buttonContainer.getChildren().add(b);
    }
}