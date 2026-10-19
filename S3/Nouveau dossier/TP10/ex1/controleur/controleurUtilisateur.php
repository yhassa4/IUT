<?php
    
    require_once("modele/utilisateur.php");
    require_once("./config/connexion.php");

        require_once("./controleur/controleur.php");
    Connexion::connect();

    //def de controleur voiture
    class ControleurUtilisateur extends Controleur{

        protected static $objet = "utilisateur";
        protected static $cle = "login";
        

           public static function lireUtilisateur(){
            $titre ="un utilisateur";
            $i = $_GET["login"];
            $v = utilisateur::getObjectByid(i: $i);
            include("vue/debut.php");
            include("vue/menu.html");
            if (!$v)
                include("vue/erreur.php");
            else 
                include("vue/unObjet.php");
            include("vue/fin.html");

        }
    }
    ?>