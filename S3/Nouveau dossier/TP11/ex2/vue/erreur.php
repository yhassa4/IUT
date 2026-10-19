 <?php 
 
    switch($titre){

        case "une voiture":
        echo "Oups une erreur s'est produite :( \n la voiture immarticulée $i n'existe pas dans la base!";
        break;

        case "un utilisateur":
        echo "Oups une erreur s'est produite :( \n le utilisateur avec le login $i n'existe pas dans la base!";
        break;

        default:
            echo "Oups une erreur s'est produite!!!!!";
            break;
    }

    ?>