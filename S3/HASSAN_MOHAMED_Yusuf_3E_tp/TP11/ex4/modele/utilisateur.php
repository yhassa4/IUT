<?php
include_once("modele.php");
    class Utilisateur extends Modele{

        protected static $objet = "utilisateur";
        protected static $cle = "login";
        protected string $login;
        protected string $mdp;
        protected string $nom;
        protected string $prenom;
        protected string $email;

        
      

        
        
        public function afficher () {
            echo "<p>Utilisateur $this->nom ($this->prenom $this->nom), email = $this->email</p>";
        }

        public static function addUtilisateur($login, $mdp, $nom, $prenom, $email) {
            $requetePreparee = "INSERT INTO utilisateur
                                VALUES (:login_tag, :mdp_tag, :nom_tag, :prenom_tag, :email_tag);";
            $req_prep = Connexion::pdo()->prepare($requetePreparee);
            $valeurs = array(
                "login_tag" => $login,
                "mdp_tag" => $mdp,
                "nom_tag" => $nom,
                "prenom_tag" => $prenom,
                "email_tag" => $email
            );
            try {
                $req_prep->execute($valeurs);
                return true; 
            } catch (PDOException $e) {
                return false; 
            }
        }

       

        
    }
?>