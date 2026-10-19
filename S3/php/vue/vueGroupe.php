<div class="content-container">
    <div class="stats-grid">
        <div class="stat-card-big"><h3>Étudiants</h3><div class="number"><?php echo $nbEtudiants; ?></div></div>
        <div class="stat-card-big"><h3>Redoublant</h3><div class="number"><?php echo $nbRedoublants; ?></div></div>
        <div class="stat-card-big"><h3>Apprentie</h3><div class="number"><?php echo $nbApprentis; ?></div></div>
    </div>

    <div class="main-management-grid">
        
        <div class="creation-panel">
            <h2>Création d'un Nouveau Groupe</h2>
            <form action="routeur.php?controleur=controleurGroupe&action=creer" method="post">
                
                <label>Promotion :</label>
                <select name="NumPromo" class="input-styled">
                    <option value="1">Promotion 1</option>
                    <option value="2">Promotion 2</option>
                    <option value="3">Promotion 3</option>
                    <option value="4">Promotion 4</option>
                </select>

                <label>Enseignant Référent (ID) :</label>
                <input type="number" name="NumEnsei" class="input-styled" placeholder="Ex: 1" required>

                <div style="display: flex; gap: 10px;">
                    <div>
                        <label>Groupe TD :</label>
                        <input type="number" name="NumGroupTD" class="input-styled" required>
                    </div>
                    <div>
                        <label>Groupe TP :</label>
                        <input type="number" name="NumGroupTP" class="input-styled" required>
                    </div>
                </div>

                <label>Date De Publication :</label>
                <input type="date" name="DatePubli" class="input-styled" required>

                <button type="submit" class="btn-valider">Valider la Création</button>
            </form>
        </div>

        <div class="list-panel">
            <div class="list-header">Liste Groupe : Promotion 1</div>
            <div class="group-items">
                <?php foreach($lesGroupes as $g): ?>
                <div class="group-row">
                    <span><?php echo $g->get('NumGroup'); ?></span>
                    
                </div>
                <?php endforeach; ?>
            </div>
            
            </div>
        </div>
    </div>
</div>