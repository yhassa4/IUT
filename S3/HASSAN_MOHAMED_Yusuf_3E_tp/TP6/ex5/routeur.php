 <?php 
        require_once("./config/connexion.php");
        Connexion::connect();


        $controleur = "controleurVoiture";

        if(isset($_GET["controleur"])){
            $controleur = $_GET["controleur"];
        }

         if(isset($_GET["action"])){
            $controleur = $_GET["action"];
        }

        require_once("controleur/$controleur.php");

        $controleur::$action();
        

       
      
    ?>