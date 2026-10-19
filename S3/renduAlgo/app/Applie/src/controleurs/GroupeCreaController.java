package controleurs;

import Base.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import tree.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Appli.main;

public class GroupeCreaController {

    @FXML private ComboBox<Promotion> comboPromo;
    @FXML private Label lblTotalEtu, lblTotalRedoublants, lblTotalApprentis, lblInfoGroupes, lblInfoGroupes2;
    @FXML private Spinner<Integer> spinCible;
    @FXML private Slider sliderImportance;
    @FXML private ComboBox<String> comboGloutons;
    @FXML private ListView<IGroup> listGroupes; 
    @FXML private DatePicker datePublication;
    @FXML private Spinner<Integer> spinTaille,spinTaille2;
    @FXML private RadioButton rbTD, rbTP, rbTP2, rbTD2;
    @FXML private CheckBox chkMixiteBac, chkParite, chkEcartType, chkParcours, chkAlternants;
    @FXML private CheckBox chkBac, chkGenre, chkCovoit, chkNotes, chkAlternance;
    @FXML private Slider sldBac, sldGenre, sldCovoit, sldNotes, sldAlternance;
    @FXML private NavBarController navBarController;
    @FXML private ComboBox<String> comboCiblePublication;
    @FXML private Label lblDateActuellePubli;
    @FXML private Button btnExport;
    
    ArrayList<IGroup> ListGroups = new ArrayList<>(); // base
    ArrayList<IGroup> filteredGroups = new ArrayList<>(); // filtre groupe
    public List<Etudiant> ListFiltreEtu = new List<>(); // filtre
    public List<Etudiant> ListEtu = new List<>(); // base
    private ArrayList<Commentaire> ListCommentaire = new ArrayList<>();

    @FXML
    public void initialize() {
        comboPromo.setItems(FXCollections.observableArrayList(Promotion.ListPromo));
        
        comboGloutons.setItems(FXCollections.observableArrayList(
                "G1: Diversité Bac + Remplissage",
                "G2: Parité Genre + Mixité Parcours",
                "G3: Priorité Covoiturage + Niveau",
                "G4: Parité Stricte + Lissage Notes",
                "G5: Mixité Parcours + Genre",
                "G6: Écart-type Notes + Mixité Bac",
                "G7: Équilibre Alternants + Quota Bac",
                "G8: Covoiturage + Parité"
            ));
        
        this.ListEtu = Etudiant.ListEtu.copy();
        comboCiblePublication.setItems(FXCollections.observableArrayList(
        	    "Tous les groupes",
        	    "Tous les TD uniquement",
        	    "Tous les TP uniquement"
        	));
        	comboCiblePublication.getSelectionModel().selectFirst();
        	
        	comboCiblePublication.valueProperty().addListener((obs, oldVal, newVal) -> mettreAJourLabelDate());

        if (!Promotion.ListPromo.isEmpty()) {
            comboPromo.getSelectionModel().selectFirst();
            chargerStatsPromo();
        }
        Enseignant currentUser = (Enseignant) main.getCurrentUser();
        if (currentUser != null && "RESPONSABLE SEMESTRE".equals(currentUser.getRole())) {
            btnExport.setVisible(true);
            btnExport.setManaged(true); 
        } else {
            btnExport.setManaged(false);
        }
        
        
        spinTaille.valueProperty().addListener((obs, oldVal, newVal) -> {
            calculerNbGroupes();
        });
        spinTaille2.valueProperty().addListener((obs, oldVal, newVal) -> {
            calculerNbGroupes();
        });
        

        navBarController.updateNav(4);
    }
    

