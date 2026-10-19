<?php
    
    require_once("modele/voiture.php");
      require_once("./controleur/controleur.php");
            require_once("./config/connexion.php");

            Connexion::connect();

    //def de controleur voiture
    class ControleurVoiture extends Controleur{

        
        protected static $objet = "voiture";
        protected static $cle ="immatriculation";

        public static function lireVoiture(){
            $titre ="une voiture";
            $i = $_GET["immatriculation"];
            $v = Voiture::getObjectByid(i: $i);
            include("vue/debut.php");
            include("vue/menu.html");
            if (!$v)
                include("vue/voiture/erreur.php");
            else 
                include("vue/unObjet.php");
            include("vue/fin.html");

        }
    }
    ?>