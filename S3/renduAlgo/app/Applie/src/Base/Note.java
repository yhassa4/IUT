package Base;

import java.util.ArrayList;

public class Note {
//////// Attributs 
    int NumNote;
    float valeur;
    float valeurMax;
    Etudiant etu;
    Semestre semestre;
    Matiere matiere;
    static ArrayList<Note> notes = new ArrayList<Note>();

//////// Constructeur 
    public Note(int NumNote ,float valeur,float valeurMax ,Etudiant etu ,Semestre semestre,Matiere matiere) {
        this.NumNote = NumNote;
        this.valeur = valeur;
        this.valeurMax = valeurMax;
        this.etu = etu;
        this.semestre = semestre;
        this.matiere = matiere;
        notes.add(this); 
    }

//////// Fonction : récupérer les notes d’un étudiant 
    public static ArrayList<Note> GetNotes(Etudiant etu){    
        ArrayList<Note> notesEtu = new ArrayList<Note>(); 
        int cpt = 0;
        for(int i = 0 ; i < notes.size(); i++) {
        	if(notes.get(i).etu != null) {
	            if(notes.get(i).etu.equals(etu)){
	                cpt++;
	                notesEtu.add(notes.get(i));
	            }
        	}
        }
        return notesEtu;
    }

//////// Getter / Setter Etudiant 
    public Etudiant getEtu() {
        return etu;
    }
    public void setEtu(Etudiant etu) {
        this.etu = etu;
    }
    public Matiere getMatiere(){
    	return this.matiere;

    }
    public Semestre getsemestre(){
    	return this.semestre;

    }
    public float getvaleur(){
    	return this.valeur;

    }
    
    
    public float getValeur() {
        return this.valeur; 
    }

    // Ce que JavaFX va appeler pour la colonne Matière
    public String getMatiereNom() {
        return (this.matiere != null) ? this.matiere.getNomMatiere() : "";
    }

    // Ce que JavaFX va appeler pour la colonne Semestre
    public String getSemestreNom() {
        return (this.semestre != null) ? this.semestre.getNomSemestre() : "";
    }
    public float getValeurMax(){
    	return this.valeurMax;

    }

	public void setValeur(float nouvelleVal) {
		if(nouvelleVal >= 0 && nouvelleVal <= this.valeurMax)
		this.valeur = nouvelleVal ;
	}
    @Override
    public String toString() {
        return this.getSemestreNom();
    }
	
	
}
