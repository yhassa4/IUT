<?php 
    require_once("utilisateur.php");
    
    if (utilisateur::checkMDP()) {
        header("Location: lireUtilisateur.php");
    } else {
        header("Location: formulaireAuthentification.php");
    }
?>