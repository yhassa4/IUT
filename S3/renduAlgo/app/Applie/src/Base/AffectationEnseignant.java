package Base;

public class AffectationEnseignant {
    private Matiere matiere;
    private GroupTD groupeTD;
    private GroupTP groupeTP;

    public AffectationEnseignant(Matiere m, GroupTD td, GroupTP tp) {
        this.matiere = m;
        this.groupeTD = td;
        this.groupeTP = tp;
    }

    // Getters
    public Matiere getMatiere() { return matiere; }
    public GroupTD getGroupeTD() { return groupeTD; }
    public GroupTP getGroupeTP() { return groupeTP; }
}