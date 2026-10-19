package controleurs;

import Appli.main;
import Base.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.Optional;

public class infoEtudiantController {

    // Labels pour afficher les informations personnelles de l'étudiant
    @FXML private Label numEtuLabel;
    @FXML private Label nomEtuLabel;
    @FXML private Label prenomEtuLabel;
    @FXML private Label genreEtuLabel;
    @FXML private Label mailProEtuLabel;
    @FXML private Label mailPersoEtuLabel;
    @FXML private Label addrEtuLabel;
    @FXML private NavBarController navBarController;
    
    // Éléments pour l'affichage dynamique du sondage
    @FXML private VBox sondageContainer; 
    @FXML private Label sondageStatusLabel;

    @FXML
    public void initialize() {
        // Récupère l'étudiant actuellement connecté
        Etudiant cUser = (Etudiant)main.getCurrentUser();
        
        // Remplit les labels avec les données de l'étudiant
        numEtuLabel.setText(cUser.getnum()+"");
        nomEtuLabel.setText(cUser.getNomEtu());
        prenomEtuLabel.setText(cUser.getPrenomEtu());
        genreEtuLabel.setText(cUser.getGenreEtu());
        mailProEtuLabel.setText(cUser.getmailpro());
        mailPersoEtuLabel.setText(cUser.getmailperso());
        addrEtuLabel.setText(cUser.getaddr());
        
        // Met à jour la barre de navigation sur l'onglet Profil
        if(navBarController != null) navBarController.updateNav(0);
        
        // Vérifie si un sondage doit être affiché pour cet étudiant
        verifierSondages(cUser);
    }

    // Parcourt la liste des sondages pour trouver un sondage actif correspondant à la promo
    private void verifierSondages(Etudiant etu) {
        Sondage sondageActif = null;
        for (Sondage s : Sondage.Sondages) {
            // Vérifie si le sondage est pour sa promo, s'il est en cours et s'il n'a pas déjà répondu
            if (s.getPromo().equals(etu.getPromotion()) && s.estEnCours()) {
                if (!aDejaRepondu(etu, s)) {
                    sondageActif = s;
                    break;
                }
            }
        }

        // Si un sondage est trouvé, crée un bouton de réponse dynamiquement
        if (sondageActif != null) {
            sondageStatusLabel.setText("Un sondage est disponible : " + sondageActif.getNomSondage());
            Button btnRepondre = new Button("Répondre au sondage");
            btnRepondre.setStyle("-fx-background-color: #a50044; -fx-text-fill: white; -fx-cursor: hand;");
            
            final Sondage sToAnswer = sondageActif;
            btnRepondre.setOnAction(e -> ouvrirFormulaireSondage(etu, sToAnswer));
            sondageContainer.getChildren().add(btnRepondre);
        }
    }

    // Vérifie dans la liste des réponses si l'étudiant est déjà lié à ce sondage
    private boolean aDejaRepondu(Etudiant etu, Sondage s) {
        for (Repond r : Repond.ListRepond) {
            if (r.getEtudiant().equals(etu) && r.getSondage().equals(s)) return true;
        }
        return false;
    }

    // Ouvre une fenêtre (Dialog) contenant les questions du sondage
    private void ouvrirFormulaireSondage(Etudiant etu, Sondage s) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle(s.getNomSondage());
        dialog.setHeaderText("Merci de répondre aux questions suivantes :");
        ButtonType submitButton = new ButtonType("Envoyer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButton, ButtonType.CANCEL); 

        VBox content = new VBox(15);
        // Génère un champ de texte pour chaque question du sondage
        for (Question q : s.getQuestions()) {
            VBox qBox = new VBox(5);
            qBox.getChildren().add(new Label(q.getTextQuestion()));
            TextField answerField = new TextField();
            answerField.setUserData(q); // Attache l'objet Question au champ pour le retrouver plus tard
            qBox.getChildren().add(answerField);
            content.getChildren().add(qBox);
        }

        dialog.getDialogPane().setContent(content);

        // Affiche la fenêtre et attend la réponse
        Optional<Void> result = dialog.showAndWait();
        if (dialog.getResult() != null) { 
            // Si l'utilisateur clique sur "Envoyer", on enregistre chaque réponse
            for (javafx.scene.Node node : content.getChildren()) {
                if (node instanceof VBox) {
                    TextField tf = (TextField) ((VBox) node).getChildren().get(1);
                    Question q = (Question) tf.getUserData();
                    // Crée une nouvelle instance de Repond pour sauvegarder la réponse
                    new Repond(etu, s, q, tf.getText());
                }
            }
            // Nettoie l'interface après la validation
            sondageContainer.getChildren().clear();
            sondageStatusLabel.setText("Merci pour votre participation !");
        }
    }
}