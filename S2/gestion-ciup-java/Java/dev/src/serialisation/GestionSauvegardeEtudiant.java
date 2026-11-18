package serialisation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import mvc.modele.gestion.ModeleGestionAttente;

public class GestionSauvegardeEtudiant {
    private static final String FICHIER_ETUDIANT = "etudiant.dat";

    // Constructeur privé pour empêcher l'instanciation
    private GestionSauvegardeEtudiant() {
    }

    // Méthode pour sauvegarder un objet ModeleService dans un fichier
    public static void sauvegarder(ModeleGestionAttente modele) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHIER_ETUDIANT))) {
            oos.writeObject(modele);
            System.out.println("Sauvegarde effectuée avec succès.");
        }
    }

    // Méthode pour charger un objet ModeleService depuis un fichier
    public static ModeleGestionAttente charger() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHIER_ETUDIANT))) {
        	ModeleGestionAttente modele = (ModeleGestionAttente) ois.readObject();
            System.out.println("Chargement effectué avec succès.");
            return modele;
        }
    }

    // Méthode pour vérifier si le fichier de sauvegarde existe
    public static boolean existeSauvegarde() {
        File fichier = new File(FICHIER_ETUDIANT);
        return fichier.exists() && fichier.isFile();
    }

    // Méthode pour supprimer le fichier de sauvegarde
    public static void supprimerSauvegarde() {
        File fichier = new File(FICHIER_ETUDIANT);
        if (fichier.exists()) {
            fichier.delete();
            System.out.println("Fichier de sauvegarde supprimé.");
        }
    }
}
