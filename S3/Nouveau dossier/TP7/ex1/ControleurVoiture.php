<?php
    require_once("modele/voiture.php");

    //def de controleur voiture
    class ControleurVoiture{

        
        public static function lireVoiture(){
            $titre = "toutes les voitures";
            $lesUtilisateurs = voiture::getAllVoitures();
            include("vue/debut.php");
            include("vue/menu.html");
            include("vue/voiture/lesVoitures.php");
            include("vue/fin.html");
        }
    }