<?php
    
    require_once("modele/Groupe.php");
    require_once("./config/connexion.php");
    require_once("modele/Etudiant.php");
    require_once("modele/redoublement.php");

    Connexion::connect();

    
    class ControleurGroupe extends Controleur{
        

        protected static $objet = "Group_";
        protected static $cle = "NumGroup";

        public static function lireObjets() {
            $titre = "Gestion des Groupes";

            $nbEtudiants = etudiant::count();
            $nbRedoublants = redoublement::count();
            $nbApprentis = etudiant::countApprentis(); 


            $sql = "SELECT * FROM Group_ WHERE NumPromo = :p";
            $st = Connexion::pdo()->prepare($sql);
            $st->execute(['p' => 1]); 
            $st->setFetchMode(PDO::FETCH_CLASS, 'Groupe');
            $lesGroupes = $st->fetchAll();

            $aff = array();
            foreach ($lesGroupes as $g) {
                $id = $g->get('NumGroup');
                $ligne = "<tr>";
                $ligne .= "<td>Groupe " . $id . "</td>";
                $ligne .= "</tr>";
                $aff[] = $ligne;
            }

            include("vue/debut.php");
            include("vue/vueGroupe.php");
            include("vue/fin.html");
        }
        public static function creer() {
            
            $sqlId = "SELECT MAX(NumGroup) FROM Group_";
            $resId = Connexion::pdo()->query($sqlId);
            $nextGroup = $resId->fetchColumn() + 1;

            
            $donnees = [
                'NumGroup'   => $nextGroup,
                'NumPromo'   => $_POST['NumPromo'],
                'NumEnsei'   => $_POST['NumEnsei'],
                'NumGroupTD' => $_POST['NumGroupTD'],
                'NumGroupTP' => $_POST['NumGroupTP'],
                'DatePubli'  => $_POST['DatePubli']
            ];

            try {
                Groupe::save($donnees);
                self::lireObjets(); 
            } catch (PDOException $e) {
                echo "Erreur lors de la création du groupe : " . $e->getMessage();
            }
        }
    }
    ?>