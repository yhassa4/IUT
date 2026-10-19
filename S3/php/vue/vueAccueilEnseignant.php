<div class="content-container">
    <div class="list-panel full-width">
        <div class="filter-bar">
            <select class="select-styled">
                <option>Promotion 1 ⌵</option>
            </select>
        </div>

        <table class="survey-list-table">
            <thead>
                <tr>
                    <th>Id Etudiant</th>
                    <th>Nom Prénom</th>
                    <th>Genre</th>
                    <th>Type Bac</th>
                    <th>Statut</th>
                    <th>Groupe</th>
                </tr>
            </thead>
            <tbody>
                <?php foreach($tab_obj as $e): ?>
                <tr>
                    <td><strong><?php echo $e->get('NumEtud'); ?></strong></td>
                    <td><?php echo $e->get('Prenometu') . ' ' . $e->get('Nometu'); ?></td>
                    <td><?php echo $e->get('Genre'); ?></td>
                    <td><?php echo $e->get('TypeBac'); ?></td>
                    <td><?php echo $e->get('Statut'); ?></td>
                    <td><?php echo $e->get('NomGroupe'); ?></td>
                </tr>
                <?php endforeach; ?>
            </tbody>
        </table>
    </div>
</div>