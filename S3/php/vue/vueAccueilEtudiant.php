<div class="student-dashboard">
    <div class="info-card">
        <h3>Mes Informations</h3>
        <p><strong>Nom:</strong> <?php echo $_SESSION['user']->get('Prenometu') . ' ' . $_SESSION['user']->get('Nometu'); ?></p>
        <p><strong>Email:</strong> <?php echo $_SESSION['user']->get('MailProEtu'); ?></p>
        <p><strong>Groupe:</strong> Groupe <?php echo $_SESSION['user']->get('NumGroup'); ?></p>
        <p><strong>Niveau:</strong> BUT <?php echo $_SESSION['user']->get('NumPromo'); ?></p>
    </div>

    <div class="stat-card-white">
        <h3>Listes des Sondages</h3>
        
        <?php if (empty($lesSondages)): ?>
            <p>Aucun Sondage Actif Pour Le Moment</p>
        <?php else: ?>
            <table class="survey-list-table">
                <thead>
                    <tr>
                        <th>Titre du Sondage</th>
                        <th>Date Limite</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <?php foreach ($lesSondages as $s): ?>
                        <tr>
                            <td><?php echo htmlspecialchars($s->get('nomsondage')); ?></td>
                            <td><?php echo $s->get('DateFinSondage'); ?></td>
                            <td>
                                <a href="routeur.php?controleur=controleurSondage&action=repondre&id=<?php echo $s->get('Numsondage'); ?>" class="btn-login" style="padding: 5px 15px; text-decoration: none; font-size: 0.9rem;">
                                    Répondre
                                </a>
                            </td>
                        </tr>
                    <?php endforeach; ?>
                </tbody>
            </table>
        <?php endif; ?>
    </div>
</div>