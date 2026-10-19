
    <?php
         $titre = "Toutes les voitures";
        include("./vue/debut.php");
        include("./vue/menu.html");
        include("./vue/fin.html");


        require_once("./modele/voiture.php");
        require_once("./config/connexion.php");

        
        $lesVoitures = Voiture::getAllVoitures();

        include("./vue/voiture/lesVoitures.php");
       
        echo "<ul>";
        
        echo "</ul>";
    ?>
