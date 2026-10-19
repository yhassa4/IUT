package Base;

import java.util.ArrayList;

public class Matiere {
//////// Attributs 
    int numMatiere;
    String NomMatiere;
    static public ArrayList<Matiere >toutesMatieres = new ArrayList<>();

//////// Constructeur 
    public Matiere(int numMatiere , String NomMatiere){
        this.numMatiere = numMatiere;
        this.NomMatiere = NomMatiere;
        toutesMatieres.add(this);
    }

//////// Getter / Setter 
    public String getNomMatiere() {
    	return this.NomMatiere;
    }
    public int getNumMatiere() { return numMatiere; }

//////// Fonction 
}

