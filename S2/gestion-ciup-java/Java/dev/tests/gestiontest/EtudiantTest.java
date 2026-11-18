package gestiontest;

import gestion.Etudiant;
import batiment.Maison;

public class EtudiantTest {
    public static void main(String[] args) {
        testGetters();
        testMaisonNonAttribuee();
        testSetMaison();
        System.out.println("Tous les tests Etudiant sont passés.");
    }

    private static void testGetters() {
        Etudiant etudiant1 = new Etudiant("Maelan", "Cahier", "Italien");
        assert etudiant1.getNom().equals("Maelan");
        assert etudiant1.getPrenom().equals("Cahier");
        assert etudiant1.getNationnalite().equals("Italien");
        System.out.println("testGetters passé.");
    }

    private static void testMaisonNonAttribuee() {
        Etudiant etudiant2 = new Etudiant("Thomato", "Grillote", "Français");
        try {
            etudiant2.getMaison();
            assert false : "Exception attendue";
        } catch (Exception e) {
            System.out.println("testMaisonNonAttribuee passé.");
        }
    }

    private static void testSetMaison() {
        Etudiant etudiant3 = new Etudiant("Rémy", "Stinus", "Tunisien");
        Maison maison = new Maison("Maison test", 10, "Tunisienne", "M. Test", "48.123, 2.456");
        etudiant3.setMaison(maison);
        try {
            assert etudiant3.getMaison() == maison;
            System.out.println("testSetMaison passé.");
        } catch (Exception e) {
            assert false : "Maison devait être définie.";
        }
    }
}