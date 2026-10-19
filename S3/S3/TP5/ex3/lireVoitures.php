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

        Connexion::connect();

        $requete = "SELECT * FROM voiture;";

        
        $resultat = connexion::pdo()->query($requete);

        
        $resultat->setFetchmode(PDO::FETCH_CLASS, "voiture");
        $lesVoitures = $resultat->fetchAll();

        
        echo "<pre>";
        print_r($lesVoitures);
        echo "</pre>";

        
        echo "<ul>";
        foreach ($lesVoitures as $voiture) {
            $voiture->afficher();
        }
        echo "</ul>";
    ?>
</body>
</html>