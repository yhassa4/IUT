<?php
include_once("modele.php");
    class Enseignant extends Modele{

        protected static $objet = "Enseignant";
        protected static $cle = "NumEnsei";
        
        protected int $NumEnsei;
        protected string $NomEnsei;
        protected string $PrenomEnsei;
        protected string $GenreEnsei;
        protected string $MailProEnsei;
        protected string $MailPersoEnsei;
        protected string $AdrrEnsei;
        protected int $Numrole;

        
       

        
        

        public static function getAllWithRoles() {
                $sql = "SELECT e.*, r.nomrole 
                        FROM Enseignant e 
                        JOIN Role r ON e.numrole = r.numrole";
                $st = Connexion::pdo()->query($sql);
                $st->setFetchMode(PDO::FETCH_CLASS, 'enseignant');
                return $st->fetchAll();
            }

        public static function countByRole($idRole) {
            $sql = "SELECT COUNT(*) FROM Enseignant WHERE Numrole = :id";
            $st = Connexion::pdo()->prepare($sql);
            $st->execute(['id' => $idRole]);
            return (int)$st->fetchColumn();
        }

        public static function countResponsables() {
            $sql = "SELECT COUNT(*) 
                    FROM Enseignant e 
                    INNER JOIN Role r ON e.numrole = r.numrole 
                    WHERE r.nomRole LIKE '%Responsable%'";
                    
            $st = Connexion::pdo()->query($sql);
            return (int)$st->fetchColumn();
        }

        public function afficher() {
            echo "<h3>Détails de l'enseignant</h3>";
            echo "<ul>";
            echo "<li>Numéro : " . $this->NumEnsei . "</li>";
            echo "<li>Nom : " . $this->NomEnsei . "</li>";
            echo "<li>Prénom : " . $this->PrenomEnsei . "</li>";
            echo "<li>Email Pro : " . $this->MailProEnsei . "</li>"; 
            echo "</ul>";
        }

        public static function getByIdentifiant($email) {
            // On fait une jointure pour récupérer le nom du rôle en même temps que l'enseignant
            $sql = "SELECT e.*, r.nomrole 
                    FROM Enseignant e 
                    JOIN Role r ON e.Numrole = r.Numrole 
                    WHERE e.MailProEnsei = :email";
                    
            $st = Connexion::pdo()->prepare($sql);
            $st->execute(['email' => $email]);
            
            // On garde FETCH_ASSOC ici pour récupérer facilement la colonne 'nomrole'
            return $st->fetch(PDO::FETCH_ASSOC); 
        }

    }
?>