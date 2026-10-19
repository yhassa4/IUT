package controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import java.io.*;
import java.util.ArrayList;

import Appli.main;
import Base.*; 

public class EtudiantController {

    @FXML private TableView<Etudiant> tableEtudiants;
    @FXML private TableColumn<Etudiant, String> colNum, colNom, colPrenom, colgenre, colAddr, colMailperso, colMailpro, colPromo, colBac, colParcour, colCursus, colMoyenne, colgtd, colgtp;
    @FXML private Button buttonImport, buttonExport;
    @FXML private ComboBox<Promotion> comboPromoFilter;
    @FXML private ComboBox<IGroup> combogroupFilter;
    @FXML private Label lblStatus;
    @FXML private NavBarController navBarController;

    @FXML
    public void initialize() {
        // Configuration initiale des colonnes et chargement des données
        configurerColonnes();
        
        // Remplissage du filtre des promotions
        comboPromoFilter.setItems(FXCollections.observableArrayList(Promotion.ListPromo));
        comboPromoFilter.setPromptText("tous");
        comboPromoFilter.setOnAction(e -> filtrerTableau());
     
        actualiserTableau();
        
        // Restriction de l'import aux responsables uniquement
        Enseignant cUser = (Enseignant)main.getCurrentUser();
        if(!cUser.getRole().equals("RESPONSABLE DE FILIÈRE") && !cUser.getRole().equals("RESPONSABLE DE FORMATION")) {
            buttonImport.setVisible(false);
        }
        
        navBarController.updateNav(1);
    }

