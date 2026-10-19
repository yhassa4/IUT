<?php
require_once("modele/Etudiant.php");
require_once("modele/Enseignant.php");

class controleurConnexion {

    public static function afficherPage() {
        $titre = "Connexion";
        include("vue/debut.php");
        include("vue/vueConnexion.php");
        include("vue/fin.html");
    }

    public static function connecter() {
        $login = $_POST['login'];
        $mdp = $_POST['mdp'];

        // Enseignant
        $userData = Enseignant::getByIdentifiant($login);

        if ($userData) {
            $passwordValide = ($mdp == $userData['password'] || password_verify($mdp, $userData['password']));
            
            if ($passwordValide) {
                $userObj = new Enseignant($userData);
                $_SESSION['user'] = $userObj;
                $_SESSION['role'] = $userData['nomrole']; 
                header("Location: routeur.php?controleur=controleurEtudiant&action=lireObjets");
                exit();
            }
        }

        // Etudiant
        $userEtu = Etudiant::getByIdentifiant($login);
        if ($userEtu) {
            $passwordValideEtu = ($mdp == $userEtu->get('password') || password_verify($mdp, $userEtu->get('password')));
            
            if ($passwordValideEtu) {
                $_SESSION['user'] = $userEtu;
                $_SESSION['role'] = 'etudiant';
                header("Location: routeur.php?controleur=controleurGlobal&action=accueil");
                exit();
            }
        }
        
        // 3. ÉCHEC
        header("Location: routeur.php?controleur=controleurConnexion&action=afficherPage&error=1");
        exit();
    }

    public static function deconnecter() {
        session_unset();
        session_destroy();
        header("Location: routeur.php");
        exit();
    }
}
?>