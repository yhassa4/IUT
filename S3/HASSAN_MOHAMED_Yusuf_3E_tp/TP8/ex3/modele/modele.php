<?php
    class Modele {

        public function __construct($donnees = NULL){
            if(!is_null($donnees)){
                foreach($donnees as $attribut=> $valeur){
                    $this->set($attribut,$valeur);
                }
            }
        }

        public function get($attribut){
            return $this ->$attribut;
        }

        public function set($attribut,$valeur){
            $this->$attribut = $valeur;
        }

        public static function getAll(){
            require_once("./config/connexion.php");
            Connexion::connect();

            $table = static::$objet;

            $requete = "SELECT * FROM $table;";

            $resultat = Connexion::pdo()->query($requete);
            $resultat->setFetchMode(PDO::FETCH_CLASS, "$table");

            $lesObjet = $resultat->fetchAll();

            return $lesObjet;
        }
        

    }
?>