 <?php 
        require_once("./config/connexion.php");
        Connexion::connect();


        $controleur = "controleurVoiture";
        $action = "lireVoitures";
        if(isset($_GET["controleur"])){
            $controleur = $_GET["controleur"];
        }

         if(isset($_GET["action"])){
            $action = $_GET["action"];
        }

        require_once("controleur/$controleur.php");

        $controleur::$action();
        

       
      
    ?>