    @FXML
    private void chargerStatsPromo() {
        Promotion selected = comboPromo.getValue();
        if (selected == null) return;
        ListFiltreEtu = new List<>();
        
        int total = 0;
        int redoublants = 0;
        int apprentis = 0;

        for (int i = 0; i < ListEtu.length()-1; i++) {
            Etudiant e = ListEtu.get(i);
            if (e.getPromotion() != null && e.getPromotion().equals(selected)) {
            	ListFiltreEtu.add(-1, e);
            }
            if (e.getPromotion() != null && e.getPromotion().equals(selected)) {
                total++;
                if (e.estRedoublant()) redoublants++;
            }
            if (e.getPromotion() != null && e.getPromotion().equals(selected)) {
                if (e.getApprentissage()) apprentis++;
            }
        }

        lblTotalEtu.setText(String.valueOf(total));
        lblTotalRedoublants.setText(String.valueOf(redoublants));
        lblTotalApprentis.setText(String.valueOf(apprentis));
        
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = 
        (SpinnerValueFactory.IntegerSpinnerValueFactory) spinTaille.getValueFactory();
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory2 = 
        (SpinnerValueFactory.IntegerSpinnerValueFactory) spinTaille2.getValueFactory();
        valueFactory.setMax(total);
        valueFactory2.setMax(total);
        actualiserListeGroupes();
    }
    @FXML
    private void calculerNbGroupes() {
        try {
            int totalEtu = Integer.parseInt(lblTotalEtu.getText());
            int tailleVisee = spinTaille.getValue();
            
            if (tailleVisee > 0 && totalEtu > 0) {
                int nbGroupes = (int) Math.ceil((double) totalEtu / tailleVisee);
                lblInfoGroupes.setText("Estimation : " + nbGroupes + " groupes");
            }
        } catch (Exception e) {
            lblInfoGroupes.setText("Erreur de calcul");
        }
        try {
            int totalEtu = Integer.parseInt(lblTotalEtu.getText());
            int tailleVisee = spinTaille2.getValue();
            
            if (tailleVisee > 0 && totalEtu > 0) {
                int nbGroupes = (int) Math.ceil((double) totalEtu / tailleVisee);
                lblInfoGroupes2.setText("Estimation : " + nbGroupes + " groupes");
            }
        } catch (Exception e) {
            lblInfoGroupes.setText("Erreur de calcul");
        }
    }
    
    

    private void actualiserListeGroupes() {
    	Promotion selected = comboPromo.getValue();
        if (selected == null) return;

        filteredGroups.clear();

        // On parcourt la liste globale des objets de liaison Group 
        for (Group gLink : Group.ListGroup2) {
            if (gLink.getPromo().getNomPromo().equals(selected.getNomPromo())) {
                
                // On récupère le TD associé à cette promo s'il existe
                if (gLink.issetGroupTD() && !filteredGroups.contains(gLink.getGroupTD())) {
                    filteredGroups.add(gLink.getGroupTD());
                }
                
                // On récupère le TP associé à cette promo s'il existe
                if (gLink.issetGroupTP() && !filteredGroups.contains(gLink.getGroupTP())) {
                    filteredGroups.add(gLink.getGroupTP());
                }
            }
        }
        
        filteredGroups.sort((g1, g2) -> g1.toString().compareTo(g2.toString()));
        listGroupes.setItems(FXCollections.observableArrayList(filteredGroups));
    }

