<?php 

    echo '<h1>Test password</h1>';

    if (isset($_GET['password'])) {
        echo "<p>l'entré e password existe :</p>";
        echo '$_GET["password"] = '.$_GET['password'];
    } else {
        echo "<p>l'entré e password n'existe pas</p>";
    }

    
?>