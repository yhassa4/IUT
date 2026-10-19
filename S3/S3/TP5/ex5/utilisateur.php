<?php
    class Utilisateur {
        
        private string $login;
        private string $mdp;
        private string $nom;
        private string $prenom;
        private string $email;

        
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
            require_once("connexion.php");
            Connexion::connect();

            $requete = "SELECT * FROM utilisateur;";

            $resultat = Connexion::pdo()->query($requete);
            $resultat->setFetchMode(PDO::FETCH_CLASS, "utilisateur");

            $lesUtilisateurs = $resultat->fetchAll();

            return $lesUtilisateurs;
        }
    }
?>