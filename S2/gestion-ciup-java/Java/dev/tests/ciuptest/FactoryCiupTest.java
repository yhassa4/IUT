package ciuptest;

import factory.FactoryCIUP;
import gestion.*;
import batiment.*;
import ciup.*;

import java.util.List;

public class FactoryCIUPTest {
    public static void main(String[] args) throws Exception {
        testCreationEtudiants();
        testCreationMaisons();
        testCreationCIUP();
        System.out.println("Tous les tests FactoryCIUP sont passés.");
    }

    private static void testCreationEtudiants() throws Exception {
        List<Etudiant> etudiants = FactoryCIUP.creerEtudiants();
        assert etudiants != null : "La liste d'étudiants ne doit pas être null.";
        assert etudiants.size() > 0 : "La liste d'étudiants doit contenir au moins un étudiant.";
        for (Etudiant etudiant : etudiants) {
            assert etudiant.getNom() != null;
            assert etudiant.getPrenom() != null;
            assert etudiant.getNationnalite() != null;
        }
        System.out.println("testCreationEtudiants passé.");
    }

    private static void testCreationMaisons() throws Exception {
        List<Maison> maisons = FactoryCIUP.creerMaisons();
        assert maisons != null : "La liste de maisons ne doit pas être null.";
        assert maisons.size() > 0 : "La liste de maisons doit contenir au moins une maison.";
        for (Maison maison : maisons) {
            assert maison.getNom() != null;
            assert maison.getCapacite() > 0;
            assert maison.getNationalite() != null;
        }
        System.out.println("testCreationMaisons passé.");
    }

    private static void testCreationCIUP() throws Exception {
        CiteInternationale cite = FactoryCIUP.creerCIUP();
        assert cite != null : "La cité créée ne doit pas être null.";

        List<Maison> maisons = cite.getListeMaisons();
        assert maisons != null && !maisons.isEmpty() : "La cité doit contenir des maisons.";

        GestionAttente gestion = cite.getGestionAttente();
        assert gestion != null : "La gestion d’attente ne doit pas être null.";

        System.out.println("testCreationCIUP passé.");
    }
}