<?php
include_once("modele.php");
    class Sondage extends Modele{

        protected static $objet = "Sondage";
        protected static $cle = "Numsondage";
        
        protected int $Numsondage;
        protected String  $nomsondage;
        protected String $typesondage;
        protected int $NumPromo;
        protected int $NumEnsei;
        
      

    
        
        public function afficher () {
            //echo "<p>Utilisateur $this->nom ($this->prenom $this->nom), email = $this->email</p>";
        }

        public static function ajouterQuestion($idSondage, $texteQuestion) {
            $sql = "INSERT INTO reponse (Numsondage, Numquestion, Reponse) VALUES (?, (SELECT IFNULL(MAX(Numquestion),0)+1 FROM reponse r WHERE r.Numsondage = ?), ?)";
            self::executeRequete($sql, [$idSondage, $idSondage, $texteQuestion]);
        }

        public static function countEnCours() {
        $sql = "SELECT COUNT(*) FROM Sondage WHERE typesondage = 1";
        $res = Connexion::pdo()->query($sql);
        return $res->fetchColumn();
        }

        public static function countTermines() {
            $sql = "SELECT COUNT(*) FROM Sondage WHERE typesondage = 0";
            $res = Connexion::pdo()->query($sql);
            return $res->fetchColumn();
        }

        public static function getSondagesParPromo($numPromo) {
            $sql = "SELECT * FROM Sondage WHERE NumPromo = ? AND typesondage = 1";
            return self::executeRequete($sql, [$numPromo])->fetchAll(PDO::FETCH_CLASS, 'Sondage');
        }

       
    }
?>