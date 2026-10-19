<div class="content-container">
    <div class="survey-card">
        <h2><?php echo htmlspecialchars($sondage->get('nomsondage')); ?></h2>
        <p>Date limite : <?php echo $sondage->get('DateFinSondage'); ?></p>

        <form action="routeur.php?controleur=controleurSondage&action=voter" method="post">
            <input type="hidden" name="idSondage" value="<?php echo $sondage->get('Numsondage'); ?>">
            
            <?php foreach ($questions as $q): ?>
                <div class="question-item" style="margin-bottom: 20px; border-bottom: 1px solid #eee; padding-bottom: 10px;">
                    <p><strong><?php echo htmlspecialchars($q['TextQuestion']); ?></strong></p>
                    
                    <div class="options">
                        <label>
                            <input type="radio" name="reponses[<?php echo $q['Numquestion']; ?>]" value="Oui" required> Oui
                        </label>
                        <label>
                            <input type="radio" name="reponses[<?php echo $q['Numquestion']; ?>]" value="Non"> Non
                        </label>
                        <label>
                            <input type="radio" name="reponses[<?php echo $q['Numquestion']; ?>]" value="Sans avis"> Sans avis
                        </label>
                    </div>
                </div>
            <?php endforeach; ?>

            <button type="submit" class="btn-submit">Envoyer mes préférences</button>
        </form>
    </div>
</div>