<div id="page-promotion" class="content-container">
    <div class="stat-card-white">
        <div class="header-flex">
            <h3>👥 Liste de la Promotion</h3>
            <span class="badge-promo">Promotion <?php echo htmlspecialchars($_SESSION['user']->get('NumPromo')); ?></span>
        </div>
        
        <table class="table-liste">
            <thead>
                <tr>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Email Universitaire</th>
                </tr>
            </thead>
            <tbody>
                <?php foreach ($camarades as $c): ?>
                    <tr>
                        <td><strong><?php echo htmlspecialchars(strtoupper($c['Nometu'])); ?></strong></td>
                        <td><?php echo htmlspecialchars($c['Prenometu']); ?></td>
                        <td><code><?php echo htmlspecialchars($c['MailProEtu']); ?></code></td>
                    </tr>
                <?php endforeach; ?>
            </tbody>
        </table>
    </div>
</div>