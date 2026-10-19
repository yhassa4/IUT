<?php
    
    require_once("modele/utilisateur.php");
    require_once("./config/connexion.php");

        require_once("./controleur/controleur.php");
    Connexion::connect();

    //def de controleur voiture
    class ControleurUtilisateur extends Controleur{

        protected static $objet = "utilisateur";
        protected static $cle = "login";
        

          
    }
    ?>