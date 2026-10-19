package Base;

public class GenreParite {
////////Attributs 
    private String genre;
    private double pourcentage;
////////Constructeur 
    public GenreParite(String genre, double pourcentage) {
        this.genre = genre;
        this.pourcentage = pourcentage;
    }

////////Getter / Setter 
    public String getGenre() {
        return genre;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    @Override
    public String toString() {
        return genre + " : " + pourcentage + "%";
    }
}