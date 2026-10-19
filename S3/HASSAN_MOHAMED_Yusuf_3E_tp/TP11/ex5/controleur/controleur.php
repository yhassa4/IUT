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
                $valeurCle = $ob->get("$cle");
                $aff[] = "<p> $objet " . $valeurCle; 
                $aff[] = '<a class="bouton" href="routeur.php?controleur=controleur'.$objetMaj.'&action=lireObjet&'.$cle.'='.$valeurCle.'">détails</a>';
                $aff[] = '<a class="bouton" href="routeur.php?controleur=controleur'.$objetMaj.'&action=afficherFormulaireModification&'.$cle.'='.$valeurCle.'">modifier</a>';
                $aff[] = '<a class="bouton" href="routeur.php?controleur=controleur'.$objetMaj.'&action=supprimerObjet&'.$cle.'='.$valeurCle.'">supprimer</a>';
                $aff[] = "</p>";
            }
            include("vue/lesObjets.php");
            include("vue/fin.html");
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

            
            public static function supprimerObjet() {
                $objet = static::$objet;
                $cle = static::$cle;
                if (isset($_GET[$cle])) {
                    $id = $_GET[$cle];
                    $objet::deleteObjetById($id); 
                }
                static::lireObjets(); 
            }

            
    }