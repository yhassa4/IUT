<?php 
    class Voiture {
        // Attributs
        private string $marque;
        private string $couleur;
        private string $immatriculation;

        // Constructeur
        public function __construct(string $m, string $c, string $i) {
            $this->marque = $m;
            $this->couleur = $c;
            $this->immatriculation = $i;
        }

        // setters et getters
        public function get($attribut) {return $this->attribut;}
        public function set($attribut, $valeur) {$this->attribut = $valeur;}

        // afficher
        public function afficher () {
            echo "<p>Voiture ".$this->immatriculation.", de marque ".$this->marque.", de couleur ".$this->couleur."</p>";
        }
    }
?>