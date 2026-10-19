<?php 

    echo "<h1>Affichage de la voiture</h1>";
    
    echo '<pre>';
    print_r($_GET);
    echo '</pre>';

    echo "<p>voiture ".$_GET["immatriculation"]." de marque ".$_GET["marque"]." et de couleur ".$_GET["couleur"]."</p>";

    echo "<a href=".'formulaireGET.html'.">retour au formulaire</a>";

?>