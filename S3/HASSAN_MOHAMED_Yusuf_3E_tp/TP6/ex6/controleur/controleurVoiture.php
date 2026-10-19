<?php
    
    require_once("modele/voiture.php");

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
    }
    ?>