<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="utf-8">
        <title>Ceci est la page générée par ex1.php </title>
    </head>
    <body>
        <h1>Affichage de $_GET</h1>
        <?php
        $today = getdate();
        echo "<pre>";
        print_r($_GET);
        echo"</pre>";

        
        ?> 
    </body>
</html>