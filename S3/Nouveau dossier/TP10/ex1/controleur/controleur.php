<?php
    require_once("modele/modele.php");
    require_once("modele/utilisateur.php");
    require_once("modele/voiture.php");

    class Controleur {


        public static function lireObjets(){
            $objet = static::$objet;
            $objetMaj=ucfirst($objet);
            $cle = static::$cle;
            $titre = "Les $objet";
            $lesObjet = $objet::getAll();
            include("vue/debut.php");
            include("vue/menu.html");
            foreach ($lesObjet as $ob) {
                $aff[]= "<p>  $objet " ;
                $aff[]= $ob->get("$cle"); 

                $aff[]= '<a class=bouton href="routeur.php?controleur=controleur'.$objetMaj.'&action=lire'.$objetMaj.'&'.$cle."=".$ob->get("$cle").'">détails</a>';
                $aff[]= "</p>";
            }
            include("vue/lesObjets.php");
            include("vue/fin.html");
            }
    }