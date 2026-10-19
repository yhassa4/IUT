package Base;

public class Parcour {
//////// Attributs 
    private int numParcour;
    private String nomParcour;

//////// Constructeur par défaut 
    public Parcour() {}

//////// Constructeur avec paramètres 
    public Parcour(int numParcour, String nomParcour) {
        this.numParcour = numParcour;
        this.nomParcour = nomParcour;
    }

//////// Getter / Setter NumParcour 
    public int getNumParcour() { 
        return numParcour; 
    }
    public void setNumParcour(int numParcour) { 
        this.numParcour = numParcour; 
    }

//////// Getter / Setter NomParcour 
    public String getNomParcour() { 
        return nomParcour; 
    }
    public void setNomParcour(String nomParcour) { 
        this.nomParcour = nomParcour; 
    }

//////// Fonction 
    
}
