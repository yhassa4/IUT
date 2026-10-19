<?php

 // Affiche les affectations de groupe de l'étudiant et la liste de ses camarades.
 
?>

<link rel="stylesheet" href="css/styleEtudiant.css">

<div id="page-mes-groupes" class="content-container">
    
    <div class="stat-card-white">
        <h2 class="group-section-title">🎓 Mes Groupes</h2>
        
        <div class="creation-panel">
            <p class="info-texte">Retrouvez ici vos affectations pédagogiques pour le semestre en cours.</p>
            
            <div class="group-display-container">
                <div class="group-card">
                    <span class="group-label">Affectation TD</span>
                    <span class="group-name">
                        <?php echo htmlspecialchars($groupes['nomTD'] ?? 'Non assigné'); ?>
                    </span>
                </div>

                <div class="group-card">
                    <span class="group-label">Affectation TP</span>
                    <span class="group-name">
                        <?php echo htmlspecialchars($groupes['nomTP'] ?? 'Non assigné'); ?>
                    </span>
                </div>
            </div>
        </div>

        <div class="camarades-section">
            <h3 class="group-section-title secondary">👥 Mes camarades de groupe (TD)</h3>
            
            <table class="table-liste">
                <thead>
                    <tr>
                        <th>Nom</th>
                        <th>Prénom</th>
                        <th>Email Universitaire</th>
                    </tr>
                </thead>
                <tbody>
                    <?php if (!empty($camarades)): ?>
                        <?php foreach ($camarades as $c): ?>
                            <tr>
                                <td><strong><?php echo htmlspecialchars(strtoupper($c['Nometu'])); ?></strong></td>
                                <td><?php echo htmlspecialchars($c['Prenometu']); ?></td>
                                <td>
                                    <a href="mailto:<?php echo htmlspecialchars($c['MailProEtu']); ?>" class="email-link">
                                        <code><?php echo htmlspecialchars($c['MailProEtu']); ?></code>
                                    </a>
                                </td>
                            </tr>
                        <?php endforeach; ?>
                    <?php else: ?>
                        <tr>
                            <td colspan="3" class="no-data">
                                Vous n'avez pas encore de camarades assignés à ce groupe.
                            </td>
                        </tr>
                    <?php endif; ?>
                </tbody>
            </table>
        </div>
    </div>
</div>