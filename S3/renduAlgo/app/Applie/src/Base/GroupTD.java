package Base;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class GroupTD implements IGroup {
//////// Attributs 
    private int numGroupTD;
    private String nomGroupTD;
    private int tailleGroupTD;
    private LocalDate datePubli;

//////// Getter / Setter Nom du groupe TD 
    public String getNomGroupTD() {
        return nomGroupTD;
    }
    public void setNomGroupTD(String nomGroupTD) {
        this.nomGroupTD = nomGroupTD;
    }
@Override
    public int getTailleMax() {
        return this.tailleGroupTD;
    }

//////// Constructeur 
    public GroupTD(int numGroupTD, String nomGroupTD, int tailleGroupTD,LocalDate datePubli) {
        this.numGroupTD = numGroupTD;
        this.nomGroupTD = nomGroupTD;
        this.tailleGroupTD = tailleGroupTD;
        this.datePubli = datePubli;
       Group.GroupList.add(this);
    }
    public GroupTD(int numGroupTD, String nomGroupTD, int tailleGroupTD) {
        this.numGroupTD = numGroupTD;
        this.nomGroupTD = nomGroupTD;
        this.tailleGroupTD = tailleGroupTD;
        this.datePubli = LocalDate.now().plusMonths(1);
       Group.GroupList.add(this);
    }

//////// Fonction : verifier que l'on peux ajouter un étudiant au groupe TD 
@Override
    public boolean ajouterEtudiant(){
        if(GetnbrEtudiant() + 1 > tailleGroupTD) {
            return false;
        }
        return true;    
    }

//////// Fonction : récupérer la liste des étudiants du groupe TD 
@Override
public ArrayList<Etudiant> GetEtudiant() {
    ArrayList<Etudiant> membre = new ArrayList<>();

    for (int i = 0; i < Etudiant.ListEtu.length(); i++) {
        Etudiant etu = Etudiant.ListEtu.get(i);

      
        if (etu != null && etu.getGroup() != null && etu.getGroup().getGroupTD() != null) { // verif 
            if (etu.getGroup().getGroupTD().equals(this)) {
                membre.add(etu);
            }
        }
    }

    return membre;
}



//////// Fonction : récupérer le nombre d’étudiants du groupe TD 
@Override
    public int GetnbrEtudiant() {
        return GetEtudiant().size(); 
    } 

//////// Fonction : afficher les étudiants du groupe TD 

@Override
public void display() {
    ArrayList<Etudiant> membres = GetEtudiant();
    if (membres.isEmpty()) {
        //System.out.println("Aucun étudiant trouvé.");//debug
        return;
    }

    // --- 1. CALCUL DES LARGEURS DE COLONNES ---
    String[] headers = {"Prénom", "Nom", "Genre", "Bac", "Parcours", "Covoit", "Type"};
    int maxPrenom = headers[0].length(), maxNom = headers[1].length(), 
        maxGenre = headers[2].length(), maxBac = headers[3].length(), 
        maxParcours = headers[4].length(), maxCovoit = headers[5].length(),
        maxType = headers[6].length(); 

    for (Etudiant e : membres) {
        maxPrenom = Math.max(maxPrenom, e.getPrenomEtu().length());
        maxNom = Math.max(maxNom, e.getNomEtu().length());
        maxGenre = Math.max(maxGenre, e.getGenreEtu().length());
        maxBac = Math.max(maxBac, e.getBac() != null ? e.getBac().getNomBac().length() : 0);
        maxParcours = Math.max(maxParcours, e.getParcour() != null ? e.getParcour().getNomParcour().length() : 0);
        
        // Calcul pour la colonne Type
        String typeStr = e.getApprentissage() ? "Alternant" : "Initial";
        maxType = Math.max(maxType, typeStr.length());

        ArrayList<Etudiant> covoits = e.getPartenairesCovoit();
        if (!covoits.isEmpty()) {
            String infoCovoit = covoits.get(0).getNomEtu(); 
            maxCovoit = Math.max(maxCovoit, infoCovoit.length());
        }
    }

    // --- 2. AFFICHAGE DU TABLEAU ---
    String separator = "+-" + "-".repeat(maxPrenom) + "-+-" + "-".repeat(maxNom) + "-+-" + "-".repeat(maxGenre) + 
                       "-+-" + "-".repeat(maxBac) + "-+-" + "-".repeat(maxParcours) + "-+-" + "-".repeat(maxCovoit) + 
                       "-+-" + "-".repeat(maxType) + "-+";

    System.out.println(separator);
    System.out.printf("| %-" + maxPrenom + "s | %-" + maxNom + "s | %-" + maxGenre + "s | %-" + maxBac + "s | %-" + maxParcours + "s | %-" + maxCovoit + "s | %-" + maxType + "s |%n", 
                      headers[0], headers[1], headers[2], headers[3], headers[4], headers[5], headers[6]);
    System.out.println(separator);

    int nbAlternants = 0; // Pour les stats plus bas
    for (Etudiant e : membres) {
        ArrayList<Etudiant> covoits = e.getPartenairesCovoit();
        String infoCovoit = covoits.isEmpty() ? "-" : covoits.get(0).getNomEtu();
        String typeStr = e.getApprentissage() ? "Alternant" : "Initial";
        if(e.getApprentissage()) nbAlternants++;

        System.out.printf("| %-" + maxPrenom + "s | %-" + maxNom + "s | %-" + maxGenre + "s | %-" + maxBac + "s | %-" + maxParcours + "s | %-" + maxCovoit + "s | %-" + maxType + "s |%n",
                e.getPrenomEtu(), e.getNomEtu(), e.getGenreEtu(), 
                e.getBac() != null ? e.getBac().getNomBac() : "", 
                e.getParcour() != null ? e.getParcour().getNomParcour() : "",
                infoCovoit, typeStr);
    }
    System.out.println(separator);

    // --- 3. STATISTIQUES ---
    System.out.println("STATISTIQUES DU GROUPE :");

    // Statistique Alternants (Nouveau)
    double pctAlt = (nbAlternants * 100.0) / membres.size();
    System.out.printf("  [Statut]   -> Alternants : %.1f%% (%d) | Initiaux : %.1f%% (%d)%n", 
            pctAlt, nbAlternants, (100.0 - pctAlt), (membres.size() - nbAlternants));

    // Parité Genre
    System.out.print("  [Genre]    -> ");
    ArrayList<GenreParite> pariteG = getPariteGenre();
    for (int i = 0; i < pariteG.size(); i++) {
        System.out.print(pariteG.get(i).getGenre() + " : " + String.format("%.1f", pariteG.get(i).getPourcentage()) + "%");
        if (i < pariteG.size() - 1) System.out.print(" | ");
    }
    System.out.println();

    // Parité Bac
    System.out.print("  [Bac]      -> ");
    java.util.Map<String, Integer> counts = new java.util.HashMap<>();
    for (Etudiant e : membres) {
        String nomBac = (e.getBac() != null) ? e.getBac().getNomBac() : "Inconnu";
        counts.put(nomBac, counts.getOrDefault(nomBac, 0) + 1);
    }
    
    int total = membres.size();
    int idxBac = 0;
    for (String bacName : counts.keySet()) {
        double pourcentage = (counts.get(bacName) * 100.0) / total;
        System.out.print(bacName + " : " + String.format("%.1f", pourcentage) + "%");
        if (idxBac < counts.size() - 1) System.out.print(" | ");
        idxBac++;
    }
    System.out.println();

    // Écart-type et Remplissage
    System.out.printf("  [Niveau]   -> Écart-type global : %.2f%n", ecarTypeGlobal());
    System.out.printf("  [Effectif] -> %d étudiants%n", GetnbrEtudiant());

    // Diversité Parcours
    System.out.print("  [Parcours] -> ");
    java.util.Map<String, Integer> parcoursCounts = new java.util.HashMap<>();
    for (Etudiant e : membres) {
        String nP = (e.getParcour() != null) ? e.getParcour().getNomParcour() : "Inconnu";
        parcoursCounts.put(nP, parcoursCounts.getOrDefault(nP, 0) + 1);
    }
    int pIdx = 0;
    for (String p : parcoursCounts.keySet()) {
        double pct = (parcoursCounts.get(p) * 100.0) / total;
        System.out.print(p + " : " + String.format("%.1f", pct) + "%");
        if (pIdx < parcoursCounts.size() - 1) System.out.print(" | ");
        pIdx++;
    }
    System.out.println();
    
    // Statistique Covoiturage
    int nbCovoit = 0;
    for(Etudiant e : membres) if(!e.getPartenairesCovoit().isEmpty()) nbCovoit++;
    System.out.println("  [Covoit]   -> " + nbCovoit + " étudiants en covoiturage.");
    
    System.out.println(separator + "\n");
}
////////Fonction : return l'ecar Type d'un matiere du group
	@Override
	public float ecarTypeMatiere(Matiere M) {
		ArrayList<Etudiant> membres = this.GetEtudiant();
		if (membres.isEmpty()) {
		        return 0; // cas : pas d'étudiants
		  }
		float total = 0 ;
		for(int i = 0 ;i<membres.size();i++) {// calcul moyenne
			total += membres.get(i).GetMoyenneMatiere(M);
		}
		float moyenne = total / membres.size();
	    
	    float sommeCarres = 0;
	    for (Etudiant e : membres) {//calcul ecart type
	        float note = e.GetMoyenneMatiere(M);
	        sommeCarres += Math.pow(note - moyenne, 2);
	    }

	  
	    return (float) Math.sqrt(sommeCarres / membres.size());
	}
////////Fonction : return l'ecar Type general du group
	@Override
	public float ecarTypeGlobal() {
	    ArrayList<Etudiant> membres = this.GetEtudiant();
	    if (membres.isEmpty()) {
	        return 0; // cas : pas d'étudiants
	    }
	    float total = 0;
	    for (Etudiant e : membres) {
	        total += e.GetMoyenneGenerale(); 
	    }
	    float moyenne = total / membres.size();
	    float sommeCarres = 0;
	    for (Etudiant e : membres) {
	        float moyEtu = e.GetMoyenneGenerale();
	        sommeCarres += Math.pow(moyEtu - moyenne, 2);
	    }
	    return (float) Math.sqrt(sommeCarres / membres.size());
	}
////////Fonction : return un tableau de pariter d'un group
	@Override
	public ArrayList<GenreParite> getPariteGenre() {
	    ArrayList<Etudiant> membres = GetEtudiant();
	    ArrayList<GenreParite> parite = new ArrayList<>();

	    if (membres.isEmpty()) {
	        return parite; //cas : pas d'étudiants  
	    }

	    // Liste des genres distincts
	    ArrayList<String> genres = new ArrayList<>();
	    for (Etudiant e : membres) {
	        String g = e.getGenreEtu().toUpperCase();
	        if (!genres.contains(g)) {
	            genres.add(g);
	        }
	    }

	    int total = membres.size();

	    // Calcul pour chaque genre
	    for (String g : genres) {
	        int count = 0;
	        for (Etudiant e : membres) {
	            if (e.getGenreEtu().equalsIgnoreCase(g)) {
	                count++;
	            }
	        }
	        double pourcentage = (count * 100.0) / total;
	        parite.add(new GenreParite(g, pourcentage));
	    }

	    return parite;
	}
////////Fonction : afficher la pariter d'un group
	@Override
	public void afficherPariteGenre() {
	    ArrayList<GenreParite> parite = getPariteGenre();
	    if (parite.isEmpty()) {
	        System.out.println("Aucun étudiant dans le groupe.");
	        return;
	    }

	    System.out.println("Parité des genres dans le groupe :");
	    for (GenreParite gp : parite) {
	        System.out.println(gp);
	    }
	}
////////Fonction : afficher la pariter d'un group
	@Override
	public float moyenneGeneraleGroupe() {
	    ArrayList<Etudiant> membres = GetEtudiant();
	    if (membres.isEmpty()) {
	        return 0; // pas d'étudiants
	    }
	    float total = 0;
	    for (Etudiant e : membres) {
	        total += e.GetMoyenneGenerale();
	    }
	    return total / membres.size();
	}
////////Fonction : return la moyenne d'une matiere pour l ensemble des etudiants d'un group
	@Override
	public float moyenneMatiereGroupe(Matiere M) {
	    ArrayList<Etudiant> membres = GetEtudiant();
	    if (membres.isEmpty()) {
	        return 0; // pas d'étudiants
	    }
	    float total = 0;
	    for (Etudiant e : membres) {
	    	if(e.GetMoyenneMatiere(M)!= null) {
	    		total += e.GetMoyenneMatiere(M);
	    	}
	    }
	    return total / membres.size();
	}
////////Fonction : return les matiere ou les etudiants ou tous au minimum une note
	@Override
	public ArrayList<Matiere> getMatieresAvecNotes() {
		ArrayList<Etudiant> membres = GetEtudiant();
	    ArrayList<Matiere> matieresValides = new ArrayList<>();

	    if (membres.isEmpty()) {
	        return matieresValides; // aucun étudiant
	    }

	    for (Matiere m : Matiere.toutesMatieres) {
	        boolean tousOntNote = false;

	        for (Etudiant e : membres) {
	            Float note = e.GetMoyenneMatiere(m);
	            if (note != null) { // si un étudiant n'a pas de note pour cette matière
	                tousOntNote = true;
	                break;
	            }
	        }

	        if (tousOntNote) {
	            matieresValides.add(m);
	        }
	    }

	    return matieresValides;
	}
////////Fonction : affichage des moyenne par matiere d'un group
	@Override
	public void  displayMoyenneMatiereGroup() {
		ArrayList<Matiere> matieres = getMatieresAvecNotes();
		for(int i = 0 ;i<matieres.size();i++) {
			System.out.println("nom de la matiere : "+matieres.get(i).getNomMatiere()+"  moyenne = "+moyenneMatiereGroupe(matieres.get(i)));
		}
		
	}
@Override
public String getNomGroup() {
	return this.nomGroupTD;
}
public String toString() {
    return this.getNomGroup();
}


@Override
public void setDatePublication(LocalDate nouvelleDate) {
		this.datePubli = nouvelleDate;
}
@Override
public LocalDate getDatePublication() {
	return this.datePubli;
}

@Override
public boolean isActif(){
	LocalDate myObj = LocalDate.now();
	if(this.datePubli == null)return false ;
	if(this.datePubli.isBefore(myObj)) return true ;
	return false ;
}

}
