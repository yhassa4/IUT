<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lire utilisateurs</title>
</head>
<body>
    <?php 
        include("menu.html");

        require_once("utilisateur.php");

        $lesUtilisateurs = Utilisateur::getAllUtilisateurs();

        foreach ($lesUtilisateurs as $utilisateur) {
            $utilisateur->afficher();
        }
    ?>
</body>
</html>