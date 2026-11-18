package serialisation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import mvc.modele.ciup.ModeleCiteInternationale;


public class GestionSauvegardeCite {
    private static final String FICHIER_CITE = "cite.dat";

    // Constructeur privé pour empêcher l'instanciation
    private GestionSauvegardeCite() {
    }

    // Méthode pour sauvegarder un objet ModeleService dans un fichier
    public static void sauvegarder(ModeleCiteInternationale modele) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHIER_CITE))) {
            oos.writeObject(modele);
            System.out.println("Sauvegarde effectuée avec succès.");
        }
    }

    // Méthode pour charger un objet ModeleService depuis un fichier
    public static ModeleCiteInternationale charger() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHIER_CITE))) {
        	ModeleCiteInternationale modele = (ModeleCiteInternationale) ois.readObject();
            System.out.println("Chargement effectué avec succès.");
            return modele;
        }
    }

    // Méthode pour vérifier si le fichier de sauvegarde existe
    public static boolean existeSauvegarde() {
        File fichier = new File(FICHIER_CITE);
        return fichier.exists() && fichier.isFile();
    }

    // Méthode pour supprimer le fichier de sauvegarde
    public static void supprimerSauvegarde() {
        File fichier = new File(FICHIER_CITE);
        if (fichier.exists()) {
            fichier.delete();
            System.out.println("Fichier de sauvegarde supprimé.");
        }
    }
}
