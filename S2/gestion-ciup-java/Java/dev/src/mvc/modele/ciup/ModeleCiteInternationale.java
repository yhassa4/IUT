package mvc.modele.ciup;

import java.io.Serializable;
import java.util.*;

import mvc.modele.batiment.ModeleMaison;
import mvc.modele.gestion.ModeleGestionAttente;
import mvc.modele.utilisateur.ModeleEtudiant;

public class ModeleCiteInternationale  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nom;

	private ModeleGestionAttente saGestion;
	protected ArrayList<ModeleMaison> listeMaison;


	
	
    public ModeleCiteInternationale(String nom, ModeleGestionAttente saGestion) {
    	this.nom = nom;
      
        this.saGestion = saGestion;
        this.listeMaison = new ArrayList<>();
        
        // Initialiser les maisons
        initMaisons();
    }
    
    /**
     * Initialise la liste des maisons avec des données de test
     */
    private void initMaisons() {
        try {
            ajouterMaison(new ModeleMaison("Maison Internationale", 100, "Internationale", "M. International", "Paris", this));
            ajouterMaison(new ModeleMaison("Maison de France", 80, "Française", "Mme Dupont", "Paris", this));
            ajouterMaison(new ModeleMaison("Maison du Japon", 60, "Japonaise", "M. Tanaka", "Paris", this));
            ajouterMaison(new ModeleMaison("Maison du Brésil", 75, "Brésilienne", "M. Silva", "Paris", this));
            ajouterMaison(new ModeleMaison("Maison de l'Italie", 90, "Italienne", "Mme Romano", "Paris", this));
            
            // Ajout d'étudiants dans certaines maisons pour tester l'affichage de l'occupation
            ModeleMaison maisonFrance = listeMaison.get(1);
            for (int i = 0; i < 70; i++) { // Remplir à 70/80
                maisonFrance.ajouterEtudiant(new ModeleEtudiant("Nom" + i, "Prenom" + i, "Française", null));
            }
            
            ModeleMaison maisonJapon = listeMaison.get(2);
            for (int i = 0; i < 60; i++) { // Remplir complètement
                maisonJapon.ajouterEtudiant(new ModeleEtudiant("Nom" + i, "Prenom" + i, "Japonaise", null));
            }
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation des maisons : " + e.getMessage());
        }
    }
    
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
    
    
	/**
	 * 
	 * @param maison
	 */
    public void ajouterMaison(ModeleMaison maison) throws Exception 
    {
        if (listeMaison.contains(maison))
        {
            throw new Exception("La maison existe deja");
        }
        else
        {        
            listeMaison.add(maison);
        }
    }

    /**
     * 
     * @param maison
     * @throws Exception
     */
    public void supprimerMaison(ModeleMaison maison) throws Exception 
    {
        if (listeMaison.contains(maison))
        {
            listeMaison.remove(maison);
        }
        else
        {                
            throw new Exception("La maison n'existe pas");
        }
    }

	public void afficherMaisons() {
        for (ModeleMaison m : listeMaison) {
            System.out.println("   - " + m.getNom());
        }
	}

    /**
     * Retourne une copie de la liste des maisons
     * @return Liste des maisons de la cité
     */
    public ArrayList<ModeleMaison> getListeMaisons() {
        return new ArrayList<>(listeMaison);
    }
    
	public void afficherGestionAttente() throws Exception {
	    saGestion.afficherListeAttente();
	}
	

	/**
	 * Trouve une maison disponible pour un étudiant selon les critères suivants:
	 * 1. Maison souhaitée si spécifiée et disponible
	 * 2. Maison correspondant à sa nationalité si disponible
	 * 3. Maison la moins occupée parmi celles disponibles
	 * 
	 * @param etudiant L'étudiant à placer
	 * @return La maison trouvée ou null si aucune place disponible
	 * @throws Exception Si la maison souhaitée n'existe pas dans la cité
	 */

	public ModeleMaison trouverMaisonPourEtudiant(ModeleEtudiant etudiant) throws Exception {
		// 1. Vérifier la maison souhaitée
		String souhait = etudiant.getSouhaitMaison();
		if (souhait != null && !souhait.isEmpty()) {
			boolean maisonExists = false;
			for (ModeleMaison maison : listeMaison) {
				if (maison.getNom().trim().equalsIgnoreCase(souhait.trim())) {
					maisonExists = true;
					if (maison.verificationPlace()) {
						return maison;
					}
					break;
				}
			}
			if (!maisonExists) {
				throw new Exception("La maison '" + souhait + "' n'existe pas dans la cité");
			}
		}

		// 2. Vérifier la maison de sa nationalité

		
		String nationalite = etudiant.getNationalite();

		for (ModeleMaison maison : listeMaison) {
			if (maison.getNationnalite().equals(nationalite) && maison.verificationPlace()) {
				return maison;
			}
		}

		// 3. Chercher la maison la moins occupée parmi celles disponibles
		ModeleMaison maisonMoinsRemplie = null;
		int minOccupation = Integer.MAX_VALUE;
		
		for (ModeleMaison maison : listeMaison) {
			if (maison.verificationPlace()) {
				int occupation = maison.getEtudiants().size();
				if (occupation < minOccupation) {
					minOccupation = occupation;
					maisonMoinsRemplie = maison;
				}
			}
		}
		
		return maisonMoinsRemplie;
	}

	/**
	 * Tente d'attribuer une maison à l'étudiant selon les critères établis.
	 * En cas d'échec, l'étudiant sera placé en liste d'attente.
	 * 
	 * @param etudiant L'étudiant à placer
	 * @return true si l'étudiant a été placé, false s'il est en liste d'attente
	 */
	public boolean attribuerMaisonEtudiant(ModeleEtudiant etudiant) {
		try {
			ModeleMaison maison = trouverMaisonPourEtudiant(etudiant);
			if (maison != null) {
				try {
					maison.ajouterEtudiant(etudiant);
					return true;
				} catch (Exception e) {
					System.out.println("Erreur lors de l'ajout de l'étudiant: " + e.getMessage());
					return false;
				}
			}
		} catch (Exception e) {
			if (e.getMessage().contains("n'existe pas dans la cité")) {
				System.out.println(e.getMessage());
			} else {
				System.err.println(e.getMessage());
			}
			throw new RuntimeException(e.getMessage());
		}
		return false;
	}



	public void GererAttente() {
		ModeleEtudiant etudiant = saGestion.attribuerMaison();
		if (etudiant != null) {
			if (attribuerMaisonEtudiant(etudiant)) {
				System.out.println("Étudiant " + etudiant.getNom() + " " + etudiant.getPrenom() + " placé avec succès.");
			} else {
				try {
					saGestion.ajouterEnAttente(etudiant);
					System.out.println("Impossible de placer l'étudiant pour le moment, remis en liste d'attente.");
				} catch (Exception e) {
					System.err.println("Erreur lors de la remise en liste d'attente: " + e.getMessage());
				}
			}
		} else {
			System.out.println("Aucun étudiant en attente.");
		}

	}

}