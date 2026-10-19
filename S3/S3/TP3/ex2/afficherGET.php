<?php

    echo '<h1>Affichage de $_GET</h1>';

    //$nom = $_GET['nom'];
    //$prenom = $_GET['prenom'];
    //$age = $_GET['age'];
    // afficherGET.php?nom=Dupont&prenom=Antoine&age=25

    /*
    $a = $_GET['a'];
    $b = $_GET['b'];
    $c = $_GET['c'];
    $login = $_GET['login'];
    $mdp = $_GET['mdp'];
    */
    // afficherGET.php?a=1&b=3&c=17&login=toto&mdp=12345


    echo '<pre>';
    print_r($_GET);
    echo '</pre>';

    echo "<a href=".'formulaireGET.html'.">retour au formulaire</a>";
?>
