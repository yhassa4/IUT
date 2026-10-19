<?php
include_once("modele.php");
    class Promotion extends Modele{
        protected static $objet = "Promotion";
        protected static $cle = "NumPromo";
        
        protected int $NumPromo;
        protected int $NumSemestre;
        protected int $Numdate ;
       
        

        
        

       
        
        public function afficher () {
            //echo "<p>Utilisateur $this->nom ($this->prenom $this->nom), email = $this->email</p>";
        }

        
    }
?>