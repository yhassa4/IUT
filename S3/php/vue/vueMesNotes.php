<div class="content-container">
    <h2>Mes Notes</h2>

    

    <table class="teacher-table">
        <thead>
            <tr>
                <th>Semestre</th>
                <th>Matière</th>
                <th>Note</th>
                <th>Note MAX</th>
            </tr>
        </thead>
        <tbody>
            <?php foreach($mesNotes as $n): ?>
            <tr>
                <td><?php echo htmlspecialchars($n['Nomsemestre']); ?></td>
                <td><?php echo htmlspecialchars($n['NomMatiere']); ?></td>
                <td><?php echo number_format($n['valeur'], 1); ?></td>
                <td><?php echo number_format($n['valeurMax'], 1); ?></td>
            </tr>
            <?php endforeach; ?>
        </tbody>
    </table>

    <div style="background-color: #b30b44; color: white; padding: 15px; margin-top: 20px; text-align: right; border-radius: 4px; font-weight: bold;">
        MOYENNE DU SEMESTRE : <?php echo number_format($moyenne, 2); ?> / 20
    </div>
</div>