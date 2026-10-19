# Modes de décision pour les algorithmes gloutons

Ce document décrit, pour chaque algorithme glouton du projet, **les contraintes**, **les critères**, une **la formule de score **, le **comportement attendu**, les **limites** et des **notes d’implémentation**. Le format est conçu pour qu’un camarade puisse implémenter les mêmes classes et algorithmes.

---

## Glouton 1

**Contraintes**  
- **Capacité de groupe** : ne pas dépasser `tailleMax`.  
- **Placement unique** : un étudiant ne peut être que dans un seul groupe du type (TD ou TP).  
- **Pré‑placements** : ne pas modifier les étudiants déjà affectés.

**Critères**  
- **Écart‑type des moyennes** du groupe (minimiser).  
- **Homogénéité de Bac** (minimiser le nombre d’étudiants du même Bac).  
- **Remplissage** (favoriser les groupes moins remplis).

**Formule de score**  
score = ecarType + (nbMemeBac - 1) + (nbEtudiants * 0.5)

**Interprétation** : simuler l’ajout de l’étudiant, calculer `score`, enleve l’étudiant, choisir le groupe minimisant `score`.

**Comportement attendu**  
- Rapide et déterministe.  
- Sensible à l’ordre d’itération des étudiants.

**Limites**  
- décisions locales sans vision globale.  
- Peut créer déséquilibres si les poids sont mal réglés.

---

# Glouton 2

**Contraintes**  
- **Capacité de groupe** : respecter la limite maximale.  
- **Placement unique** : un étudiant ne peut être affecté qu’une seule fois.  
- **Pré‑placements** : ne pas déplacer les étudiants déjà assignés.

**Critères**  
- **Parité de genre** : minimiser la sur‑représentation d’un genre.  
- **Diversité des parcours** : éviter de regrouper trop d’étudiants du même parcours.  
- **Écart‑type des notes** : maintenir un niveau scolaire homogène.

**Formule de score**  
score = (memeGenre - 1) + (memeParcours - 1) + ecarType
**Interprétation**  
Simuler l’ajout, mesurer l’impact sur la parité, la diversité des parcours et l’écart‑type, puis choisir le groupe minimisant ce score.

**Comportement attendu**  
- Favorise la mixité (genre + parcours).  
- Maintient un certain équilibre de niveau.

**Limites**  
- Peut entrer en conflit avec l’équilibre scolaire si la population est très déséquilibrée.  
- Toujours basé sur des décisions locales.

---

# Glouton 3

**Contraintes**  
- **Bloc covoiturage** : tous les partenaires doivent être placés ensemble si possible.  
- **Capacité** : le groupe doit avoir assez de places pour accueillir tout le bloc.  
- **Placement unique** : un étudiant ne peut être affecté qu’une fois.

**Critères**  
- **Écart‑type global** après insertion du bloc.  
- **Diversité des parcours** : minimiser le nombre d’étudiants du même parcours.  
- **Intégration du bloc** : éviter de dégrader la cohérence du groupe.

**Formule de score**  
score = ecarType + nbMemeParcours

**Interprétation**  
Tester chaque groupe capable d’accueillir le bloc, simuler l’ajout du bloc complet, calculer le score, annuler la simulation, puis choisir le meilleur groupe.

**Comportement attendu**  
- Respect strict du covoiturage.  
- Place les blocs de manière cohérente.

**Limites**  
- Si un bloc est trop grand, aucun groupe ne peut l’accueillir.  
- Simulation plus coûteuse que les autres gloutons.

---

# Glouton 4

**Contraintes**  
- **Capacité de groupe** : ne pas dépasser la taille maximale.  
- **Placement unique** : un étudiant ne peut être affecté qu’une fois.  
- **Pré‑placements** : ne pas modifier les affectations existantes.

**Critères**  
- **Parité stricte** : minimiser `|nbHommes - nbFemmes|`.  
- **Écart‑type des notes** : maintenir un niveau scolaire homogène.

**Formule de score**  
score = |nbHommes - nbFemmes| + ecarType
**Interprétation**  
Simuler l’ajout, mesurer l’impact sur la parité et l’écart‑type, puis choisir le groupe minimisant ce score.

**Comportement attendu**  
- Priorité forte à la parité.  
- Maintien d’un équilibre scolaire secondaire.

**Limites**  
- Peut sacrifier la diversité des parcours.  
- Peut créer des groupes très hétérogènes académiquement si la parité domine.

---

# Glouton 5

**Contraintes**  
- **Capacité de groupe** : respecter la limite maximale.  
- **Placement unique** : un étudiant ne peut être affecté qu’une fois.  
- **Pré‑placements** : ne pas déplacer les étudiants déjà assignés.

**Critères**  
- **Diversité des parcours** : minimiser le nombre d’étudiants du même parcours.  
- **Parité de genre** : minimiser l’écart hommes/femmes.