    @FXML
    private void genererGloutonManuel() {
        ArrayList<Number> ListCritere = new ArrayList<>();
        ArrayList<Number> Listcoef = new ArrayList<>();

        int taille = spinTaille.getValue();
        boolean isTD = rbTD.isSelected();
        
        // Vérifications 
        if (!isTD && GroupTPisSet()) {
            System.out.println("il existe deja des group TP"); return;
        }
        if (isTD && GroupTDisSet()) {
            System.out.println("il existe deja des group TD"); return;
        }

        int type = isTD ? 1 : 10;
        int totalEtu = Integer.parseInt(lblTotalEtu.getText());
        int nbGroupes = (int) Math.ceil((double) totalEtu / taille);

        Promotion p = comboPromo.getValue();
        if (p == null) return;

        //Générer les objets groupes
        if (isTD) {
            genererGroupesLocaux(nbGroupes, taille, 0, 0);
        } else {
            genererGroupesLocaux(0, 0, nbGroupes, taille);
        }

        //Préparation des poids
        double pBac = chkBac.isSelected() ? sldBac.getValue() : 0;
        double pGenre = chkGenre.isSelected() ? sldGenre.getValue() : 0;
        double pCovoit = chkCovoit.isSelected() ? sldCovoit.getValue() : 0;
        double pNotes = chkNotes.isSelected() ? sldNotes.getValue() : 0;
        double pAlternance = chkAlternance.isSelected() ? sldAlternance.getValue() : 0;

        if (pBac == 0 && pGenre == 0 && pCovoit == 0 && pNotes == 0 && pAlternance == 0) {
            System.out.println("Erreur : Aucun critère sélectionné");
            return;
        }

        //Récupérer les groupes créés dans ListGroups
        ArrayList<IGroup> ListGroupCible = new ArrayList<>();
        for (IGroup g : ListGroups) {
            if (g instanceof GroupTD && type == 1) ListGroupCible.add(g);
            else if (g instanceof GroupTP && type == 10) ListGroupCible.add(g);
        }

        // Remplissage des listes de critères pour l'algo custom
        if (pBac > 0) { ListCritere.add(0); Listcoef.add((float) pBac); }
        if (pGenre > 0) { ListCritere.add(1); Listcoef.add((float) pGenre); }
        if (pCovoit > 0) { ListCritere.add(2); Listcoef.add((float) pCovoit); }
        if (pNotes > 0) { ListCritere.add(3); Listcoef.add((float) pNotes); }
        if (pAlternance > 0) { ListCritere.add(4); Listcoef.add((float) pAlternance); }

        // Lancement de l'algorithme
        mainAlgo.gloutonCustom(ListFiltreEtu, type, ListCritere, Listcoef, ListGroupCible);

        //Actualisation finale de l'affichage
        actualiserListeGroupes();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Génération manuelle terminée !");
        alert.showAndWait();
    }

    @FXML
    private void genererGloutonListe() {
    	int index = comboGloutons.getSelectionModel().getSelectedIndex();
        if (index == -1) return;


        int taille = spinTaille2.getValue();
        boolean isTD = rbTD2.isSelected();
        if (!isTD) {
        	if(GroupTPisSet()) {
        		System.out.println("il existe deja des group TP");return;
        	}
        }
        if (isTD) {
        	if(GroupTDisSet()) {
        		System.out.println("il existe deja des group TD");return;
        	}
        }
        int type = 10;
        if(isTD) {type =1;}

            int totalEtu = Integer.parseInt(lblTotalEtu.getText());
            int tailleVisee = spinTaille2.getValue();
            int nbGroupes = 0 ;
            if (tailleVisee > 0 && totalEtu > 0) { 
            	nbGroupes = (int) Math.ceil((double) totalEtu / tailleVisee);
            }
            
        
            
        Promotion p = comboPromo.getValue();
        if (p == null) return;
        
        //Générer les nouveaux groupes
        if(isTD) {
        	genererGroupesLocaux(nbGroupes, taille, 0, 0);
        } else {
        	genererGroupesLocaux(0, 0, nbGroupes, taille);
        }
        
       
        ArrayList<IGroup> ListGroup = new ArrayList<>();
        for(IGroup g : ListGroups) {
            if(g instanceof GroupTD && type == 1) ListGroup.add(g);
            else if (g instanceof GroupTP && type == 10) ListGroup.add(g);
        }
        
        
        
        System.out.println("size : " + ListFiltreEtu.length());
        switch (index) {
            case 0: 
                mainAlgo.glouton1(ListFiltreEtu,type,ListGroup); 
                break;
            case 1: 
                mainAlgo.glouton2(ListFiltreEtu, type,ListGroup);
                break;
            case 2: 
                mainAlgo.glouton3(ListFiltreEtu, type,ListGroup);
                break;
            case 3: 
                mainAlgo.glouton4(ListFiltreEtu, type,ListGroup);
                break;
            case 4: 
                mainAlgo.glouton5(ListFiltreEtu, type,ListGroup);
                break;
            case 5: 
                mainAlgo.glouton6(ListFiltreEtu, type,ListGroup);
                break;
            case 6: 
                mainAlgo.glouton7(ListFiltreEtu, type,ListGroup);
                break;
            case 7: 
                mainAlgo.glouton8(ListFiltreEtu, type,ListGroup);
                break;
        }

        actualiserListeGroupes();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Génération terminée");
        alert.setHeaderText(null);
        alert.setContentText("Les groupes ont été générés avec l'algorithme : " + comboGloutons.getValue());
        alert.showAndWait();
    }

