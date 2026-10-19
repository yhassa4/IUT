<div class="content-container">
    <div class="stats-grid">
        <div class="stat-card-big"><h3>Sondages En Cours</h3><div class="number"><?php echo $nbEnCours; ?></div></div>
        <div class="stat-card-big"><h3>Sondages Terminés</h3><div class="number"><?php echo $nbTermines; ?></div></div>
        <div class="stat-card-big"><h3>Sondage Total</h3><div class="number"><?php echo $nbTotal; ?></div></div>
    </div>

    <div class="main-management-grid">
        <div class="creation-panel survey-creation">
            <h2>Création de Sondage</h2>
            <form action="routeur.php?controleur=controleurSondage&action=creer" method="post">
                <div class="survey-flex">
                    <div class="survey-left-col">
                        <label>Titre du Sondage</label>
                        <input type="text" name="titre" class="input-styled" placeholder="Ex: Avis sur le module PHP" required>
                        <br/>

                        <label>Date De Fin :</label>
                        <div class="date-inputs">
                            <input type="date" name="dateFin" class="input-styled" required>
                        </div>
                        <br/>

                        <label>Promotion ciblée :</label>
                        <select name="promo" class="promo-select-full">
                            <option value="1">BUT 1</option>
                            <option value="2">BUT 2</option>
                            <option value="3">BUT 3</option>
                        </select>
                    </div>

                    <div class="survey-right-col">
                        <h3 class="title-questions">Questions du Sondage</h3>
                        <div id="questions-container">
                            <div class="question-box">
                                <div class="question-header"><span>Question 1</span></div>
                                <textarea name="questions[]" placeholder="Entrez votre question ici..." required></textarea>
                            </div>
                        </div>
                        
                        <button type="button" onclick="ajouterQuestion()" style="margin-bottom: 15px; background: #6c757d; color: white; border: none; padding: 5px 10px; border-radius: 4px; cursor: pointer;">
                            + Ajouter une question
                        </button>

                        <label>Envoyer une notification Mail :</label>
                        <div class="radio-group">
                            <label><input type="radio" name="mail" value="1" checked> Oui</label>
                            <label><input type="radio" name="mail" value="0"> Non</label>
                        </div>
                        
                        <button type="submit" class="btn-cree">Créer le sondage</button>
                    </div>
                </div>
            </form>
        </div>

        <div class="list-panel">
            <h2>Liste des Sondages</h2>
            <table class="survey-list-table">
                <thead>
                    <tr>
                        <th>Nom</th>
                        <th>Statut</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <?php if (empty($lesSondages)): ?>
                        <tr><td colspan="3" style="text-align:center;">Aucun sondage créé.</td></tr>
                    <?php else: ?>
                        <?php foreach($lesSondages as $s): 
                            $id = $s->get('Numsondage');
                        ?>
                        <tr>
                            <td><strong><?php echo htmlspecialchars($s->get('nomsondage')); ?></strong></td>
                            <td>
                                <span class="badge <?php echo ($s->get('typesondage') == 1) ? 'badge-oui' : 'badge-non'; ?>">
                                    <?php echo ($s->get('typesondage') == 1) ? 'En cours' : 'Terminé'; ?>
                                </span>
                            </td>
                            <td>
                                <div style="display: flex; gap: 10px;">
                                    <a href="routeur.php?controleur=controleurSondage&action=voirResultats&id=<?php echo $id; ?>" class="btn-search" title="Voir les résultats">📊</a>
                                    
                                    <a href="routeur.php?controleur=controleurSondage&action=supprimer&id=<?php echo $id; ?>" 
                                       onclick="return confirm('Supprimer ce sondage ?');" 
                                       class="btn-delete" title="Supprimer">🗑️</a>
                                </div>
                            </td>
                        </tr>
                        <?php endforeach; ?>
                    <?php endif; ?>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script>
// Script pour ajouter des champs de texte pour les questions supplémentaires
let questionCount = 1;
function ajouterQuestion() {
    questionCount++;
    const container = document.getElementById('questions-container');
    const div = document.createElement('div');
    div.className = 'question-box';
    div.innerHTML = `
        <div class="question-header"><span>Question ${questionCount}</span></div>
        <textarea name="questions[]" placeholder="Entrez votre question ici..." required></textarea>
    `;
    container.appendChild(div);
}
</script>