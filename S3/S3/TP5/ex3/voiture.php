<?php 
    class voiture {
       
        private string $marque;
        private string $couleur;
        private string $immatriculation;

       
        public function __construct(string $i = NULL, string $c = NULL, string $m = NULL) {
            if (!is_null($i)) {
                $this->immatriculation = $i;
                $this->couleur = $c;
                $this->marque = $m;
            }
        }

        // setters et getters
        public function get($attribut) {return $this->attribut;}
        public function set($attribut, $valeur) {$this->attribut = $valeur;}

        // afficher
        public function afficher () {
            echo "<li>Voiture ".$this->immatriculation.", de marque ".$this->marque.", de couleur ".$this->couleur."</li>";
        }
    }
?>