package Base;

import java.util.ArrayList;

public class Covoiture {
//////// Attributs 
    private Etudiant etu1;
    private Etudiant etu2;
    static private ArrayList<Covoiture> ListCovoit = new ArrayList<Covoiture>();
    
//////// Constructeur par défaut 
    public Covoiture() {}

//////// Constructeur avec paramètres 
    public Covoiture(Etudiant etu1, Etudiant etu2) {// pas de verif  = trigger de verif dans la BD
    	this.etu1 = etu1 ;
    	this.etu2 = etu2 ;
    	ListCovoit.add(this);
    }

//////// Getter / Setter 
    public Etudiant getetu1() { 
        return etu1; 
    }

    public void setetu1(Etudiant etu1) { 
        this.etu1 = etu1; 
    }

    public Etudiant getetu2() { 
        return etu2; 
    }


    public void setetu2(Etudiant etu2) { 
        this.etu2 = etu2; 
    }
    static public ArrayList<Covoiture> getListCovoit(){
    	return ListCovoit ;
    }
    
//////// Fonction statique 
    

//////// Fonction 
   
}