    private void configurerColonnes() {
        // Liaison des données de l'étudiant aux colonnes du tableau
        colNum.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getnum())));
        colNom.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNomEtu()));
        colPrenom.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getPrenomEtu()));
        colMailpro.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getmailpro()));
        colAddr.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getaddr()));
        colgenre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getGenre()));
        colBac.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getBac().getNomBac()));
        colParcour.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getParcour().getNomParcour()));
        
        // Affichage textuel du type de cursus
        colCursus.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getApprentissage() ? "Apprentissage" : "Initial"));
        
        // Affichage sécurisé de la promotion
        colPromo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getPromotion() != null ? d.getValue().getPromotion().getNomPromo() : "N/A"));

        // Calcul et formatage de la moyenne générale
        colMoyenne.setCellValueFactory(d -> new SimpleStringProperty(String.format("%.2f / 20", d.getValue().GetMoyenneGenerale())));
        
        // Récupération des noms des groupes TD et TP
        colgtd.setCellValueFactory(d -> {
            String g = (d.getValue().getGroup() != null && d.getValue().getGroup().getGroupTD() != null) ? d.getValue().getGroup().getGroupTD().getNomGroup() : "null";
            return new SimpleStringProperty(g);
        });
        colgtp.setCellValueFactory(d -> {
            String g = (d.getValue().getGroup() != null && d.getValue().getGroup().getGroupTP() != null) ? d.getValue().getGroup().getGroupTP().getNomGroup() : "null";
            return new SimpleStringProperty(g);
        });
    }

    private void actualiserTableau() {
        // Recharge la liste complète des étudiants dans la table
        ArrayList<Etudiant> listetu = new ArrayList<>();
        for (int i = 0; i < Etudiant.ListEtu.length()-1; i++) {
            listetu.add(Etudiant.ListEtu.get(i));
        }
        tableEtudiants.setItems(FXCollections.observableArrayList(listetu));
    }

    @FXML
    private void filtrerTableau() {
        // Filtrage croisé par Promotion et par Groupe (TD/TP)
        Promotion p = comboPromoFilter.getValue();
        IGroup g = combogroupFilter.getValue();
        ArrayList<Etudiant> filtre = new ArrayList<>();

        if (p == null) {
            actualiserTableau();
        } else {
            // Premier filtre : Promotion
            for (int i = 0; i < Etudiant.ListEtu.length()-1; i++) {
                Etudiant e = Etudiant.ListEtu.get(i);
                if (e.getPromotion() != null && e.getPromotion().equals(p)) filtre.add(e);
            }
            tableEtudiants.setItems(FXCollections.observableArrayList(filtre));
        }
        
        // Mise à jour dynamique du filtre Groupe selon la promotion choisie
        ArrayList<IGroup> groups = new ArrayList<>();
        for(Etudiant e : filtre) {
            if(e.getGroup() != null && e.getPromotion() == p) {
                if(e.getGroup().issetGroupTD() && !groups.contains(e.getGroup().getGroupTD())) groups.add(e.getGroup().getGroupTD());
                if(e.getGroup().issetGroupTP() && !groups.contains(e.getGroup().getGroupTP())) groups.add(e.getGroup().getGroupTP());
            }
        }
        combogroupFilter.setItems(FXCollections.observableArrayList(groups));

        // Deuxième filtre : Groupe
        if(g != null) {
            ArrayList<Etudiant> filtre2 = new ArrayList<>();
            for(Etudiant e : filtre) {
                if(e.getGroup() != null) {
                    if((e.getGroup().issetGroupTD() && g.getNomGroup().equals(e.getGroup().getGroupTD().getNomGroup())) ||
                       (e.getGroup().issetGroupTP() && g.getNomGroup().equals(e.getGroup().getGroupTP().getNomGroup()))) {
                        if(!filtre2.contains(e)) filtre2.add(e);
                    }
                }
            }
            tableEtudiants.setItems(FXCollections.observableArrayList(filtre2));
        }
    }
    
    @FXML private void resetFilter() {
        // Réinitialise tous les filtres et réaffiche tout
        comboPromoFilter.setValue(null);
        combogroupFilter.setValue(null);
        actualiserTableau();
    }

    @FXML
    private void importerNotes() {
        // Importation de notes via un fichier CSV
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        File file = fc.showOpenDialog(null);
        if (file == null) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String ligne = br.readLine(); 
            int notesAjoutees = 0;

            while ((ligne = br.readLine()) != null) {
                String[] data = ligne.split(";");
                if (data.length >= 7) {
                    Etudiant etu = trouverEtudiant(Integer.parseInt(data[0].trim()));
                    Semestre sem = trouverSemestre(data[3].trim());
                    Matiere mat = trouverMatiere(data[4].trim());

                    if (etu != null && sem != null && mat != null) {
                        float vMax = Float.parseFloat(data[5].replace(",", "."));
                        float vNote = Float.parseFloat(data[6].replace(",", "."));
                        new Note((int)(Math.random()*10000), vNote, vMax, etu, sem, mat);
                        notesAjoutees++;
                    }
                }
            }
            lblStatus.setText(notesAjoutees + " notes importées.");
            tableEtudiants.refresh();
        } catch (Exception e) {
            lblStatus.setText("Erreur import : " + e.getMessage());
        }
    }

    @FXML
    private void exporterDonnees() {
        // Exportation des notes de tous les étudiants au format CSV
        FileChooser fc = new FileChooser();
        fc.setInitialFileName("export_notes.csv");
        File file = fc.showSaveDialog(null);
        if (file == null) return;

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("NumEtu;Prenom;Nom;Semestre;Matiere;NoteMax;Note");
            for (int i = 0 ; i < Etudiant.ListEtu.length() ; i++) {
                Etudiant etu = Etudiant.ListEtu.get(i);
                for (Note note : Note.GetNotes(etu)) {
                    writer.println(String.format(java.util.Locale.US, "%d;%s;%s;%s;%s;%.2f;%.2f", 
                        etu.getnum(), etu.getPrenomEtu(), etu.getNomEtu(), 
                        note.getsemestre().getNomSemestre(), note.getMatiere().getNomMatiere(), 
                        note.getValeurMax(), note.getValeur()));
                }
            }
            lblStatus.setText("Export réussi.");
        } catch (Exception e) {
            lblStatus.setText("Erreur export.");
        }
    }

    private Etudiant trouverEtudiant(int id) {
        for (int i = 0; i < Etudiant.ListEtu.length(); i++) {
            if (Etudiant.ListEtu.get(i).getnum() == id) return Etudiant.ListEtu.get(i);
        }
        return null;
    }

    private Semestre trouverSemestre(String nom) {
        for(Semestre se : Semestre.ListSemestre) if(se.getNomSemestre().equals(nom)) return se;
        return null;
    }

    private Matiere trouverMatiere(String nom) {
        for (Matiere m : Matiere.toutesMatieres) if (m.getNomMatiere().equalsIgnoreCase(nom)) return m;
        return new Matiere(Matiere.toutesMatieres.size() + 1, nom);
    }

    @FXML
    private void ouvrirGestionNotes() {
        // Ouvre une fenêtre pour visualiser et modifier les notes de l'étudiant sélectionné
        Etudiant selection = tableEtudiants.getSelectionModel().getSelectedItem();
        if (selection == null) return;

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Notes de " + selection.getNomComplet());
        
        VBox layout = new VBox(10);
        ComboBox<Semestre> comboSem = new ComboBox<>();
        comboSem.setItems(FXCollections.observableArrayList(Semestre.ListSemestre));
        VBox zoneMoyennes = new VBox(10);

        // Mise à jour des notes affichées selon le semestre choisi
        comboSem.setOnAction(e -> {
            zoneMoyennes.getChildren().clear();
            Semestre s = comboSem.getValue();
            if (s == null) return;

            for (Matiere m : Matiere.toutesMatieres) {
                Float moyenne = selection.GetMoyenneMatiere(m, s);
                if (moyenne != null) {
                    VBox bloc = new VBox(5);
                    bloc.getChildren().add(new Label(m.getNomMatiere() + " - Moyenne: " + String.format("%.2f/20", moyenne)));
                    
                    for (Note n : Note.GetNotes(selection)) {
                        if (n.getMatiere().equals(m) && n.getsemestre().equals(s)) {
                            HBox ligne = new HBox(5);
                            TextField edit = new TextField(String.valueOf(n.getValeur()));
                            Button btn = new Button("Modifier");
                            btn.setOnAction(ev -> {
                                n.setValeur(Float.parseFloat(edit.getText().replace(",", ".")));
                                comboSem.getOnAction().handle(null); // Rafraîchit l'affichage
                            });
                            ligne.getChildren().addAll(edit, new Label("/" + n.getValeurMax()), btn);
                            bloc.getChildren().add(ligne);
                        }
                    }
                    zoneMoyennes.getChildren().add(bloc);
                }
            }
        });

        layout.getChildren().addAll(comboSem, zoneMoyennes);
        dialog.getDialogPane().setContent(layout);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }

    @FXML
    private void ModifierEtudiant() {
        // Fenêtre de modification des informations de base et de la promotion
        Etudiant selection = tableEtudiants.getSelectionModel().getSelectedItem();
        if (selection == null) return;

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modifier " + selection.getNomComplet());
        ButtonType boutonValider = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(boutonValider, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        TextField txtNom = new TextField(selection.getNomEtu());
        TextField txtPrenom = new TextField(selection.getPrenomEtu());
        ComboBox<Promotion> comboPromo = new ComboBox<>(FXCollections.observableArrayList(Promotion.ListPromo));
        comboPromo.setValue(selection.getPromotion());

        grid.add(new Label("Nom:"), 0, 0); grid.add(txtNom, 1, 0);
        grid.add(new Label("Prénom:"), 0, 1); grid.add(txtPrenom, 1, 1);
        grid.add(new Label("Promo:"), 0, 2); grid.add(comboPromo, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait().ifPresent(response -> {
            if (response == boutonValider) {
                selection.setNomEtu(txtNom.getText());
                selection.setPrenomEtu(txtPrenom.getText());
                // Si la promo change, on réinitialise les groupes TD/TP
                if(!selection.getPromotion().equals(comboPromo.getValue())) {
                    selection.setPromo(comboPromo.getValue());
                    selection.unsetGroupTD();
                    selection.unsetGroupTP();
                }
                actualiserTableau();
            }
        });
    }
}