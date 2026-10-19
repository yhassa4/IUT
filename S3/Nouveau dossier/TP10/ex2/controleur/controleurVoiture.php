<?php
    
    require_once("modele/voiture.php");
      require_once("./controleur/controleur.php");
            require_once("./config/connexion.php");

            Connexion::connect();

    //def de controleur voiture
    class ControleurVoiture extends Controleur{

        
        protected static $objet = "voiture";
        protected static $cle ="immatriculation";

       
    }
    ?>