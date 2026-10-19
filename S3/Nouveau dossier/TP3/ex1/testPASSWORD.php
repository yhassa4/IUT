<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="utf-8">
       
    </head>
    <body>
        <h1>test password</h1>
        <?php
        
       

        if(isset($_GET["password"])){
            
            echo "<pre>";
            echo "l'entrée password existe :";
            echo '$_GET["password"] = '.$_GET["password"];
            echo "</pre>";
        }
        else{
             echo "l'entrée password n'existe pas";
        }

        
        ?> 
    </body>
</html>