**Formule de score**  
score = |nbHommes - nbFemmes| + nbMemeParcours

**Interprétation**  
Simuler l’ajout, mesurer l’impact sur la parité et la diversité des parcours, puis choisir le groupe minimisant ce score.

**Comportement attendu**  
- Favorise la diversité académique.  
- Maintient un certain équilibre de genre.

**Limites**  
- Peu efficace si les etudiants manque de diversité de parcours.  
- Peut entrer en conflit avec l’écart-type.

---
# Glouton 6

**Contraintes**  
- **Capacité de groupe** : ne pas dépasser la taille maximale.  
- **Placement unique** : un étudiant ne peut être affecté qu’une seule fois dans le type (TD ou TP).  
- **Pré‑placements** : ne pas modifier les étudiants déjà assignés.

**Critères**  
- **Équilibre des types de Bac** : éviter de regrouper trop d’étudiants du même Bac.  
- **Écart‑type global** : maintenir un niveau scolaire homogène dans chaque groupe.

**Formule de score**  
score = nbMemeBac + ecarType

**Interprétation**  
Simuler l’ajout de l’étudiant, mesurer l’impact sur la mixité des Bacs et sur l’écart‑type global, puis choisir le groupe minimisant ce score.

**Comportement attendu**  
- Favorise la diversité des Bacs.  
- Maintient un équilibre scolaire .

**Limites**  
- Peut ignorer la parité ou les parcours, car seul le Bac et le niveau sont considérés.  
- Décisions locales : peut créer des déséquilibres globaux si la population est très homogène ou très hétérogène.

---

# Glouton 7

**Contraintes**  
- **Capacité de groupe** : respecter la taille maximale.  
- **Placement unique** : un étudiant ne peut être affecté qu’une fois.  
- **Pré‑placements** : ne pas déplacer les étudiants déjà assignés.

**Critères**  
- **Équilibre Alternance / Formation Initiale** : éviter de regrouper trop d’alternants ou trop d’étudiants en formation initiale dans un même groupe.  
- **Mixité des Bac** : limiter la concentration d’un même Bac dans un groupe.

**Formule de score**  
score = nbMemeStatut + nbMemeBac

**Interprétation**  
Simuler l’ajout de l’étudiant, évaluer l’équilibre alternants/initial et la mixité des Bacs, puis choisir le groupe minimisant ce score.

**Comportement attendu**  
- Produit des groupes équilibrés en termes de statut (alternance vs initial).  
- Favorise une diversité de Bac.

**Limites**  
- Ne prend pas en compte le niveau scolaire (pas d’écart‑type).  
- Peut créer des groupes académiquement déséquilibrés si la priorité au statut domine.

---

# Glouton 8

**Contraintes**  
- **Capacité de groupe** : le groupe doit pouvoir accueillir tout le bloc de covoiturage.  
- **Placement unique** : un étudiant ne peut être affecté qu’une fois.  
- **Respect du covoiturage** : les partenaires doivent être placés ensemble si possible.

**Critères**  
- **Covoiturage** : placer ensemble les étudiants covoiturant pour faciliter leurs déplacements.  
- **Équilibre des genres** : minimiser l’écart hommes/femmes dans chaque groupe.  
- **Remplissage** : éviter les groupes trop pleins ou trop vides.

**Formule de score**  
score = |nbHommes - nbFemmes| + (nbEtudiants * 0.2)

**Interprétation**  
Simuler l’ajout du bloc de covoiturage, mesurer l’impact sur la parité et le remplissage du groupe, puis choisir le groupe minimisant ce score.

**Comportement attendu**  
- Respect strict du covoiturage.  
- Groupes plus équilibrés en genre.  
- Répartition plus homogène du nombre d’étudiants.

**Limites**  
- Le covoiturage peut forcer des placements sous‑optimaux académiquement.  
- Si les blocs sont grands, certains groupes deviennent impossibles à équilibrer.  
- Décisions locales : ne garantit pas un équilibre global parfait.

---
# Brute Force 1

**Contraintes**  
- **Capacité de groupe** : chaque groupe doit respecter sa taille maximale.  
- **Placement unique** : un étudiant ne peut être affecté qu’une seule fois dans le type (TD ou TP).  
- **Pré‑placements** : les étudiants déjà assignés doivent être conservés.  

**Critères**  
- **Écart‑type global** : minimiser les différences de niveau entre les groupes.  
- **Mixité des Bacs** : éviter les groupes trop homogènes.  
- **Taille des groupes** : pénaliser les groupes trop remplis.  

**Formule de score**  
score = ecarType + nbMemeBac + nbEtudiants

**Interprétation**  
Simuler toutes les répartitions possibles, calculer le score global de chaque configuration, puis sélectionner celle qui minimise ce score.

**Comportement attendu**  
- Explore **toutes les combinaisons possibles**.  
- Trouve la **meilleure solution théorique** selon les critères définis.  
- Sert de **référence optimale** pour comparer les gloutons.

