<div id="page-covoiturage" class="content-container">
    <div class="stat-card-white">
        <h3> Mon Groupe de Covoiturage</h3>
        <p>Saisissez le <b>Nom</b> et le <b>Prénom</b> de vos collègues.</p>
        <p><small>(Le groupe peut contenir de 2 à 4 personnes maximum, vous inclus)</small></p>

        <?php if (isset($_SESSION['error_covoit'])): ?>
            <div class="alert-error-covoit">
                ⚠️ <?php echo $_SESSION['error_covoit']; unset($_SESSION['error_covoit']); ?>
            </div>
        <?php endif; ?>
        
        <form action="routeur.php?controleur=controleurEtudiant&action=enregistrerCovoiturage" method="post" class="modal-form">
            <div id="inputs-container">
                
                <div class="collegue-bloc">
                    <label class="bloc-label">Collègue 1 :</label>
                    <input type="text" name="nom[]" placeholder="NOM" required class="input-covoit">
                    <input type="text" name="prenom[]" placeholder="Prénom" required class="input-covoit">
                </div>

                <div class="collegue-bloc">
                    <label class="bloc-label">Collègue 2 (Optionnel) :</label>
                    <input type="text" name="nom[]" placeholder="NOM" class="input-covoit">
                    <input type="text" name="prenom[]" placeholder="Prénom" class="input-covoit">
                </div>

                <div class="collegue-bloc">
                    <label class="bloc-label">Collègue 3 (Optionnel) :</label>
                    <input type="text" name="nom[]" placeholder="NOM" class="input-covoit">
                    <input type="text" name="prenom[]" placeholder="Prénom" class="input-covoit">
                </div>

            </div>
            
            <div class="footer-form">
                <button type="submit" class="btn-submit-covoit">
                    Enregistrer le groupe
                </button>
            </div>
        </form>
    </div>
</div>