<?php 
require_once("modele/modele.php");
require_once("modele/Etudiant.php");
require_once("modele/Enseignant.php");

session_start(); 

require_once("./config/connexion.php");
Connexion::connect();

require_once("controleur/controleur.php");
require_once("controleur/controleurConnexion.php");

// Définition des variables
$controleur = $_GET["controleur"] ?? "controleurConnexion";
$action = $_GET["action"] ?? "afficherPage";
$tableauControleurs = ["controleurEnseignant", "controleurEtudiant", "controleurGroupe", "controleurPromotion", "controleurSondage", "controleurGlobal", "controleurConnexion"];

// Sécurité : Accès aux actions publiques
$actions_publiques = ["afficherPage", "connecter", "deconnecter"];
if (!isset($_SESSION['user']) && !in_array($action, $actions_publiques)) {
    header("Location: routeur.php?controleur=controleurConnexion&action=afficherPage");
    exit();
}

if (isset($_SESSION['role'])) {
    $role = $_SESSION['role'];
    
    // Liste large des rôles ayant des droits de gestion
    $liste_responsables = [
        1, 2, 3, 4, 
        '1', '2', '3', '4',
        'Responsable semestre', 
        'Responsable d annee', 
        'Responsable de fillieres', 
        'Responsable de formation', 
        'admin', 'responsable', 'enseignant'
    ];

    $is_responsable = in_array($role, $liste_responsables);

    // etudiants
    
    if ($role === 'etudiant') {
        $actions_etu = ["mesNotes", "mesGroupes", "voirPromotion", "afficherCovoiturage", "repondre", "voter", "accueil", "deconnecter"];

        $controleur_ok = ($controleur === "controleurEtudiant" || $controleur === "controleurSondage" || $controleur === "controleurGlobal" || $controleur === "controleurConnexion");

        if (!$controleur_ok || !in_array($action, $actions_etu)) {
            $controleur = "controleurGlobal";
            $action = "accueil"; 
        }
    }

    // Responsable/Enseignant
    else if ($is_responsable) {
        $is_super_admin = ($role == 4 || $role == '4' || $role === 'Responsable de formation' || $role === 'admin');

        if ($controleur === "controleurEnseignant") {
            $actions_critiques = ["creer", "modifier", "supprimer", "enregistrer", "afficherFormulaire"];
            
            if (in_array($action, $actions_critiques) && !$is_super_admin) {
                $controleur = "controleurGlobal";
                $action = "accueil";
            }
        }
    }
}

// Chargement du fichier contrôleur
if(in_array($controleur, $tableauControleurs)){
    require_once("controleur/$controleur.php");
} else {
    $controleur = "controleurGlobal";
    require_once("controleur/$controleur.php");
}

// Vérification de la méthode et exécution
if(!method_exists($controleur, $action)){
    $action = (isset($_SESSION['user'])) ? "accueil" : "afficherPage";
    $controleur = (isset($_SESSION['user'])) ? "controleurGlobal" : "controleurConnexion";
    if (!class_exists($controleur)) {
        require_once("controleur/$controleur.php");
    }
}

$controleur::$action();
?>