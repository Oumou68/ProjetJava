
# Projet : Partage de biens d'une colonie spatiale (Partie 2)

## **Introduction**
Ce projet vise à modéliser et résoudre le problème de répartition des ressources dans une colonie spatiale. Les colons ont des préférences pour certaines ressources et des relations de type "ne s'aiment pas". L'objectif est de minimiser le nombre de colons jaloux lors de l'affectation des ressources.

Ce document fournit des instructions d'utilisation, des détails sur l'algorithme implémenté, et des informations sur les fonctionnalités.

---

## **Instructions d'utilisation**

### **Exécution du programme**
1. **Mode manuel (Partie 1) :**
   - Lancez le programme sans fournir de fichier en argument.
   - Exemple :
     ```bash
     java -jar ColonyProject.jar
     ```
   - Vous serez guidé pour créer une colonie, ajouter des relations entre colons, et définir leurs préférences.

2. **Mode fichier (Partie 2) :**
   - Fournissez un fichier texte décrivant la colonie.
   - Exemple :
     ```bash
     java -jar ColonyProject.jar chemin/vers/le_fichier.txt
     ```
   - Format du fichier attendu :
     ```text
     colon(A).
     colon(B).
     colon(C).
     ressource(o1).
     ressource(o2).
     ressource(o3).
     deteste(A,B).
     deteste(B,C).
     preferences(A,o1,o2,o3).
     preferences(B,o2,o3,o1).
     preferences(C,o3,o1,o2).
     ```

3. **Menu interactif (Partie 2) :**
   - Une fois le fichier chargé, vous accéderez à un menu :
     1. **Résolution automatique :** Utilise un algorithme pour minimiser les jalousies.
     2. **Sauvegarde :** Enregistre la solution dans un fichier au format `colon:ressource`.
     3. **Fin :** Quitte le programme.

---

## **Détails de l'algorithme**

### **Algorithme de recuit simulé**
L'algorithme de recuit simulé améliore l'affectation initiale en explorant l'espace des solutions de manière stochastique. Voici les étapes principales :

1. **Solution initiale :**
   - Chaque colon reçoit sa ressource préférée encore disponible.

2. **Itérations :**
   - À chaque étape :
     - Une paire de colons est sélectionnée aléatoirement.
     - Leurs ressources sont échangées pour générer une solution voisine.
   - La nouvelle solution est acceptée avec une probabilité basée sur la différence de coût et la température actuelle.


---

## **Fonctionnalités principales**

### **1. Lecture et validation des fichiers**
- Lecture des fichiers définissant les colons, ressources, relations et préférences.
- Validation de la syntaxe et cohérence des données (ex. : nombre de ressources égal au nombre de colons).

### **2. Construction de la colonie**
- Création dynamique de colons et gestion des relations et préférences.

### **3. Résolution automatique**
- Minimisation des jalousies grâce à l'algorithme de recuit simulé.

### **4. Sauvegarde des solutions**
- Export des affectations sous forme de fichier texte lisible.

### **5. Menu interactif**
- Interface texte simple permettant de naviguer entre les différentes fonctionnalités.

---





---

## **Structure du projet**

```
ColonyProject/
├── src/
│   ├── colony/         # Modélisation des colons et de la colonie
│   ├── solver/         # Algorithmes de résolution
│   ├── io/             # Gestion des fichiers d'entrée/sortie
│   ├── ui/             # Interface utilisateur (menu interactif)
├── README.md           # Documentation
└── ...
```

---

## **Auteurs**
- **Étudiant 1 :** Doralie Dorcal
- **Étudiant 2 :** Oumou Hawa BAH




