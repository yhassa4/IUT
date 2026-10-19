<?php

class Voiture{
    private string  $marque;
    private string  $couleur;
    private string  $immatriculation;

    //getter
    public function getmarque (){
        return $this -> marque; 
    }

    public function getcouleur (){
        return $this -> couleur; 
    }
    public function getimmatriculation (){
        return $this -> immatriculation; 
    }

    //setter 
    public function setmarque ($m){
         $this -> marque = $m; 
    }

    public function setcouleur ($c){
         $this -> couleur = $c; 
    }
    public function setimmatriculation ($i){
         $this -> immatriculation = $i; 
    }

    //constructeur 
    public function __construct(string  $immatriculation = NULL, string  $marque = NULL, string  $couleur = NULL){
        if($immatriculation!=NULL)
        {
        $this -> immatriculation = $immatriculation;
        $this -> marque = $marque;
        $this -> couleur = $couleur;
        }
        

    }
    public static function getAllVoitures(){
        $requete = 'SELECT * FROM voiture ';
        $resultat = connexion::pdo()->query($requete);
        $resultat ->setFetchmode(PDO::FETCH_CLASS,"voiture");
        $lesvoiture = $resultat ->fetchAll();

        return $lesvoiture;
    }

    //affichage 
    public function afficher(){
        echo "<p>voiture $this->immatriculation, de marque $this->marque, de couleur $this->couleur </p> ";

    }


}

?>