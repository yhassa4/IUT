<?php
require_once("modele/Sondage.php");
require_once("./config/connexion.php");
Connexion::connect();

class ControleurSondage extends Controleur {

    protected static $objet = "Sondage";
    protected static $cle = "Numsondage";

    public static function lireObjets() {
        $titre = "Gestion des Sondages";

        $nbEnCours = Sondage::countEnCours(); 
        $nbTermines = Sondage::countTermines();
        $nbTotal = $nbEnCours + $nbTermines;

        $lesSondages = Sondage::getAll();
        $aff = array();
        foreach ($lesSondages as $s) {
            $nom = $s->get('nomsondage'); 
            $type = $s->get('typesondage'); 
            $id = $s->get('Numsondage');

            $ligne = "<tr>";
            $ligne .= "<td>" . htmlspecialchars($nom) . "</td>";
            $ligne .= "<td>" . ($type == 1 ? 'En cours' : 'Terminé') . "</td>";
            $ligne .= "<td>
                        <a href='routeur.php?controleur=controleurSondage&action=voirResultats&id=$id' class='btn-search'>Résultats</a>
                      </td>";
            $ligne .= "</tr>";
            $aff[] = $ligne;
        }

        include("vue/debut.php");
        include("vue/vueSondage.php");
        include("vue/fin.html");
    }

    
    public static function creer() {
        $donnees = [
            'nomsondage'       => $_POST['titre'],
            'typesondage'      => 1, 
            'NumPromo'         => $_POST['promo'],
            'NumEnsei'         => $_SESSION['user']->get('NumEnsei'), 
            'DateDebutSondage' => date('Y-m-d'),
            'DateFinSondage'   => $_POST['dateFin']
        ];

       
        $idSondage = Sondage::save($donnees);

        //  On insère les questions dans les tables Question et reponse
        if (!empty($_POST['questions'])) {
            foreach ($_POST['questions'] as $index => $texteQuestion) {
                if (!empty(trim($texteQuestion))) {
                    $numQ = $index + 1;

                    // Insertion dans la table parente Question
                    $sqlQ = "INSERT INTO Question (Numsondage, Numquestion, TextQuestion) VALUES (:idS, :numQ, :txtQ)";
                    $stmtQ = Connexion::pdo()->prepare($sqlQ);
                    $stmtQ->execute([
                        ':idS'  => $idSondage,
                        ':numQ' => $numQ,
                        ':txtQ' => $texteQuestion
                    ]);

                    // Insertion dans la table reponse
                    $sqlR = "INSERT INTO reponse (Numsondage, Numquestion, Numreponse, Reponse) 
                            VALUES (:idS, :numQ, :numR, :txtR)";
                    $stmtR = Connexion::pdo()->prepare($sqlR);
                    $stmtR->execute([
                        ':idS'  => $idSondage,
                        ':numQ' => $numQ,
                        ':numR' => 1,
                        ':txtR' => $texteQuestion
                    ]);
                }
            }
        }
        header("Location: routeur.php?controleur=controleurSondage&action=lireObjets");
        exit();
    }

    // Affiche le formulaire de vote 
    public static function repondre() {
        if (!isset($_GET['id'])) {
            header("Location: routeur.php?controleur=controleurGlobal&action=accueil");
            exit();
        }

        $idSondage = $_GET['id'];
        
        $sondage = Sondage::getObjectByid($idSondage);

        // verif date!
        if ($sondage) {
            $dateFin = $sondage->get('DateFinSondage');
            $aujourdhui = date('Y-m-d');

            if ($aujourdhui > $dateFin) {
                header("Location: routeur.php?controleur=controleurGlobal&action=accueil&error=expire");
                exit();
            }
        } else {
            header("Location: routeur.php?controleur=controleurGlobal&action=accueil");
            exit();
        }

        $titre = "Répondre au Sondage";

        // On récup les questions du sondage
        $db = Connexion::pdo();
        $stmt = $db->prepare("SELECT * FROM Question WHERE Numsondage = ?");
        $stmt->execute([$idSondage]);
        $questions = $stmt->fetchAll(PDO::FETCH_ASSOC);

        include("vue/debut.php");
        include("vue/vueRepondreSondage.php");
        include("vue/fin.html");
    }

