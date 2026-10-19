<?php 

    echo "Construction de l'objet Voiture";

    if (!isset($_SESSION['username'])) {
        header("Location: lireVoitures.php");
    }

    // Récupérer ces données postées dans des variables,
    $marque = $_GET["marque"];
    $couleur = $_GET["couleur"];
    $immatriculation = $_GET["immatriculation"];

    // ajouter la voiture dans base de donnees
    require_once("voiture.php");
    if (voiture::addVoiture()) {
        header("Location: lireVoitures.php");
    } else {
        $message="Erreur lors de l'insertion";
        echo '<script type="text/javascript"> window.alert("ERROR : '.$message.'");</script>';
    }

    // retour au formulaire
    echo "<a href=".'formulaireVoiture.php'.">retour au formulaire</a>";


?>