<?php
    
    require_once("modele/Enseignant.php");
    require_once("./config/connexion.php");
    Connexion::connect();

    
    class ControleurEnseignant extends Controleur{
        

        protected static $objet = "Enseignant";
        protected static $cle = "NumEnsei";

        public static function lireObjets() {
            $titre = "Gestion des Enseignants";

            $nbTotal = Enseignant::count(); 
            $nbSemestre = Enseignant::countByRole(1);
            $nbAnnee = Enseignant::countByRole(2);
            $nbFiliere = Enseignant::countByRole(3);

          
            $lesObjet = enseignant::getAllWithRoles();
            $aff = array(); 
            foreach ($lesObjet as $ob) {
                $ligne = "<tr>";
                $ligne .= "<td><b>" . $ob->get('NumEnsei') . "</b></td>";
                $ligne .= "<td>" . $ob->get('NomEnsei') . " " . $ob->get('PrenomEnsei') . "</td>";
                $ligne .= "<td>" . $ob->get('GenreEnsei') . "</td>";
                
                $ligne .= "<td>" . $ob->get('nomrole') . "</td>"; 
                $ligne .= "<td>" . $ob->get('MailProEnsei') . "</td>";
                $ligne .= "<td>" . $ob->get('AddrEnsei') . "</td>";
                $ligne .= "</tr>";
                $aff[] = $ligne;
            }

            include("vue/debut.php");
            include("vue/vueEnseignant.php");
            include("vue/fin.html");
        }
        

       public static function getAllWithRoles() {
            $requete = "SELECT e.*, r.nomrole 
                        FROM Enseignant e 
                        JOIN role r ON e.numrole = r.numrole";
            $resultat = Connexion::pdo()->query($requete); 
            $resultat->setFetchMode(PDO::FETCH_CLASS, 'Enseignant');
            return $resultat->fetchAll();
        }

        public static function creer() {
            $sqlId = "SELECT MAX(NumEnsei) FROM Enseignant";
            $resId = Connexion::pdo()->query($sqlId);
            $nextId = $resId->fetchColumn() + 1;
            $donnees = [
                'NumEnsei'      => $nextId,
                'NomEnsei'      => $_POST['NomEnsei'],
                'PrenomEnsei'   => $_POST['PrenomEnsei'],
                'GenreEnsei'    => $_POST['GenreEnsei'],
                'MailProEnsei'  => $_POST['MailProEnsei'],
                'MailPersoEnsei'=> $_POST['MailPersoEnsei'] ?? null,
                'AddrEnsei'     => $_POST['AddrEnsei'] ?? null,
                'Numrole'       => $_POST['Numrole'], // Val de 1 à 4
                'password'      => password_hash($_POST['password'], PASSWORD_DEFAULT)
            ];

            try {
                enseignant::save($donnees);
                self::lireObjets(); 
            } catch (PDOException $e) {
                echo "Erreur d'insertion : " . $e->getMessage();
            }
        }
        

          
    
        
    }
    ?>