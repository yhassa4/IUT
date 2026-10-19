
    <?php
         $titre = "Toutes les voitures";

        include("./vue/menu.html");

        require_once("./modele/voiture.php");
        require_once("./config/connexion.php");

        
        $lesVoitures = Voiture::getAllVoitures();

       
        echo "<ul>";
        foreach ($lesVoitures as $voiture) {
            $voiture->afficher();
        }
        echo "</ul>";
    ?>
</body>
</html>