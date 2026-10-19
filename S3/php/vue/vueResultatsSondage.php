<link rel="stylesheet" href="css/styleSondage.css">

<div class="content-container">
    <h2>Résultats : <?php echo htmlspecialchars($sondageNom); ?></h2>

    <div class="stats-container">
        <div class="stat-box total">
            <p>Total Réponses</p>
            <h3><?php echo $total; ?></h3>
        </div>
        
        <div class="stat-box oui">
            <p>Votants OUI</p>
            <h3><?php echo $stats['Oui']; ?></h3>
            <small><?php echo ($total > 0) ? round(($stats['Oui']/$total)*100) : 0; ?>%</small>
        </div>

        <div class="stat-box non">
            <p>Votants NON</p>
            <h3><?php echo $stats['Non']; ?></h3>
            <small><?php echo ($total > 0) ? round(($stats['Non']/$total)*100) : 0; ?>%</small>
        </div>

        <div class="stat-box avis">
            <p>SANS AVIS</p>
            <h3><?php echo $stats['Sans avis']; ?></h3>
            <small><?php echo ($total > 0) ? round(($stats['Sans avis']/$total)*100) : 0; ?>%</small>
        </div>
    </div>

    <h3>Liste détaillée des votes</h3>
    <table class="teacher-table">
        <thead>
            <tr>
                <th>Étudiant</th>
                <th>Question</th>
                <th>Réponse</th>
            </tr>
        </thead>
        <tbody>
            <?php if (empty($resultats)): ?>
                <tr>
                    <td colspan="3" style="text-align: center; color: #999;">Aucune réponse pour le moment.</td>
                </tr>
            <?php else: ?>
                <?php foreach ($resultats as $res): ?>
                <tr>
                    <td><?php echo htmlspecialchars($res['Nometu'] . " " . $res['Prenometu']); ?></td>
                    <td><?php echo htmlspecialchars($res['QuestionTexte']); ?></td>
                    <td>
                        <?php 
                            $classeBadge = 'badge-avis';
                            if($res['reponse'] == 'Oui') $classeBadge = 'badge-oui';
                            if($res['reponse'] == 'Non') $classeBadge = 'badge-non';
                        ?>
                        <span class="badge <?php echo $classeBadge; ?>">
                            <?php echo htmlspecialchars($res['reponse']); ?>
                        </span>
                    </td>
                </tr>
                <?php endforeach; ?>
            <?php endif; ?>
        </tbody>
    </table>
    
    <a href="routeur.php?controleur=controleurSondage&action=lireObjets" class="btn-back">
        ← Retour aux sondages
    </a>
</div>