**Limites**  
- **Complexité exponentielle** : le nombre de combinaisons explose avec le nombre d’étudiants.  
- **Temps d’exécution très élevé** : rapidement inutilisable au‑delà de quelques dizaines d’étudiants.  
- **Impraticable en production** : réservé aux tests, validations ou petits échantillons.  

---
# Brute Force 2

**Contraintes**  
- **Capacité de groupe** : chaque groupe doit respecter sa taille maximale.  
- **Placement unique** : un étudiant ne peut être affecté qu’une seule fois dans le type (TD ou TP).  
- **Pré‑placements** : les étudiants déjà assignés doivent être conservés.  
- **Respect du covoiturage** : les partenaires doivent être placés ensemble si possible.

**Critères**  
- **Écart‑type global** : maintenir un niveau scolaire homogène.  
- **Mixité des Bacs** : pénaliser les groupes trop concentrés en Bac Général, Techno ou Pro.  
- **Parité** : réduire l’écart hommes/femmes dans chaque groupe.  
- **Covoiturage** : pénaliser les groupes où des partenaires de covoiturage sont séparés.

**Formule de score**  
score = ecarType + (nbGeneral² + nbTechno² + nbPro²) + |nbHommes - nbFemmes| + covoitSepare

**Interprétation**  
Simuler toutes les répartitions possibles, calculer pour chacune le score global intégrant niveau scolaire, mixité des Bacs, parité et cohérence du covoiturage, puis sélectionner la configuration minimisant ce score.

**Comportement attendu**  
- Explore **toutes les combinaisons possibles** de placement.  
- Trouve la **meilleure solution théorique** selon les critères définis.  
- Sert de **référence optimale** pour évaluer la qualité des gloutons.

**Limites**  
- **Complexité exponentielle** : le nombre de combinaisons explose avec le nombre d’étudiants.  
- **Temps d’exécution très élevé** : inutilisable sur une promo complète.  
- **Très sensible aux blocs de covoiturage**, qui augmentent encore la combinatoire.  
- **Réservé aux tests et validations**, pas à une utilisation réelle.

---
# Brute Force 3

**Contraintes**  
- **Capacité de groupe** : chaque groupe doit respecter sa taille maximale.  
- **Placement unique** : un étudiant ne peut être affecté qu’une seule fois dans le type (TD ou TP).  
- **Pré‑placements** : les étudiants déjà assignés doivent être conservés.  

**Critères**  
- **Écart à la moyenne cible (10/20)** : favoriser des groupes dont la moyenne générale est proche de 10.  
- **Mixité des parcours** : éviter les groupes trop homogènes (Dev / Réseau / DB).  
- **Écart‑type des notes** : maintenir un niveau scolaire homogène.

**Formule de score**  
score = |10 - moyenneGroupe| + (nbDev² + nbReseau² + nbDB²) + ecarType

**Interprétation**  
Simuler toutes les répartitions possibles, mesurer l’écart à la moyenne cible, la diversité des parcours et l’écart‑type, puis sélectionner la configuration minimisant ce score.

**Comportement attendu**  
- Produit des groupes équilibrés autour d’une moyenne cible.  
- Favorise la diversité des parcours.  
- Sert de référence optimale.

**Limites**  
- **Complexité exponentielle** : très coûteux dès que le nombre d’étudiants augmente.  
- **Temps d’exécution élevé** : inutilisable en production.  
- **Ne prend pas en compte la parité ou le covoiturage**.  
---
# Brute Force 4

**Contraintes**  
- **Capacité de groupe** : chaque groupe doit respecter sa taille maximale.  
- **Placement unique** : un étudiant ne peut être affecté qu’une seule fois dans le type (TD ou TP).  
- **Pré‑placements** : les étudiants déjà assignés doivent être conservés.

**Critères**  
- **Équilibre Alternants / Initiaux** : viser un nombre cible d’alternants par groupe (ex. 4).  
- **Mixité des Bacs** : éviter la domination d’un Bac (Général / Techno / Pro).  
- **Remplissage** : éviter les groupes trop vides ou trop pleins.

**Formule de score**  
score = |nbAlternants - 4|+ (nbGeneral² + nbTechno² + nbPro²)+ |tailleMax - tailleActuelle|  

**Interprétation**  
Simuler toutes les répartitions possibles, mesurer l’équilibre alternants/initiaux, la diversité des Bacs et le remplissage, puis sélectionner la configuration minimisant ce score.

**Comportement attendu**  
- Produit des groupes équilibrés en statut (alternant / initial).  
- Favorise une répartition homogène des Bacs.  
- Sert de référence optimale pour comparer les gloutons.

**Limites**  
- **Complexité exponentielle** : impraticable sur une grande promo.  
- **Ne prend pas en compte le niveau scolaire** (pas d’écart‑type).  
- **Ne gère pas le covoiturage**.  