    // Enregistre les réponses dans la table repond
    public static function voter() {
        $numEtu = $_SESSION['user']->get('Numetu');
        $idSondage = $_POST['idSondage'];
        
        $sondage = Sondage::getObjectByid($idSondage);
        
        if ($sondage) {
            $dateFin = $sondage->get('DateFinSondage');
            $aujourdhui = date('Y-m-d');

            // ici on vérifie si on n'a pas dépassé la date limite
            if ($aujourdhui <= $dateFin) {
                
                if (isset($_POST['reponses']) && is_array($_POST['reponses'])) {
                    $db = Connexion::pdo();
                    foreach ($_POST['reponses'] as $numQ => $valeur) {
                        $sql = "INSERT INTO repond (Numetu, Numsondage, Numquestion, reponse) 
                                VALUES (:etu, :son, :que, :rep)
                                ON DUPLICATE KEY UPDATE reponse = :rep";
                        
                        $stmt = $db->prepare($sql);
                        $stmt->execute([
                            ':etu' => $numEtu,
                            ':son' => $idSondage,
                            ':que' => $numQ,
                            ':rep' => $valeur
                        ]);
                    }
                }
                // succès
                header("Location: routeur.php?controleur=controleurGlobal&action=accueil&msg=vote_ok");
                exit();
                
            } else {
                //  date est dépassée
                header("Location: routeur.php?controleur=controleurGlobal&action=accueil&error=expire");
                exit();
            }
        } else {
            // sondage n'existe pas
            header("Location: routeur.php?controleur=controleurGlobal&action=accueil");
            exit();
        }
    }

    
    public static function voirResultats() {
        $idSondage = $_GET['id'];
        $titre = "Résultats du Sondage";
        
        $db = Connexion::pdo();
        $sql = "SELECT e.Nometu, e.Prenometu, q.TextQuestion as QuestionTexte, r.reponse 
                FROM repond r 
                JOIN Etudiant e ON r.Numetu = e.Numetu
                JOIN Question q ON r.Numsondage = q.Numsondage AND r.Numquestion = q.Numquestion
                WHERE r.Numsondage = ?";
                
        $stmt = $db->prepare($sql);
        $stmt->execute([$idSondage]);
        $resultats = $stmt->fetchAll(PDO::FETCH_ASSOC);

        
        $stats = ['Oui' => 0, 'Non' => 0, 'Sans avis' => 0];
        foreach ($resultats as $res) {
            if (isset($stats[$res['reponse']])) {
                $stats[$res['reponse']]++;
            }
        }
        $total = count($resultats);
        
        $s = Sondage::getObjectByid($idSondage);
        $sondageNom = $s ? $s->get('nomsondage') : "Inconnu";

        include("vue/debut.php");
        include("vue/vueResultatsSondage.php");
        include("vue/fin.html");
    }

    public static function supprimer() {
        
        if (isset($_GET['id'])) {
            $id = $_GET['id'];
            $db = Connexion::pdo();

            // Supprimer les votes des étudiants
            $stmt1 = $db->prepare("DELETE FROM repond WHERE Numsondage = ?");
            $stmt1->execute([$id]);

            // Supprimer les questions
            $stmt2 = $db->prepare("DELETE FROM reponse WHERE Numsondage = ?");
            $stmt2->execute([$id]);
            $stmt3 = $db->prepare("DELETE FROM Question WHERE Numsondage = ?");
            $stmt3->execute([$id]);

            // Supprimer le sondage lui-même
            $stmt4 = $db->prepare("DELETE FROM Sondage WHERE Numsondage = ?");
            $stmt4->execute([$id]);
        }
        
        header("Location: routeur.php?controleur=controleurSondage&action=lireObjets&msg=supprime");
        exit();
    }
}
?>