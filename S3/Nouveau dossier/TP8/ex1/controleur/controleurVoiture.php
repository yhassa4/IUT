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
            foreach ($lesVoitures as $voiture) {
                $aff[]= "<p> voiture immatriculée" ;
                $aff[]= $voiture->get('immatriculation'); 
                $aff[]='<a class=bouton href="routeur.php?controleur=controleurVoiture&action=lireVoiture&immat='.$voiture->get('immatriculation').'">détails</a>';
                $aff[]="</p>";
            }

            include("vue/voiture/lesVoitures.php");
            include("vue/fin.html");
            }

        public static function lireVoiture(){
            $titre ="une voiture";
            $i = $_GET["immat"];
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