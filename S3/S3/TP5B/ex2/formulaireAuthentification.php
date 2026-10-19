<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulaire Utilisateur</title>
</head>
<body>
    <?php 
        include("menu.html");
    ?>
    <h2>Formulaire Authentification</h2>
    <form action="authentifierUtilisateur.php" method="post">
        <label for="login">Login</label>
        <input type="text" name="login" id="login" required><br>
        <label for="mdp">Mot de passe</label>
        <input type="password" name="mdp" id="mdp" required><br>
        <input type="submit" value="Envoyer">
    </form>
</body>
</html>