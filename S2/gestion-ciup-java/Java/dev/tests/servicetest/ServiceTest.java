package servicetest;


import service.Service;
import batiment.MaisonInternationale;


public class ServiceTest {
    public static void main(String[] args) {
        testGetMaisonInternationaleException();
        testGetMaisonInternationaleSuccess();
        System.out.println("Tous les tests de Service sont passés.");
    }

    private static void testGetMaisonInternationaleException() {
        try {
            Service service = new Service("Bibliothèque", null);
            service.getMaisonInternationale();
            assert false : "Aucune exception levée.";
        } catch (Exception e) {
            System.out.println("testGetMaisonInternationaleException passé.");
        }
    }

    private static void testGetMaisonInternationaleSuccess() {
        try {
            MaisonInternationale maison = new MaisonInternationale("Maison Afrique", 20, "M. Diallo", "48.9216, 2.3351");
            Service service = new Service("Accueil", maison);
            assert service.getMaisonInternationale() == maison;
            System.out.println("testGetMaisonInternationaleSuccess passé.");
        } catch (Exception e) {
            assert false : "Exception non attendue.";
        }
    }
}

