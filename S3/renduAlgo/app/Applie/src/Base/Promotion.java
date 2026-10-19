package Base;

import java.util.ArrayList;

public class Promotion {
//////// Attributs 
    private int numPromo;
    private Semestre numSemestre;
    private int annee ;
    public static ArrayList<Promotion> ListPromo = new  ArrayList<Promotion>();

//////// Constructeur avec paramètres 
    public Promotion(int numPromo, Semestre numSemestre, int annee) {
        this.numPromo = numPromo;
        this.numSemestre = numSemestre;
        this.annee = annee;
        ListPromo.add(this);
    }
    


//////// Getter / Setter 
    @Override
    public String toString() {
        return this.getNomPromo();
    }


public String getNomPromo() {
	return "promotion " + annee +" "+numSemestre.getNomSemestre();
}
public Semestre getnumSemestre() {
	return numSemestre;
}

//////// Fonction 

}
