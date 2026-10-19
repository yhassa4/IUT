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

        public static function getObjectByid($i){

            $table = static::$objet;
            $id = static::$cle;
            
            $requetePreparee = "SELECT * FROM $table WHERE $id = :id;" ;

            $req_prep = Connexion::pdo()->prepare($requetePreparee);

            $valeurs = array("id" => $i);
            try{
                $req_prep->execute($valeurs);

                $req_prep->setFetchmode(PDO::FETCH_CLASS,"$table");

                $v = $req_prep->fetch();

                return $v;

            }catch(PDOExceotion $e ){
                echo $e->getMessege();
            }

        }

        public static function deleteObjetById($id) {
            $table = static::$objet;
            $cle = static::$cle;
            $requetePreparee = "DELETE FROM $table WHERE $cle = :id_tag;";
            $req_prep = Connexion::pdo()->prepare($requetePreparee);
            $valeurs = array("id_tag" => $id);
            try {
                $req_prep->execute($valeurs);
            } catch (PDOException $e) {
                echo $e->getMessage();
            }
        }
        

    }
?>