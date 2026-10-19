package Base;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Commentaire {
    private int numCommentaire;
    private Enseignant numEnsei; 
    private String contenu;
    private LocalDateTime dateCreation;
    private GroupTD numGroupTD; 
    private GroupTP numGroupTP; 

    // Constructeur complet
    public Commentaire(int numCommentaire, Enseignant numEnsei, String contenu, 
                       LocalDateTime dateCreation, GroupTD numGroupTD, GroupTP numGroupTP) {
        this.numCommentaire = numCommentaire;
        this.numEnsei = numEnsei;
        this.contenu = contenu;
        this.dateCreation = dateCreation;
        this.numGroupTD = numGroupTD;
        this.numGroupTP = numGroupTP;
    }

    // Getters et Setters
    public int getNumCommentaire() { return numCommentaire; }
    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    
    public boolean estLieATD() { return numGroupTD != null; }
    public boolean estLieATP() { return numGroupTP != null; }
    
    
    public String getDateFormatee() {
        if (dateCreation == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateCreation.format(formatter);
    }

    /**
     * Utile pour afficher le commentaire dans une ListView
     */
    @Override
    public String toString() {
        return "[" + getDateFormatee() + "] : " + contenu;
    }

	public Enseignant getNumEnsei() {
		return numEnsei;
	}
	public IGroup getGroup() {
		if(numGroupTD instanceof GroupTD)return numGroupTD;
		return numGroupTP;
	}
	
	
}