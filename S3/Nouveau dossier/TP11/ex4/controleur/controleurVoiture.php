<?php
    
    require_once("modele/voiture.php");
      require_once("./controleur/controleur.php");
            require_once("./config/connexion.php");

            Connexion::connect();

    //def de controleur voiture
    class ControleurVoiture extends Controleur{

        
        protected static $objet = "voiture";
        protected static $cle ="immatriculation";


        public static function afficherFormulaireCreation() {
            $titre = "Créer une voiture";
            include("vue/debut.php");
            include("vue/menu.html");
            include("vue/voiture/formulaireCreation.html");
            include("vue/fin.html");
        }
        
        public static function creerVoiture() {
            $titre = "Créer une voiture";
            $i = $_GET["immatriculation"];
            $m = $_GET["marque"];
            $c = $_GET["couleur"];
            
            $succes = voiture::addVoiture($i, $m, $c);
            
            if ($succes) {
                static::lireObjets(); 
            } else {
                static::afficherFormulaireCreation(); 
            }
        }

        

       
    }
    ?>