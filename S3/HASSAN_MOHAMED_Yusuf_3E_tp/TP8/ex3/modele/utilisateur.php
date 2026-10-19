<?php
include_once("modele.php");
    class Utilisateur extends Modele{

        protected static $objet = "utilisateur";
        
        protected string $login;
        protected string $mdp;
        protected string $nom;
        protected string $prenom;
        protected string $email;

        
      

        
        
        public function afficher () {
            echo "<p>Utilisateur $this->nom ($this->prenom $this->nom), email = $this->email</p>";
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