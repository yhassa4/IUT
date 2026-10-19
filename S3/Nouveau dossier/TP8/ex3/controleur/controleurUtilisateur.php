<?php
    
    require_once("modele/utilisateur.php");
    require_once("./config/connexion.php");
    Connexion::connect();

    //def de controleur voiture
    class ControleurUtilisateur{

        
        public static function lireUtilisateurs(){
            $titre = "tous les utilisateurs";
            $lesUtilisateurs = Utilisateur::getAll();
            include("vue/debut.php");
            include("vue/menu.html");
            foreach ($lesUtilisateurs as $utilisateur) {
                $aff[]= "<p> utilisateur " ;
                $aff[]= $utilisateur->get('login'); 
                $aff[]= '<a class=bouton href="routeur.php?controleur=controleurUtilisateur&action=lireUtilisateur&login='.$utilisateur->get('login').'">détails</a>';
                $aff[]= "</p>";
            }
            include("vue/utilisateur/lesUtilisateurs.php");
            include("vue/fin.html");
            }

           public static function lireUtilisateur(){
            $titre ="un utilisateur";
            $i = $_GET["login"];
            $v = utilisateur::getUtilisateureBylogin(i: $i);
            include("vue/debut.php");
            include("vue/menu.html");
            if (!$v)
                include("vue/utilisateur/erreur.php");
            else 
                include("vue/utilisateur/unUtilisateur.php");
            include("vue/fin.html");

        }
    }
    ?>