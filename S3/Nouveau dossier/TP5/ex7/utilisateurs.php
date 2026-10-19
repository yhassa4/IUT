

<?php

class Utilisateurs{
    private string  $login;
    private string  $nom;
    private string  $prenom;
    private string  $email;
    private string  $mdp;
    

    //getter
    public function getlogin (){
        return $this -> login; 
    }

    public function getnom (){
        return $this -> nom; 
    }

    public function getprenom (){
        return $this -> prenom; 
    }

    public function getmail (){
        return $this -> mail; 
    }
    

    //setter 
    public function setlogin ($l){
         $this -> login = $l; 
    }

    public function setnom ($c){
         $this -> nom = $c; 
    }

    public function setprenom ($m){
         $this -> prenom = $m; 
    }

    public function setmail ($c){
         $this -> mail = $c; 
    }
   

    //constructeur 
    public function __construct(string  $login = NULL, string  $mail = NULL, string  $nom = NULL, string  $prenom = NULL){
        
        if(!is_null($login))
        {
        $this -> login = $login;
        $this -> nom = $nom;
        $this -> prenom = $prenom;
        $this -> mail = $mail;
        }  
        
        
    }
    public static function getAllUtilisateurs(){
        $requete = 'SELECT * FROM utilisateur ';
        $resultat = connexion::pdo()->query($requete);
        $resultat ->setFetchmode(PDO::FETCH_CLASS,"Utilisateurs");
        $lesutilisateurs = $resultat ->fetchAll();

        return $lesutilisateurs;
    }

    //affichage 
    public function afficher(){
        echo "<p>utilisateur $this->login ( $this->nom, $this->prenom), mail=$this->email </p> ";

    }


}

?>