    private boolean GroupTPisSet() {
        Promotion selected = comboPromo.getValue();
        for (int i = 0;i <ListEtu.length()-1;i++) {
        	Etudiant etu = ListEtu.get(i);
        	if(etu.getGroup().getPromo().getNomPromo().equals(selected.getNomPromo())) {
	        	if(etu.getGroup().issetGroupTP()) {return true;}
        	}
        }

    	return false ;
    }
    
    private boolean GroupTDisSet() {

        Promotion selected = comboPromo.getValue();
        for (int i = 0;i <ListEtu.length()-1;i++) {
        	Etudiant etu = ListEtu.get(i);
        	if(etu.getGroup().getPromo().getNomPromo().equals(selected.getNomPromo())) {
	        	if(etu.getGroup().issetGroupTD()) {return true;}
        	}
        }
    	return false ;
    }
    
    
    @FXML
    private void voirMembres() {
        IGroup g = listGroupes.getSelectionModel().getSelectedItem();
        if (g == null) return;

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Gestion du groupe : " + g.toString());
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
        dialog.getDialogPane().setMinWidth(500);

        VBox mainBox = new VBox(15);
        mainBox.setPadding(new javafx.geometry.Insets(15));

        // STATISTIQUES
        VBox statsBox = new VBox(5);
        statsBox.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 10; -fx-border-color: #ddd;");
        
        ArrayList<Etudiant> membres = g.GetEtudiant();
        int nbMembres = membres.size();
        long nbFemmes = membres.stream().filter(e -> "Femme".equalsIgnoreCase(e.getGenre())).count();
        long nbRedoublants = membres.stream().filter(Etudiant::estRedoublant).count();
        long nbAlternants = membres.stream().filter(e -> e.getApprentissage() != null && e.getApprentissage()).count();

        // Calcul parité simple
        String parite = nbMembres == 0 ? "N/A" : (nbFemmes * 100 / nbMembres) + "% F / " + ((nbMembres - nbFemmes) * 100 / nbMembres) + "% H";

        statsBox.getChildren().addAll(
            new Label("Membres : " + nbMembres + " / " + g.getTailleMax()),
            new Label("Parité : " + parite),
            new Label("Redoublants : " + nbRedoublants + " | Alternants : " + nbAlternants)
        );

        Label lblMembres = new Label("Membres actuels :");
        lblMembres.setStyle("-fx-font-weight: bold;");
        
        ListView<Etudiant> membersListView = new ListView<>(FXCollections.observableArrayList(membres));
        membersListView.setPrefHeight(150);
        
        Button btnRetirer = new Button("Retirer l'étudiant sélectionné");
        btnRetirer.setMaxWidth(Double.MAX_VALUE);
        btnRetirer.setOnAction(e -> {
            Etudiant selected = membersListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                if (g instanceof GroupTD) selected.getGroup().setGroupTD(null);
                else if (g instanceof GroupTP) selected.getGroup().setGroupTP(null);
                dialog.close();
                voirMembres();
            }
        });

