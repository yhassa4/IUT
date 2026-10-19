<?php
    $titre = "tous les utilisateurs";
    //affichage de debut de fichier html
    include("vue/debut.php");

    //insertion du menu 
    include("vue/menu.html");

    //insertion des classes Utilisateur et connexion 
    require_once("modele/utilisateur.php");
    require_once("config/connexion.php");

    //appel de la methode connexion
    Connexion::connect();

    //recup des utilisateurs
    $lesUtilisateurs = Utilisateur::getAllUtilisateurs();

    //affichege des utilisateurs 
    include("vue/utilisateur/lesUtilisateurs.php");

    //affichage de la fin html 
    include("vue/fin.html");
    ?>