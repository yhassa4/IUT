<?php
include_once("modele.php");
    class Groupe extends Modele{

        protected static $objet = "Group_";
        protected static $cle = "NumGroup";
        
        protected int $NumPromo;
        protected int $NumGroup;
        protected int $NumEnsei;
        protected int $NumGroupTD;
        protected int $NumGroupTP;
        

        
       

        
        
        public function afficher () {
            //echo "<p>Utilisateur $this->nom ($this->prenom $this->nom), email = $this->email</p>";
        }

        
    }
?>