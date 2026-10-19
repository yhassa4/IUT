<?php
    require_once("modele/modele.php");


    class Controleur {

        public static function lireObjets(){
            $objet = static::$objet;
            $objetMaj = ucfirst($objet);
            $cle = static::$cle;

            
            $lesObjet = $objet::getAll(); 

            
            $aff = array(); 
            foreach ($lesObjet as $ob) {
                $id = $ob->get($cle);
                $aff[] = "<tr>";
                $aff[] = "<td>$id</td>";
                $aff[] = "<td>Détails de l'objet $objet</td>";
                $aff[] = "<td><a href='#' style='color:blue'>Détails</a></td>";
                $aff[] = "</tr>";
            }

            
            include("vue/debut.php");      
            include("vue/lesObjets.php");  
            
           
            if(file_exists("vue/fin.html")) include("vue/fin.html");
        }

    



            public static function lireObjet(){
                $objet = static::$objet;
                $objetMaj=ucfirst($objet);
                $cle = static::$cle;
                $titre ="1 $objet";
                $i = $_GET["$cle"];
                $v = $objet::getObjectByid(i: $i);
                include("vue/debut.php");
                include("vue/menu.html");
                if (!$v)
                    include("vue/erreur.php");
                else 
                    include("vue/unObjet.php");
                include("vue/fin.html");
            }
    }