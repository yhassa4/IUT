<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8">
    <title> les voitures </title>
  </head>
  <body>
    <?php
    require_once("connexion.php");
    require_once("voiture.php");
    
    connexion::connect();

    $requete = 'SELECT * FROM voiture ';
    $resultat = connexion::pdo()->query($requete);
    $resultat ->setFetchmode(PDO::FETCH_CLASS,"voiture");
    $savoiture = $resultat ->fetchAll();

    print_r($savoiture);
    echo"<ul>";
    foreach($savoiture as $voiture){

        $voiture->afficher();
    }
    echo"</ul>";


?>
  </body>
</html>