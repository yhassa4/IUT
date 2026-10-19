
    <?php 
        $titre = "Tout les utilisateurs";
        include("./vue/debut.php");
        include("./vue/menu.html");
        include("./vue/fin.html");


        require_once("./modele/utilisateur.php");

        $lesUtilisateurs = Utilisateur::getAllUtilisateurs();
        include("./vue/utilisateur/lesUtilisateurs.php");
      
    ?>
