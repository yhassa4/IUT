<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu</title>
</head>
<style>
    .menu {
        display: flex;
        justify-content: left;
        gap: 30px;
        margin: 10px;
    }

    .menu > a {
        color: #000000;
        text-decoration: none;
        border: #000000 solid 1px;
        border-radius: 10px;
        padding: 10px;
        box-shadow: 1px 1px 10px #000000;
    }
</style>
<body>
    <?php 
        require_once("utilisateur.php");
        session_start();
        if (!isset($_SESSION['username'])) {
            echo '<div class="menu">';
            echo '<a href="lireVoitures.php">liste des voitures</a>';
            echo '<a href="lireUtilisateur.php">liste des utilisateur</a>';
            echo '<a href="formulaireAuthentification.php">S\'authentifier</a>';
            echo '</div>';
        } else {
            require_once("utilisateur.php");

            $user = Utilisateur::getUser($_SESSION['username']);
            $nom_user = $user->getNom();
            $prenom_user = $user->getPrenom();

            echo "<h1> Bienvenue $nom_user $prenom_user</h1>";
            
            echo '<div class="menu">';
            echo '<a href="lireVoitures.php">liste des voitures</a>';
            echo '<a href="lireUtilisateur.php">liste des utilisateur</a>';
            echo '<a href="formulaireVoiture.php">Formulaire voiture</a>';
            echo '<a href="formulaireUtilisateur.php">Formulaire utilisateur</a>';
            echo '<a href="finirSession.php">Se déconnecter</a>';
            echo '</div>';
        }


    ?>
</body>
</html>