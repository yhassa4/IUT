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

        
        public function get($attribut) {return $this->attribut;}
        public function set($attribut, $valeur) {$this->attribut = $valeur;}

        // afficher
        public function afficher () {
            echo "<li>Voiture immarticulée".$this->immatriculation.", de marque ".$this->marque.", de couleur ".$this->couleur."</li>";
        }

        static public function getAllVoitures () {

            $requete = "SELECT * FROM voiture;";
            $resultat = Connexion::pdo()->query($requete);
            $resultat->setFetchmode(PDO::FETCH_CLASS, "voiture");
            $lesVoitures = $resultat->fetchAll();

            return $lesVoitures;
        }

        public static function getVoitureByImmat($i){
            $requetePreparee = "SELECT * FROM voiture WHERE immatriculation = :numero;" ;

            $req_prep = Connexion::pdo()->prepare($requetePreparee);

            $valeurs = array("numero" => $i);
            try{
                $req_prep->execute($valeurs);

                $req_prep->setFetchmode(PDO::FETCH_CLASS,'voiture');

                $v = $req_prep->fetch();

                return $v;

            }catch(PDOExceotion $e ){
                echo $e->getMessege();
            }
        }
    }
?>