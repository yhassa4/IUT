package Base;

import java.util.ArrayList;

import tree.List;

public class Etudiant implements User{
//////// Attributs 
    private int numEtu;
    private String nomEtu;
    private String prenomEtu;
    private String genreEtu;
    private String mailProEtu;
    private String mailPersoEtu;
    private String addrEtu;
    private Bac bac;
    private Promotion promo;
    private Group group;
    private Parcour parcour; 
    private Boolean apprentissage ;
    private final String Role = "etudiant";
    private final String password  = "pass";
    static public List<Etudiant> ListEtu = new List<>();

//////// Constructeur 
    public Etudiant(int numEtu, String nomEtu, String prenomEtu, String genreEtu,
                    String mailProEtu, String mailPersoEtu, String addrEtu,
                    Bac Bac, Promotion Promo, Parcour Parcour) {
        this.numEtu = numEtu;
        this.nomEtu = nomEtu;
        this.prenomEtu = prenomEtu;
        this.genreEtu = genreEtu;
        this.mailProEtu = mailProEtu;
        this.mailPersoEtu = mailPersoEtu;
        this.addrEtu = addrEtu;
        this.bac = Bac;
        this.promo = Promo;
        this.group = new Group(this.promo, this.numEtu, this.numEtu);
        if(Parcour.getNumParcour() == 3) {
        	apprentissage = true ;
        }else {
        	apprentissage =false;
        }
        this.parcour = Parcour;
        ListEtu.add(-1, this);
    }


//////// Fonction : ajout etudiant  
    boolean setGroup(IGroup g) {
    	if (this.group == null) {
            this.group = new Group(this.promo, this.numEtu, this.numEtu);
        }
    	
    	
        if (g instanceof GroupTD) { // cas :on essaye d'ajouter un GroupTD 
            GroupTD groupTD = (GroupTD) g; // copie address memoire 

            if (this.group.issetGroupTD()) { //check si l'etudiant a deja un groupTD
               // System.out.println("Cet étudiant a déjà un groupe TD.");//debug
                return false;
            }
            if (!groupTD.ajouterEtudiant()) { // check si le groupTD a assez de place pour le prendre 
                //System.out.println("Le groupe " + groupTD.getNomGroupTD() + " est déjà au maximum.");//debug
                return false;
            }
            //System.out.println("L'étudiant a bien été ajouté au groupe TD.");//debug
            this.group.setGroupTD(groupTD); //ajoute le groupTD a l'etudiant
            return true;

        } else if (g instanceof GroupTP) {//cas :on essaye d'ajouter un GroupTP
            GroupTP groupTP = (GroupTP) g;

            if (this.group.issetGroupTP()) { //check si l'etudiant a deja un groupTP
                System.out.println("Cet étudiant a déjà un groupe TP.");//debug
                return false;
            }
            if (!groupTP.ajouterEtudiant()) {// check si le groupTP a assez de place pour le prendre
                System.out.println("Le groupe " + groupTP.getNomGroupTP() + " est déjà au maximum.");//debug
                return false;
            }
            //System.out.println("L'étudiant a bien été ajouté au groupe TP.");//debug
            this.group.setGroupTP(groupTP);//ajoute le groupTP a l'etudiant
            return true;
        }

       //System.out.println("Le groupe fourni n'est pas reconnu.");//debug
        return false;
    }

//////// Fonction : affichage des notes 
    void Displaynotes() {
        ArrayList<Note> notes = Note.GetNotes(this); // recupere les notes de cette etudiant (TOUTE LES NOTES)
        if(notes.size() == 0 ) { // cas de base 
            return ;
        }
        System.out.println("les notes de l'etudiant "+ this.getPrenomEtu() + " "+this.getNomEtu());
        for(int i =0 ;i < notes.size();i++) {
            System.out.println("note :" + notes.get(i).valeur+"/"+notes.get(i).valeurMax+ 
                               " | matiere :" + notes.get(i).matiere.NomMatiere);
        }
    }

//////// Fonction : calcul moyenne par matière 
    public Float GetMoyenneMatiere(Matiere matiere) {
        ArrayList<Note> notes = Note.GetNotes(this); // toutes les notes de l'étudiant
        int cpt = 0;
        float somme = 0;

        for (Note n : notes) {
            if (n.getMatiere().equals(matiere)) {
                cpt++;
                somme += (n.valeur * 20.0f) / n.valeurMax; // transformation note/20
            }
        }

        if (cpt == 0) {
            return null; // cas : pas de note pour cette matiere
        }

        return somme / cpt;
    }
  
//////// Fonction : affichage moyenne générale 
    void DisplayMoyennenotes() {
        ArrayList<Note> notes = Note.GetNotes(this);
        ArrayList<Matiere> tempM = new ArrayList();
        for(int i = 0 ;i < notes.size();i++) {
        	if(!tempM.contains(notes.get(i).getMatiere())) {
        		tempM.add(notes.get(i).getMatiere());
        	}
        }
        if(tempM.size() == 0 ) {System.out.println("cette etudiant n'a pas de note");} 
        System.out.println("Moyenne de l'etudiant : "+this.getNomEtu() + " " + this.getPrenomEtu());
        System.out.println("----------------------------------------------------------------");
        for(int i = 0 ;i < tempM.size();i++) {
        	System.out.println(tempM.get(i).getNomMatiere());
        	System.out.println("Moyenne : " + this.GetMoyenneMatiere(tempM.get(i)));
        	System.out.println("--------------------------------------");
        }
        System.out.println("----------------------------------------------------------------");
       
    }
////////Fonction : calcul moyenne générale
	public float GetMoyenneGenerale() {
		ArrayList<Note> notes = Note.GetNotes(this); // toutes les notes de l'étudiant
		if (notes.isEmpty()) {
		   return 0; 
		}
		
		float somme = 0;
		for (Note n : notes) {
			somme += (n.valeur * 20.0f) / n.valeurMax;
		}
			return somme / notes.size();
}

	
////////Fonction : Unset le groupTP et TD 
	public void unsetGroupTD() {
		    this.group.setGroupTD(null);
		}
		