        Separator sep = new Separator();
        Label lblAjout = new Label("Ajouter un étudiant de la promo :");
        lblAjout.setStyle("-fx-font-weight: bold;");
        
        ComboBox<Etudiant> comboDispo = new ComboBox<>();
        for(int i=0; i < ListFiltreEtu.length()-1; i++) {
            Etudiant e = ListFiltreEtu.get(i);
            boolean aDejaGroupe = (g instanceof GroupTD) ? e.getGroup().issetGroupTD() : e.getGroup().issetGroupTP();
            if(!aDejaGroupe) comboDispo.getItems().add(e);
        }
        
        Button btnAjouter = new Button("Ajouter au groupe");
        btnAjouter.setMaxWidth(Double.MAX_VALUE);
        btnAjouter.setOnAction(e -> {
            Etudiant aAjouter = comboDispo.getValue();
            if (aAjouter != null && membres.size() < g.getTailleMax()) {
                if (g instanceof GroupTD) aAjouter.getGroup().setGroupTD((GroupTD) g);
                else if (g instanceof GroupTP) aAjouter.getGroup().setGroupTP((GroupTP) g);
                dialog.close();
                voirMembres();
            } else if (membres.size() >= g.getTailleMax()) {
                new Alert(Alert.AlertType.WARNING, "Le groupe est plein !").show();
            }
        });

