<div class="content-container">
    <div class="stats-grid-enseignant">
    <div class="stat-card-white">
        <h3>Enseignant</h3>
        <div class="big-num"><?php echo $nbTotal; ?></div>
    </div>
    <div class="stat-card-white">
        <h3>Responsables de Filière</h3>
        <div class="big-num"><?php echo $nbFiliere; ?></div>
    </div>
    <div class="stat-card-white">
        <h3>Resp. Semestre</h3>
        <div class="big-num"><?php echo $nbSemestre; ?></div>
    </div>
    <div class="stat-card-white">
        <h3>Resp. Année</h3>
        <div class="big-num"><?php echo $nbAnnee; ?></div>
    </div>
    </div>

    <div class="action-bar">
        
        <button id="openModal" class="btn-add">Ajouter Enseignant</button>
    </div>

    

    <div id="addTeacherModal" class="modal">
        <div class="modal-content">
            <span class="close-btn">&times;</span>
            <h2 style="text-align:center; font-size: 24px;">Ajout Enseignant</h2>
            
            <form action="routeur.php?controleur=controleurEnseignant&action=creer" method="post" class="modal-form">
                <div class="input-group">
                    <label>Nom :</label>
                    <input type="text" name="NomEnsei" required>
                </div>

                <div class="input-group">
                    <label>Prénom :</label>
                    <input type="text" name="PrenomEnsei" required>
                </div>

                <div class="input-group">
                    <label>Genre :</label>
                    <select name="GenreEnsei">
                        <option value="M">Masculin</option>
                        <option value="F">Féminin</option>
                    </select>
                </div>

                <div class="input-group">
                    <label>Email Pro :</label>
                    <input type="email" name="MailProEnsei" required>
                </div>

                <div class="input-group">
                    <label>Email Perso :</label>
                    <input type="email" name="MailPersoEnsei">
                </div>

                <div class="input-group">
                    <label>Adresse :</label>
                    <input type="text" name="AddrEnsei" placeholder="Ex: 5 rue des Lilas, Paris">
                </div>

                <div class="input-group">
                    <label>Rôle :</label>
                    <select name="Numrole">
                        <option value="1">Responsable semestre</option>
                        <option value="2">Responsable d'année</option>
                        <option value="3">Responsable de filières</option>
                        <option value="4">Responsable de formation</option>
                    </select>
                </div>

                <div class="input-group">
                    <label>Mot de passe :</label>
                    <input type="password" name="password" required placeholder="Mot de passe temporaire">
                </div>

                <button type="submit" class="btn-submit-modal">Valider l'ajout</button>
            </form>
        </div>
    </div>

    <table class="teacher-table">
        <thead>
            <tr>    
                <th>Id</th>
                <th>Nom Prénom</th>
                <th>Genre</th>
                <th>Rôle</th>
                <th>Email Pro</th>
                <th>Address</th>
            </tr>
        </thead>
        <tbody>
            <?php if(!empty($aff)) { foreach($aff as $l) echo $l; } ?>
        </tbody>
    </table>
</div>

<script>
    var modal = document.getElementById("addTeacherModal");
    var btn = document.getElementById("openModal");
    var span = document.getElementsByClassName("close-btn")[0];

    btn.onclick = function() { modal.style.display = "block"; }
    span.onclick = function() { modal.style.display = "none"; }
    window.onclick = function(event) {
        if (event.target == modal) { modal.style.display = "none"; }
    }
</script>