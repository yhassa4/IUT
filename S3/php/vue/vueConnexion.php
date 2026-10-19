<link rel="stylesheet" href="css/styleConnexion.css">

<div class="login-wrapper">
    <div class="login-card">
        
        <h2>Plateforme de Gestion des Groupes</h2>

        <form action="routeur.php?controleur=controleurConnexion&action=connecter" method="post">
            <div class="input-group">
                <label>Email</label>
                <input type="email" name="login" required>
            </div>

            <div class="input-group">
                <label>Mot de passe</label>
                <input type="password" name="mdp" required>
            </div>

            <button type="submit" class="btn-login">
                Se connecter
            </button>
        </form>

        <div class="info-box">
            <p>Les personnes autorisées à se connecter:</p>
            <ul>
                <li>Les etudiants</li>
                <li>Les enseignants</li>
                <li>Les responsables</li>
                <li>Le responsable-formation</li>
            </ul>
        </div>
    </div>
</div>