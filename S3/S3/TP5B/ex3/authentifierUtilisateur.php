<?php 
    require_once("utilisateur.php");
    
    if (utilisateur::checkMDP()) {
        session_start();
        $_SESSION['username'] = $_POST['login'];
        header("Location: lireUtilisateur.php");
    } else {
        header("Location: formulaireAuthentification.php");
    }
?>