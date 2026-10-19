<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="utf-8">
        <title>Ceci est la page générée par ex1.php </title>
    </head>
    <body>
        <h1>Ceci est la page générée par l'interpretation de  ex1.php</h1>
        <?php
        $today = getdate();
        echo "<pre>";
        print_r($today);
        echo"</pre>";

        $u = date('l jS \of F Y h:i:s A');
        $f = get_current_user();
        echo "<p>l'utilisateur $f a demandé cette page le $u</p>";
        ?> 
    </body>
</html>
    