<link rel="stylesheet" href="css/styleGroupes.css">

<div class="content-container">
    <h2 class="group-section-title">📁 Importation des Notes via CSV</h2>
    
    <div class="creation-panel">
        <p><strong>Instruction :</strong> Sélectionnez votre fichier <code>.csv</code> respectant le format : 
        <br><code>Numetu ; NumSemestre ; Matière 1 ; Matière 2 ; ...</code></p>
        
        <form action="routeur.php?controleur=controleurEtudiant&action=importerNotes" method="post" enctype="multipart/form-data" class="import-form">
            <div class="file-input-wrapper">
                <input type="file" name="fichier_csv" accept=".csv" required>
            </div>
            <button type="submit" class="btn-cree" style="margin-top: 20px;">
                Lancer l'importation massive
            </button>
        </form>
    </div>
</div>