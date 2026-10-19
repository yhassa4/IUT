<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="utf-8">
       
    </head>
    <body>
        <h1>tests de construction d'objets voiture</h1>
        <?php
        require_once("voiture.php");

        $Voiture1 = new Voiture("123AB45","Renault","blue" );
        $Voiture2 = new Voiture("456CD78","Ferrari","rouge" );
        $Voiture3 = new Voiture( "JB007", "Aston Martin", "gris" );
        
        $Voiture1->afficher();
        $Voiture2->afficher();
        $Voiture3->afficher();


        



        
        ?> 
    </body>
</html>