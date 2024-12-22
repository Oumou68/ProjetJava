package ui;

import colony.Colon;
import colony.Colonie;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import solver.NaiveSolver;
import solver.Solver;

public class BuildColony {
	

    /**
     * PARTIE 1 - Construction manuelle, affectation manuelle, etc.
     *            (avec ressources = String)
     */
    
	public static void runPartie1() {
        Scanner sc = new Scanner(System.in);
        int n = -1;

        // On suppose toujours qu'on a n colons ET n ressources
        while (n < 1 || n > 26) {
            System.out.print("Entrez le nombre de colons (1..26) : ");
            while (!sc.hasNextInt()) {
                System.out.println("Veuillez saisir un entier.");
                sc.next();
            }
            n = sc.nextInt();
        }
        sc.nextLine(); // consomme \n

        // Crée la Colonie
        Colonie colony = new Colonie(n);
        // (Dans Colonie, on crée n colons nommés "A","B","C"... 
        //  ou "0","1","2"... selon votre implémentation.)

        // On crée un objet Affectation (avec Map<String,String>)
        Affectation aff = new Affectation(colony);

        // Menu initial : relations, prefs
        boolean fini = false;
        while (!fini) {
            System.out.println("\n1) Ajouter une relation entre colons");
            System.out.println("2) Ajouter les préférences d'un colon");
            System.out.println("3) Fin (passer à l'affectation)");
            System.out.print("Votre choix : ");

            String input = sc.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide (pas un entier).");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("Entrez deux colons (ex: A B) : ");
                    String c1 = sc.next();
                    String c2 = sc.next();
                    sc.nextLine();
                    // On ajoute la relation "ne s'aiment pas"
                    colony.ajouterRelation(c1, c2);
                }

                case 2 -> {
                    System.out.println("Entrez le colon et ses préférences (ex : A 1 2 3 4) : ");
                    String colonInput = sc.nextLine();
                    String[] parts = colonInput.split(" ");
                    String colon = String.valueOf(parts[0]);
                    List<Integer> prefsInt = new ArrayList<>();
                    List<String> pref = new ArrayList<>();
                    try{
                    	for (int i = 1; i < parts.length; i++) {
                    
                        if ((Integer.parseInt(parts[i])) <= n){
                            prefsInt.add(Integer.valueOf(parts[i]));
                        }
                        else{
                            throw new NumberFormatException(); 
                        }

                    }
                    }catch(NumberFormatException e) {
                    	System.out.println("Entree invalide de ressource");
                    }   
                    try{
                    	Colon c = colony.getColons().stream().filter(col -> col.getNom().equals(colon)).findFirst().get();
                    	 for (int preferences : prefsInt){
                             pref.add(String.valueOf(preferences));
                         }
                         c.setPreferences(pref);
                    }catch(NoSuchElementException e) {
                    	System.out.println("Colon non existant");
                    }
                   
                }
                case 3 -> {
                    // Vérifier que tous les colons ont leurs préférences complètes
                    boolean allOk = colony.tousLesColonsOntPreferences();
                    if (!allOk){
                        for (Colon colon : colony.getColons()) {
                            if (colon.getPreferences() == null || colon.getPreferences().size() != n) {
                                System.out.println("Le colon " + colon.getNom() + " n'a pas de préférences complètes.");
                            }
                        } 
                    }else{
                        fini = true;
                    }
                }
                default -> System.out.println("Choix invalide (1..3).");
            }
        }

        // Une fois les prefs et relations définies, on affecte
        aff.affecterResources();
        aff.afficherAffecation();

        // Menu post-affectation
        boolean fini2 = false;
        while (!fini2) {
            System.out.println("\n1) Échanger les ressources de deux colons");
            System.out.println("2) Afficher le nombre de colons jaloux");
            System.out.println("3) Fin");
            System.out.print("Votre choix : ");

            if (!sc.hasNextInt()) {
                System.out.println("Entrée invalide (pas un entier).");
                sc.next();
                continue;
            }
            int ch2 = sc.nextInt();
            sc.nextLine();

            switch (ch2) {
                case 1 -> {
                    System.out.println("Entrez deux colons (ex: A B) : ");
                    String c1 = sc.next();
                    String c2 = sc.next();
                    sc.nextLine();
                    aff.echangerResources(c1, c2);
                    aff.afficherAffecation();
                }
                case 2 -> {
                    System.out.println("Nb colons jaloux = " + aff.calculerJalousie());
                }
                case 3 -> {
                    System.out.println("Fin du programme (partie 1).");
                    fini2 = true;
                }
                default -> System.out.println("Choix invalide (1..3).");
            }
        }
    }

    /**
     * PARTIE 2 - Menu 3 options : solver, sauvegarde, fin
     * @param colonie la colonie créée par par le fichier
     * ICI, on suppose que le parseur + solver manipulent des ressources en String (ex: "o1").
     */
    public static void runPartie2(Colonie colonie) {
        Scanner sc = new Scanner(System.in);
        // Le solver renvoie Map<String,String> => colon->resource
        Solver solver = new NaiveSolver(); 
        // ... mais si votre interface Solver est paramétrée différemment, adaptez.

        // On stocke la solution dans Map<String, String>
        Map<String, String> currentSolution = null;
        boolean fini = false;

        while (!fini) {
            System.out.println("\n=== MENU PARTIE 2 ===");
            System.out.println("1) Résolution automatique");
            System.out.println("2) Sauvegarder la solution actuelle");
            System.out.println("3) Fin");
            System.out.print("Votre choix : ");

            int ch;
            try {
                ch = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide (pas un entier).");
                sc.next();
                continue;
            }
            sc.nextLine(); // consomme \n

            switch (ch) {
                case 1 -> {
                    // On appelle solve(...) => renvoie Map<String, String>
                    currentSolution = solver.solve(colonie);
                    // computeCost(...) => nb colons jaloux
                    int cost = solver.computeCost(currentSolution, colonie);
                    System.out.println("Solution générée. Nb colons jaloux = " + cost);
                }
                case 2 -> {
                    if (currentSolution == null) {
                        System.out.println("Aucune solution à sauvegarder. Faites d'abord l'option 1.");
                    } else {
                        System.out.print("Entrez le chemin du fichier de sauvegarde : ");
                        String outPath = sc.nextLine();
                        io.ColonyFileWriter writer = new io.ColonyFileWriter();
                        try {
                            // writer.saveSolution(...) doit accepter Map<String,String>
                            writer.saveSolution(outPath, currentSolution);
                            System.out.println("Solution sauvegardée dans " + outPath);
                        } catch (IOException e) {
                            System.out.println("Erreur lors de la sauvegarde: " + e.getMessage());
                        }
                    }
                }
                case 3 -> {
                    System.out.println("Fin (partie 2).");
                    fini = true;
                }
                default -> System.out.println("Choix invalide (1..3).");
            }
        }
    }
}
