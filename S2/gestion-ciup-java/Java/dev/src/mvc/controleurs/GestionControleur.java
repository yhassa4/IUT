package mvc.controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mvc.modele.gestion.ModeleGestionAttente;
import mvc.vues.gestion.GestionVue;

public class GestionControleur implements ActionListener
{
	static public final String INSCRIRE_ACTION = "INSCRIRE";

	ModeleGestionAttente _modele;
	GestionVue _vue;
	
	
	public GestionControleur(ModeleGestionAttente modele, GestionVue vue)
	{
		_modele = modele;
		_vue = vue;
		
		_vue.setEcouteur(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		_vue.updateEtudiant();
		
	}
}
