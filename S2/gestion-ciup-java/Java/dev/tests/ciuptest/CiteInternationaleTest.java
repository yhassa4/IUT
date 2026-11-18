package ciuptest;

import ciup.*;
import gestion.*;
import batiment.*;

public class CiteInternationaleTest {
    public static void main(String[] args) throws Exception {
        testAjoutMaison();
        testAjoutMaisonExistante();
        testSuppressionMaison();
        testSuppressionMaisonInexistante();
        System.out.println("Tous les tests CiteInternationale sont passés.");
    }

    private static CiteInternationale createCite() {
        GestionAttente gestion = new GestionAttente();
        return new CiteInternationale(gestion);
    }

    private static void testAjoutMaison() throws Exception {
        CiteInternationale cite = createCite();
        Maison maison = new Maison("Maison Test", 50, "Française", "M. Test", "0,0");
        cite.ajouterMaison(maison);
        System.out.println("testAjoutMaison passé.");
    }

    private static void testAjoutMaisonExistante() throws Exception {
        CiteInternationale cite = createCite();
        Maison maison = new Maison("Maison Test", 50, "Française", "M. Test", "0,0");
        cite.ajouterMaison(maison);
        try {
            cite.ajouterMaison(maison);
            assert false : "Exception attendue.";
        } catch (Exception e) {
            System.out.println("testAjoutMaisonExistante passé.");
        }
    }

    private static void testSuppressionMaison() throws Exception {
        CiteInternationale cite = createCite();
        Maison maison = new Maison("Maison Test", 50, "Française", "M. Test", "0,0");
        cite.ajouterMaison(maison);
        cite.supprimerMaison(maison);
        System.out.println("testSuppressionMaison passé.");
    }

    private static void testSuppressionMaisonInexistante() {
        try {
            CiteInternationale cite = createCite();
            Maison maison = new Maison("Maison Test", 50, "Française", "M. Test", "0,0");
            cite.supprimerMaison(maison);
            assert false : "Exception attendue.";
        } catch (Exception e) {
            System.out.println("testSuppressionMaisonInexistante passé.");
        }
    }
}