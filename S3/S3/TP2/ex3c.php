<?php 

    $nbAnnees = 13;
    $capitalInitial = $_GET["capitalInitial"];
    $taux = $_GET["taux"];

    $anneeCourante = 2025;
    $capital[$anneeCourante] = $capitalInitial;
    $compterAnnees = 0;

    $seuil = $_GET["seuil"];

    while ($capital[$anneeCourante] < $seuil) {
        $capital[$anneeCourante  + 1] = $capital[$anneeCourante] * (1 + $taux / 100); // Calcul
        $capital[$anneeCourante + 1] = floor($capital[$anneeCourante + 1]);
        $anneeCourante++;
        $compterAnnees++;
    } 

    echo "<h1>Dépassement d'un seuil donné</h1>";
    echo "<p>seuil à atteindre : ".$seuil." €</p>";
    echo "<p>capital initial : ".$capitalInitial." €</p>";
    echo "<p>taux d'intérêts : ".$taux." %</p>";
    echo "<p>capital en début d'année ".$compterAnnees." : ".$capital[$anneeCourante]."</p>";


?>