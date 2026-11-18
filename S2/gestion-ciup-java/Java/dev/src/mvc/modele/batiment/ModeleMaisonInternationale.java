package mvc.modele.batiment;

import java.io.Serializable;
import java.util.*;

import mvc.modele.ciup.ModeleCiteInternationale;
import mvc.modele.service.*;

public class ModeleMaisonInternationale extends ModeleMaison implements Serializable{
	
	private ArrayList<ModeleRestauration > sesServices;


	public ModeleMaisonInternationale(String nom, int nbChambre, String directeur, String localisation, ModeleCiteInternationale saCite) {
		super(nom, nbChambre, "Internationale", directeur, localisation, saCite);
		this.sesServices = new ArrayList<>();
	}

	/**
	 * 
	 * @param service
	 */
	
	

	public void afficherServices() throws  IllegalArgumentException {
			if (this.sesServices.isEmpty()) {
				throw new IllegalArgumentException("La liste des etudiants est vide");
			}else {
				for (ModeleRestauration  s : sesServices) {
					System.out.println("   - " + s.getNom());
				}
			}	
	}
	public void ajouterService(ModeleRestauration  service)throws IllegalArgumentException {
		if (sesServices.contains(service))
		{
			throw new IllegalArgumentException("Le service existe deja");
		}
		else
		{
			sesServices.add(service);
		}
	}

	
	public void supprimerService(ModeleService service)throws IllegalArgumentException {
		if (sesServices.contains(service))
		{
			sesServices.remove(service);
		}
		else
		{
			throw new IllegalArgumentException("Le service n'existe pas");
		}
	}
	
	public ArrayList<ModeleRestauration > getServices() {
	    return new ArrayList<>(sesServices); // pour éviter modification directe
	}

}


