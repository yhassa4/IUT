<?php
// ca marche pas du coup j'ai force en minuscules pour la comparaison
$roleUser = mb_strtolower($_SESSION['role'] ?? 'enseignant');

$liste_autorisee = [
    'responsable semestre', 
    'responsable d annee', 
    'responsable de fillieres', 
    'responsable de formation', 
    'admin', 
    'responsable'
];


$canEdit = in_array($roleUser, $liste_autorisee);

?>

<div class="content-container">
    <div class="stats-grid-small">
        <div class="stat-card-white">
            <h3>Étudiants</h3>
            <div class="big-num"><?php echo $nbEtudiants; ?></div>
        </div>
        <div class="stat-card-white">
            <h3>Groupes</h3>
            <div class="big-num"><?php echo $nbGroupes; ?></div>
        </div>
        <div class="stat-card-white">
            <h3>Promotions</h3>
            <div class="big-num"><?php echo $nbPromotions; ?></div>
        </div>
    </div>

    <div class="action-bar-etudiant">
        
        
        <?php if ($canEdit): ?>
            <button id="openModalEtudiant" class="btn-add-etudiant">ajouter etudiant</button>
        <?php endif; ?>
    </div>

    <table class="teacher-table"> 
        <thead>
            <tr>
                <th>Id Etudiant</th>
                <th>Nom Prénom</th>
                <th>Genre</th>
                <th>Type Bac</th>
                <th>Statut</th>
                <th>Groupe</th>
                <th>Mail</th>
                
            </tr>
        </thead>
        <tbody>
            <?php 
            if (!empty($aff)) {
                foreach($aff as $ligne) {
                    echo $ligne;
                }
            } else {
                $colCount = $canEdit ? 8 : 7;
                echo "<tr><td colspan='$colCount' style='text-align:center;'>Aucun étudiant trouvé</td></tr>";
            }
            ?>
        </tbody>
    </table>
</div>

<?php if ($canEdit): ?>
<div id="modalEtudiant" class="modal">
    <div class="modal-content" style="max-height: 85vh; overflow-y: auto;">
        <span class="close-btn">&times;</span>
        <h2 style="text-align:center;">Ajout Étudiant</h2>
        
        <form action="routeur.php?controleur=controleurEtudiant&action=creer" method="post" class="modal-form">
            <label>Nom :</label>
            <input type="text" name="nomEtu" required>
            
            <label>Prénom :</label>
            <input type="text" name="prenomEtu" required>

            <label>Genre :</label>
            <select name="genreEtu">
                <option value="M">Masculin</option>
                <option value="F">Féminin</option>
            </select>

            <label>Type de Bac :</label>
            <select name="typeBac">
                <option value="1">Bac Général</option>
                <option value="2">Bac Technologique</option>
                <option value="3">Bac Professionnel</option>
                <option value="4">Bac STI2D</option>
                <option value="5">Bac STMG</option>
            </select>

            <label>Statut :</label>
            <select name="statutEtu">
                <option value="Apprentissage">Apprentissage</option>
                <option value="Initial">Initial</option>
                <option value="Redoublant">Redoublant</option>
            </select>

            <div class="input-group">
                <label>Email Personnel :</label>
                <input type="email" name="MailPersoEtu" placeholder="exemple@gmail.com" required>
            </div>

            <div class="input-group">
                <label>Adresse :</label>
                <input type="text" name="AddrEtu" placeholder="Rue, Ville, Code Postal" required>
            </div>

            <div class="input-group">
                <label>Mot de passe :</label>
                <input type="password" name="password" required>
            </div>

            <div class="input-group">
                <label>Promotion (1 à 6) :</label>
                <input type="number" name="NumPromo" min="1" max="6" value="1" required>
            </div>

            <div class="input-group">
                <label>Groupe (1, 12, 18, etc.) :</label>
                <input type="number" name="NumGroup" value="1" required>
            </div>

            <div class="input-group">
                <label>Parcours (1:A, 2:B, 3:C) :</label>
                <select name="Numparcour">
                    <option value="1">Parcours A</option>
                    <option value="2">Parcours B</option>
                    <option value="3">Parcours C</option>
                </select>
            </div>

            <button type="submit" class="btn-submit-modal">Valider l'ajout</button>
        </form>
    </div>
</div>

<script>
    var modalEtu = document.getElementById("modalEtudiant");
    var btnEtu = document.getElementById("openModalEtudiant");
    var spanEtu = document.querySelector("#modalEtudiant .close-btn");

    if (btnEtu) {
        btnEtu.onclick = function() { modalEtu.style.display = "block"; }
        spanEtu.onclick = function() { modalEtu.style.display = "none"; }
        window.onclick = function(event) {
            if (event.target == modalEtu) { modalEtu.style.display = "none"; }
        }
    }
</script>
<?php endif; ?>