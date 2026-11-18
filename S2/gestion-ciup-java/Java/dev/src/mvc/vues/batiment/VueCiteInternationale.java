package mvc.vues.batiment;

import javax.swing.*;

import mvc.modele.ciup.ModeleCiteInternationale;
import mvc.modele.gestion.ModeleGestionAttente;

public class VueCiteInternationale {
	
	static public final String ACTION_AFFICHER_LISTE = "AFFICHER_LISTE";
	
	ModeleGestionAttente _modeleGestion;
	ModeleCiteInternationale _modeleCite;
	
	public VueCiteInternationale(ModeleCiteInternationale modeleCite, ModeleGestionAttente modeleGestion)
	{
		_modeleCite = modeleCite;
		_modeleGestion = modeleGestion;
	}
}
