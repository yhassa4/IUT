<?php
require_once("controleur/controleur.php");
require_once("modele/Etudiant.php");
require_once("modele/Enseignant.php");
require_once("modele/Groupe.php");
require_once("modele/Promotion.php");
require_once("modele/Sondage.php");

class controleurGlobal extends Controleur {

    public static function accueil() {
        $titre = "Accueil";

        if (isset($_SESSION['role'])) {
            require_once("modele/Etudiant.php");
            require_once("modele/Groupe.php"); 
            require_once("modele/Promotion.php"); 

            
            $nbEtudiants = Etudiant::count();
            $nbGroupes = Groupe::count(); 
            $nbPromotions = Promotion::count();

            include("vue/debut.php"); 

            if ($_SESSION['role'] === 'etudiant') {
                $etudiant = $_SESSION['user']; 
                $numPromo = $etudiant->get('NumPromo');

                $sql = "SELECT * FROM sondage WHERE NumPromo = ? AND typesondage = 1";
                $lesSondages = Sondage::getSondagesParPromo($numPromo);

                include("vue/vueAccueilEtudiant.php");
            }
            elseif ($_SESSION['role'] === 'enseignant') {
                $tab_obj = Etudiant::getAll(); 
                include("vue/vueAccueilEnseignant.php");
            } 
            else {
                $nbEtudiants = Etudiant::count();
                $nbEnseignants = Enseignant::count();
                $nbPromotions = Promotion::count();
                include("vue/vueEnsemble.php"); 
            }

            include("vue/fin.html");
        }
    }
}
?>