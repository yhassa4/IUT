<?php

    echo "<h1>Utilisateur est créé</h1>";

    $loginCree = $_POST["login"];
    $mdpCree = $_POST["mdp"];
    $nomCree = $_POST["nom"];
    $prenomCree = $_POST["prenom"];
    $emailCree = $_POST["email"];

    require_once("Utilisateur.php");
    $utilisateurCree = new Utilisateur(l : $loginCree, m : $mdpCree, n : $nomCree, pn : $prenomCree, e : $emailCree);

    $utilisateurCree->afficher();

    echo "<a href="."formulaireUtilisateur.html".">retour au formulaire</a>";

?>
