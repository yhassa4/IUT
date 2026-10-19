package controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.chart.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Base.*;
import java.util.ArrayList;
import java.util.Date;

public class ResponsableController {

    // Labels pour les statistiques rapides (compteurs)
    @FXML private Label lblTotalEtudiants, lblSondagesCours, lblGroupesNonPublies, lblGroupesPublies;
    
    // Tableau et colonnes pour le suivi des sondages
    @FXML private TableView<Sondage> tableSondages;
    @FXML private TableColumn<Sondage, String> colNomSondage, colPromo, colParticipation, colDateFin;
    
    // Graphiques de type "Camembert" (PieChart) pour les statistiques visuelles
    @FXML private PieChart chartPromos; // Répartition par promo
    @FXML private PieChart chartRedoublants, chartAlternants, chartParcours;
    
    // Filtre par promotion et barre de navigation
    @FXML private ComboBox<Promotion> comboPromoFilter;
    @FXML private NavBarController navBarController;

    @FXML
    public void initialize() {
        // Prépare la structure du tableau
        configurerTableau();
        
        // Remplit le menu de filtrage avec les promotions existantes
        comboPromoFilter.setItems(FXCollections.observableArrayList(Promotion.ListPromo));
        
        // Relance les graphiques dès qu'on change la promotion dans le menu
        comboPromoFilter.setOnAction(event -> remplirGraphiques());
        
        // Met à jour la barre de navigation sur l'onglet "Dashboard"
        navBarController.updateNav(0);
        
        // Charge les données initiales
        actualiserDonnees();
    }

    // Réinitialise le filtre pour afficher les statistiques de tous les étudiants
    @FXML
    private void resetFiltre() {
        comboPromoFilter.setValue(null);
        remplirGraphiques();
    }

    // Définit comment les données des sondages sont affichées dans les colonnes
    private void configurerTableau() {
        colNomSondage.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNomSondage()));
        colPromo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPromo().getNomPromo()));
        
        // Calcule et affiche le pourcentage de participation en temps réel
        colParticipation.setCellValueFactory(data -> {
            Sondage s = data.getValue();
            double taux = calculerTaux(s);
            return new SimpleStringProperty(String.format("%.1f%%", taux));
        });
        
        // Affiche la date de fin ou "Terminer" si le sondage est clos
        colDateFin.setCellValueFactory(data -> {
            Sondage s = data.getValue();
            Date dateFin = (Date)s.getdateFin();
            if(s.estTerminer()) return new SimpleStringProperty("Terminer");
            return new SimpleStringProperty(dateFin.toString());
        });
    }

    // Logique de calcul du taux de participation (Réponses reçues / Total étudiants concernés)
    private double calculerTaux(Sondage s) {
        int totalEtuPromo = 0;
        // Compte combien d'étudiants appartiennent à la promo du sondage
        for (int i = 0; i < Etudiant.ListEtu.length()-1; i++) {
            Etudiant e = Etudiant.ListEtu.get(i);
            if (e.getPromotion().equals(s.getPromo())) totalEtuPromo++;
        }

        if (totalEtuPromo == 0) return 0.0;

        // Compte les réponses uniques (un étudiant peut avoir répondu à plusieurs questions)
        ArrayList<Etudiant> etudiantsAyantRepondu = new ArrayList<>();
        for (Repond r : Repond.ListRepond) {
            if (r.getSondage().equals(s)) {
                Etudiant e = r.getEtudiant();
                if (!etudiantsAyantRepondu.contains(e)) etudiantsAyantRepondu.add(e);
            }
        }
        return (double) etudiantsAyantRepondu.size() / totalEtuPromo * 100;
    }

    // Met à jour les chiffres globaux du tableau de bord
    private void actualiserDonnees() {
        lblTotalEtudiants.setText(String.valueOf(Etudiant.ListEtu.length()));

        // Compte les sondages actuellement ouverts
        int actifs = 0;
        for (Sondage s : Sondage.Sondages) {
            if (s.estEnCours()) actifs++;
        }
        lblSondagesCours.setText(String.valueOf(actifs));

        // Compte les groupes publiés (actifs) et non publiés
        int cpt = 0;
        for(IGroup g : Group.GroupList) {
            if(g.isActif()) cpt++;
        }
        lblGroupesNonPublies.setText(String.valueOf(Group.GroupList.size() - cpt));
        lblGroupesPublies.setText(String.valueOf(cpt));
        
        tableSondages.setItems(FXCollections.observableArrayList(Sondage.Sondages));
        remplirGraphiques();
        tableSondages.refresh();
    }

    // Génère les données pour les graphiques circulaires selon le filtre sélectionné
    private void remplirGraphiques() {
        int redoublants = 0, nonRedoublants = 0;
        int alternants = 0, classiques = 0;
        java.util.Map<String, Integer> compteParcours = new java.util.HashMap<>();

        Promotion promoSelectionnee = comboPromoFilter.getValue();

        for (int i = 0; i < Etudiant.ListEtu.length()-1; i++) {
            Etudiant e = Etudiant.ListEtu.get(i);

            // Filtre : on prend l'étudiant si aucune promo n'est choisie OU si c'est la sienne
            if (promoSelectionnee == null || e.getPromotion().equals(promoSelectionnee)) {
                if (e.estRedoublant()) redoublants++; else nonRedoublants++;
                if (e.getApprentissage()) alternants++; else classiques++;

                // Compte la répartition par parcours (informatique, réseaux, etc.)
                String nomParcours = e.getParcour().getNomParcour();
                compteParcours.put(nomParcours, compteParcours.getOrDefault(nomParcours, 0) + 1); 
            }
        }

        // Envoi des données aux graphiques JavaFX
        chartRedoublants.setData(FXCollections.observableArrayList(
            new PieChart.Data("Redoublants", redoublants),
            new PieChart.Data("Non Redoublants", nonRedoublants)
        ));

        chartAlternants.setData(FXCollections.observableArrayList(
            new PieChart.Data("Alternants", alternants),
            new PieChart.Data("Initiaux", classiques)
        ));

        // Dynamisation du graphique des parcours
        ObservableList<PieChart.Data> parcourspi = FXCollections.observableArrayList();
        for (var entry : compteParcours.entrySet()) {
            parcourspi.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        chartParcours.setData(parcourspi);
    }
}