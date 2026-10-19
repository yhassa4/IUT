<?php 

    session_start();
    //Redirige vers une page sans droit
    header("Location: lireVoitures.php");
    session_destroy(); // Supprime les variables de session

?>