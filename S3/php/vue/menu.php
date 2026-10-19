<?php
$current_page = isset($_GET['controleur']) ? $_GET['controleur'] : 'home';
?>

<div class="top-bar">
    <div class="logo-univ">
        université PARIS-SACLAY | IUT D'ORSAY
    </div>
</div>

<div class="main-nav">
    <div class="nav-left">
        <div class="app-title">
            🎓 Plateforme De Gestion Des Groupes
        </div>
        <select class="select-promo">
            <option>Promotion 1</option>
            <option>Promotion 2</option>
        </select>
    </div>

    <div class="nav-right">
        <span class="user-name">Paul Pierre</span>
        <span class="badge-role">Responsables</span>
        <a href="index.php?action=deconnexion" class="btn-logout">Deconnexion</a>
    </div>
</div>

<div class="sub-nav-container">
    <div class="sub-nav">
        <a href="routeur.php?controleur=controleurGlobal&action=accueil" 
           class="nav-link <?= ($current_page == 'controleurGlobal') ? 'active' : '' ?>">
           Vue D'ensemble
        </a>

        <a href="routeur.php?controleur=controleurEnseignant&action=lireObjets" 
           class="nav-link <?= ($current_page == 'controleurEnseignant') ? 'active' : '' ?>">
           Enseignant
        </a>

        <a href="routeur.php?controleur=controleurGroupe&action=lireObjets" 
           class="nav-link <?= ($current_page == 'controleurGroupe') ? 'active' : '' ?>">
           Groupe
        </a>

        <a href="routeur.php?controleur=ControleurSondage&action=lireObjets" 
           class="nav-link <?= ($current_page == 'ControleurSondage') ? 'active' : '' ?>">
           Sondage
        </a>

        <a href="routeur.php?controleur=controleurEtudiant&action=lireObjets" 
           class="nav-link <?= ($current_page == 'controleurEtudiant') ? 'active' : '' ?>">
           Etudiant
        </a>
    </div>
</div>