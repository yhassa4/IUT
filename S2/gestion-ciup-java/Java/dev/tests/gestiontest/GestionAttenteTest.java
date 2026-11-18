package gestiontest;

import gestion.*;
import batiment.*;

public class GestionAttenteTest {
    public static void main(String[] args) throws Exception {
        testAjouterEtudiant();
        testAjouterEtudiantDejaDansListe();
        testSupprimerEtudiant();
        testSupprimerInexistant();
        testAttribuerMaison();
        testAfficherListeVide();
        System.out.println("Tous les tests GestionAttente sont passés.");
    }

    private static void testAjouterEtudiant() throws Exception {
        GestionAttente ga = new GestionAttente();
        Etudiant etudiant = new Etudiant("Test", "Etudiant", "Test");
        ga.ajouterEnAttente(etudiant);
        System.out.println("testAjouterEtudiant passé.");
    }

    private static void testAjouterEtudiantDejaDansListe() throws Exception {
        GestionAttente ga = new GestionAttente();
        Etudiant etudiant = new Etudiant("Test", "Etudiant", "Test");
        ga.ajouterEnAttente(etudiant);
        try {
            ga.ajouterEnAttente(etudiant);
            assert false : "Exception attendue.";
        } catch (Exception ex) {
            System.out.println("testAjouterEtudiantDejaDansListe passé.");
        }
    }

    private static void testSupprimerEtudiant() throws Exception {
        GestionAttente ga = new GestionAttente();
        Etudiant etudiant = new Etudiant("Test", "Etudiant", "Test");
        ga.ajouterEnAttente(etudiant);
        ga.supprimerDeListe(etudiant);
        System.out.println("testSupprimerEtudiant passé.");
    }

    private static void testSupprimerInexistant() {
        GestionAttente ga = new GestionAttente();
        Etudiant etudiant = new Etudiant("Test", "Etudiant", "Test");
        try {
            ga.supprimerDeListe(etudiant);
            assert false : "Exception attendue.";
        } catch (Exception ex) {
            System.out.println("testSupprimerInexistant passé.");
        }
    }

    private static void testAttribuerMaison() throws Exception {
        GestionAttente ga = new GestionAttente();
        Etudiant etudiant = new Etudiant("Test", "Etudiant", "Test");
        ga.ajouterEnAttente(etudiant);
        Etudiant attribue = ga.attribuerMaison();
        assert attribue == etudiant;
        System.out.println("testAttribuerMaison passé.");
    }

    private static void testAfficherListeVide() {
        GestionAttente ga = new GestionAttente();
        try {
            ga.afficherListeAttente();
            assert false : "Exception attendue.";
        } catch (Exception e) {
            System.out.println("testAfficherListeVide passé.");
        }
    }
}