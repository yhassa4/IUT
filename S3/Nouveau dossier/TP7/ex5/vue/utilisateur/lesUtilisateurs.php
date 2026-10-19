
     <?php 
 
    foreach ($lesUtilisateurs as $utilisateur) {
            echo "<p> utilisateur " ;
             echo $utilisateur->get('login'); 
           echo '<a class=bouton href="routeur.php?controleur=controleurUtilisateur&action=lireUtilisateur&login='.$utilisateur->get('login').'">détails</a>';
        echo "</p>";
        
        }

    ?>