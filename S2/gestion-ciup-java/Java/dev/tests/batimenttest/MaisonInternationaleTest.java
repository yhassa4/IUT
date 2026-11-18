package batimenttest;

import service.Service;
import batiment.MaisonInternationale;

public class MaisonInternationaleTest {
	private MaisonInternationale maison;

	public static void main(String[] args) {
		MaisonInternationaleTest test = new MaisonInternationaleTest();

		test.afficherService_NormalInput_ExpectedReturnValue();
		test.afficherService_IllegalInput_ThrowsIllegalArgumentException();

		test.ajouterService_NormalInput_ExpectedReturnValue();
		test.ajouterService_IllegalInput_ThrowsIllegalArgumentException();

		test.supprimerService_NormalInput_ExpectedReturnValue();
		test.supprimerService_IllegalInput_ThrowsIllegalArgumentException();

		System.out.println("All tests passed.");
	}

	private void setup(int someValue) {
		maison = new MaisonInternationale("Maison Internationale", someValue, "Mme Dupont", "Paris");
	}

	private void afficherService_NormalInput_ExpectedReturnValue() {
		setup(1);
		try {
			Service service1 = new Service("Bibliothèque", maison);
			Service service2 = new Service("Cafétéria", maison);
			maison.ajouterService(service1);
			maison.ajouterService(service2);
			maison.afficherServices(); // Affichage console
			System.out.println("afficherService_NormalInput_ExpectedReturnValue passed.");
		} catch (Exception e) {
			assert false : "Aucune exception ne doit être levée ici." + e;
		}
	}

	private void afficherService_IllegalInput_ThrowsIllegalArgumentException() {
		setup(2);
		try {
			maison.afficherServices(); // Doit juste afficher rien sans planter
			
		} catch (IllegalArgumentException e) {
            System.out.println("afficherService_IllegalInput_ThrowsIllegalArgumentException passed.");
        }catch (Exception error) {
            assert false : " Exception inattendu :  " + error.getMessage();
        }
	}

	private void ajouterService_NormalInput_ExpectedReturnValue() {
		setup(3);
		try {
			Service service = new Service("Cinéma", maison);
			maison.ajouterService(service);
			
			assert maison.getServices().contains(service) : "Le service n'a pas été ajouté correctement";
			
			System.out.println("ajouterService_NormalInput_ExpectedReturnValue passed.");
		} catch (Exception e) {
			assert false : "Pas d'exception attendue pour ajout normal." + e;
		}
	}



	private void ajouterService_IllegalInput_ThrowsIllegalArgumentException() {
		setup(4);
		try {
			Service service = new Service("Cinéma", maison);
			maison.ajouterService(service);
			maison.ajouterService(service); // Doublon
			assert false : "Exception attendue mais non levée.";
		} catch (Exception e) {
			System.out.println("ajouterService_IllegalInput_ThrowsIllegalArgumentException passed.");
		}
	}

	private void supprimerService_NormalInput_ExpectedReturnValue() {
		setup(5);
		try {
			Service service = new Service("Salle de jeux", maison);
			maison.ajouterService(service);
			maison.supprimerService(service);

			assert !maison.getServices().contains(service) : "Le service n'a pas été supprimé correctement";

			System.out.println("supprimerService_NormalInput_ExpectedReturnValue passed.");
		} catch (Exception e) {
			assert false : "Pas d'exception attendue pour suppression valide." + e;
		}
	}


	private void supprimerService_IllegalInput_ThrowsIllegalArgumentException() {
		setup(6);
		try {
			Service service = new Service("Inexistant", maison);
			maison.supprimerService(service); // Service non ajouté
			assert false : "Exception attendue mais non levée.";
		}  catch (IllegalArgumentException ex) {
            System.out.println("supprimerService_IllegalInput_ThrowsIllegalArgumentException passed.");
        }catch (Exception error) {
            assert false : " Exception inattendu :  " + error.getMessage();
        }
	}
}