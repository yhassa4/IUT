package Base;

import java.util.ArrayList;

public class Group  {
//////// Attributs 
    private Promotion Promo;
    private int numGroup;
    private int numEnsei;  
    private GroupTD GroupTD; 
    private GroupTP GroupTP; 
   public static ArrayList<IGroup> GroupList = new ArrayList();
    public static ArrayList<Group> ListGroup2 = new ArrayList();

//////// Constructeur avec TD et TP 
    public Group(Promotion Promo,int numGroup,int numEnsei , GroupTD numGroupTD, GroupTP numGroupTP) {
        this.Promo = Promo;
        this.numGroup = numGroup;
        this.numEnsei = numEnsei;
        this.GroupTD = numGroupTD;
        this.GroupTP = numGroupTP;
       ListGroup2.add(this);
    }

//////// Constructeur sans TD et TP 
    public Group(Promotion Promo,int numGroup,int numEnsei ) {
        this.Promo = Promo;
        this.numGroup = numGroup;
        this.numEnsei = numEnsei;
        this.GroupTD = null;
        this.GroupTP = null;
        ListGroup2.add(this);
    }

public Group(Promotion promoActuelle) {
	this.Promo = promoActuelle ;
}

//////// Fonction : vérifier si un TD est défini 
    public boolean issetGroupTD(){
        if(GroupTD == null) {return false;}
        return true;
    }

//////// Fonction : vérifier si un TP est défini 
    public boolean issetGroupTP(){
        if(GroupTP == null) {return false;}
        return true;
    }

//////// Getter / Setter TD 
    public GroupTD getGroupTD() {
        return GroupTD;
    }
    public void setGroupTD(GroupTD numGroupTD) {
        this.GroupTD = numGroupTD;
    }

//////// Getter / Setter TP 
    public GroupTP getGroupTP() {
        return GroupTP;
    }
    public void setGroupTP(GroupTP numGroupTP) {
        this.GroupTP = numGroupTP;
    }
////////Getter / Setter TP 
 public Promotion getPromo() {return this.Promo;}
    
    
    
}
