package Base;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import tree.List;
import tree.ListI;
public class mainAlgo {

	public static ArrayList<GroupTD> ListGroupTDMain = new  ArrayList<>();
	public static ArrayList<GroupTP>ListGroupTPMain = new  ArrayList<>();
	static float meilleurScoreGlobal = Float.MAX_VALUE; // Meilleur score global trouvé (plus bas = meilleur)
	static int[] meilleureConfig; // Configuration gagnante ( meilleureConfig[i] = groupe de l'étudiant i)
	static int[] configActuelle;// Configuration actuelle testée (configActuelle[i] = groupe de l'étudiant i)

	public static void genererNotes(Etudiant etu, Semestre semestre,Semestre semestre2, int nbNotesParMatiere) {
		Random rand = new Random();
		int idNote = 0;

		for (Matiere m : Matiere.toutesMatieres) {
			for (int i = 0; i < nbNotesParMatiere; i++) {
				int valeurMax = (rand.nextBoolean()) ? 20 : 40; // barème 20 ou 40
				float valeur = rand.nextInt(valeurMax + 1); // note aléatoire entre 0 et valeurMax
				Semestre s = (rand.nextBoolean()) ? semestre : semestre2;
				new Note(idNote++, valeur, valeurMax, etu, s, m);
			}
		}
	}
	public static void AttribuerNote(Semestre semestre , int nbNotesParMatiere){
		Semestre s2 = new Semestre(2,"s2");
		for (int i = 0; i < Etudiant.ListEtu.length(); i++) {
			Etudiant e = Etudiant.ListEtu.get(i);
			genererNotes(e, semestre,s2, nbNotesParMatiere);
		}
	}

	public static void genererCovoiturage(int nombreDePaires) {
		Random rand = new Random();
		int nbEtu = Etudiant.ListEtu.length();
		int covoitCree = 0;
		int tentatives = 0;

		while (covoitCree < nombreDePaires && tentatives < 5000) {
			tentatives++;

			int idx1 = rand.nextInt(nbEtu-1);
			int idx2 = rand.nextInt(nbEtu-1);

			if (idx1 != idx2) {
				Etudiant e1 = Etudiant.ListEtu.get(idx1);
				Etudiant e2 = Etudiant.ListEtu.get(idx2);

				if (e1.getPartenairesCovoit().isEmpty() && e2.getPartenairesCovoit().isEmpty()) {

					new Covoiture(e1, e2);

					covoitCree++;
				}
			}
		}
		// System.out.println("-> " + covoitCree + " paires de covoiturage ont été générées.");//debug
	}

	static void clearNote() {
		Note.notes.clear();
	}
	static void clearEtudiant() {
		Etudiant.ListEtu = new tree.List<>();
	}

	public static void genererGroupes(int nbTD, int tailleTD, int nbTP, int tailleTP) {
		// Générer les TD
		for (int i = 0; i < nbTD; i++) {
			GroupTD gTD = new GroupTD(i, "GroupTD" + (i+1), tailleTD);
			ListGroupTDMain.add(gTD);
		}

		// Générer les TP
		for (int i = 0; i < nbTP; i++) {
			GroupTP gTP = new GroupTP(i, "GroupTP" + (i+1), tailleTP);
			ListGroupTPMain.add(gTP);
		}
	}
	public static void CreationGroup(Semestre semestre, Bac general, Bac techno, Bac pro,
			Promotion promo, Parcour A, Parcour B, Parcour C,int taillegTD,int taillegTP,int nbretu) {
		Scanner scanner = new Scanner(System.in);



		int totalgTD;
		if (nbretu % taillegTD == 0) {
		    totalgTD = nbretu / taillegTD;
		} else {
		    totalgTD = nbretu / taillegTD + 1;
		}

		int totalgTP;
		if (nbretu % taillegTP == 0) {
		    totalgTP = nbretu / taillegTP;
		} else {
		    totalgTP = nbretu / taillegTP + 1;
		}

		// Génération des groupes
		genererGroupes(totalgTD, taillegTD, totalgTP, taillegTP);

		// 4. Génération des étudiants 
		for (int i = 0; i < nbretu; i++) {
			Bac bacChoisi = (i % 3 == 0) ? general : (i % 3 == 1) ? techno : pro;
			Parcour p = (i % 3 == 0) ? A : (i % 3 == 1) ? B : C;

			new Etudiant(i, "Nom" + i, "Prenom" + i, (i % 2 == 0 ? "Homme" : "Femme"),
					"etu" + i + "@univ.fr", "etu" + i + "@gmail.com", "Adresse " + i,
					bacChoisi, promo, p);
		}

		genererCovoiturage(20);
		AttribuerNote(semestre, 2);
	}
	public static void CreationGroup2(Semestre semestre, Bac general, Bac techno, Bac pro,
			Promotion promo, Parcour A, Parcour B, Parcour C,int nbretu) {
		Scanner scanner = new Scanner(System.in);

		// 4. Génération des étudiants 
		for (int i = 0; i < nbretu; i++) {
			Bac bacChoisi = (i % 3 == 0) ? general : (i % 3 == 1) ? techno : pro;
			Parcour p = (i % 3 == 0) ? A : (i % 3 == 1) ? B : C;

			new Etudiant(i, "Nom" + i, "Prenom" + i, (i % 2 == 0 ? "Homme" : "Femme"),
					"etu" + i + "@univ.fr", "etu" + i + "@gmail.com", "Adresse " + i,
					bacChoisi, promo, p);
		}

		genererCovoiturage(20);
		AttribuerNote(semestre, 2);
	}






	/**
	 * CRITÈRES DE L'ALGO GLOUTON 1 :
	 * 1. Équilibre du niveau scolaire (Ecart-type des moyennes générales).
	 * 2. Mixité des Bac (Éviter d'avoir trop d'étudiants du même Bac dans un groupe).
	 * 3. Équilibre du remplissage (Favoriser les groupes qui ont moins d'étudiants).
	 */
	public static void glouton1(List<Etudiant> ListEtu, int type) {
		// Arrêt de la récursion si la liste est vide
		if (ListEtu == null || ListEtu.data() == null) return;

		// Sélection de la liste (TD ou TP) selon le paramètre 'type'
		ArrayList<? extends IGroup> listGroup;
		if (type == 1) {
			listGroup = ListGroupTDMain;
		} else {
			listGroup = ListGroupTPMain;
		}  

		Etudiant etu = ListEtu.data();
		int meilleurIndex = -1;
		float meilleurScore = 1000; // On cherche le score le plus bas possible

		// Parcours de tous les groupes disponibles
		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);

