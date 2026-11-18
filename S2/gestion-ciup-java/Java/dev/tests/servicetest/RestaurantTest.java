package servicetest;

import service.Restaurant;
import service.Menu;
import service.TypeRepas;
import gestion.GestionAttente;
import ciup.CiteInternationale;

public class RestaurantTest {
    public static void main(String[] args) {
        testAjouterPlat();
        testGetCiteException();
        testGetCiteSuccess();
        System.out.println("Tous les tests de Restaurant sont passés.");
    }

    private static void testAjouterPlat() {
        Restaurant resto = new Restaurant("Resto du coin", "2025-04-10");
        Menu plat = new Menu("Pizza", "Délicieuse", TypeRepas.CLASSIQUE);
        resto.ajouterPlat(plat);
        System.out.println("testAjouterPlat passé.");
    }

    private static void testGetCiteException() {
        try {
            Restaurant resto = new Restaurant("Resto vide", "2025-04-10");
            resto.getCite();
            assert false : "Aucune exception levée.";
        } catch (Exception e) {
            System.out.println("testGetCiteException passé.");
        }
    }

    private static void testGetCiteSuccess() {
        try {
            GestionAttente test = new GestionAttente();
            Restaurant resto = new Restaurant("Resto lié", "2025-04-10");
            CiteInternationale cite = new CiteInternationale(test);
            resto.setCite(cite);
            assert resto.getCite() == cite;
            System.out.println("testGetCiteSuccess passé.");
        } catch (Exception e) {
            assert false : "Exception non attendue.";
        }
    }
}

