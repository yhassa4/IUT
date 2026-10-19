<?php
include_once("modele.php");
    class Utilisateur extends Modele{
        
        protected string $login;
        protected string $mdp;
        protected string $nom;
        protected string $prenom;
        protected string $email;

        
        public function __construct(string $l = NULL, string $m = NULL, string $n = NULL, string $pn = NULL, string $e = NULL) {
            if (!is_null($l)) {
                $this->login = $l;
                $this->mdp = $m;
                $this->nom = $n;
                $this->prenom = $pn;
                $this->email = $e;
            }
        }

        
        
        public function afficher () {
            echo "<p>Utilisateur $this->nom ($this->prenom $this->nom), email = $this->email</p>";
        }

        static public function getAllUtilisateurs () {
            require_once("./config/connexion.php");
            Connexion::connect();

            $requete = "SELECT * FROM utilisateur;";

            $resultat = Connexion::pdo()->query($requete);
            $resultat->setFetchMode(PDO::FETCH_CLASS, "utilisateur");

            $lesUtilisateurs = $resultat->fetchAll();

            return $lesUtilisateurs;
        }

        public static function getUtilisateureBylogin($i){
            $requetePreparee = "SELECT * FROM utilisateur WHERE login = :id;" ;

            $req_prep = Connexion::pdo()->prepare($requetePreparee);

            $valeurs = array("id" => $i);
            try{
                $req_prep->execute($valeurs);

                $req_prep->setFetchmode(PDO::FETCH_CLASS,'utilisateur');

                $v = $req_prep->fetch();

                return $v;

            }catch(PDOExceotion $e ){
                echo $e->getMessege();
            }
        }
    }
?>