        mainBox.getChildren().addAll(statsBox, lblMembres, membersListView, btnRetirer, sep, lblAjout, comboDispo, btnAjouter);
        dialog.getDialogPane().setContent(mainBox);
        dialog.showAndWait();
        actualiserListeGroupes(); 
    }

    @FXML
    private void ajouterCommentaire() {
        IGroup groupeSelectionne = listGroupes.getSelectionModel().getSelectedItem();
        if (groupeSelectionne == null) {
            new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un groupe !").show();
            return;
        }

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Historique des commentaires - " + groupeSelectionne.toString());
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setMinWidth(450);

        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(15));

        ArrayList<Commentaire> commentairesDuGroupe = filtrerCommentairesParGroupe(groupeSelectionne);

        ListView<Commentaire> lvCommentaires = new ListView<>(FXCollections.observableArrayList(commentairesDuGroupe));
        lvCommentaires.setPrefHeight(250);
        
        lvCommentaires.setCellFactory(param -> new ListCell<Commentaire>() {
            @Override
            protected void updateItem(Commentaire item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    
                    setText("[" + item.getDateFormatee() + "] Enseignant #" + item.getNumEnsei().getNomComplet() + " :\n" + item.getContenu());
                    setWrapText(true);
                }
            }
        });

        Button btnAjouter = new Button("+ Ajouter un commentaire");
        btnAjouter.setMaxWidth(Double.MAX_VALUE);
        btnAjouter.setOnAction(e -> {
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle("Nouveau commentaire");
            tid.setHeaderText("Ajouter une note pour " + groupeSelectionne.toString());
            tid.setContentText("Contenu :");
            
            tid.showAndWait().ifPresent(text -> {
                if (!text.trim().isEmpty()) {
                	
                    GroupTD td = (groupeSelectionne instanceof GroupTD) ? (GroupTD) groupeSelectionne : null;
                    GroupTP tp = (groupeSelectionne instanceof GroupTP) ? (GroupTP) groupeSelectionne : null;
                    MainController mainCtrl = Appli.main.getMainController();
                    Enseignant cUser = (Enseignant)main.getCurrentUser();
                    Commentaire nc = new Commentaire(0,cUser,text, LocalDateTime.now(), td, tp);
                    ListCommentaire.add(nc);
                    
                    lvCommentaires.getItems().add(nc);
                }
            });
        });

        layout.getChildren().addAll(new Label("Commentaires enregistrés :"), lvCommentaires, btnAjouter);
        dialog.getDialogPane().setContent(layout);
        dialog.showAndWait();
    }
    private ArrayList<Commentaire> filtrerCommentairesParGroupe(IGroup g) {
        ArrayList<Commentaire> resultar = new ArrayList<>();
        
        for(Commentaire c : ListCommentaire) {
        	if(c.getGroup().equals(g)) {
        		resultar.add(c);
        	}
        }
        return resultar;
    }
    
    
    
    private void mettreAJourLabelDate() {
        String cible = comboCiblePublication.getValue();
        if (cible == null || filteredGroups.isEmpty()) {
            lblDateActuellePubli.setText("Aucune");
            return;
        }

        LocalDate datePlusProche = null;

        for (IGroup g : filteredGroups) {
            boolean match = cible.equals("Tous les groupes") ||
                           (cible.contains("TD") && g instanceof GroupTD) ||
                           (cible.contains("TP") && g instanceof GroupTP);

            if (match && g.getDatePublication() != null) {
                if (datePlusProche == null || g.getDatePublication().isBefore(datePlusProche)) {
                    datePlusProche = g.getDatePublication();
                }
            }
        }
        
        lblDateActuellePubli.setText(datePlusProche == null ? "Non définie" : datePlusProche.toString());
    }

    @FXML
    private void modifierDatePublication() {
        LocalDate nouvelleDate = datePublication.getValue();
        String cible = comboCiblePublication.getValue();

        if (nouvelleDate == null) {
            new Alert(Alert.AlertType.WARNING, "Veuillez choisir une date dans le calendrier.").show();
            return;
        }

        int count = 0;
        for (IGroup g : filteredGroups) {
            if (cible.equals("Tous les groupes") || 
               (cible.contains("TD") && g instanceof GroupTD) || 
               (cible.contains("TP") && g instanceof GroupTP)) {
                g.setDatePublication(nouvelleDate);
                count++;
            }
        }
        
        mettreAJourLabelDate();
        new Alert(Alert.AlertType.INFORMATION, count + " groupes mis à jour.").show();
    }

    @FXML
    private void publierMaintenant() {
        String cible = comboCiblePublication.getValue();
        LocalDate aujourdhui = LocalDate.now();

        for (IGroup g : filteredGroups) {
            if (cible.equals("Tous les groupes") || 
               (cible.contains("TD") && g instanceof GroupTD) || 
               (cible.contains("TP") && g instanceof GroupTP)) {
                g.setDatePublication(aujourdhui.minusMonths(1));
            }
        }
        
        mettreAJourLabelDate();
        new Alert(Alert.AlertType.INFORMATION, "Publication immédiate effectuée pour : " + cible).show();
    }

    @FXML
    private void supprimerGroupes() {
        if (filteredGroups.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Il n'y a aucun groupe à supprimer pour cette promotion.").show();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation de suppression massive");
        confirm.setHeaderText("Supprimer tous les groupes affichés ?");
        confirm.setContentText("Vous allez supprimer " + filteredGroups.size() + " groupe(s). \n" +
                               "Les étudiants associés redeviendront non-assignés.");
        
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                
                for (int i = 0; i < ListEtu.length() - 1; i++) {
                    Etudiant etu = ListEtu.get(i);
                    if (etu.getGroup() != null) {
                        if (filteredGroups.contains(etu.getGroup().getGroupTD())) {
                            etu.getGroup().setGroupTD(null);
                        }
                        if (filteredGroups.contains(etu.getGroup().getGroupTP())) {
                            etu.getGroup().setGroupTP(null);
                        }
                    }
                }


                Group.ListGroup2.removeIf(link -> 
                    filteredGroups.contains(link.getGroupTD()) || 
                    filteredGroups.contains(link.getGroupTP())
                );

                ListGroups.removeAll(filteredGroups);

                filteredGroups.clear();
                actualiserListeGroupes();

                new Alert(Alert.AlertType.INFORMATION, "Tous les groupes de la promotion ont été supprimés.").show();
            }
        });
    }

    
    private void genererGroupesLocaux(int nbTD, int tailleTD, int nbTP, int tailleTP) {
    	System.out.println("Génération de " + nbTD + " groupes de TD");
        for (int i = 0; i < nbTD; i++) {
            GroupTD gTD = new GroupTD(i, "GroupTD" + (i + 1), tailleTD);
            ListGroups.add(gTD);
        }

        System.out.println("Génération de " + nbTP + " groupes de TP");
        for (int j = 0; j < nbTP; j++) { 
            GroupTP gTP = new GroupTP(j, "GroupTP" + (j + 1), tailleTP);
            ListGroups.add(gTP);
        }
    
    }
    
    @FXML
    private void exporterTout() {
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("Enregistrer l'export");
        fileChooser.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("Fichier CSV", "*.csv"));
        java.io.File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (java.io.PrintWriter writer = new java.io.PrintWriter(file)) {
                writer.println("GroupTP;Etudiants");

                for (IGroup g : ListGroups) {
                    // Récupérer les membres
                    StringBuilder membres = new StringBuilder();
                    for (Etudiant e : g.GetEtudiant()) {
                        membres.append(e.getNomComplet()).append(", ");
                    }


                    // Écriture de la ligne
                    writer.println(g.toString() + ";" + membres.toString() + ";" );
                }

                new Alert(Alert.AlertType.INFORMATION, "Exportation réussie !").show();

            } catch (java.io.IOException e) {
                new Alert(Alert.AlertType.ERROR, "Erreur lors de l'export : " + e.getMessage()).show();
            }
        }
    }
    @FXML
    private void suppgroup() {
        IGroup groupeSelectionne = listGroupes.getSelectionModel().getSelectedItem();
        
        if (groupeSelectionne == null) {
            new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un groupe à supprimer.").show();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, 
            "Voulez-vous vraiment supprimer le groupe " + groupeSelectionne + " ?", 
            ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();

        if (confirm.getResult() == ButtonType.YES) {
            
            for (int i = 0; i < ListEtu.length() - 1; i++) {
                Etudiant etu = ListEtu.get(i);
                if (etu.getGroup() != null) {
                    if (groupeSelectionne instanceof GroupTD && etu.getGroup().getGroupTD() == groupeSelectionne) {
                        etu.getGroup().setGroupTD(null);
                    } else if (groupeSelectionne instanceof GroupTP && etu.getGroup().getGroupTP() == groupeSelectionne) {
                        etu.getGroup().setGroupTP(null);
                    }
                }
            }

            Group.ListGroup2.removeIf(link -> 
                (groupeSelectionne instanceof GroupTD && link.getGroupTD() == groupeSelectionne) ||
                (groupeSelectionne instanceof GroupTP && link.getGroupTP() == groupeSelectionne)
            );

            ListGroups.remove(groupeSelectionne);

            actualiserListeGroupes();
            
            new Alert(Alert.AlertType.INFORMATION, "Groupe supprimé avec succès.").show();
        }
    }
    @FXML
    private void ajouterGroup() {
        Promotion promoActuelle = comboPromo.getValue();
        if (promoActuelle == null) return;

        boolean isTD = rbTD.isSelected();
        int taille = spinTaille.getValue();
        
        String nom = "Group" + (isTD ? "TD" : "TP") + (ListGroups.size() + 1);
        IGroup nouveauGroupe;
        if (isTD) {
            nouveauGroupe = new GroupTD((int)System.currentTimeMillis(), nom, taille);
        } else {
            nouveauGroupe = new GroupTP((int)System.currentTimeMillis(), nom, taille);
        }
        ListGroups.add(nouveauGroupe);

        Group lien = new Group(promoActuelle); 
        if (isTD) {
            lien.setGroupTD((GroupTD) nouveauGroupe);
        } else {
            lien.setGroupTP((GroupTP) nouveauGroupe);
        }
        
        Group.ListGroup2.add(lien);

        actualiserListeGroupes();
    }
    
    
    
    
}