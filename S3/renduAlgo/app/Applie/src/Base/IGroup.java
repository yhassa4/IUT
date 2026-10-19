package Base;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public interface IGroup {

    boolean ajouterEtudiant();
    float ecarTypeMatiere(Matiere m);
    float ecarTypeGlobal();
    ArrayList<GenreParite> getPariteGenre();
    void afficherPariteGenre();
    int GetnbrEtudiant();
    ArrayList<Etudiant> GetEtudiant();
    void display();
    float moyenneGeneraleGroupe();
    float moyenneMatiereGroupe(Matiere M);
    ArrayList<Matiere> getMatieresAvecNotes();
    void  displayMoyenneMatiereGroup();
	int getTailleMax();
	String getNomGroup();
	void setDatePublication(LocalDate nouvelleDate);
	LocalDate getDatePublication();
	boolean isActif();
    }
