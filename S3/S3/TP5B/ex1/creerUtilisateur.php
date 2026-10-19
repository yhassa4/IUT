<?php 

    echo "Construction de l'objet Utilisateur";

    // Afficher le tableau $_GET
    echo "<pre>";
    print_r($_GET);
    echo "</pre>";

    /*
    // Récupérer ces données postées dans des variables,
    $marque = $_GET["marque"];
    $couleur = $_GET["couleur"];
    $immatriculation = $_GET["immatriculation"]; */

    // ajouter la voiture dans base de donnees
    require_once("utilisateur.php");
    if (utilisateur::addUtilisateur()) {
        header("Location: lireUtilisateur.php");
    } else {
        $message="Erreur lors de l'insertion";
        echo '<script type="text/javascript"> window.alert("ERROR : '.$message.'");</script>';
    }

    // retour au formulaire
    echo "<a href=".'formulaireVoiture.php'.">retour au formulaire</a>";


?>