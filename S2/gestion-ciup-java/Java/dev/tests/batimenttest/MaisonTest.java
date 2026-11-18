package batimenttest;

import batiment.Maison;
import gestion.Etudiant;

public class MaisonTest {

    private Maison maison;
    

    public static void main(String[] args) {
        MaisonTest test = new MaisonTest();
        test.afficherEtudiants_NormalInput_ExpectedReturnValue();
        test.afficherEtudiants_IllegalInput_ThrowsIllegalArgumentException();
        
        
        test.ajouterEtudiant_NormalInput_ExpectedReturnValue();
        test.ajouterEtudiant_IllegalInput_ThrowsIllegalArgumentException();
        
        test.supprimerEtudiant_NormalInput_ExpectedReturnValue();
        test.supprimerEtudiant_IllegalInput_ThrowsIllegalArgumentException();
        
        test.verificationPlace_NormalInput_ExpectedReturnValue();
        System.out.println("All tests passed.");
    }

    private void setup(int someValue) {
        maison = new Maison("MaisonTest", someValue, "Française", "Mme Dupont", "Paris");
    }

    
    private void afficherEtudiants_NormalInput_ExpectedReturnValue() {
        setup(1);
        Etudiant e = new Etudiant("Doe", "John", "Française");
        
        try {
            maison.ajouterEtudiant(e);
        } catch (Exception ex) {
            assert false : "Exception inattendue" + ex;
        }
        maison.afficherEtudiants(); // On vérifie visuellement ici
        System.out.println("afficherEtudiants_NormalInput_ExpectedReturnValue passed.");
    }
    
    private void afficherEtudiants_IllegalInput_ThrowsIllegalArgumentException() {
        setup(2);
        try {
            maison.afficherEtudiants();
        } catch (IllegalArgumentException e) {
            System.out.println("afficherEtudiants_IllegalInput_ThrowsIllegalArgumentException passed.");
        }catch (Exception error) {
            assert false : " Exception inattendu :  " + error.getMessage();
        }
    }
    
    
    
    
    private void ajouterEtudiant_NormalInput_ExpectedReturnValue() {
    	setup(3);
        try {
            Maison maison = new Maison("Maison A", 2, "Française", "Dupont", "Paris");
            Etudiant etudiant = new Etudiant("Durand", "Sophie", "12345"); // À adapter selon ta classe Etudiant

            maison.ajouterEtudiant(etudiant);

            assert maison.getEtudiants().contains(etudiant) : "L'étudiant n'a pas été ajouté correctement.";
            System.out.println("ajouterEtudiant_NormalInput_ExpectedReturnValue passed.");
        } catch (Exception e) {
            assert false : "Aucune exception attendue pour un ajout correct." + e;
        }
    }


    private void ajouterEtudiant_IllegalInput_ThrowsIllegalArgumentException() {
        setup(4);
        Etudiant e = new Etudiant("Durand", "Luc", "Française");

        try {
            maison.ajouterEtudiant(e);
            maison.ajouterEtudiant(e); // Doublon
            assert false : "Exception attendue mais non levée";
        } catch (IllegalArgumentException ex) {
            System.out.println("ajouterEtudiant_IllegalInput_ThrowsIllegalArgumentException passed.");
        }catch (Exception error) {
            assert false : " Exception inattendu :  " + error.getMessage();
        }
    }
    

    private void supprimerEtudiant_NormalInput_ExpectedReturnValue() {
    	setup(5);
        try {
            Maison maison = new Maison("Maison B", 2, "Italienne", "Rossi", "Rome");
            Etudiant etudiant = new Etudiant("Ferrari", "Marco", "67890");

            maison.ajouterEtudiant(etudiant);
            maison.supprimerEtudiant(etudiant);

            assert !maison.getEtudiants().contains(etudiant) : "L'étudiant n'a pas été supprimé correctement.";
            System.out.println("supprimerEtudiant_NormalInput_ExpectedReturnValue passed.");
        } catch (Exception e) {
            assert false : "Aucune exception attendue pour une suppression correcte." + e;
        }
    }


    private void supprimerEtudiant_IllegalInput_ThrowsIllegalArgumentException() {
        setup(6);  // Initialiser avec 6 chambres
        Etudiant e = new Etudiant("Lopez", "Carlos", "Française"); // L'étudiant à supprimer, mais non présent

        try {
            maison.supprimerEtudiant(e); // On tente de supprimer l'étudiant, mais il n'est pas dans la maison
            assert false : "Exception attendue mais non levée";  // Si l'exception n'est pas levée, le test échoue
        } catch (IllegalArgumentException ex) {  // On capture spécifiquement IllegalArgumentException
            System.out.println("supprimerEtudiant_IllegalInput_ThrowsIllegalArgumentException passed.");
        } catch (Exception error) {
            assert false : " Exception inattendu :  " + error.getMessage();
        }
    }


    private void verificationPlace_NormalInput_ExpectedReturnValue() {
        setup(7);
        assert maison.verificationPlace();
        System.out.println("verificationPlace_NormalInput_ExpectedReturnValue passed.");
    }
}
