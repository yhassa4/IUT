<?php
    
    require_once("modele/Promotion.php");
    require_once("./config/connexion.php");
    Connexion::connect();

    
    class ControleurPromotion extends Controleur{
        

        protected static $objet = "Promotion";
        protected static $cle = "login";
    }
    ?>