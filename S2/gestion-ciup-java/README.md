# Gestion d'une Cité Internationale Universitaire (CIUP)

Application **Java** de gestion d'une cité internationale universitaire, réalisée en équipe dans le cadre de la **SAÉ S2 (BUT Informatique, IUT d'Orsay)**.

## Présentation
L'application permet de gérer l'ensemble d'une cité internationale :
- **Bâtiments / maisons internationales** : création, suivi des places disponibles, affectation.
- **Étudiants** : inscription, nationalité, attribution à une maison.
- **File d'attente** : mise en attente et attribution automatique d'une maison selon les places.
- **Restauration** : restaurants, cafétérias, menus et types de repas/services.
- **Sauvegarde** : persistance des données par **sérialisation** Java.

## Architecture
- **Patron MVC** : séparation nette `modele` / `vues` (Swing) / `controleurs`.
- Packages métier : `batiment`, `ciup`, `gestion`, `service`, `utilisateur`.
- **Sérialisation** pour sauvegarder/charger l'état (cité, étudiants, restauration).
- **Tests unitaires JUnit** sur les classes métier.

## Technologies
Java · Swing (interfaces graphiques) · JUnit · conception UML · architecture MVC.

## Équipe
Projet de groupe (GH8) : Léo Kalman, Selman Bouzlafa, Soan Gameiro, **Yusuf Hassan Mohamed**.
Contribution de Yusuf : développement et **tests du package `service`** (Menu, Restaurant, Service, TypeRepas), en plus du travail commun sur l'application.

## Lancer le projet
1. Ouvrir **Eclipse** → *File > Open Projects from File System*.
2. Sélectionner le dossier `Java/dev`.
3. Lancer `mvc/ApplicationCIUP.java` ; les tests sont dans `Java/dev/tests`.

> Code source publié à titre de portfolio. Le dépôt d'origine (SAÉ) était hébergé sur le GitLab de l'IUT.