			// Vérifie s'il reste de la place dans le groupe
			if (groupe.ajouterEtudiant()) {

				// Simulation : placement temporaire pour calculer les stats
				etu.setGroup(groupe); 

				// Calcul du score basé sur l'écart-type 
				float ecarType = groupe.ecarTypeGlobal();

				// Calcul du score basé sur le type de Bac
				int nbMemeBac = 0;
				ArrayList<Etudiant> membres = groupe.GetEtudiant();
				for (Etudiant e : membres) {
					if (e.getBac().getNomBac().equals(etu.getBac().getNomBac())) {
						nbMemeBac++;
					}
				}

				// Calcul du score basé sur le nombre d'étudiants
				int nbEtudiants = groupe.GetnbrEtudiant();

				// FORMULE DU SCORE : plus c'est bas, mieux c'est(* 0.5f gere l'importance)
				float score = ecarType + (nbMemeBac - 1) + (nbEtudiants * 0.5f);

				// Si ce groupe est meilleur que le précédent, on le garde en mémoire
				if (score < meilleurScore) {
					meilleurScore = score;
					meilleurIndex = i;
				}

				// NETTOYAGE : On retire l'étudiant pour tester le groupe suivant sans fausser les calculs		
				if (type == 1) {
					etu.unsetGroupTD();
				} else {
					etu.unsetGroupTP();
				}
			}
		}

		// AFFECTATION FINALE : On place l'étudiant dans le meilleur groupe trouvé
		if (meilleurIndex != -1) {
			etu.setGroup(listGroup.get(meilleurIndex));
		}

		// Récursion
		if (ListEtu.tail() != null) {
			glouton1(ListEtu.tail(), type);
		}
	}


	public static void glouton1(List<Etudiant> ListEtu, int type,ArrayList<IGroup> Groups) {
		// Arrêt de la récursion si la liste est vide
		if (ListEtu == null || ListEtu.data() == null) return;

		// Sélection de la liste (TD ou TP) selon le paramètre 'type'
		ArrayList<? extends IGroup> listGroup = Groups;

		Etudiant etu = ListEtu.data();
		int meilleurIndex = -1;
		float meilleurScore = 1000; // On cherche le score le plus bas possible

		// Parcours de tous les groupes disponibles
		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);

			// Vérifie s'il reste de la place dans le groupe
			if (groupe.ajouterEtudiant()) {

				// Simulation : placement temporaire pour calculer les stats
				etu.setGroup(groupe); 

				// Calcul du score basé sur l'écart-type 
				float ecarType = groupe.ecarTypeGlobal();

				// Calcul du score basé sur le type de Bac
				int nbMemeBac = 0;
				ArrayList<Etudiant> membres = groupe.GetEtudiant();
				for (Etudiant e : membres) {
					if (e.getBac().getNomBac().equals(etu.getBac().getNomBac())) {
						nbMemeBac++;
					}
				}

				// Calcul du score basé sur le nombre d'étudiants
				int nbEtudiants = groupe.GetnbrEtudiant();

				// FORMULE DU SCORE : plus c'est bas, mieux c'est(* 0.5f gere l'importance)
				float score = ecarType + (nbMemeBac - 1) + (nbEtudiants * 0.5f);

				// Si ce groupe est meilleur que le précédent, on le garde en mémoire
				if (score < meilleurScore) {
					meilleurScore = score;
					meilleurIndex = i;
				}

				// NETTOYAGE : On retire l'étudiant pour tester le groupe suivant sans fausser les calculs		
				if (type == 1) {
					etu.unsetGroupTD();
				} else {
					etu.unsetGroupTP();
				}
			}
		}

		// AFFECTATION FINALE : On place l'étudiant dans le meilleur groupe trouvé
		if (meilleurIndex != -1) {
			etu.setGroup(listGroup.get(meilleurIndex));
		}

		// Récursion
		if (ListEtu.tail() != null) {
			glouton1(ListEtu.tail(), type,Groups);
		}
	}





















	/**
	 * CRITÈRES DE L'ALGO GLOUTON 2 :
	 * 1. Équilibre des genres (Parité Homme/Femme).
	 * 2. Diversité des parcours
	 * 3. Écart-type des notes
	 */
	public static void glouton2(List<Etudiant> ListEtu, int type) {
		// Condition d'arrêt : on sort si la liste est vide
		if (ListEtu == null || ListEtu.data() == null) return;

		// Sélection dynamique de la liste (TD ou TP) selon le choix de l'utilisateur
		ArrayList<? extends IGroup> listGroup;
		if (type == 1) {
			listGroup = ListGroupTDMain;
		} else {
			listGroup = ListGroupTPMain;
		} 

		Etudiant etu = ListEtu.data();
		int meilleurIndex = -1;
		float meilleurScore = Float.MAX_VALUE; // Initialisé au maximum possible

		// On parcourt chaque groupe de la liste sélectionnée
		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);

			// On vérifie si le groupe peut encore accueillir un étudiant
			if (groupe.ajouterEtudiant()) {

				// Simulation : placement temporaire de l'étudiant dans le groupe
				etu.setGroup(groupe);
				ArrayList<Etudiant> membres = groupe.GetEtudiant();

				// CRITÈRE 1 : PARITÉ GENRE
				// On compte combien d'étudiants du même sexe sont déjà présents
				int memeGenre = 0;
				for (Etudiant e : membres) {
					if (e.getGenreEtu().equalsIgnoreCase(etu.getGenreEtu())) {
						memeGenre++;
					}
				}
				float scoreGenre = (memeGenre - 1); // -1 pour ne pas se compter soi-même

				// CRITÈRE 2 : MIXITÉ DES PARCOURS
				// On cherche à minimiser le regroupement d'étudiants issus du même parcours
				int memeParcours = 0;
				for (Etudiant e : membres) {
					if (e.getParcour().getNomParcour().equals(etu.getParcour().getNomParcour())) {
						memeParcours++;
					}
				}
				float scoreParcours = (memeParcours - 1);

				// CRITÈRE 3 : NIVEAU SCOLAIRE
				// Utilisation de l'écart-type pour maintenir un groupe homogène
				float scoreNotes = groupe.ecarTypeGlobal();

				// SCORE TOTAL : Plus le résultat est faible, plus le groupe est équilibré
				float scoreTotal = scoreGenre + scoreParcours + scoreNotes;

				// Si ce groupe est le meilleur trouvé jusqu'ici, on enregistre son index
				if (scoreTotal < meilleurScore) {
					meilleurScore = scoreTotal;
					meilleurIndex = i;
				}

				// NETTOYAGE : On retire l'étudiant des deux types de groupes pour le test suivant
				if (type == 1) {
					etu.unsetGroupTD();
				} else {
					etu.unsetGroupTP();
				}
			}
		}

		// AFFECTATION FINALE : Placement définitif dans le meilleur groupe trouvé
		if (meilleurIndex != -1) {
			etu.setGroup(listGroup.get(meilleurIndex));
		}

		// RÉCURSION 
		if (ListEtu.tail() != null) {
			glouton2(ListEtu.tail(), type);
		}
	}

	public static void glouton2(List<Etudiant> ListEtu, int type,ArrayList<IGroup> Groups) {
		// Condition d'arrêt : on sort si la liste est vide
		if (ListEtu == null || ListEtu.data() == null) return;

		// Sélection dynamique de la liste (TD ou TP) selon le choix de l'utilisateur
		ArrayList<? extends IGroup> listGroup = Groups;

		Etudiant etu = ListEtu.data();
		int meilleurIndex = -1;
		float meilleurScore = Float.MAX_VALUE; // Initialisé au maximum possible

		// On parcourt chaque groupe de la liste sélectionnée
		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);

			// On vérifie si le groupe peut encore accueillir un étudiant
			if (groupe.ajouterEtudiant()) {

				// Simulation : placement temporaire de l'étudiant dans le groupe
				etu.setGroup(groupe);
				ArrayList<Etudiant> membres = groupe.GetEtudiant();

				// CRITÈRE 1 : PARITÉ GENRE
				// On compte combien d'étudiants du même sexe sont déjà présents
				int memeGenre = 0;
				for (Etudiant e : membres) {
					if (e.getGenreEtu().equalsIgnoreCase(etu.getGenreEtu())) {
						memeGenre++;
					}
				}
				float scoreGenre = (memeGenre - 1); // -1 pour ne pas se compter soi-même

				// CRITÈRE 2 : MIXITÉ DES PARCOURS
				// On cherche à minimiser le regroupement d'étudiants issus du même parcours
				int memeParcours = 0;
				for (Etudiant e : membres) {
					if (e.getParcour().getNomParcour().equals(etu.getParcour().getNomParcour())) {
						memeParcours++;
					}
				}
				float scoreParcours = (memeParcours - 1);

				// CRITÈRE 3 : NIVEAU SCOLAIRE
				// Utilisation de l'écart-type pour maintenir un groupe homogène
				float scoreNotes = groupe.ecarTypeGlobal();

				// SCORE TOTAL : Plus le résultat est faible, plus le groupe est équilibré
				float scoreTotal = scoreGenre + scoreParcours + scoreNotes;

				// Si ce groupe est le meilleur trouvé jusqu'ici, on enregistre son index
				if (scoreTotal < meilleurScore) {
					meilleurScore = scoreTotal;
					meilleurIndex = i;
				}

				// NETTOYAGE : On retire l'étudiant des deux types de groupes pour le test suivant
				if (type == 1) {
					etu.unsetGroupTD();
				} else {
					etu.unsetGroupTP();
				}
			}
		}

		// AFFECTATION FINALE : Placement définitif dans le meilleur groupe trouvé
		if (meilleurIndex != -1) {
			etu.setGroup(listGroup.get(meilleurIndex));
		}

		// RÉCURSION 
		if (ListEtu.tail() != null) {
			glouton2(ListEtu.tail(), type,Groups);
		}
	}









	/**
	 * CRITÈRES DE L'ALGO GLOUTON 3 :
	 * 1. Gestion du Covoiturage .
	 * 2. Diversité des parcours .
	 * 3. écart-type.
	 */
	public static void glouton3(List<Etudiant> listEtu, int type) {
		// Condition d'arrêt de la récursion
		if (listEtu == null || listEtu.data() == null) return;

		Etudiant etu = listEtu.data();
		ArrayList<? extends IGroup> listGroup;

		// Sélection dynamique de la liste cible (TD ou TP)
		if (type == 1) {
			listGroup = ListGroupTDMain;
		} else {
			listGroup = ListGroupTPMain;
		} 

		// Sécurité : On vérifie si l'étudiant est déjà placé dans le type de groupe demandé(cas covoiturage)
		boolean dejaPlace = (type == 1) ? 
				(etu.getGroup() != null && etu.getGroup().issetGroupTD()) :
					(etu.getGroup() != null && etu.getGroup().issetGroupTP());

		if (dejaPlace) {
			if (listEtu.tail() != null) glouton3(listEtu.tail(), type);
			return;
		}

		// 1. RECUPERATION DES COVOITURAGE (this + Partenaires )
		ArrayList<Etudiant> groupeC = new ArrayList<>();
		groupeC.add(etu); 

		ArrayList<Etudiant> partenaires = etu.getPartenairesCovoit();
		if (partenaires != null) {
			for (Etudiant p : partenaires) {
				if (!groupeC.contains(p)) {
					groupeC.add(p);
				}
			}
		}

		int nbAInscrire = groupeC.size();
		int meilleurIndex = -1;
		float meilleurScore = Float.MAX_VALUE;

		// 2. RECHERCHE DU MEILLEUR GROUPE POUR LE groupeC
		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);

			// On vérifie s'il y a assez de place pour TOUT le groupe de covoiturage
			int placesRestantes = groupe.getTailleMax() - groupe.GetnbrEtudiant();

			if (placesRestantes >= nbAInscrire) {

				// Simulation : on place temporairement tout le bloc
				for (Etudiant e : groupeC) {
					e.setGroup(groupe);
				}

				// Calcul du score basé sur l'écart-type global
				float ecarType = groupe.ecarTypeGlobal();

				// Calcul du score basé sur la mixité des parcours
				int memeParcours = 0;
				ArrayList<Etudiant> membres = groupe.GetEtudiant();
				for (Etudiant m : membres) {
					if (m.getParcour().getNomParcour().equals(etu.getParcour().getNomParcour())) {
						memeParcours++;
					}
				}

				// Formule 
				float score = ecarType + memeParcours;

				if (score < meilleurScore) {
					meilleurScore = score;
					meilleurIndex = i;
				}

				// NETTOYAGE : On libère les places pour le test du groupe suivant
				for (Etudiant e : groupeC) {
					if (type == 1) {
						etu.unsetGroupTD();
					} else {
						etu.unsetGroupTP();
					}
				}
			}
		}

		// 3. AFFECTATION FINALE DU BLOC
		if (meilleurIndex != -1) {
			IGroup g = listGroup.get(meilleurIndex);
			for (Etudiant e : groupeC) {
				e.setGroup(g);
			}
		} 

		// 4. RÉCURSION 
		if (listEtu.tail() != null) {
			glouton3(listEtu.tail(), type);
		}
	}


	public static void glouton3(List<Etudiant> listEtu, int type,ArrayList<IGroup> Groups) {
		// Condition d'arrêt de la récursion
		if (listEtu == null || listEtu.data() == null) return;

		Etudiant etu = listEtu.data();
		ArrayList<? extends IGroup> listGroup = Groups ;

		// Sécurité : On vérifie si l'étudiant est déjà placé dans le type de groupe demandé(cas covoiturage)
		boolean dejaPlace = (type == 1) ? 
				(etu.getGroup() != null && etu.getGroup().issetGroupTD()) :
					(etu.getGroup() != null && etu.getGroup().issetGroupTP());

		if (dejaPlace) {
			if (listEtu.tail() != null) glouton3(listEtu.tail(), type,Groups);
			return;
		}

		// 1. RECUPERATION DES COVOITURAGE (this + Partenaires )
		ArrayList<Etudiant> groupeC = new ArrayList<>();
		groupeC.add(etu); 

		ArrayList<Etudiant> partenaires = etu.getPartenairesCovoit();
		if (partenaires != null) {
			for (Etudiant p : partenaires) {
				if (!groupeC.contains(p)) {
					groupeC.add(p);
				}
			}
		}

		int nbAInscrire = groupeC.size();
		int meilleurIndex = -1;
		float meilleurScore = Float.MAX_VALUE;

		// 2. RECHERCHE DU MEILLEUR GROUPE POUR LE groupeC
		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);

			// On vérifie s'il y a assez de place pour TOUT le groupe de covoiturage
			int placesRestantes = groupe.getTailleMax() - groupe.GetnbrEtudiant();

			if (placesRestantes >= nbAInscrire) {

				// Simulation : on place temporairement tout le bloc
				for (Etudiant e : groupeC) {
					e.setGroup(groupe);
				}

				// Calcul du score basé sur l'écart-type global
				float ecarType = groupe.ecarTypeGlobal();

				// Calcul du score basé sur la mixité des parcours
				int memeParcours = 0;
				ArrayList<Etudiant> membres = groupe.GetEtudiant();
				for (Etudiant m : membres) {
					if (m.getParcour().getNomParcour().equals(etu.getParcour().getNomParcour())) {
						memeParcours++;
					}
				}

				// Formule 
				float score = ecarType + memeParcours;

				if (score < meilleurScore) {
					meilleurScore = score;
					meilleurIndex = i;
				}

				// NETTOYAGE : On libère les places pour le test du groupe suivant
				for (Etudiant e : groupeC) {
					if (type == 1) {
						etu.unsetGroupTD();
					} else {
						etu.unsetGroupTP();
					}
				}
			}
		}

		// 3. AFFECTATION FINALE DU BLOC
		if (meilleurIndex != -1) {
			IGroup g = listGroup.get(meilleurIndex);
			for (Etudiant e : groupeC) {
				e.setGroup(g);
			}
		} 

		// 4. RÉCURSION 
		if (listEtu.tail() != null) {
			glouton3(listEtu.tail(), type,Groups);
		}
	}






	/**
	 * CRITÈRES DE L'ALGO GLOUTON 4 :
	 * 1. Priorité à la Parité .
	 * 2. Écart-type des notes.
	 */
	public static void glouton4(List<Etudiant> ListEtu, int type) {
		// Condition d'arrêt de la récursion
		if (ListEtu == null || ListEtu.data() == null) return;

		// Sélection dynamique de la liste cible (TD ou TP)
		ArrayList<? extends IGroup> listGroup;
		if (type == 1) {
			listGroup = ListGroupTDMain;
		} else {
			listGroup = ListGroupTPMain;
		} 

		Etudiant etu = ListEtu.data();

		// Sécurité : On vérifie si l'étudiant est déjà placé dans le type de groupe demandé
		boolean dejaPlace = (type == 1) ? 
				(etu.getGroup() != null && etu.getGroup().issetGroupTD()) :
					(etu.getGroup() != null && etu.getGroup().issetGroupTP());

		if (dejaPlace) {
			if (ListEtu.tail() != null) glouton4(ListEtu.tail(), type);
			return;
		}

		int meilleurIndex = -1;
		float meilleurScore = Float.MAX_VALUE;

		// Parcours de tous les groupes 
		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);

			// Vérification de la capacité maximale du groupe
			if (groupe.ajouterEtudiant()) {

				// Simulation : placement temporaire
				etu.setGroup(groupe);
				ArrayList<Etudiant> membres = groupe.GetEtudiant();

				// CRITÈRE 1 : CALCUL DE LA PARITÉ
				int nbHommes = 0;
				int nbFemmes = 0;
				for (Etudiant m : membres) {
					if (m.getGenreEtu().equalsIgnoreCase("Homme")) nbHommes++;
					else if (m.getGenreEtu().equalsIgnoreCase("Femme")) nbFemmes++;
				}

				// On cherche à minimiser l'écart entre le nombre d'hommes et de femmes
				int differenceGenre = Math.abs(nbHommes - nbFemmes);

				// CRITÈRE 2 : CALCUL DU NIVEAU SCOLAIRE
				float scoreNotes = groupe.ecarTypeGlobal();

				// SCORE TOTAL
				float scoreTotal = differenceGenre  + scoreNotes;

				if (scoreTotal < meilleurScore) {
					meilleurScore = scoreTotal;
					meilleurIndex = i;
				}

				// NETTOYAGE 
				if (type == 1) {
					etu.unsetGroupTD();
				} else {
					etu.unsetGroupTP();
				}
			}
		}

		// AFFECTATION FINALE 
		if (meilleurIndex != -1) {
			etu.setGroup(listGroup.get(meilleurIndex));
		}

		// RÉCURSION 
		if (ListEtu.tail() != null) {
			glouton4(ListEtu.tail(), type);
		}
	}





	public static void glouton4(List<Etudiant> ListEtu, int type,ArrayList<IGroup> Groups) {
		// Condition d'arrêt de la récursion
		if (ListEtu == null || ListEtu.data() == null) return;

		// Sélection dynamique de la liste cible (TD ou TP)
		ArrayList<? extends IGroup> listGroup = Groups ;

		Etudiant etu = ListEtu.data();

		// Sécurité : On vérifie si l'étudiant est déjà placé dans le type de groupe demandé
		boolean dejaPlace = (type == 1) ? 
				(etu.getGroup() != null && etu.getGroup().issetGroupTD()) :
					(etu.getGroup() != null && etu.getGroup().issetGroupTP());

		if (dejaPlace) {
			if (ListEtu.tail() != null) glouton4(ListEtu.tail(), type);
			return;
		}

		int meilleurIndex = -1;
		float meilleurScore = Float.MAX_VALUE;

		// Parcours de tous les groupes 
		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);

			// Vérification de la capacité maximale du groupe
			if (groupe.ajouterEtudiant()) {

				// Simulation : placement temporaire
				etu.setGroup(groupe);
				ArrayList<Etudiant> membres = groupe.GetEtudiant();

				// CRITÈRE 1 : CALCUL DE LA PARITÉ
				int nbHommes = 0;
				int nbFemmes = 0;
				for (Etudiant m : membres) {
					if (m.getGenreEtu().equalsIgnoreCase("Homme")) nbHommes++;
					else if (m.getGenreEtu().equalsIgnoreCase("Femme")) nbFemmes++;
				}

				// On cherche à minimiser l'écart entre le nombre d'hommes et de femmes
				int differenceGenre = Math.abs(nbHommes - nbFemmes);

				// CRITÈRE 2 : CALCUL DU NIVEAU SCOLAIRE
				float scoreNotes = groupe.ecarTypeGlobal();

				// SCORE TOTAL
				float scoreTotal = differenceGenre  + scoreNotes;

				if (scoreTotal < meilleurScore) {
					meilleurScore = scoreTotal;
					meilleurIndex = i;
				}

				// NETTOYAGE 
				if (type == 1) {
					etu.unsetGroupTD();
				} else {
					etu.unsetGroupTP();
				}
			}
		}

		// AFFECTATION FINALE 
		if (meilleurIndex != -1) {
			etu.setGroup(listGroup.get(meilleurIndex));
		}

		// RÉCURSION 
		if (ListEtu.tail() != null) {
			glouton4(ListEtu.tail(), type,Groups);
		}
	}






















	/**
	 * CRITÈRES DE L'ALGO GLOUTON 5 :
	 * 1. Diversité des Parcours d'origine.
	 * 2. Parité Homme/Femme .
	 */
	public static void glouton5(List<Etudiant> ListEtu, int type) {
		// 1. Arrêt récursif : on stoppe quand on a parcouru toute la liste
		if (ListEtu == null || ListEtu.data() == null) return;

		// Sélection dynamique de la liste (TD ou TP)
		ArrayList<? extends IGroup> listGroup;
		if (type == 1) {
			listGroup = ListGroupTDMain;
		} else {
			listGroup = ListGroupTPMain;
		} 
		Etudiant etu = ListEtu.data();

		// 2. Sécurité : Vérification si l'étudiant est déjà placé dans ce type de groupe
		boolean dejaPlace = (type == 1) ? 
				(etu.getGroup() != null && etu.getGroup().issetGroupTD()) :
					(etu.getGroup() != null && etu.getGroup().issetGroupTP());

		if (dejaPlace) {
			if (ListEtu.tail() != null) glouton5(ListEtu.tail(), type);
			return;
		}

		int meilleurIndex = -1;
		float meilleurScore = Float.MAX_VALUE;

		// 3. Parcours des groupes disponibles
		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);

			// On vérifie s'il reste de la place physique dans le groupe
			if (groupe.ajouterEtudiant()) {

				// --- SIMULATION ---
				// On place l'étudiant temporairement pour voir l'impact sur les statistiques
				etu.setGroup(groupe);
				ArrayList<Etudiant> membres = groupe.GetEtudiant();

				// CRITÈRE 1 : PARITÉ GENRE
				// On cherche à minimiser l'écart entre le nombre d'hommes et de femmes
				int nbHommes = 0;
				int nbFemmes = 0;
				for (Etudiant m : membres) {
					if (m.getGenreEtu().equalsIgnoreCase("Homme")) nbHommes++;
					else nbFemmes++; 
				}
				// Plus la différence est petite, meilleur est le score 
				float scoreGenre = Math.abs(nbHommes - nbFemmes);

				// CRITÈRE 2 : DIVERSITÉ DES PARCOURS
				// On compte combien de personnes ont DÉJÀ le même parcours que l'étudiant actuel
				int nbMemeParcours = 0;
				String parcoursActuel = etu.getParcour().getNomParcour();

				for (Etudiant m : membres) {
					if (m.getParcour().getNomParcour().equals(parcoursActuel)) {
						nbMemeParcours++;
					}
				}
				// Chaque personne du même parcours augmente le "malus" (score)
				float scoreParcours = nbMemeParcours;

				// SCORE TOTAL
				float scoreTotal = scoreGenre + scoreParcours;

				if (scoreTotal < meilleurScore) {
					meilleurScore = scoreTotal;
					meilleurIndex = i;
				}

				// --- NETTOYAGE SIMULATION ---
				if (type == 1) {
					etu.unsetGroupTD();
				} else {
					etu.unsetGroupTP();
				}
			}
		}

		// 4. AFFECTATION DÉFINITIVE
		if (meilleurIndex != -1) {
			etu.setGroup(listGroup.get(meilleurIndex));
		} else {
			System.out.println("Attention : Pas de place trouvée pour " + etu.getNomEtu());
		}

		// 5. APPEL RÉCURSIF 
		if (ListEtu.tail() != null) {
			glouton5(ListEtu.tail(), type);
		}
	}





	public static void glouton5(List<Etudiant> ListEtu, int type,ArrayList<IGroup> Groups) {
		// 1. Arrêt récursif : on stoppe quand on a parcouru toute la liste
		if (ListEtu == null || ListEtu.data() == null) return;

		// Sélection dynamique de la liste (TD ou TP)
		ArrayList<? extends IGroup> listGroup = Groups ;
		Etudiant etu = ListEtu.data();

		// 2. Sécurité : Vérification si l'étudiant est déjà placé dans ce type de groupe
		boolean dejaPlace = (type == 1) ? 
				(etu.getGroup() != null && etu.getGroup().issetGroupTD()) :
					(etu.getGroup() != null && etu.getGroup().issetGroupTP());

		if (dejaPlace) {
			if (ListEtu.tail() != null) glouton5(ListEtu.tail(), type);
			return;
		}

		int meilleurIndex = -1;
		float meilleurScore = Float.MAX_VALUE;

		// 3. Parcours des groupes disponibles
		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);

			// On vérifie s'il reste de la place physique dans le groupe
			if (groupe.ajouterEtudiant()) {

				// --- SIMULATION ---
				// On place l'étudiant temporairement pour voir l'impact sur les statistiques
				etu.setGroup(groupe);
				ArrayList<Etudiant> membres = groupe.GetEtudiant();

				// CRITÈRE 1 : PARITÉ GENRE
				// On cherche à minimiser l'écart entre le nombre d'hommes et de femmes
				int nbHommes = 0;
				int nbFemmes = 0;
				for (Etudiant m : membres) {
					if (m.getGenreEtu().equalsIgnoreCase("Homme")) nbHommes++;
					else nbFemmes++; 
				}
				// Plus la différence est petite, meilleur est le score 
				float scoreGenre = Math.abs(nbHommes - nbFemmes);

				// CRITÈRE 2 : DIVERSITÉ DES PARCOURS
				// On compte combien de personnes ont DÉJÀ le même parcours que l'étudiant actuel
				int nbMemeParcours = 0;
				String parcoursActuel = etu.getParcour().getNomParcour();

				for (Etudiant m : membres) {
					if (m.getParcour().getNomParcour().equals(parcoursActuel)) {
						nbMemeParcours++;
					}
				}
				// Chaque personne du même parcours augmente le "malus" (score)
				float scoreParcours = nbMemeParcours;

				// SCORE TOTAL
				float scoreTotal = scoreGenre + scoreParcours;

				if (scoreTotal < meilleurScore) {
					meilleurScore = scoreTotal;
					meilleurIndex = i;
				}

				// --- NETTOYAGE SIMULATION ---
				if (type == 1) {
					etu.unsetGroupTD();
				} else {
					etu.unsetGroupTP();
				}
			}
		}

		// 4. AFFECTATION DÉFINITIVE
		if (meilleurIndex != -1) {
			etu.setGroup(listGroup.get(meilleurIndex));
		} else {
			System.out.println("Attention : Pas de place trouvée pour " + etu.getNomEtu());
		}

		// 5. APPEL RÉCURSIF 
		if (ListEtu.tail() != null) {
			glouton5(ListEtu.tail(), type,Groups);
		}
	}








	/**
	 * CRITÈRES DE L'ALGO GLOUTON 6 :
	 * 1. Équilibre des types de Bac.
	 * 2. Écart-type global.
	 */
	public static void glouton6(List<Etudiant> ListEtu, int type) {
		// 1. Arrêt récursif : Fin de la liste des étudiants
		if (ListEtu == null || ListEtu.data() == null) return;

		// Sélection dynamique de la liste cible (TD ou TP)
		ArrayList<? extends IGroup> listGroup;
		if (type == 1) {
			listGroup = ListGroupTDMain;
		} else {
			listGroup = ListGroupTPMain;
		} 
		Etudiant etu = ListEtu.data();

		// 2. Sécurité : Vérification si l'étudiant est déjà affecté à ce type de groupe
		boolean dejaPlace = (type == 1) ? 
				(etu.getGroup() != null && etu.getGroup().issetGroupTD()) :
					(etu.getGroup() != null && etu.getGroup().issetGroupTP());

		if (dejaPlace) {
			if (ListEtu.tail() != null) glouton6(ListEtu.tail(), type);
			return;
		}

		int meilleurIndex = -1;
		float meilleurScore = Float.MAX_VALUE;

		// 3. Recherche du meilleur groupe disponible
		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);

			// Vérification de la capacité d'accueil
			if (groupe.ajouterEtudiant()) {

				// --- SIMULATION ---
				etu.setGroup(groupe);
				ArrayList<Etudiant> membres = groupe.GetEtudiant();

				// CRITÈRE 1 : MIXITÉ DES BACS
				// On compte combien d'étudiants dans ce groupe ont le même Bac que l'étudiant actuel
				int nbMemeBac = 0;
				if (etu.getBac() != null) {
					String bacActuel = etu.getBac().getNomBac();
					for (Etudiant m : membres) {
						if (m.getBac() != null && m.getBac().getNomBac().equals(bacActuel)) {
							nbMemeBac++;
						}
					}
				}
				float scoreBac = nbMemeBac;

				// CRITÈRE 2 : ÉCART-TYPE (NIVEAU)
				float scoreNotes = groupe.ecarTypeGlobal();

				// SCORE TOTAL
				float scoreTotal = scoreBac + scoreNotes;

				if (scoreTotal < meilleurScore) {
					meilleurScore = scoreTotal;
					meilleurIndex = i;
				}

				// --- NETTOYAGE ---
				if (type == 1) {
					etu.unsetGroupTD();
				} else {
					etu.unsetGroupTP();
				}
			}
		}

		// 4. AFFECTATION DÉFINITIVE
		if (meilleurIndex != -1) {
			etu.setGroup(listGroup.get(meilleurIndex));
		} else {
			System.out.println("Alerte : Pas de place pour " + etu.getNomEtu());
		}

		// 5. RÉCURSION : Passage à l'étudiant suivant
		if (ListEtu.tail() != null) {
			glouton6(ListEtu.tail(), type);
		}
	}
	
	public static void glouton6(List<Etudiant> ListEtu, int type,ArrayList<IGroup> Groups) {
		// 1. Arrêt récursif : Fin de la liste des étudiants
		if (ListEtu == null || ListEtu.data() == null) return;

		// Sélection dynamique de la liste cible (TD ou TP)
		ArrayList<? extends IGroup> listGroup= Groups ;
		Etudiant etu = ListEtu.data();

		// 2. Sécurité : Vérification si l'étudiant est déjà affecté à ce type de groupe
		boolean dejaPlace = (type == 1) ? 
				(etu.getGroup() != null && etu.getGroup().issetGroupTD()) :
					(etu.getGroup() != null && etu.getGroup().issetGroupTP());

		if (dejaPlace) {
			if (ListEtu.tail() != null) glouton6(ListEtu.tail(), type);
			return;
		}

		int meilleurIndex = -1;
		float meilleurScore = Float.MAX_VALUE;

		// 3. Recherche du meilleur groupe disponible
		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);

			// Vérification de la capacité d'accueil
			if (groupe.ajouterEtudiant()) {

				// --- SIMULATION ---
				etu.setGroup(groupe);
				ArrayList<Etudiant> membres = groupe.GetEtudiant();

				// CRITÈRE 1 : MIXITÉ DES BACS
				// On compte combien d'étudiants dans ce groupe ont le même Bac que l'étudiant actuel
				int nbMemeBac = 0;
				if (etu.getBac() != null) {
					String bacActuel = etu.getBac().getNomBac();
					for (Etudiant m : membres) {
						if (m.getBac() != null && m.getBac().getNomBac().equals(bacActuel)) {
							nbMemeBac++;
						}
					}
				}
				float scoreBac = nbMemeBac;

				// CRITÈRE 2 : ÉCART-TYPE (NIVEAU)
				float scoreNotes = groupe.ecarTypeGlobal();

				// SCORE TOTAL
				float scoreTotal = scoreBac + scoreNotes;

				if (scoreTotal < meilleurScore) {
					meilleurScore = scoreTotal;
					meilleurIndex = i;
				}

				// --- NETTOYAGE ---
				if (type == 1) {
					etu.unsetGroupTD();
				} else {
					etu.unsetGroupTP();
				}
			}
		}

		// 4. AFFECTATION DÉFINITIVE
		if (meilleurIndex != -1) {
			etu.setGroup(listGroup.get(meilleurIndex));
		} else {
			System.out.println("Alerte : Pas de place pour " + etu.getNomEtu());
		}

		// 5. RÉCURSION : Passage à l'étudiant suivant
		if (ListEtu.tail() != null) {
			glouton6(ListEtu.tail(), type,Groups);
		}
	}



	/**
	 * CRITÈRES DE L'ALGO GLOUTON 7 :
	 * 1. Équilibre Alternance / Formation Initiale .
	 * 2. Mixité des Bac .
	 */
	public static void glouton7(List<Etudiant> ListEtu, int type) {
		// 1. Arrêt récursif : Fin de la liste des étudiants
		if (ListEtu == null || ListEtu.data() == null) return;

		// Sélection dynamique de la liste cible (TD ou TP)
		ArrayList<? extends IGroup> listGroup;
		if (type == 1) {
			listGroup = ListGroupTDMain;
		} else {
			listGroup = ListGroupTPMain;
		} 
		Etudiant etu = ListEtu.data();

		// 2. Sécurité : Vérification si l'étudiant est déjà affecté à ce type de groupe
		boolean dejaPlace = (type == 1) ? 
				(etu.getGroup() != null && etu.getGroup().issetGroupTD()) :
					(etu.getGroup() != null && etu.getGroup().issetGroupTP());

		if (dejaPlace) {
			if (ListEtu.tail() != null) glouton7(ListEtu.tail(), type);
			return;
		}

		int meilleurIndex = -1;
		float meilleurScore = Float.MAX_VALUE;

		// 3. Recherche du meilleur groupe disponible
		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);

			// Vérification de la place physique (Taille Max du groupe)
			if (groupe.ajouterEtudiant()) {

				// --- SIMULATION ---
				etu.setGroup(groupe);
				ArrayList<Etudiant> membres = groupe.GetEtudiant();

				// CRITÈRE 1 : ÉQUILIBRE ALTERNANTS / INITIAUX
				int nbMemeStatut = 0;
				boolean statutEtu = etu.getApprentissage();

				for (Etudiant m : membres) {
					if (m.getApprentissage() == statutEtu) {
						nbMemeStatut++;
					}
				}
				float scoreAlternance = nbMemeStatut;

				// CRITÈRE 2 : QUOTA DE BAC (Mixité)
				int nbMemeBac = 0;
				if (etu.getBac() != null) {
					String nomBac = etu.getBac().getNomBac();
					for (Etudiant m : membres) {
						if (m.getBac() != null && m.getBac().getNomBac().equals(nomBac)) {
							nbMemeBac++;
						}
					}
				}
				float scoreBac = nbMemeBac;

				// SCORE TOTAL
				float scoreTotal = scoreAlternance + scoreBac;

				if (scoreTotal < meilleurScore) {
					meilleurScore = scoreTotal;
					meilleurIndex = i;
				}

				// --- NETTOYAGE ---
				if (type == 1) {
					etu.unsetGroupTD();
				} else {
					etu.unsetGroupTP();
				}
			}
		}

		// 4. AFFECTATION DÉFINITIVE
		if (meilleurIndex != -1) {
			etu.setGroup(listGroup.get(meilleurIndex));
		} else {
			System.out.println("Info : Groupe complet pour " + etu.getNomEtu());
		}

		// 5. RÉCURSION : Passage à l'étudiant suivant
		if (ListEtu.tail() != null) {
			glouton7(ListEtu.tail(), type);
		}
	}




	public static void glouton7(List<Etudiant> ListEtu, int type,ArrayList<IGroup> Groups) {
		// 1. Arrêt récursif : Fin de la liste des étudiants
		if (ListEtu == null || ListEtu.data() == null) return;

		// Sélection dynamique de la liste cible (TD ou TP)
		ArrayList<? extends IGroup> listGroup = Groups ;
		Etudiant etu = ListEtu.data();

		// 2. Sécurité : Vérification si l'étudiant est déjà affecté à ce type de groupe
		boolean dejaPlace = (type == 1) ? 
				(etu.getGroup() != null && etu.getGroup().issetGroupTD()) :
					(etu.getGroup() != null && etu.getGroup().issetGroupTP());

		if (dejaPlace) {
			if (ListEtu.tail() != null) glouton7(ListEtu.tail(), type);
			return;
		}

		int meilleurIndex = -1;
		float meilleurScore = Float.MAX_VALUE;

		// 3. Recherche du meilleur groupe disponible
		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);

			// Vérification de la place physique (Taille Max du groupe)
			if (groupe.ajouterEtudiant()) {

				// --- SIMULATION ---
				etu.setGroup(groupe);
				ArrayList<Etudiant> membres = groupe.GetEtudiant();

				// CRITÈRE 1 : ÉQUILIBRE ALTERNANTS / INITIAUX
				int nbMemeStatut = 0;
				boolean statutEtu = etu.getApprentissage();

				for (Etudiant m : membres) {
					if (m.getApprentissage() == statutEtu) {
						nbMemeStatut++;
					}
				}
				float scoreAlternance = nbMemeStatut;

				// CRITÈRE 2 : QUOTA DE BAC (Mixité)
				int nbMemeBac = 0;
				if (etu.getBac() != null) {
					String nomBac = etu.getBac().getNomBac();
					for (Etudiant m : membres) {
						if (m.getBac() != null && m.getBac().getNomBac().equals(nomBac)) {
							nbMemeBac++;
						}
					}
				}
				float scoreBac = nbMemeBac;

				// SCORE TOTAL
				float scoreTotal = scoreAlternance + scoreBac;

				if (scoreTotal < meilleurScore) {
					meilleurScore = scoreTotal;
					meilleurIndex = i;
				}

				// --- NETTOYAGE ---
				if (type == 1) {
					etu.unsetGroupTD();
				} else {
					etu.unsetGroupTP();
				}
			}
		}

		// 4. AFFECTATION DÉFINITIVE
		if (meilleurIndex != -1) {
			etu.setGroup(listGroup.get(meilleurIndex));
		} else {
			System.out.println("Info : Groupe complet pour " + etu.getNomEtu());
		}

		// 5. RÉCURSION : Passage à l'étudiant suivant
		if (ListEtu.tail() != null) {
			glouton7(ListEtu.tail(), type,Groups);
		}
	}



	/**
	 * CRITÈRES DE L'ALGO GLOUTON 8 :
	 * 1. Covoiturage.
	 * 2. Équilibre des genres .
	 */
	public static void glouton8(List<Etudiant> ListEtu, int type) {
		// 1. Arrêt récursif
		if (ListEtu == null || ListEtu.data() == null) return;

		// Sélection dynamique du type de groupe (TD ou TP)
		ArrayList<? extends IGroup> listGroup;
		if (type == 1) {
			listGroup = ListGroupTDMain;
		} else {
			listGroup = ListGroupTPMain;
		} 

		Etudiant etuActuel = ListEtu.data();

		// 2. Sécurité : Vérifie si l'étudiant a déjà été placé 
		boolean dejaPlace = (type == 1) ? 
				(etuActuel.getGroup() != null && etuActuel.getGroup().issetGroupTD()) :
					(etuActuel.getGroup() != null && etuActuel.getGroup().issetGroupTP());

		if (dejaPlace) {
			if (ListEtu.tail() != null) glouton8(ListEtu.tail(), type);
			return;
		}

		// 3. RECUPERATION DES COVOITURAGE (this + Partenaires )
		ArrayList<Etudiant> groupeC = etuActuel.getPartenairesCovoit();
		if (!groupeC.contains(etuActuel)) {
			groupeC.add(etuActuel); 
		}

		int meilleurIndex = -1;
		float meilleurScore = Float.MAX_VALUE;

		// 4. RECHERCHE DU MEILLEUR GROUPE 
		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);


			if (groupe.GetnbrEtudiant() + groupeC.size() <= groupe.getTailleMax()) {

				// --- SIMULATION ---
				for (Etudiant e : groupeC) e.setGroup(groupe);

				ArrayList<Etudiant> membres = groupe.GetEtudiant();
				int nbH = 0, nbF = 0;
				for (Etudiant m : membres) {
					if (m.getGenreEtu().equalsIgnoreCase("Homme")) nbH++;
					else nbF++;
				}

				// CRITÈRE 1 : Écart de parité 
				float scoreParite = Math.abs(nbH - nbF);

				// CRITÈRE 2 : Équilibre du remplissage
				float scoreRemplissage = groupe.GetnbrEtudiant() * 0.2f;

				float scoreTotal = scoreParite + scoreRemplissage;

				if (scoreTotal < meilleurScore) {
					meilleurScore = scoreTotal;
					meilleurIndex = i;
				}

				// --- NETTOYAGE ---
				for (Etudiant e : groupeC) {
					if (type == 1) {
						e.unsetGroupTD();
					} else {
						e.unsetGroupTP();
					}
				}
			}
		}

		// 5. AFFECTATION FINALE 
		if (meilleurIndex != -1) {
			IGroup gSelectionne = listGroup.get(meilleurIndex);
			for (Etudiant e : groupeC) {
				e.setGroup(gSelectionne);
			}
		} else {
			System.out.println("Alerte : Impossible de placer le bloc de " + etuActuel.getNomEtu());
		}

		// 6. RÉCURSION : Passage à l'étudiant suivant
		if (ListEtu.tail() != null) {
			glouton8(ListEtu.tail(), type);
		}
	}

	
	
	
	
	
	
	public static void glouton8(List<Etudiant> ListEtu, int type,ArrayList<IGroup> Groups) {
		// 1. Arrêt récursif
		if (ListEtu == null || ListEtu.data() == null) return;

		// Sélection dynamique du type de groupe (TD ou TP)
		ArrayList<? extends IGroup> listGroup;
		if (type == 1) {
			listGroup = ListGroupTDMain;
		} else {
			listGroup = ListGroupTPMain;
		} 

		Etudiant etuActuel = ListEtu.data();

		// 2. Sécurité : Vérifie si l'étudiant a déjà été placé 
		boolean dejaPlace = (type == 1) ? 
				(etuActuel.getGroup() != null && etuActuel.getGroup().issetGroupTD()) :
					(etuActuel.getGroup() != null && etuActuel.getGroup().issetGroupTP());

		if (dejaPlace) {
			if (ListEtu.tail() != null) glouton8(ListEtu.tail(), type);
			return;
		}

		// 3. RECUPERATION DES COVOITURAGE (this + Partenaires )
		ArrayList<Etudiant> groupeC = etuActuel.getPartenairesCovoit();
		if (!groupeC.contains(etuActuel)) {
			groupeC.add(etuActuel); 
		}

		int meilleurIndex = -1;
		float meilleurScore = Float.MAX_VALUE;

		// 4. RECHERCHE DU MEILLEUR GROUPE 
		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);


			if (groupe.GetnbrEtudiant() + groupeC.size() <= groupe.getTailleMax()) {

				// --- SIMULATION ---
				for (Etudiant e : groupeC) e.setGroup(groupe);

				ArrayList<Etudiant> membres = groupe.GetEtudiant();
				int nbH = 0, nbF = 0;
				for (Etudiant m : membres) {
					if (m.getGenreEtu().equalsIgnoreCase("Homme")) nbH++;
					else nbF++;
				}

				// CRITÈRE 1 : Écart de parité 
				float scoreParite = Math.abs(nbH - nbF);

				// CRITÈRE 2 : Équilibre du remplissage
				float scoreRemplissage = groupe.GetnbrEtudiant() * 0.2f;

				float scoreTotal = scoreParite + scoreRemplissage;

				if (scoreTotal < meilleurScore) {
					meilleurScore = scoreTotal;
					meilleurIndex = i;
				}

				// --- NETTOYAGE ---
				for (Etudiant e : groupeC) {
					if (type == 1) {
						e.unsetGroupTD();
					} else {
						e.unsetGroupTP();
					}
				}
			}
		}

		// 5. AFFECTATION FINALE 
		if (meilleurIndex != -1) {
			IGroup gSelectionne = listGroup.get(meilleurIndex);
			for (Etudiant e : groupeC) {
				e.setGroup(gSelectionne);
			}
		} else {
			System.out.println("Alerte : Impossible de placer le bloc de " + etuActuel.getNomEtu());
		}

		// 6. RÉCURSION : Passage à l'étudiant suivant
		if (ListEtu.tail() != null) {
			glouton8(ListEtu.tail(), type,Groups);
		}
	}	
	
	

	static void appliquerMeilleureConfig(List<Etudiant> PromoComplete, int type) {
		ArrayList<? extends IGroup> listGroup ;
		if(type == 1) {listGroup = ListGroupTDMain ;}else {listGroup = ListGroupTPMain;}

		List<Etudiant> curr = PromoComplete;
		int i = 0;

		while (curr != null && curr.data() != null) {
			int indexGroupeGagnant = meilleureConfig[i];
			IGroup groupe = listGroup.get(indexGroupeGagnant);

			curr.data().setGroup(groupe); // On affecte pour de bon

			curr = curr.tail();
			i++;
		}
		System.out.println("La meilleure configuration a été appliquée avec succès !");
	}
	public static void resetBF(){
		meilleurScoreGlobal = Float.MAX_VALUE;
		//Arrays.fill(meilleureConfig, -1);
		//Arrays.fill(configActuelle, -1);
	}
	static void Affichage() {
		for(int i = 0 ;i< (mainAlgo.ListGroupTDMain).size();i++) {
			ListGroupTDMain.get(i).display();
		}
		for(int i = 0 ;i< (mainAlgo.ListGroupTPMain).size();i++) {
			ListGroupTPMain.get(i).display();
		}

	}

	public static void gloutonCustom(List<Etudiant> ListEtu, int type, ArrayList<Number> listCritere, ArrayList<Number> listCoef,ArrayList<IGroup> ListGroup) {
	    if (ListEtu == null || ListEtu.data() == null) return;

	    ArrayList<? extends IGroup> listGroup = ListGroup;
	    Etudiant etu = ListEtu.data();

	    boolean dejaPlace = (type == 1) ? 
	            (etu.getGroup() != null && etu.getGroup().issetGroupTD()) :
	            (etu.getGroup() != null && etu.getGroup().issetGroupTP());

	    if (dejaPlace) {
	        if (ListEtu.tail() != null) gloutonCustom(ListEtu.tail(), type, listCritere, listCoef,ListGroup);
	        return;
	    }

	    int meilleurIndex = -1;
	    float meilleurScoreTotal = Float.MAX_VALUE;
	    ArrayList<Etudiant> bloc = new ArrayList<>();
	    for (int i = 0; i < listGroup.size(); i++) {
	        IGroup groupe = listGroup.get(i);
	        
	        // On vérifie la place (cas spécial : si Covoit activé, on vérifie la place pour le bloc)
	        int nbAInscrire = 1;
	        bloc = new ArrayList<>();
	        bloc.add(etu);
	        
	        if (listCritere.contains(2)) { // Si critère Covoiturage (index 2) est présent
	            for (Etudiant p : etu.getPartenairesCovoit()) {
	                if (!bloc.contains(p)) bloc.add(p);
	            }
	            nbAInscrire = bloc.size();
	        }

	        if (groupe.GetnbrEtudiant() + nbAInscrire <= groupe.getTailleMax()) {
	            // Simulation
	            for(Etudiant e : bloc) e.setGroup(groupe);
	            
	            float scoreSimulation = 0;

	            // Parcours des critères choisis par l'utilisateur
	            for (int j = 0; j < listCritere.size(); j++) {
	                int critereId = listCritere.get(j).intValue();
	                float coef = listCoef.get(j).floatValue();

	                switch (critereId) {
	                    case 0: // MIXITÉ BAC
	                        int memeBac = 0;
	                        for (Etudiant m : groupe.GetEtudiant()) {
	                            if (m.getBac().getNomBac().equals(etu.getBac().getNomBac())) memeBac++;
	                        }
	                        scoreSimulation += (memeBac - 1) * coef;
	                        break;

	                    case 1: // PARITÉ GENRE
	                        int nbH = 0, nbF = 0;
	                        for (Etudiant m : groupe.GetEtudiant()) {
	                            if (m.getGenreEtu().equalsIgnoreCase("Homme")) nbH++; else nbF++;
	                        }
	                        scoreSimulation += Math.abs(nbH - nbF) * coef;
	                        break;

	                    case 2: // COVOITURAGE (Ici on favorise le remplissage équilibré si activé)
	                        scoreSimulation += (groupe.GetnbrEtudiant() * 0.1f) * coef;
	                        break;

	                    case 3: // ÉQUILIBRE NOTES 
	                        scoreSimulation += groupe.ecarTypeGlobal() * coef;
	                        break;

	                    case 4: // ALTERNANCE
	                        int memeStatut = 0;
	                        for (Etudiant m : groupe.GetEtudiant()) {
	                            if (m.getApprentissage() == etu.getApprentissage()) memeStatut++;
	                        }
	                        scoreSimulation += (memeStatut - 1) * coef;
	                        break;
	                }
	            }

	            if (scoreSimulation < meilleurScoreTotal) {
	                meilleurScoreTotal = scoreSimulation;
	                meilleurIndex = i;
	            }

	            // Nettoyage simulation
	            for(Etudiant e : bloc) {
	                if (type == 1) e.unsetGroupTD(); else e.unsetGroupTP();
	            }
	        }
	    }

	    // Affectation finale
	    if (meilleurIndex != -1) {
	        for(Etudiant e : bloc) e.setGroup(listGroup.get(meilleurIndex));
	    }

	    // Récursion
	    if (ListEtu.tail() != null) gloutonCustom(ListEtu.tail(), type, listCritere, listCoef,ListGroup);
	}
	
	
	
	























	static float bruteForce1(List<Etudiant> Promo, int indexEtu, int type) {
		// --- CAS DE BASE ---
		if (Promo == null || Promo.data() == null) {
			float score = calculScoreGlobalPromo1(type);

			if (score < meilleurScoreGlobal) {
				meilleurScoreGlobal = score;
				System.arraycopy(configActuelle, 0, meilleureConfig, 0, configActuelle.length);
			}
			return score;
		}

		ArrayList<? extends IGroup> listGroup ;
		if(type == 1) listGroup = ListGroupTDMain ;else listGroup = ListGroupTPMain;
		Etudiant etuActuel = Promo.data();
		float meilleurScoreIci = Float.MAX_VALUE;

		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);

			if (groupe.ajouterEtudiant()) {
				
				etuActuel.setGroup(groupe);
				configActuelle[indexEtu] = i; 

				// Récursion 
				float scoreRetourne = bruteForce1(Promo.tail(), indexEtu + 1, type);

				if (scoreRetourne < meilleurScoreIci) {//verification du meilleur chemin
					meilleurScoreIci = scoreRetourne;
				}

				// Nettoyage
				if (type == 1) etuActuel.unsetGroupTD();
				else etuActuel.unsetGroupTP();
			}
		}
		return meilleurScoreIci;
	}





























	/**
	 * Fonction qui calcule le score TOTAL de la promo
	 */
	static float calculScoreGlobalPromo1(int type) {
		ArrayList<? extends IGroup> listGroup;
		if (type == 1) listGroup = ListGroupTDMain;
		else listGroup = ListGroupTPMain;
		float scoreTotalPromo = 0;

		for (IGroup groupe : listGroup) {
			// Écart-type
			float ecarType = groupe.ecarTypeGlobal();

			// Mixité Bac
			int nbMemeBac = 0;
			ArrayList<Etudiant> membres = groupe.GetEtudiant();
			for (int i = 0; i < membres.size(); i++) {
				for (int j = i + 1; j < membres.size(); j++) {
					Etudiant e1 = membres.get(i);
					Etudiant e2 = membres.get(j);
					if (e1.getBac() != null && e2.getBac() != null) {
						if (e1.getBac().getNomBac().equals(e2.getBac().getNomBac())) {
							nbMemeBac++;
						}
					}
				}
			}

			
			int nbEtudiants = groupe.GetnbrEtudiant();

			// Ajout au score global
			scoreTotalPromo += ecarType + nbMemeBac + nbEtudiants;
		}

		return scoreTotalPromo;
	}

	////////////////////////////////////////////

	static float bruteForce2(List<Etudiant> listeEtu, int indexEtu, int type) {
		// CAS DE BASE
		if (listeEtu == null || listeEtu.data() == null) {
			// On calcule le score de toute la promo
			float scoreFinal = calculScoreGlobalPromo2(type);

			// Si c'est un meilleur score , on le sauvergarde
			if (scoreFinal < meilleurScoreGlobal) {
				meilleurScoreGlobal = scoreFinal;
				System.arraycopy(configActuelle, 0, meilleureConfig, 0, configActuelle.length);// = une boucle for de remplissage
			}
			return scoreFinal;
		}

		ArrayList<? extends IGroup> listGroup = (type == 1) ? ListGroupTDMain : ListGroupTPMain;
		Etudiant etuActuel = listeEtu.data();
		float meilleurScoreIci = Float.MAX_VALUE;

		// boucle tout le chemins
		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);

			if (groupe.ajouterEtudiant()) {
				etuActuel.setGroup(groupe);
				configActuelle[indexEtu] = i;

				// RECURSION
				float scoreApres = bruteForce2(listeEtu.tail(), indexEtu + 1, type);

				if (scoreApres < meilleurScoreIci) {
					meilleurScoreIci = scoreApres;
				}

				// On retire l'étudiant pour libérer la place 
				if (type == 1) etuActuel.unsetGroupTD();
				else etuActuel.unsetGroupTP();
			}
		}
		return meilleurScoreIci;
	}




	static float calculScoreGlobalPromo2(int type) {
		ArrayList<? extends IGroup> listGroup = (type == 1) ? ListGroupTDMain : ListGroupTPMain;
		float scoreTotalPromo = 0;

		for (IGroup groupe : listGroup) {
			ArrayList<Etudiant> membres = groupe.GetEtudiant();

			// Si le groupe est vide, on passe au suivant
			if (membres.isEmpty()) continue;

			// 1. ÉCART-TYPE
			float scoreNiveau = groupe.ecarTypeGlobal();

			// 2. MIXITÉ DES BACS 
			// On compte combien d'étudiants par type de Bac
			int nbGeneral = 0, nbTechno = 0, nbPro = 0;
			for (Etudiant e : membres) {
				if (e.getBac() != null) {
					String nomBac = e.getBac().getNomBac().toLowerCase();
					if (nomBac.contains("general")) nbGeneral++;
					else if (nomBac.contains("techno")) nbTechno++;
					else if (nomBac.contains("pro")) nbPro++;
				}
			}
			// On utilise le carré pour punir les gros déséquilibres ,il existe peut etre une maniere de le faire autrement mais c est la seule que j ai trouver
			float scoreBac = (nbGeneral * nbGeneral + nbTechno * nbTechno + nbPro * nbPro);

			//3. PARITÉ Genres
			int hommes = 0, femmes = 0;
			for (Etudiant e : membres) {
				if (e.getGenreEtu().equalsIgnoreCase("Homme")) hommes++;
				else femmes++;
			}
			// On veut que la différence soit proche de 0
			float scoreParite = Math.abs(hommes - femmes);

			//4. COVOITURAGE
			// On vérifie si les partenaires de covoiturage sont bien ensemble
			int covoitSepare = 0;
			for (Etudiant e : membres) {
				for (Etudiant partenaire : e.getPartenairesCovoit()) {
					if (!membres.contains(partenaire)) {
						covoitSepare++;
					}
				}
			}
			float scoreCovoit = covoitSepare; 


			scoreTotalPromo += scoreNiveau + scoreBac + scoreParite + scoreCovoit;
		}

		return scoreTotalPromo;
	}


	//////////////////////////////////////////////

	static float bruteForce3(List<Etudiant> listeEtu, int indexEtu, int type) {
		// CAS DE BASE
		if (listeEtu == null || listeEtu.data() == null) {
			// On calcule le score de toute la promo une fois le placement fini
			float scoreFinal = calculScoreGlobalPromo3(type);

			// Si c'est un nouveau record
			if (scoreFinal < meilleurScoreGlobal) {
				meilleurScoreGlobal = scoreFinal;
				System.arraycopy(configActuelle, 0, meilleureConfig, 0, configActuelle.length);
			}
			return scoreFinal;
		}

		ArrayList<? extends IGroup> listGroup = (type == 1) ? ListGroupTDMain : ListGroupTPMain;
		Etudiant etuActuel = listeEtu.data();
		float meilleurScoreIci = Float.MAX_VALUE;

		//boucle
		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);

			if (groupe.ajouterEtudiant()) {
				//On place l'étudiant pour le test
				etuActuel.setGroup(groupe);
				configActuelle[indexEtu] = i;

				// RECURSION
				float scoreApres = bruteForce3(listeEtu.tail(), indexEtu + 1, type);

				if (scoreApres < meilleurScoreIci) {
					meilleurScoreIci = scoreApres;
				}

				// On retire l'étudiant 
				if (type == 1) etuActuel.unsetGroupTD();
				else etuActuel.unsetGroupTP();
			}
		}
		return meilleurScoreIci;
	}





	static float calculScoreGlobalPromo3(int type) {
		ArrayList<? extends IGroup> listGroup = (type == 1) ? ListGroupTDMain : ListGroupTPMain;
		float scoreTotalPromo = 0;

		for (IGroup groupe : listGroup) {
			ArrayList<Etudiant> membres = groupe.GetEtudiant();
			if (membres.isEmpty()) continue;

			// 1. Écart par rapport à la moyenne(10/20)
			float moyG = groupe.moyenneGeneraleGroupe();
			float scoreMoyenne = Math.abs(10 - moyG);

			// 2. Mixité des parcours 
			int nbDev = 0, nbReseau = 0, nbDB = 0;
			for (Etudiant e : membres) {
				String p = e.getParcour().getNomParcour().toLowerCase();
				if (p.contains("dev")) nbDev++;
				else if (p.contains("reseau")) nbReseau++;
				else if (p.contains("db")) nbDB++;
			}
			float scoreParcours = (nbDev * nbDev + nbReseau * nbReseau + nbDB * nbDB);

			// 3. Écart-type 
			float scoreEcartType = groupe.ecarTypeGlobal();

			scoreTotalPromo += scoreMoyenne + scoreParcours + scoreEcartType;
		}
		return scoreTotalPromo;
	}

	/////////////////////////////////////////////////////////////////////////////
	static float bruteForce4(List<Etudiant> listeEtu, int indexEtu, int type) {
		// CAS DE BASE 
		if (listeEtu == null || listeEtu.data() == null) {
			float scoreFinal = calculScoreGlobalPromo4(type);

			// Si c'est un nouveau record
			if (scoreFinal < meilleurScoreGlobal) {
				meilleurScoreGlobal = scoreFinal;
				System.arraycopy(configActuelle, 0, meilleureConfig, 0, configActuelle.length);
			}
			return scoreFinal;
		}

		ArrayList<? extends IGroup> listGroup = (type == 1) ? ListGroupTDMain : ListGroupTPMain;
		Etudiant etuActuel = listeEtu.data();
		float meilleurScoreIci = Float.MAX_VALUE;

		// boucle 
		for (int i = 0; i < listGroup.size(); i++) {
			IGroup groupe = listGroup.get(i);

			if (groupe.ajouterEtudiant()) {
			
				etuActuel.setGroup(groupe);
				configActuelle[indexEtu] = i;

				// RECURSION
				float scoreApres = bruteForce4(listeEtu.tail(), indexEtu + 1, type);

				if (scoreApres < meilleurScoreIci) {
					meilleurScoreIci = scoreApres;
				}

				// 3. On retire l'étudiant
				if (type == 1) etuActuel.unsetGroupTD();
				else etuActuel.unsetGroupTP();
			}
		}
		return meilleurScoreIci;
	}





	static float calculScoreGlobalPromo4(int type) {
		ArrayList<? extends IGroup> listGroup = (type == 1) ? ListGroupTDMain : ListGroupTPMain;
		float scoreTotalPromo = 0;

		for (IGroup groupe : listGroup) {
			ArrayList<Etudiant> membres = groupe.GetEtudiant();
			if (membres.isEmpty()) continue;

			// 1. Équilibre Alternants / Initiaux 
			int nbAlt = 0;
			for (Etudiant e : membres) {
				if (e.getApprentissage()) nbAlt++;
			}
			float scoreAlternance = Math.abs(4 - nbAlt);

			// 2. Équilibre des 3 types de Bac (Pro, General, Technologique)
			int nbGeneral = 0, nbTechno = 0, nbPro = 0;
			for (Etudiant e : membres) {
				if (e.getBac() != null) {
					String nomBac = e.getBac().getNomBac().toLowerCase();
					if (nomBac.contains("general")) nbGeneral++;
					else if (nomBac.contains("techno")) nbTechno++;
					else if (nomBac.contains("pro")) nbPro++;
				}
			}
			// Pénalité : plus un Bac est dominant, plus le score (malus) augmente
			float scoreBac = (nbGeneral * nbGeneral + nbTechno * nbTechno + nbPro * nbPro);

			// 3. Remplissage du groupe
			float scoreRemplissage = Math.abs(groupe.getTailleMax() - membres.size());

			// Score total pour ce groupe
			scoreTotalPromo += scoreAlternance + scoreBac + scoreRemplissage;
		}
		return scoreTotalPromo;
	}










	public static void main(String[] args) {

		Bac general = new Bac(0, "general") ;
		Bac techno = new Bac(1, "technologique") ;
		Bac pro = new Bac(2, "pro") ;

		Semestre semestre2025 = new Semestre(0," semestre2025");
		Promotion Promotion2025 = new Promotion(0, semestre2025, 10);

		Parcour A = new Parcour(1, "dev");
		Parcour B = new Parcour(2, "reseau");
		Parcour C = new Parcour(3, "DB");




		Matiere maths = new Matiere(0 , "maths");
		Matiere anglais = new Matiere(1 , "anglais");
		Matiere physique = new Matiere(2 , "physique");
		Matiere Francais = new Matiere(3 , "Francais");
		Matiere DevelopementWeb = new Matiere(5 , "Developement web");
		Matiere Cplus = new Matiere(6 , "C");
		Matiere Csharp = new Matiere(7 , "C#");
		Matiere Analyse = new Matiere(8 , "Analyse");
		Matiere coo = new Matiere(9 , "conception orienter objet");





		//////////// ALGORITHMES 1
		System.out.println("\n" + "=".repeat(70));
		System.out.println("/////////// ALGORITHMES Aurlien");
		System.out.println("=".repeat(70));

		// Algo Glouton 1
		CreationGroup(semestre2025, general, techno, pro, Promotion2025, A, B, C, 20, 20, 120);
		System.out.println("\n/// ALGO GLOUTON 1");
		System.out.println("// Critères:");
		System.out.println("// 1. Écart-type des moyennes");
		System.out.println("// 2. Mixité des Bac");
		System.out.println("// 3. Équilibre du remplissage");
		glouton1(Etudiant.ListEtu, 1);
		System.out.println("// Résultat ");
		Affichage();
		clearEtudiant();
		ListGroupTDMain.clear();
		ListGroupTPMain.clear();

		System.out.println("\n" + "=".repeat(50));
		// Algo Glouton 2
		CreationGroup(semestre2025, general, techno, pro, Promotion2025, A, B, C, 20, 20, 120);
		System.out.println("\n/// ALGO GLOUTON 2");
		System.out.println("// Critères:");
		System.out.println("// 1. Parité Homme/Femme");
		System.out.println("// 2. parcours");
		System.out.println("// 3. Écart-type");
		glouton2(Etudiant.ListEtu, 1);
		System.out.println("// Résultat ");
		Affichage();
		clearEtudiant();
		ListGroupTDMain.clear();
		ListGroupTPMain.clear();

		System.out.println("\n" + "=".repeat(50));
		// Algo Brute Force 1
		CreationGroup(semestre2025, general, techno, pro, Promotion2025, A, B, C, 3, 3, 8);
		System.out.println("\n/// ALGO BRUTE FORCE 1");
		System.out.println("// Critères:");
		System.out.println("// 1. Écart-type global");
		System.out.println("// 2. Mixité des Bac");
		System.out.println("// 3. Remplissage des groupes");
		meilleureConfig = new int[11];
		configActuelle = new int[11];
		meilleurScoreGlobal = Float.MAX_VALUE;
		bruteForce1(Etudiant.ListEtu, 0, 1);
		appliquerMeilleureConfig(Etudiant.ListEtu, 1);
		System.out.println("// Résultat ");
		Affichage();
		clearEtudiant();
		ListGroupTDMain.clear();
		ListGroupTPMain.clear();
		resetBF();

		//////////// ALGORITHMES ÉTUDIANTS 2
		System.out.println("\n" + "=".repeat(70));
		System.out.println("/////////// ALGORITHMES Yussuf");
		System.out.println("=".repeat(70));

		// Algo Glouton 3
		CreationGroup(semestre2025, general, techno, pro, Promotion2025, A, B, C, 20, 20, 120);
		System.out.println("\n/// ALGO GLOUTON 3");
		System.out.println("// Critères:");
		System.out.println("// 1. Gestion du Covoiturage");
		System.out.println("// 2. Diversité des parcours");
		System.out.println("// 3. Écart-type global");
		glouton3(Etudiant.ListEtu, 1);
		System.out.println("// Résultat ");
		Affichage();
		clearEtudiant();
		ListGroupTDMain.clear();
		ListGroupTPMain.clear();

		System.out.println("\n" + "=".repeat(50));
		// Algo Glouton 4
		CreationGroup(semestre2025, general, techno, pro, Promotion2025, A, B, C, 20, 20, 120);
		System.out.println("\n/// ALGO GLOUTON 4");
		System.out.println("// Critères:");
		System.out.println("// 1.Parité Homme/Femme");
		System.out.println("// 2. Écart-type");
		glouton4(Etudiant.ListEtu, 1);
		System.out.println("// Résultat ");
		Affichage();
		clearEtudiant();
		ListGroupTDMain.clear();
		ListGroupTPMain.clear();

		System.out.println("\n" + "=".repeat(50));
		// Algo Brute Force 2
		CreationGroup(semestre2025, general, techno, pro, Promotion2025, A, B, C, 3, 3, 8);
		System.out.println("\n/// ALGO BRUTE FORCE 2");
		System.out.println("// Critères:");
		System.out.println("// 1. Écart-type ");
		System.out.println("// 2. Mixité des Bac");
		System.out.println("// 3. Parité des Genres");
		System.out.println("// 4. Covoiturage");
		meilleureConfig = new int[11];
		configActuelle = new int[11];
		meilleurScoreGlobal = Float.MAX_VALUE;
		bruteForce2(Etudiant.ListEtu, 0, 1);
		appliquerMeilleureConfig(Etudiant.ListEtu, 1);
		System.out.println("// Résultat ");
		Affichage();
		clearEtudiant();
		ListGroupTDMain.clear();
		ListGroupTPMain.clear();
		resetBF();

		//////////// ALGORITHMES ÉTUDIANTS 3
		System.out.println("\n" + "=".repeat(70));
		System.out.println("/////////// ALGORITHMES Leo");
		System.out.println("=".repeat(70));

		// Algo Glouton 5
		CreationGroup(semestre2025, general, techno, pro, Promotion2025, A, B, C, 20, 20, 120);
		System.out.println("\n/// ALGO GLOUTON 5");
		System.out.println("// Critères:");
		System.out.println("// 1. Diversité des bac");
		System.out.println("// 2. Parité Homme/Femme");
		glouton5(Etudiant.ListEtu, 1);
		System.out.println("// Résultat ");
		Affichage();
		clearEtudiant();
		ListGroupTDMain.clear();
		ListGroupTPMain.clear();

		System.out.println("\n" + "=".repeat(50));
		// Algo Glouton 6
		CreationGroup(semestre2025, general, techno, pro, Promotion2025, A, B, C, 20, 20, 120);
		System.out.println("\n/// ALGO GLOUTON 6");
		System.out.println("// Critères:");
		System.out.println("// 1. Équilibre des types de Bac");
		System.out.println("// 2. Écart-type ");
		glouton6(Etudiant.ListEtu, 1);
		System.out.println("// Résultat ");
		Affichage();
		clearEtudiant();
		ListGroupTDMain.clear();
		ListGroupTPMain.clear();

		System.out.println("\n" + "=".repeat(50));
		// Algo Brute Force 3
		CreationGroup(semestre2025, general, techno, pro, Promotion2025, A, B, C, 3, 3, 8);
		System.out.println("\n/// ALGO BRUTE FORCE 3");
		System.out.println("// Critères:");
		System.out.println("// 1. Moyenne ");
		System.out.println("// 2. equilibre des parcours");
		System.out.println("// 3. Écart-type");
		meilleureConfig = new int[11];
		configActuelle = new int[11];
		meilleurScoreGlobal = Float.MAX_VALUE;
		bruteForce3(Etudiant.ListEtu, 0, 1);
		appliquerMeilleureConfig(Etudiant.ListEtu, 1);
		System.out.println("// Résultat ");
		Affichage();
		clearEtudiant();
		ListGroupTDMain.clear();
		ListGroupTPMain.clear();
		resetBF();

		//////////// ALGORITHMES ÉTUDIANTS 4
		System.out.println("\n" + "=".repeat(70));
		System.out.println("/////////// ALGORITHMES Mattew");
		System.out.println("=".repeat(70));

		// Algo Glouton 7
		CreationGroup(semestre2025, general, techno, pro, Promotion2025, A, B, C, 20, 20, 120);
		System.out.println("\n/// ALGO GLOUTON 7");
		System.out.println("// Critères:");
		System.out.println("// 1. Équilibre Alternance / Formation Initiale");
		System.out.println("// 2. Mixité des Bac");
		glouton7(Etudiant.ListEtu, 1);
		System.out.println("// Résultat ");
		Affichage();
		clearEtudiant();
		ListGroupTDMain.clear();
		ListGroupTPMain.clear();

		System.out.println("\n" + "=".repeat(50));
		// Algo Glouton 8
		CreationGroup(semestre2025, general, techno, pro, Promotion2025, A, B, C, 20, 20, 120);
		System.out.println("\n/// ALGO GLOUTON 8");
		System.out.println("// Critères:");
		System.out.println("// 1. Covoiturage");
		System.out.println("// 2. Équilibre des genres");
		glouton8(Etudiant.ListEtu, 1);
		System.out.println("// Résultat ");
		Affichage();
		clearEtudiant();
		ListGroupTDMain.clear();
		ListGroupTPMain.clear();

		System.out.println("\n" + "=".repeat(50));
		// Algo Brute Force 4
		CreationGroup(semestre2025, general, techno, pro, Promotion2025, A, B, C, 3, 3, 8);
		System.out.println("\n/// ALGO BRUTE FORCE 4");
		System.out.println("// Critères:");
		System.out.println("// 1. Équilibre Alternants/Initiaux");
		System.out.println("// 2. Équilibre des 3 types de Bac");
		System.out.println("// 3. Remplissage du groupe");
		meilleureConfig = new int[11];
		configActuelle = new int[11];
		meilleurScoreGlobal = Float.MAX_VALUE;
		bruteForce4(Etudiant.ListEtu, 0, 1);
		appliquerMeilleureConfig(Etudiant.ListEtu, 1);
		System.out.println("// Résultat ");
		Affichage();

		System.out.println("\n" + "=".repeat(70));
		System.out.println("FIN");
		System.out.println("=".repeat(70));


	}





}
