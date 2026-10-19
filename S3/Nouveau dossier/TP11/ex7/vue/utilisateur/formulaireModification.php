<main>
    <form action="routeur.php" method="get">
        <input type="hidden" name="controleur" value="controleurUtilisateur">
        <input type="hidden" name="action" value="modifierUtilisateur">

        <div>
            <label for="login">Login</label>
            <input type="text" name="login" value="<?php echo $login; ?>" readonly>
        </div>
        <div>
            <label for="mdp">Mot de passe</label>
            <input type="password" name="mdp" value="<?php echo $mdp; ?>" >
        </div>
        <div>
            <label for="nom">Nom</label>
            <input type="text" name="nom" value="<?php echo $nom; ?>" >
        </div>
        <div>
            <label for="prenom">Prénom</label>
            <input type="text" name="prenom" value="<?php echo $prenom; ?>" >
        </div>
        <div>
            <label for="email">Email</label>
            <input type="email" name="email" value="<?php echo $email; ?>" >
        </div>
        <button type="submit">modifier l'utilisateur</button>
    </form>
</main>