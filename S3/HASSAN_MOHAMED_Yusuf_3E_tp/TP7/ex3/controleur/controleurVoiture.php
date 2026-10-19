<?php
    
    require_once("modele/voiture.php");
            require_once("./config/connexion.php");

            Connexion::connect();

    //def de controleur voiture
    class ControleurVoiture{

        
        public static function lireVoitures(){
            $titre = "toutes les voitures";
            $lesVoitures = voiture::getAllVoitures();
            include("vue/debut.php");
            include("vue/menu.html");
            include("vue/voiture/lesVoitures.php");
            include("vue/fin.html");
        }

        public static function lireVoiture(){
            $titre ="une voiture";
            $i = $_GET["immatriculation"];
            $v = Voiture::getVoitureByImmat(i: $i);
            include("vue/debut.php");
            include("vue/menu.html");
            if (!$v)
                include("vue/voiture/erreur.php");
            else 
                include("vue/voiture/uneVoiture.php");
            include("vue/fin.html");

        }
    }
    ?>