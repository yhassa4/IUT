<?php
    class Utilisateur {
        
        private string $login;
        private string $mdp;
        private string $nom;
        private string $prenom;
        private string $email;

        
        public function __construct(string $l, string $m, string $n, string $pn, string $e) {
            $this->login = $l;
            $this->mdp = $m;
            $this->nom = $n;
            $this->prenom = $pn;
            $this->email = $e;
        }
        
        public function afficher () {
            echo "<h3>Information de cet utilisateur</h3>";
            echo "<p>Login : $this->login<p>";
            echo "<p>Mot de passe : $this->mdp<p>";
            echo "<p>Nom : $this->nom<p>";
            echo "<p>Prenom : $this->prenom<p>";
            echo "<p>Email : $this->email<p>";
        }
    }
?>