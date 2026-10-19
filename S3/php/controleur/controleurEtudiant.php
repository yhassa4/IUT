<?php
require_once("modele/Etudiant.php");
require_once("modele/Groupe.php");    
require_once("modele/Promotion.php"); 
require_once("./config/connexion.php");


class controleurEtudiant extends Controleur {
    
    protected static $objet = "Etudiant";
    protected static $cle = "Numetu";

    private static $roles_autorises = [
        'Responsable semestre', 
        'Responsable d annee', 
        'Responsable de fillieres', 
        'Responsable de formation', 
        'admin', 'responsable'
    ];

    public static function lireObjets() {
        $lesEtudiants = etudiant::getAll();
        $nbEtudiants = count($lesEtudiants);
        $nbGroupes = count(groupe::getAll()); 
        $nbPromotions = count(promotion::getAll());
        $aff = [];
        foreach($lesEtudiants as $e) {
            $statut = ($e->get('apprentissage') == 1) ? "Apprentissage" : "Initial";
            $ligne = "<tr>";
            $ligne .= "<td>" . $e->get('Numetu') . "</td>";
            $ligne .= "<td>" . $e->get('Nometu') . " " . $e->get('Prenometu') . "</td>";
            $ligne .= "<td>" . $e->get('GenreEtu') . "</td>";
            $ligne .= "<td> Bac " . $e->get('NumBac') . "</td>";
            $ligne .= "<td>" . $statut . "</td>";
            $ligne .= "<td> Groupe " . $e->get('NumGroup') . "</td>";
            $ligne .= "<td>" . $e->get('MailPersoEtu') . "</td>";
            $ligne .= "</tr>";
            $aff[] = $ligne;
        }

        include("vue/debut.php");
        include("vue/vueEtudiant.php");
        include("vue/fin.html");
    }

    public static function creer() {
        if (!isset($_SESSION['role']) || $_SESSION['role'] === 'etudiant') {
            header("Location: routeur.php?action=accueil&error=access_denied");
            exit();
        }
        $donnees = [
            'Nometu' => $_POST['nomEtu'],
            'Prenometu' => $_POST['prenomEtu'],
            'GenreEtu' => $_POST['genreEtu'],
            'NumBac' => $_POST['typeBac'],
            'apprentissage' => ($_POST['statutEtu'] === 'Apprentissage') ? 1 : 0,
            'MailPersoEtu' => $_POST['MailPersoEtu'],
            'AddrEtu' => $_POST['AddrEtu'],
            'password' => password_hash($_POST['password'], PASSWORD_DEFAULT),
            'NumPromo' => $_POST['NumPromo'],
            'NumGroup' => $_POST['NumGroup'],
            'Numparcour' => $_POST['Numparcour'],
            'MailProEtu' => strtolower($_POST['prenomEtu'] . "." . $_POST['nomEtu'] . "@universite.fr")
        ];

        Etudiant::save($donnees);
        header("Location: routeur.php?controleur=controleurEtudiant&action=lireObjets&msg=ajout_ok");
        exit();
    }

    public static function voirPromotion() {
        $db = Connexion::pdo();
        $maPromo = $_SESSION['user']->get('NumPromo');

        $sql = "SELECT Nometu, Prenometu, MailProEtu 
                FROM Etudiant 
                WHERE NumPromo = ? 
                ORDER BY Nometu ASC";
        
        $st = $db->prepare($sql);
        $st->execute([$maPromo]);
        $camarades = $st->fetchAll(PDO::FETCH_ASSOC);

        $titre = "Ma Promotion";
        include("vue/debut.php");
        include("vue/vueListePromotion.php"); 
        include("vue/fin.html");
    }

    public static function mesNotes() {
        $numEtu = $_SESSION['user']->get('Numetu');
        $db = Connexion::pdo();
        $sql = "SELECT s.Nomsemestre, m.NomMatiere, n.valeur, n.valeurMax 
                FROM Note n
                JOIN Matiere m ON n.numMatiere = m.numMatiere
                JOIN Semestre s ON n.NumSemestre = s.NumSemestre
                WHERE n.Numetu = ?
                ORDER BY s.NumSemestre ASC, m.NomMatiere ASC";

        $stmt = $db->prepare($sql);
        $stmt->execute([$numEtu]);
        $mesNotes = $stmt->fetchAll(PDO::FETCH_ASSOC);
        $totalPoints = 0;
        $totalMax = 0;
        foreach ($mesNotes as $n) {
            $totalPoints += $n['valeur'];
            $totalMax += $n['valeurMax'];
        }
        $moyenne = ($totalMax > 0) ? ($totalPoints / $totalMax) * 20 : 0;

        $titre = "Mes Notes";
        include("vue/debut.php");
        include("vue/vueMesNotes.php");
        include("vue/fin.html");
    }

    public static function afficherCovoiturage() {
        $titre = "Covoiturage";
        include("vue/debut.php");
        include("vue/vueCovoiturage.php");
        include("vue/fin.html");
    }

