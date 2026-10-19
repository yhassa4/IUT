package Base;
import java.util.ArrayList;
import java.util.Date;

public class Sondage {
    private int numSondage;
    private String nomSondage;
    private boolean typeSondage; // 0 ou 1
    private Promotion promo;
    private Date dateDebut;
    private Date dateFin;
    private ArrayList<Question> questions = new ArrayList<>();
    public static ArrayList<Sondage> Sondages = new ArrayList<>();
    

    public Sondage(int num, String nom, boolean type, Promotion p, Date debut, Date fin) {
        this.numSondage = num;
        this.nomSondage = nom;
        this.typeSondage = type;
        this.promo = p;
        this.dateDebut = debut;
        this.dateFin = fin;
        Sondages.add(this);
    }

    public void addQuestion(Question q) { this.questions.add(q); }
    public ArrayList<Question> getQuestions() { return questions; }
    public String getNomSondage() {return nomSondage;}
    public Promotion getPromo() {return this.promo;}
    public Date getdateDebut() {return dateDebut;}
    public Date getdateFin() {return dateFin;}
    public boolean estEnCours() {
        Date aujourdhui = new Date();
        return aujourdhui.after(dateDebut) && aujourdhui.before(dateFin);
    }
    public boolean estTerminer() {
        Date aujourdhui = new Date();
        return aujourdhui.before(dateDebut);
    }
    
    
}



