# 🚪 Reach the Door First

**Projet SAE S4 - IUT d'Orsay** **Équipe TP4A-G3 :** Gameiro Soan, Hassan Yusuf, Kalman Léo


## 📝 Présentation du projet
"Reach the Door" est un jeu multijoueur compétitif en 2D pixel-art. Le projet consiste à développer une architecture client-serveur temps réel où le smartphone sert de manette et l'écran principal d'écran de jeu.

### Concept et Règles :
* **Objectif Principal :** Atteindre la porte de sortie située à l'opposé du point de départ.
* **Format Points :** Le jeu se déroule en plusieurs niveaux, à chaque niveau, il sera attribué à l'équipe arrivant à la porte en premier, un certain nombre de point en fonction de la difficulté du niveau. (1 point pour le niveau 1, 2 pour le niveau 2 etc.)
* **Système de Relais (Tag-Team) :** Contrairement aux jeux de parcours classiques en équipe, un seul joueur par équipe est actif à l'écran. 
* **Mécanique de Mort/Respawn :** Lorsqu'un joueur meurt (piques, lave, scie), il est éliminé pour la manche. Le joueur suivant dans la file d'attente de l'équipe apparaît instantanément au point de départ (**Start Zone**).
* **Zone de Jeu Fixe :** Tout le niveau tient sur un seul écran (pas de scrolling) pour une lisibilité totale.

### Inspirations :
* **Trap Adventure 2 / Cat Mario :** Pour le côté "Die & Retry" et les pièges punitifs.
* **Geometry Dash Platformer:** Pour la précision des sauts, le dynamisme et parfois le level design.
* **Fall Guys :** Pour le format de course et le chaos de groupe.
* **Celeste :** Pour la maniabilité, le système de wall jump et le level design.

---

## 🛠️ Choix Technologiques
* **Moteur de jeu :** **Godot Engine 4.x** (GDScript). Gestion de la physique 2D et intégration du protocole réseau via `WebSocketPeer`.
* **Communication :** **WebSockets (Protocole WSS)** pour une connexion bidirectionnelle persistante et un Input Lag réduit (< 50ms).
* **Tunneling :** **ngrok** pour exposer le serveur local via un tunnel sécurisé..
* **Client Manette :** Interface **HTML5/JavaScript** universelle. Pas d'installation requise, utilisation directe via le navigateur du smartphone.

---

## 🚀 Installation et Setup (Nouveau PC)

### 1. Configuration de ngrok
1. Télécharger ngrok et l'extraire.
2. Ajouter l'AuthToken (une seule fois) :

`ngrok config add-authtoken <TOKEN>`

3. Lancer le tunnnel sur le port 8080 :

`ngrok http --domain=lizzie-guardable-chanelle.ngrok-free.dev 8080`

### 2. Lancement du serveur (Godot)
1. Ouvrir le projet dans Godot.
2. Lancer la scène principale (F5)
3. Vérifier la console pour voir si le serveur est prêt.

### 3. Connexion des manettes
1. Scanner le QR code