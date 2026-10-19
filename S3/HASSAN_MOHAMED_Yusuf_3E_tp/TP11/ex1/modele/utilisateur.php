<?php
include_once("modele.php");
    class Utilisateur extends Modele{

        protected static $objet = "utilisateur";
        protected static $cle = "login";
        protected string $login;
        protected string $mdp;
        protected string $nom;
        protected string $prenom;
        protected string $email;

        
      

        
        
        public function afficher () {
            echo "<p>Utilisateur $this->nom ($this->prenom $this->nom), email = $this->email</p>";
        }

       

        
    }
?>