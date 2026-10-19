

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

    public static function addVoiture($immatriculation, $marque, $couleur) {
// 1. écriture de la requête avec des "tags" représentant les valeurs des paramètres
        $requeteAvecTags = "INSERT INTO voiture(`immatriculation`, `marque`, `couleur`) VALUES(:immatriculation, :marque, :couleur);";
// 2. préparation de la requête avec la méthode prepare()
        $requetePreparee = connexion::pdo()->prepare($requeteAvecTags);
// 3. le tableau des valeurs qui remplaceront les tags
        $valeurs = array();
        $valeurs["immatriculation"] = $immatriculation;
        $valeurs["marque"] = $marque;
        $valeurs["couleur"] = $couleur;
// 4. lancement de la requête avec le tableau des valeurs
        try {
            $requetePreparee->execute($valeurs);
            return true;
        } catch(PDOException $e) {
            //Debug : echo "<p>".$e->getMessage()."</p>";
            //permet la gestion des messages d'erreur
            return false;
} }

    //affichage 
    public function afficher(){
        echo "<p>voiture $this->immatriculation, de marque $this->marque, de couleur $this->couleur </p> ";

    }


}

?>