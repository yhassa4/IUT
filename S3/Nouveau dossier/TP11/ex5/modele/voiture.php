<?php 
include_once("modele.php");
    class voiture extends Modele{

        protected static $objet = "voiture";
        protected static $cle ="immatriculation";
        protected string $marque;
        protected string $couleur;
        protected string $immatriculation;

        
       
        
        


        // afficher
        public function afficher () {
            echo "<li>Voiture immarticulée".$this->immatriculation.", de marque ".$this->marque.", de couleur ".$this->couleur."</li>";
        }

        public static function addVoiture($i, $m, $c) {
            $requetePreparee = "INSERT INTO voiture VALUES (:i_tag, :m_tag, :c_tag);";
            $req_prep = Connexion::pdo()->prepare($requetePreparee);
            $valeurs = array(
                "i_tag" => $i,
                "m_tag" => $m,
                "c_tag" => $c
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