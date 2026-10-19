<?php
include_once("modele.php");
    class Etudiant extends Modele{

        protected static $objet = "Etudiant";
        protected static $cle = "NumEtu";
        
        protected int $NumEtu;
        protected string $NomEtu;
        protected string $PrenomEtu;
        protected string $GenreEtu;
        protected string $MailProEtu;
        protected string $MailPersoEtu;
        protected string $AddrEtu;
        protected int $NumBac;
        protected int $NumPromo;
        protected int $NumGroup;
        protected int $Numparcour;

        
       

       
        
        public function afficher () {
            //echo "<p>Utilisateur $this->nom ($this->prenom $this->nom), email = $this->email</p>";
        }

        public static function countApprentis() {
        $sql = "SELECT COUNT(*) FROM Etudiant WHERE apprentissage = 1";
        $res = Connexion::pdo()->query($sql);
        return $res->fetchColumn();
    }

    public static function getByIdentifiant($email) {
        
        $sql = "SELECT * FROM " . static::$objet . " WHERE MailProEtu = :email";
        $st = Connexion::pdo()->prepare($sql);
        $st->execute(['email' => $email]);
        $st->setFetchMode(PDO::FETCH_CLASS, static::$objet);
        return $st->fetch();
    }

       
    }
?>