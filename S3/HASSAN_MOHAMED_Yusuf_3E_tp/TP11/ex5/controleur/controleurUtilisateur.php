<?php
    
    require_once("modele/utilisateur.php");
    require_once("./config/connexion.php");

        require_once("./controleur/controleur.php");
    Connexion::connect();

    //def de controleur voiture
    class ControleurUtilisateur extends Controleur{

        protected static $objet = "utilisateur";
        protected static $cle = "login";


        public static function afficherFormulaireCreation() {
            $titre = "création utilisateur";
            include("vue/debut.php");
            include("vue/menu.html");
            include("vue/utilisateur/formulaireCreation.html");
            include("vue/fin.html");
        }

        public static function creerUtilisateur() {
            $login = $_GET["login"];
            $mdp = $_GET["mdp"];
            $nom = $_GET["nom"];
            $prenom = $_GET["prenom"];
            $email = $_GET["email"];
            

            $succes = Utilisateur::addUtilisateur($login, $mdp, $nom, $prenom, $email);
            
            if ($succes) {
                static::lireObjets(); 
            } else {
                static::afficherFormulaireCreation(); 
            }
        }
        

          
    }
    ?>