	public void unsetGroupTP() {
		    this.group.setGroupTP(null);
		}
	
////////Fonction : retourne ses partenaire de covoiturage
	public ArrayList<Etudiant> getPartenairesCovoit(){
		ArrayList<Etudiant> PartenaireCovoit = new ArrayList<Etudiant>() ;
		ArrayList<Covoiture> Listcovoit = Covoiture.getListCovoit();
		
		for(int i = 0;i < Listcovoit.size();i++) { //cas : sa demande
			if(Listcovoit.get(i).getetu1() == this){
				if (!PartenaireCovoit.contains(Listcovoit.get(i).getetu2())) {
					PartenaireCovoit.add(Listcovoit.get(i).getetu2());	
				}
			}
			if(Listcovoit.get(i).getetu2() == this){//cas : demande de son camarade
				if (!PartenaireCovoit.contains(Listcovoit.get(i).getetu1())) {
					PartenaireCovoit.add(Listcovoit.get(i).getetu1());	
				}
			}	
		}
		return PartenaireCovoit;
	}
	
	

//////// Getter / Setter 
    public Group getGroup(){
        return this.group ;
    }
    public Boolean getApprentissage(){
    	return this.apprentissage;
    }
    
    public String getNomEtu() {
        return nomEtu;
    }
    public void setNomEtu(String nomEtu) {
        this.nomEtu = nomEtu;
    }
    public String getPrenomEtu() {
        return prenomEtu;
    }
    public void setPrenomEtu(String prenomEtu) {
        this.prenomEtu = prenomEtu;
    }
    public void setPromo(Promotion p) {
        this.promo = p ;
    }
    public Bac getBac() {
        return bac;
    }
    public void setBac(Bac bac) {
        this.bac = bac;
    }
    public Parcour getParcour() {
        return parcour;
    }
    public void setParcour(Parcour parcour) {
        this.parcour = parcour;
    }
    public String getGenreEtu() {
        return genreEtu;
    }
    public void setGenreEtu(String genreEtu) {
        this.genreEtu = genreEtu;
    }

	@Override
	public String getRole() {
		return this.Role;
	}
	public String getPassword() {return this.password;}

	@Override
	public String getNomComplet() {
		return this.prenomEtu + " " + this.nomEtu;
	}
	public String getaddr() {
		return this.addrEtu;
	}
	public int getnum() {
		return this.numEtu;
	}
	public String getmailpro() {
		return this.mailProEtu;
	}
	public String getmailperso() {
		return this.mailPersoEtu;
	}
	@Override
	public String getNom() {
	    return this.nomEtu;
	}
	@Override
	public String getPrenom() {
	    return this.prenomEtu;
	}
	public String getGenre() { return this.genreEtu; }
	public String getMailProEtu() { return this.mailProEtu; }
	public Promotion getPromotion() { return this.promo; }


	public boolean estRedoublant() {
		for(redoublement r : redoublement.Listredoublement) {
			if(r.getEtudiant().equals(this)) return true;
		}
		return false;
	}


	public Float GetMoyenneMatiere(Matiere m, Semestre s) {
		ArrayList<Note> notes = Note.GetNotes(this); // toutes les notes de l'étudiant
        int cpt = 0;
        float somme = 0;

        for (Note n : notes) {
            if (n.getMatiere().equals(m) && n.getSemestreNom().equals(s.getNomSemestre())) {
                cpt++;
                somme += (n.valeur * 20.0f) / n.valeurMax; // transformation note/20
            }
        }

        if (cpt == 0) {
            return null; // cas : pas de note pour cette matiere
        }

        return somme / cpt;
	}
	
    @Override
    public String toString() {
        return this.getNomComplet();
    }
	
	
	
}
