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

        public static function afficherFormulaireModification() {
            $cle = static::$cle;
            $id = $_GET[$cle]; 
            
      
            $v = voiture::getObjectById($id); 
            
            if ($v) {
                $i = $v->get('immatriculation');
                $m = $v->get('marque');
                $c = $v->get('couleur');
                
                $titre = "modifier voiture";
                include("vue/debut.php");
                include("vue/menu.html");
                include("vue/voiture/formulaireModification.php");
                include("vue/fin.html");
            }
        }

        public static function modifierVoiture() {
            $i = $_GET["immatriculation"];
            $m = $_GET["marque"];
            $c = $_GET["couleur"];


            voiture::updateVoiture($i, $m, $c);
            static::lireObjets();
        }

        

       
    }
    ?>