<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="utf-8">
       
    </head>
    <body>
        <h1>construction de l'objet Voiture</h1>
        <?php
        include_once("voiture.php");
        $v = new voiture($_GET['immatriculation'],$_GET['marque'],$_GET['couleur']);
        $v->afficher();
        
        echo "<pre>";
        print_r($_GET);
        echo"</pre>";

        
        ?> 
    </body>
</html>