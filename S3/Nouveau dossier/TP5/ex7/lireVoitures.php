<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8">
    <title> les voitures </title>
  </head>
  <body>
    <h1>les voitures</h1>
    <?php
    include("menu.html");
    require_once("connexion.php");
    require_once("voiture.php");
    
    connexion::connect();

    //exo3
    //$requete = 'SELECT * FROM voiture ';
    //$resultat = connexion::pdo()->query($requete);
    //$resultat ->setFetchmode(PDO::FETCH_CLASS,"voiture");
    //$savoiture = $resultat ->fetchAll();

    //echo"<pre>";
    //print_r($savoiture);
    //echo"</pre>";

    
    $savoiture = voiture::getAllVoitures();

    echo"<ul>";
    foreach($savoiture as $voiture){
        echo"<li>";

        $voiture->afficher();
         echo"</li>";
    }
    echo"</ul>";


?>
  </body>
</html>