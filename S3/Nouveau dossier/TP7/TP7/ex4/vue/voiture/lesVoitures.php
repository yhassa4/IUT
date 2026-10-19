 <?php 
 
    foreach ($lesVoitures as $voiture) {
            echo "<p>voiture immatriculée" 
             echo $voiture['immat']; 
           echo "<a href="routeur.php?controleur=controleurVoiture&action=lireVoiture&immat=<?php echo $voiture['immat']; ?>">détails</a>"
        echo "</p>"
        
        }

    ?>