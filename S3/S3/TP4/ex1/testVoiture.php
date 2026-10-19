<?php 
    echo "<h1>Test Voiture</h1>";

    require_once("voiture.php");
    $voiture1 = new voiture(m : "Renault", c : "bleu", i : "123AB45");
    $voiture2 = new voiture(m : "Ferrari", c : "rouge", i : "456CD78");
    $voiture3 = new voiture(m : "Aston Martin", c : "gris", i : "JB007");

    $voiture1->afficher();
    $voiture2->afficher();
    $voiture3->afficher();

?>