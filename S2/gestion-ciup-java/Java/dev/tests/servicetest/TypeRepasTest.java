package servicetest;

import service.TypeRepas;

public class TypeRepasTest {
    public static void main(String[] args) {
        testEnumValues();
        System.out.println("Tous les tests de TypeRepas sont passés.");
    }

    private static void testEnumValues() {
        TypeRepas repas = TypeRepas.VEGAN;
        assert repas == TypeRepas.VEGAN;
        repas = TypeRepas.CLASSIQUE;
        assert repas.toString().equals("CLASSIQUE");
        System.out.println("testEnumValues passé.");
    }
}

