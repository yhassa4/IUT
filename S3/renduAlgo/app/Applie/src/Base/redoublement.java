package Base;
import java.util.ArrayList;

public class redoublement {
//////// Attributs 
    int Numredoublement;
    Promotion promo;
    Etudiant etu;
    public static ArrayList<redoublement> Listredoublement = new ArrayList<>();

//////// Constructeur avec paramètres 
    public redoublement(int num, Promotion promo, Etudiant etu) {
        Numredoublement = num;
        this.promo = promo;
        this.etu = etu;
        Listredoublement.add(this);
    }

//////// Getter / Setter 
public Etudiant getEtudiant() {
	return this.etu;
}

//////// Fonction 

}
