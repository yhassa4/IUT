package mvc.controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mvc.vues.VueCiup;


/**
 * Contrôleur principal de l'application CIUP.
 * Gère la navigation entre les différentes vues (Maison, Étudiants, Services).
 */
public class ControleurCiup implements ActionListener {

	// -----------------------------
	// Constantes ACTION COMMAND
	// -----------------------------
	static public final String BOUTON_MAISON = "MAISON";
	static public final String BOUTON_ETUDIANT = "ETUDIANT";
	static public final String BOUTON_SERVICE = "SERVICE";

	// -----------------------------
	// Attributs
	// -----------------------------
	private VueCiup _vue;
	private ControleurEtudiant controleurEtudiant;

	// -----------------------------
	// Constructeur
	// -----------------------------
	public ControleurCiup(VueCiup vue) {
		this._vue = vue;

		// Initialiser le contrôleur étudiant
		//controleurEtudiant = new ControleurEtudiant(vue.getModeleGestionAttente(), vue.getVueEtudiant());

		// Ajouter les écouteurs aux boutons
		_vue.getButtonE().addActionListener(this);
		_vue.getButtonM().addActionListener(this);
		_vue.getButtonS().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		switch (command) {
			case BOUTON_MAISON:
				_vue.afficherVue("MAISONS");
				break;
				
		    case BOUTON_ETUDIANT:
		        _vue.afficherVue("ETUDIANTS");
		        break;
		    case BOUTON_SERVICE:
		        _vue.afficherVue("SERVICES");
		        break;
		}

		// Toujours redessiner l'interface
		_vue.redessiner();
	}
}
