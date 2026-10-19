<?php 

    echo "Construction de l'objet Voiture";

    
    echo "<pre>";
    print_r($_GET);
    echo "</pre>";

    
    $marque = $_GET["marque"];
    $couleur = $_GET["couleur"];
    $immatriculation = $_GET["immatriculation"];

    
    require_once("voiture.php");
    if (voiture::addVoiture()) {
        header("Location: lireVoitures.php");
    } else {
        $message="Erreur lors de l'insertion";
        echo '<script type="text/javascript"> window.alert("ERROR : '.$message.'");</script>';
    }

    
    echo "<a href=".'formulaireVoiture.php'.">retour au formulaire</a>";


?>