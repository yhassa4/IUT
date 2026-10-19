
    <?php 
        $titre = "Tout les utilisateurs";
        include("./vue/debut.php");
        include("./vue/menu.html");

        require_once("./modele/utilisateur.php");

        $lesUtilisateurs = Utilisateur::getAllUtilisateurs();

        foreach ($lesUtilisateurs as $utilisateur) {
            $utilisateur->afficher();
        }
    ?>
</body>
</html>