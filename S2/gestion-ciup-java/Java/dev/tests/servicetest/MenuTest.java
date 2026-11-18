package servicetest;

import service.Menu;
import service.TypeRepas;

public class MenuTest {
    public static void main(String[] args) {
        testGetters();
        testSetters();
        System.out.println("Tous les tests de Menu sont passés.");
    }

    private static void testGetters() {
        Menu plat = new Menu("Couscous", "Plat du jour", TypeRepas.VEGETARIEN);
        assert plat.getNom().equals("Couscous") : "Erreur mauvais  nom";
        assert plat.getInformation().equals("Plat du jour") : "Erreur mauvais information ";
        assert plat.getType() == TypeRepas.VEGETARIEN :  "Erreur mauvais type ";
        System.out.println("testGetters passé.");
    }

    private static void testSetters() {
        Menu plat = new Menu("Pizza", "Pizza Margherita", TypeRepas.CLASSIQUE);
        plat.setNom("Pasta");
        plat.setInformation("Pâtes carbonara");
        assert plat.getNom().equals("Pasta") : "Erreur mauvais nom ";
        assert plat.getInformation().equals("Pâtes carbonara") : "Erreur mauvais information ";
        System.out.println("testSetters passé.");
    }
}

