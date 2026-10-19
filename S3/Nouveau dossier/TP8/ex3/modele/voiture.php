<?php 
include_once("modele.php");
    class voiture extends Modele{

        protected static $objet = "voiture";
        
        protected string $marque;
        protected string $couleur;
        protected string $immatriculation;

        
       
        
        


        // afficher
        public function afficher () {
            echo "<li>Voiture immarticulée".$this->immatriculation.", de marque ".$this->marque.", de couleur ".$this->couleur."</li>";
        }

       

        public static function getVoitureByImmat($i){
            $requetePreparee = "SELECT * FROM voiture WHERE immatriculation = :numero;" ;

            $req_prep = Connexion::pdo()->prepare($requetePreparee);

            $valeurs = array("numero" => $i);
            try{
                $req_prep->execute($valeurs);

                $req_prep->setFetchmode(PDO::FETCH_CLASS,'voiture');

                $v = $req_prep->fetch();

                return $v;

            }catch(PDOExceotion $e ){
                echo $e->getMessege();
            }
        }
    }
?>