<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulaire Utilisateur</title>
</head>
<body>
    <?php 
        include("menu.php");
        if (!isset($_SESSION['username'])) {
            header("Location: lireVoitures.php");
        }
    ?>
    <h2>Formulaire Utilisateur</h2>
    <form action="creerUtilisateur.php" method="post">
        <label for="login">Login</label>
        <input type="text" name="login" id="login" required><br>
        <label for="mdp">Mot de passe</label>
        <input type="password" name="mdp" id="mdp" required><br>
        <label for="nom">Nom</label>
        <input type="text" name="nom" id="nom" required><br>
        <label for="prenom">Prenom</label>
        <input type="text" name="prenom" id="prenom" required><br>
        <label for="email">E-mail</label>
        <input type="email" name="email" id="email" required><br>
        <input type="submit" value="Envoyer">
    </form>
</body>
</html>