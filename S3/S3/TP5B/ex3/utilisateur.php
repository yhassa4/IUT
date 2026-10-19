<?php
    class Utilisateur {
        // Attributs
        private string $login;
        private string $mdp;
        private string $nom;
        private string $prenom;
        private string $email;

        // Constructeur
        public function __construct(string $l = NULL, string $m = NULL, string $n = NULL, string $pn = NULL, string $e = NULL) {
            if (!is_null($l)) {
                $this->login = $l;
                $this->mdp = $m;
                $this->nom = $n;
                $this->prenom = $pn;
                $this->email = $e;
            }
        }

        public function getMDP () {
            return $this->mdp;
        }

        public function getNom () {
            return $this->nom;
        }

        public function getPrenom () {
            return $this->prenom;
        }
        
        public function afficher () {
            echo "<p>Utilisateur $this->nom ($this->prenom $this->nom), email = $this->email</p>";
        }

        static public function getAllUtilisateurs () {
            require_once("connexion.php");
            Connexion::connect();

            $requete = "SELECT * FROM utilisateur;";

            $resultat = Connexion::pdo()->query($requete);
            $resultat->setFetchMode(PDO::FETCH_CLASS, "utilisateur");

            $lesUtilisateurs = $resultat->fetchAll();

            return $lesUtilisateurs;
        }

        static public function addUtilisateur () {
            $login_cree = $_POST["login"];
            $mdp_hash = password_hash($_POST["mdp"], PASSWORD_BCRYPT);
            
            require_once("connexion.php");
            Connexion::connect();

            $requeteCreUserAvecTag = "INSERT INTO utilisateur (`login`, `mdp`, `nom`, `prenom`, `email`) VALUES (:login, :mdp, :nom, :prenom, :email)"; 

            $requetePreparer = connexion::pdo()->prepare($requeteCreUserAvecTag);

            $valeur = array();
            $valeur["login"] = $login_cree;
            $valeur["mdp"] = $mdp_hash;
            $valeur["nom"] = $_POST["nom"];
            $valeur["prenom"] = $_POST["prenom"];
            $valeur["email"] = $_POST["email"];

            try {
                $requetePreparer->execute($valeur);
                return true;
            } catch (PDOException $e) {
                echo $e->getMessage();
                return false;
            }
        }

        static public function checkMDP () {
            require_once("connexion.php");
            Connexion::connect();

            $requeteAvecTag = "SELECT * FROM utilisateur WHERE login = (:login);";
            $requetePreparer = connexion::pdo()->prepare($requeteAvecTag);

            $valeur = array();
            $valeur["login"] = $_POST["login"];

            $requetePreparer->execute($valeur);
            $requetePreparer->setFetchMode(PDO::FETCH_CLASS,"Utilisateur");
            $user = $requetePreparer->fetch();

            if ($user) {
                $test = password_verify($_POST["mdp"], $user->getMDP());
                return $test;
            }
            return false;
        }

        static public function getUser (string $login) {
            require_once("connexion.php");
            Connexion::connect();

            $requeteAvecTag = "SELECT * FROM utilisateur WHERE login = (:login);";
            $requetePreparer = connexion::pdo()->prepare($requeteAvecTag);

            $valeur = array();
            $valeur["login"] = $login;

            $requetePreparer->execute($valeur);
            $requetePreparer->setFetchMode(PDO::FETCH_CLASS,"Utilisateur");
            $user = $requetePreparer->fetch();

            return $user;
        }
    }
?>