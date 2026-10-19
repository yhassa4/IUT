<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title><?php echo $titre ?? 'Gestion des Groupes'; ?></title>
    <link rel="stylesheet" href="./css/styleMenu.css">
    <link rel="stylesheet" href="css/style_covoiturage.css">
    <link rel="stylesheet" href="css/styleEtudiant.css">
</head>
<body>

    <div class="top-header">
        <div class="logo-univ">Université PARIS-SACLAY</div>
        <div class="iut-text">IUT D'ORSAY</div>
    </div>

    <div class="title-bar">
        <div class="app-title">
            <span>🎓 Plateforme De Gestion Des Groupes</span>
        </div>
        
        <div class="user-nav">
            <?php if (isset($_SESSION['user'])): ?>
                <?php 
                    $role = $_SESSION['role'];
                    // Un utilisateur est considéré responsable s'il a le rôle admin ou l'un des IDs numériques 1,2,3,4
                    $liste_respo = [1, 2, 3, 4, '1', '2', '3', '4', 'Responsable semestre', 'Responsable d annee', 'Responsable de fillieres', 'Responsable de formation', 'admin', 'responsable'];
                    $is_responsable = in_array($role, $liste_respo);
                    
                ?>

                <div class="promo-badge">
                    <?php 
                        echo ($role === 'etudiant') ? 'Promo ' . $_SESSION['user']->get('NumPromo') : 'Administration'; 
                    ?> ⌵
                </div>

                <span class="user-name">
                    <?php 
                        if ($role === 'etudiant') {
                            echo htmlspecialchars($_SESSION['user']->get('Prenometu') . ' ' . $_SESSION['user']->get('Nometu'));
                        } else {
                            echo htmlspecialchars($_SESSION['user']->get('PrenomEnsei') . ' ' . $_SESSION['user']->get('NomEnsei'));
                        }
                    ?>
                </span>

                <span class="role-badge">
                    <?php 
                        if (in_array($role, [1, 2, 3, 4])) echo "Responsable";
                        else echo ucfirst($role); 
                    ?>
                </span>
                
                <a href="routeur.php?controleur=controleurConnexion&action=deconnecter" class="logout-btn">Deconnexion</a>
            <?php endif; ?>
        </div>
    </div>

    <div class="menu-container">
        <div class="nav-pills">
            <?php 
                $c = $_GET['controleur'] ?? 'controleurGlobal'; 
                $a = $_GET['action'] ?? 'accueil';
            ?>
            
            <a href="routeur.php?controleur=controleurGlobal&action=accueil" 
               class="nav-link <?php echo ($c == 'controleurGlobal' && $a == 'accueil') ? 'active' : ''; ?>">VUE D'ENSEMBLE</a>
            
            <?php if (isset($_SESSION['role'])): ?>
                
                <?php if ($role === 'etudiant'): ?>
                    <a href="routeur.php?controleur=controleurEtudiant&action=mesNotes" 
                       class="nav-link <?php echo ($a == 'mesNotes') ? 'active' : ''; ?>">MES NOTES</a>
                    
                    <a href="routeur.php?controleur=controleurEtudiant&action=mesGroupes" 
                       class="nav-link <?php echo ($a == 'mesGroupes') ? 'active' : ''; ?>">MES GROUPES</a>

                    <a href="routeur.php?controleur=controleurEtudiant&action=afficherCovoiturage" 
                       class="nav-link <?php echo ($a == 'afficherCovoiturage') ? 'active' : ''; ?>">COVOITURAGE</a>

                    <a href="routeur.php?controleur=controleurEtudiant&action=voirPromotion" 
                        class="nav-link <?php echo ($a == 'voirPromotion') ? 'active' : ''; ?>">MA PROMO</a>

                <?php elseif ($is_responsable): ?>
    
                    <?php if ($role === 'admin' || $role == 4 || $role === 'Responsable de formation'): ?>
                        <a href="routeur.php?controleur=controleurEnseignant&action=lireObjets" 
                        class="nav-link <?php echo ($c == 'controleurEnseignant') ? 'active' : ''; ?>">ENSEIGNANTS</a>
                    <?php endif; ?>
                    
                    <a href="routeur.php?controleur=controleurGroupe&action=lireObjets" 
                    class="nav-link <?php echo ($c == 'controleurGroupe') ? 'active' : ''; ?>">GROUPES</a>
                    
                    <a href="routeur.php?controleur=controleurSondage&action=lireObjets" 
                    class="nav-link <?php echo ($c == 'controleurSondage') ? 'active' : ''; ?>">SONDAGES</a>
                    
                    <a href="routeur.php?controleur=controleurEtudiant&action=lireObjets" 
                    class="nav-link <?php echo ($c == 'controleurEtudiant' && $a == 'lireObjets') ? 'active' : ''; ?>">ÉTUDIANTS</a>

                    <a href="routeur.php?controleur=controleurEtudiant&action=afficherFormulaireImport" 
                    class="nav-link <?php echo ($a == 'afficherFormulaireImport') ? 'active' : ''; ?>"> IMPORT NOTES</a>

                <?php endif; ?>
            <?php endif; ?>
        </div>
    </div>

    <div class="content-body">
        
        <?php if (isset($_GET['msg']) || isset($_GET['error'])): ?>
            <div class="notification-area">
                
                <?php if (isset($_GET['msg']) && $_GET['msg'] == 'vote_ok'): ?>
                    <div class="alert alert-success"><span>✅</span> Votre vote a été enregistré avec succès !</div>
                <?php endif; ?>

                <?php if (isset($_GET['msg']) && $_GET['msg'] == 'covoiturage_ok'): ?>
                    <div class="alert alert-success"><span>🚗</span> Vos préférences de covoiturage ont été enregistrées !</div>
                <?php endif; ?>

                <?php if (isset($_GET['msg']) && $_GET['msg'] == 'import_ok'): ?>
                    <div class="alert alert-success"><span>📊</span> Les notes ont été importées avec succès !</div>
                <?php endif; ?>

                <?php if (isset($_GET['error']) && $_GET['error'] == 'access_denied'): ?>
                    <div class="alert alert-danger"><span>🚫</span> Accès refusé. Vous n'avez pas les droits pour cette page.</div>
                <?php endif; ?>

                <?php if (isset($_GET['error']) && $_GET['error'] == 'expire'): ?>
                    <div class="alert alert-danger"><span>⚠️</span> Désolé, ce sondage est fermé.</div>
                <?php endif; ?>
                
                <?php if (isset($_GET['msg']) && $_GET['msg'] == 'supprime'): ?>
                    <div class="alert alert-info"><span>🗑️</span> Le sondage a bien été supprimé.</div>
                <?php endif; ?>

            </div>
        <?php endif; ?>