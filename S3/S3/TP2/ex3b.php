<?php 

    $nbAnnees = 13;
    $capital = array(10000);
    $taux = 5;

    $anneeCourante = 2025;
    $capital[$anneeCourante] = 10000;
    $compterAnnees = 0;

    $seuil = 18000;

    while ($capital[$anneeCourante] < $seuil) {
        $capital[$anneeCourante  + 1] = $capital[$anneeCourante] * (1 + $taux / 100); // Calcul
        $capital[$anneeCourante + 1] = floor($capital[$anneeCourante + 1]);
        $anneeCourante++;
        $compterAnnees++;
    } 

    echo "<h1>Dépassement d'un seuil donné</h1>";
    echo "<p>seuil à atteindre : ".$seuil." €</p>";
    echo "<p>capital initial : ".$capital[0]." €</p>";
    echo "<p>taux d'intérêts : ".$taux." %</p>";
    echo "<p>capital en début d'année ".$compterAnnees." : ".$capital[$anneeCourante]."</p>";


?>