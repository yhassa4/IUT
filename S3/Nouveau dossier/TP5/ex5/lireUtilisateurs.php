<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8">
    <title> les utilisateurs </title>
  </head>
  <body>
    <h1>les utilisateurs</h1>
    <?php
    include("menu.html");
    require_once("connexion.php");
    require_once("utilisateurs.php");
    
    connexion::connect();

    
    //$requete = 'SELECT * FROM voiture ';
    //$resultat = connexion::pdo()->query($requete);
    //$resultat ->setFetchmode(PDO::FETCH_CLASS,"voiture");
    //$savoiture = $resultat ->fetchAll();

    //echo"<pre>";
    //print_r($savoiture);
    //echo"</pre>";

    
    $lesutilisateurs = utilisateurs::getAllUtilisateurs();

    echo"<ul>";
    foreach($lesutilisateurs as $lesutilisateurs){
        echo"<li>";

        $lesutilisateurs->afficher();
         echo"</li>";
    }
    echo"</ul>";


?>
  </body>
</html>