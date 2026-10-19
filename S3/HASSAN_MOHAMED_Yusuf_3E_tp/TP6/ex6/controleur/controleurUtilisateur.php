<?php
    
    require_once("modele/utilisateur.php");

    //def de controleur voiture
    class ControleurUtilisateur{

        
        public static function lireUtilisateurs(){
            $titre = "tous les utilisateurs";
            $lesUtilisateurs = Utilisateur::getAllUtilisateurs();
            include("vue/debut.php");
            include("vue/menu.html");
            include("vue/utilisateur/lesUtilisateurs.php");
            include("vue/fin.html");
        }
    }
    ?>