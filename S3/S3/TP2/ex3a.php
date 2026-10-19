<?php 

    $nbAnnees = 10;
    $capital = array(10000);
    $taux = 5;

    $anneeCourante = 2025;

    $capital[0] = 10000;

    //Extrait de code
    for ($n = 0 ; $n < $nbAnnees ; $n++) { // Boucle du nombre d’années
        echo "Solde année ".$anneeCourante." = ".$capital[$n]; echo "<br>"; // Affichage
        $capital[$n + 1] = $capital[$n] * (1 + $taux / 100); // Calcul
        $capital[$n + 1] = floor($capital[$n + 1]);
        $anneeCourante++;
    }

?>