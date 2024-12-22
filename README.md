# Projet : Partage de biens d’une colonie spatiale 

---

## 1. **Introduction**
Ce projet vise à **modéliser** et à **résoudre** le problème de répartition de ressources critiques dans une colonie spatiale. Chaque **colon** possède des **préférences** (ressources qu’il souhaite obtenir) et des **relations** de type *« ne s’aiment pas »*. L’objectif est de **minimiser** le **nombre de colons jaloux** : un colon est jaloux s’il préfère la ressource d’un de ses ennemis à la sienne.

Ce document décrit l’organisation du code, les fonctionnalités implémentées et la manière d’exécuter l’application. Il récapitule aussi les limites de l’implémentation actuelle.

---

## 2. **Instructions d’utilisation**

### **2.1 Mode manuel (Partie 1)**
1. Lancez le programme **sans** fichier en argument.  
   ```bash
   java -jar DorcalBah.jar
   ```
2. Suivez les **instructions** à l’écran pour :
   - Créer la **colonie** (définir le nombre de colons, les relations « ne s’aiment pas », etc.).
   - Ajouter les **préférences** de chaque colon.  
3. À la fin, vous pourrez affecter les **ressources** (selon la stratégie naïve), puis **échanger** des ressources manuellement pour tenter de **réduire la jalousie**.

### **2.2 Mode fichier (Partie 2)**
1. Lancez le programme **avec** un fichier décrivant la colonie :  
   ```bash
   java -jar DorcalBah.jar chemin/vers/le_fichier.txt
   ```
2. Le fichier doit respecter le **format** suivant :

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
   - Ordre strict : d’abord `colon(...)`, puis `ressource(...)`, puis `deteste(...)`, puis `preferences(...)`.
   - Un point final `.` à chaque ligne.
   - Respect de la syntaxe (parenthèses, virgules).
   - Un nombre identique de colons et de ressources.

3. Après lecture du fichier, un **menu** s’affiche :
   1. **Résolution automatique** : Un algorithme attribue les ressources pour essayer de minimiser la jalousie.  
   2. **Sauvegarder la solution** : Enregistre l’affectation.  
   3. **Fin** : Quitte le programme.

---

## 3. **Détails du projet**

- Le projet **lit** et **valide** un fichier (partie 2) ou construit manuellement la colonie (partie 1).  
- Il **gère** les exceptions courantes (fichier introuvable, syntaxe invalide, saisie incorrecte, etc.).  
- Il propose un **algorithme naïf** (non garanti optimal) pour affecter les ressources ; nous n'avons pas implémenté d’**algorithme plus performant** garantissant la solution optimale.  
- Il n’y a pas de **tests unitaires** ni d’**interface graphique** : l’interface est purement textuelle.

---

## 4. **Fonctionnalités principales**

1. **Lecture et validation des fichiers**  
   - Vérifie la présence des parenthèses, du point final, l’ordre des définitions, etc.  
   - Contrôle le nombre de colons et de ressources.

2. **Construction manuelle de la colonie (Partie 1)**  
   - L’utilisateur saisit le nombre de colons.  
   - Ajoute manuellement les préférences et les relations « ne s’aiment pas ».  

3. **Résolution automatique**  
   - Une fois la colonie prête, un **algorithme naïf** attribue les ressources en tenant compte des préférences.  
   - Possibilité de quelques échanges aléatoires ou manuels pour réduire le coût.

4. **Sauvegarde de la solution**  
   - Format `colon:ressource` par ligne dans un fichier texte.  
   - Géré via `ColonyFileWriter`.

5. **Menu interactif (Partie 2)**  
   - Lecture du fichier => menu 3 options.  
   - L’utilisateur peut afficher le coût (nb de colons jaloux), puis éventuellement sauvegarder.

---

## 5. **Structure du projet**

L’arborescence standard est :

```
DorcalBah/
 └── src/
      ├── colony/
      │    ├── Colon.java  
      │    └── Colonie.java
      ├── io/
      │    ├── ColonyFileParser.java
      │    └── ColonyFileWriter.java
      ├── solver/
      │    ├── Solver.java
      │    └── NaiveSolver.java
      └── ui/
           ├── Main.java         (contient la méthode main)
           ├── Affectation.java
           └── BuildColony.java
```

### **Rôles** :

- **`colony`** : contient le *modèle* de la colonie (classe `Colonie`), chaque `Colon` (nom, préférences, relations).  
- **`io`** : gère l’**import** (`ColonyFileParser`) et l’**export** (`ColonyFileWriter`) de la colonie et/ou solutions.  
- **`solver`** : contient la**classe abstraite** `Solver` et l’**implémentation** naïve (`NaiveSolver`).  
- **`ui`** : l’**interface console** :  
  - `Main.java` : point d’entrée (`main`).  
  - `Affectation.java` : gère la partie 1 (mode manuel).  
  - `BuildColony.java` : logique de construction, menus, etc.


---

## 6. **Auteurs**

- **Étudiant 1 :** Doralie DORCAL  
- **Étudiant 2 :** Oumou Hawa BAH  



