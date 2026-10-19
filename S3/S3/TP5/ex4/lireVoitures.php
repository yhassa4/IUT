<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Les voitures</title>
</head>
<body>
    <?php
        require_once("voiture.php");
        require_once("connexion.php");

        /
        $lesVoitures = Voiture::getAllVoitures();

        
        echo "<ul>";
        foreach ($lesVoitures as $voiture) {
            $voiture->afficher();
        }
        echo "</ul>";
    ?>
</body>
</html>