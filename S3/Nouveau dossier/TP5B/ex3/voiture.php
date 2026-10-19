<?php 
    class voiture {
        // Attributs
        private string $marque;
        private string $couleur;
        private string $immatriculation;

        // Constructeur
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

        // Méthodes
        public function afficher () {
            echo "<li>Voiture ".$this->immatriculation.", de marque ".$this->marque.", de couleur ".$this->couleur."</li>";
        }

        static public function getAllVoitures () {
            require_once("connexion.php");

            Connexion::connect();

            $requete = "SELECT * FROM voiture;";
            $resultat = Connexion::pdo()->query($requete);
            $resultat->setFetchmode(PDO::FETCH_CLASS, "voiture");
            $lesVoitures = $resultat->fetchAll();

            return $lesVoitures;
        }

        static public function addVoiture() {

            $requeteAvecTag = "INSERT INTO voiture (`immatriculation`, `marque`, `couleur`) VALUES (:immatriculation, :marque, :couleur);";

            require_once("connexion.php");
            Connexion::connect();

            $requetePreparee = connexion::pdo()->prepare($requeteAvecTag);

            $valeur = array();
            $valeur["immatriculation"] = $_GET["immatriculation"];
            $valeur["marque"] = $_GET["marque"];
            $valeur["couleur"] = $_GET["couleur"];

            try {
                $requetePreparee->execute($valeur);
                return true;
            } catch (PDOException $e) {
                echo $e->getMessage();
                return false;
            }
        }
    }
?>