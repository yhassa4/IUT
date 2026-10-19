<?php 

    $capitalInitial = $_GET['capitalInitial'];
    $nbAnnees = $_GET['nbAnnees'];
    $seuil = $_GET['seuil'];

    $compterAnnees = 0;
    $capital = $capitalInitial;
    $tauxCalcule = null;

    $isValide = true;

    do {
        $tauxCalcule += 1;
        $capital = $capitalInitial * (1 + $tauxCalcule / 100) ** $nbAnnees;
        $capital = floor($capital);
        $compterAnnees++;
        if ($capital >= $seuil) {
            $isValide = false;
        }
    } while ($isValide);

    echo "<h1>calcul du taux minimum pour dépasser un seuil en un temps donné</h1>";
    echo "<p>Seuil à atteindre : ".$seuil." €</p>";
    echo "<p>Capital initial : ".$capitalInitial." €</p>";
    echo "<p>Nombre d'années : ".$nbAnnees."</p>";
    echo "<p>Taux d'intérêts calculé : ".$tauxCalcule." %</p>";
    echo "<p>Capital en début d'année ".$compterAnnees." : ".$capital." €</p>";

?>