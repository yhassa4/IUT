package controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.stream.Collectors;
import Appli.main;
import Base.Etudiant;
import Base.Note;

public class NotesEtudiantController {

    // Éléments du tableau et colonnes pour afficher les notes
    @FXML private TableView<Note> tableNotes;
    @FXML private TableColumn<Note, String> colSemestre;
    @FXML private TableColumn<Note, String> colMatiere;
    @FXML private TableColumn<Note, Float> colNote;
    @FXML private TableColumn<Note, Float> colNoteMax;
    
    // Menu de sélection du semestre et affichage de la moyenne
    @FXML private ComboBox<String> semestreCombo;
    @FXML private Label lblMoyenne;
    @FXML private NavBarController navBarController;
    
    // Récupération de l'étudiant connecté
    Etudiant cUser = (Etudiant)main.getCurrentUser();

    // Liste spéciale (Observable) qui met à jour le tableau automatiquement
    private ObservableList<Note> toutesLesNotes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Relie les colonnes aux attributs de l'objet Note (utilise les getters)
        colSemestre.setCellValueFactory(new PropertyValueFactory<>("SemestreNom"));
        colMatiere.setCellValueFactory(new PropertyValueFactory<>("matiereNom"));
        colNote.setCellValueFactory(new PropertyValueFactory<>("valeur"));
        colNoteMax.setCellValueFactory(new PropertyValueFactory<>("valeurMax"));
        
        // Récupère les notes de l'étudiant depuis la "base de données"
        chargerDonnees();

        // Construit la liste des semestres disponibles pour le menu déroulant
        ArrayList<String> nomSemestre = new ArrayList<String>();
        nomSemestre.add("Tous");
        for(int i = 0 ; i < toutesLesNotes.size() ; i++) {
            String nomS = toutesLesNotes.get(i).getsemestre().getNomSemestre();
            // Évite les doublons dans la liste des semestres
            if(!nomSemestre.contains(nomS)) {
                nomSemestre.add(nomS);
            }
        }

        // Remplit le ComboBox et définit la sélection par défaut
        semestreCombo.getItems().setAll(nomSemestre);
        semestreCombo.setValue("Tous");

        // Déclenche le filtrage dès que l'utilisateur change de semestre
        semestreCombo.setOnAction(e -> appliquerFiltre());
        
        // Premier affichage (toutes les notes)
        appliquerFiltre();
        
        // Indique à la navigation que nous sommes sur l'onglet Notes
        navBarController.updateNav(1);
    }

    // Remplit la liste locale avec les notes récupérées via la classe Note
    private void chargerDonnees() {
        ArrayList<Note> note = Note.GetNotes(cUser);
        for(int i = 0 ; i < note.size() ; i++) {
            toutesLesNotes.add(note.get(i)); 
        }
    }

    // Filtre les notes affichées selon le semestre choisi
    private void appliquerFiltre() {
        String selection = semestreCombo.getValue();
        
        // Si "Tous" est choisi, on affiche tout et on calcule la moyenne globale
        if (selection.equals("Tous")) {
            tableNotes.setItems(toutesLesNotes);
            calculerMoyenne(toutesLesNotes);
            return;
        }

        // Sinon, on crée une liste temporaire pour le semestre sélectionné
        ObservableList<Note> notesFiltre = FXCollections.observableArrayList();
        for (Note n : toutesLesNotes) {
            if (n.getsemestre().getNomSemestre().equals(selection)) {
                notesFiltre.add(n); 
            }
        }

        // Met à jour le tableau et la moyenne avec les notes filtrées
        tableNotes.setItems(notesFiltre);
        calculerMoyenne(notesFiltre);
    }

    // Calcule la moyenne des notes fournies, ramenée sur 20
    private void calculerMoyenne(ObservableList<Note> notes) {
        if (notes.isEmpty()) {
            lblMoyenne.setText("-- / 20");
            return;
        }
        
        float moyenne = 0;
        for (Note n : notes) {
            // Règle de trois pour remettre la note sur 20 (ex: 7/10 devient 14/20)
            float note20 = (n.getvaleur() / n.getValeurMax()) * 20;
            moyenne += note20;
        }
        
        // Division par le nombre de notes et formatage du texte (2 chiffres après la virgule)
        moyenne = moyenne / notes.size();
        lblMoyenne.setText(String.format("%.2f / 20", moyenne));
    }
}