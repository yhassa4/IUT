<main>
    <form action="routeur.php" method="get">
        <input type="hidden" name="controleur" value="controleurVoiture">
        <input type="hidden" name="action" value="modifierVoiture">

        <div>
            <label for="immatriculation">immatriculation</label>
            <input type="text" name="immatriculation" value="<?php echo $i; ?>" readonly>
        </div>
        <div>
            <label for="marque">marque</label>
            <input type="text" name="marque" value="<?php echo $m; ?>">
        </div>
        <div>
            <label for="couleur">couleur</label>
            <input type="text" name="couleur" value="<?php echo $c; ?>">
        </div>
        <button type="submit">modifier</button>
    </form>
</main>