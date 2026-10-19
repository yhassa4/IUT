<?php 

    echo "Construction de l'objet Voiture";

    // Afficher le tableau $_GET
    echo "<pre>";
    print_r($_GET);
    echo "</pre>";

    // Récupérer ces données postées dans des variables,
    $marque = $_GET["marque"];
    $couleur = $_GET["couleur"];
    $immatriculation = $_GET["immatriculation"];

    // Créer une voiture ayant pour caractéristiques les données récupérées,
    require_once("Voiture.php");
    $voitureCreee = new Voiture(m : $marque, c : $couleur, i : $immatriculation);

    // Afficher 
    $voitureCreee->afficher();


    // retour au formulaire
    echo "<a href=".'formulaireVoiture.html'.">retour au formulaire</a>";


?>