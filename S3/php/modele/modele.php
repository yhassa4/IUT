<?php
    class Modele implements JsonSerializable {

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

        public function jsonSerialize(): mixed 
        {
            return get_object_vars($this);
        }

        public static function getAll()
        {
            require_once("./config/connexion.php");
            Connexion::connect();

            $table = static::$objet;
            $classeAppelante = get_called_class();
            $requete = "SELECT * FROM $table;";

            $resultat = Connexion::pdo()->query($requete);
            
            // FETCH_ASSOC juste les données brutes sans logique de classe
            // FETCH_CLASS mais faut que noms des attributs concordent
            $resultat->setFetchMode(PDO::FETCH_CLASS | PDO::FETCH_PROPS_LATE, $classeAppelante);

            return $resultat->fetchAll();
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

        public static function count() {
            $table = static::$objet; 
            $sql = "SELECT COUNT(*) FROM $table";
            $st = Connexion::pdo()->query($sql);
            return (int)$st->fetchColumn();
        }

        public static function save($donnees) {
            $table = static::$objet;
            $colonnes = implode(', ', array_keys($donnees));
            $parametres = ':' . implode(', :', array_keys($donnees));
            
            $sql = "INSERT INTO $table ($colonnes) VALUES ($parametres)";
            $st = Connexion::pdo()->prepare($sql);
            $st->execute($donnees);
            
            return Connexion::pdo()->lastInsertId(); 
        }

        public static function executeRequete($sql, $params = null) {
            if ($params == null) {
                $resultat = Connexion::pdo()->query($sql);
            } else {
                $resultat = Connexion::pdo()->prepare($sql);
                $resultat->execute($params);
            }
            return $resultat;
        }
                

    }
?>