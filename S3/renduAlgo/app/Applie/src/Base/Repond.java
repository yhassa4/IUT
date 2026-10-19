package Base;

import java.util.ArrayList;

public class Repond {
    private Etudiant etudiant;
    private Sondage sondage;
    private Question question;
    private String reponseDonnee;
    public static ArrayList<Repond> ListRepond = new ArrayList<>();
    

    public Repond(Etudiant e, Sondage s, Question q, String rep) {
        this.etudiant = e;
        this.sondage = s;
        this.question = q;
        this.reponseDonnee = rep;
        ListRepond.add(this);
    }
    public Etudiant getEtudiant() { return etudiant; }
    public Sondage getSondage() { return sondage; }
    public Question getQuestion() {
    	return question;
    }
    
    public String getreponseDonnee() {return reponseDonnee;}
    
}