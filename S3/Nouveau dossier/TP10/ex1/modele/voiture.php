<?php 
include_once("modele.php");
    class voiture extends Modele{

        protected static $objet = "voiture";
        protected static $cle ="immatriculation";
        protected string $marque;
        protected string $couleur;
        protected string $immatriculation;

        
       
        
        


        // afficher
        public function afficher () {
            echo "<li>Voiture immarticulée".$this->immatriculation.", de marque ".$this->marque.", de couleur ".$this->couleur."</li>";
        }

       

        
    }
?>