    public static function enregistrerCovoiturage() {
        $db = Connexion::pdo();
        $monId = $_SESSION['user']->get('Numetu');
        $maPromo = $_SESSION['user']->get('NumPromo');
        
        $noms = $_POST['nom'] ?? [];
        $prenoms = $_POST['prenom'] ?? [];
        $erreurs = [];

        $db->prepare("DELETE FROM covoiture WHERE Numetu = ?")->execute([$monId]);

        for ($i = 0; $i < count($noms); $i++) {
            $nom = trim($noms[$i]);
            $prenom = trim($prenoms[$i]);

            if (!empty($nom) && !empty($prenom)) {
                $sql = "SELECT Numetu FROM Etudiant 
                        WHERE UPPER(Nometu) = UPPER(?) 
                        AND UPPER(Prenometu) = UPPER(?) 
                        AND NumPromo = ?";
                
                $st = $db->prepare($sql);
                $st->execute([$nom, $prenom, $maPromo]);
                $resultat = $st->fetch();

                if ($resultat) {
                    $idCollegue = $resultat['Numetu'];
                    if ($idCollegue != $monId) {
                        $ins = $db->prepare("INSERT IGNORE INTO covoiture (Numetu, Numetu_1) VALUES (?, ?)");
                        $ins->execute([$monId, $idCollegue]);
                    }
                } else {
                    $erreurs[] = htmlspecialchars($nom . " " . $prenom);
                }
            }
        }

        if (!empty($erreurs)) {
            $_SESSION['error_covoit'] = "Étudiant(s) introuvables : " . implode(", ", $erreurs);
            header("Location: routeur.php?controleur=controleurEtudiant&action=afficherCovoiturage");
        } else {
            header("Location: routeur.php?controleur=controleurGlobal&action=accueil&msg=covoiturage_ok");
        }
        exit();
    }

    public static function importerNotes() {
        if (!isset($_SESSION['role']) || !in_array($_SESSION['role'], self::$roles_autorises)) {
            header("Location: routeur.php?error=access_denied");
            exit();
        }

        if (isset($_FILES['fichier_csv']) && $_FILES['fichier_csv']['error'] == 0) {
            $file = $_FILES['fichier_csv']['tmp_name'];
            $handle = fopen($file, "r");
            $db = Connexion::pdo();
            $header = fgetcsv($handle, 1000, ";");
            
            $matieresIds = [];
            $nbImportes = 0;

            for ($i = 2; $i < count($header); $i++) {
                $nomMat = trim($header[$i]);
                if (empty($nomMat)) continue;
                $st = $db->prepare("SELECT numMatiere FROM Matiere WHERE NomMatiere = ?");
                $st->execute([$nomMat]);
                $res = $st->fetch();
                if ($res) {
                    $matieresIds[$i] = $res['numMatiere'];
                } else {
                    $ins = $db->prepare("INSERT INTO Matiere (NomMatiere) VALUES (?)");
                    $ins->execute([$nomMat]);
                    $matieresIds[$i] = $db->lastInsertId();
                }
            }

            while (($data = fgetcsv($handle, 1000, ";")) !== FALSE) {
                $numEtu = trim($data[0]);
                $numSem = trim($data[1]);
                $checkEtu = $db->prepare("SELECT COUNT(*) FROM Etudiant WHERE Numetu = ?");
                $checkEtu->execute([$numEtu]);
                
                if ($checkEtu->fetchColumn() == 0) continue; 

                for ($i = 2; $i < count($data); $i++) {
                    if (!isset($matieresIds[$i])) continue;
                    $valeurNote = str_replace(',', '.', trim($data[$i]));
                    if ($valeurNote !== "" && $valeurNote !== null) {
                        $sqlNote = "INSERT INTO Note (valeur, valeurMax, Numetu, NumSemestre, numMatiere) 
                                    VALUES (:val, 20, :etu, :sem, :mat)
                                    ON DUPLICATE KEY UPDATE valeur = :val";
                        $stNote = $db->prepare($sqlNote);
                        $stNote->execute([':val' => $valeurNote, ':etu' => $numEtu, ':sem' => $numSem, ':mat' => $matieresIds[$i]]);
                        $nbImportes++;
                    }
                }
            }
            fclose($handle);
            header("Location: routeur.php?controleur=controleurEtudiant&action=lireObjets&msg=import_ok&count=$nbImportes");
            exit();
        }
    }

    public static function mesGroupes() {
        $numEtu = $_SESSION['user']->get('Numetu');
        $db = Connexion::pdo();

        $sqlG = "SELECT td.NomGroupTD as nomTD, tp.NomGroupTP as nomTP, g.NumGroupTD
                FROM Etudiant e
                JOIN Group_ g ON e.NumGroup = g.NumGroup
                LEFT JOIN GroupTD td ON g.NumGroupTD = td.NumGroupTD
                LEFT JOIN GroupTP tp ON g.NumGroupTP = tp.NumGroupTP
                WHERE e.Numetu = ?";
        
        $stG = $db->prepare($sqlG);
        $stG->execute([$numEtu]);
        $groupes = $stG->fetch(PDO::FETCH_ASSOC);
        $camarades = [];
        if ($groupes) {
            $numGroupEtu = $_SESSION['user']->get('NumGroup');
            $sqlC = "SELECT Nometu, Prenometu, MailProEtu 
                    FROM Etudiant 
                    WHERE NumGroup = ? AND Numetu != ?
                    ORDER BY Nometu ASC";
            $stC = $db->prepare($sqlC);
            $stC->execute([$numGroupEtu, $numEtu]);
            $camarades = $stC->fetchAll(PDO::FETCH_ASSOC);
        }

        $titre = "Mes Groupes";
        include("vue/debut.php");
        include("vue/vueMesGroupes.php");
        include("vue/fin.html");
    }

    public static function afficherFormulaireImport() {
        $role = $_SESSION['role'] ?? '';
        if (!in_array($role, self::$roles_autorises)) {
            header("Location: routeur.php?action=accueil&error=access_denied_role_" . urlencode($role));
            exit();
        }

        $titre = "Importation des Notes";
        include("vue/debut.php");
        include("vue/vueImportNotes.php"); 
        include("vue/fin.html");
    }
}
?>