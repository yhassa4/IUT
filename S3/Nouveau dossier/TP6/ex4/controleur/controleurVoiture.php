<?php
    $titre = "tous les voitures";
    //affichage de debut de fichier html
    include("vue/debut.php");

    //insertion du menu 
    include("vue/menu.html");

    //insertion des classes Voiture et connexion
    require_once("modele/voiture.php");
    require_once("config/connexion.php");

    //appel de la methode connexion
    Connexion::connect();

    //recup des voitures
    $lesVoitures= Voiture::getAllVoitures();

     //affichege des voitures  
    include("vue/voiture/lesVoitures.php");

    //affichage de la fin html 
    include("vue/fin.html");
    ?>