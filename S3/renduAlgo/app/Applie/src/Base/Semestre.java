package Base;

import java.util.ArrayList;

public class Semestre {
//////// Attributs 
    int NumSemestre;
    String NomSemestre;
    public static ArrayList<Semestre> ListSemestre = new ArrayList<Semestre>();

//////// Constructeur avec paramètres 
    public Semestre(int NumSemestre, String NomSemestre) {
        this.NumSemestre = NumSemestre;
        this.NomSemestre = NomSemestre;
        ListSemestre.add(this);
    }

    @Override
    public String toString() {
        return this.getNomSemestre();
    }
//////// Getter / Setter
///
    public String getNomSemestre() {
    	return NomSemestre;
    }
//////// Fonction 
}
