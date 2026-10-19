package Base;

import java.util.ArrayList;

public class Question {
    private int numQuestion;
    private String textQuestion;
    private ArrayList<ReponsePossibilite> choix = new ArrayList<>();
    public static ArrayList<Question> ListQuestion = new ArrayList<>();
    
    public Question(int num, String text) {
        this.numQuestion = num;
        this.textQuestion = text;
        ListQuestion.add(this);
    }
    public void addReponsePossibilite(ReponsePossibilite r) { this.choix.add(r); }
    public String getTextQuestion() { return textQuestion; }
}