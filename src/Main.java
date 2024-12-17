package src;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        int n = -1;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Debut du programme");

        while(n < 1 || n > 26) {
            System.out.println("\nEntrez le nombre de colons (1 à 26) : ");
            n = scanner.nextInt();
        }

        scanner.nextLine();
        Colonie colony = new Colonie(n);
        Affectation affectation = new Affectation(colony);

        OUTER_1:
        while (true) {
            System.out.println("1) Ajouter une relation entre colons\n2) Ajouter les préférences d'un colon\n3) Fin");
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1 -> {
                        System.out.println("Entrez les deux colons (ex : A B) : ");
                        String colon1 = scanner.next();
                        String colon2 = scanner.next();
                        colony.ajouterRelation(colon1, colon2);
                        scanner.nextLine();
                    }
                    case 2 -> {
                        System.out.println("Entrez le colon et ses préférences (ex : A 1 2 3 4) : ");
                        String colonInput = scanner.nextLine();
                        String[] parts = colonInput.split(" ");
                        String colon = parts[0];
                        List<Integer> prefs = new ArrayList<>();
                        for (int i = 1; i < parts.length; i++) {
                            if ((Integer.parseInt(parts[i])) <= n){
                                prefs.add(Integer.valueOf(parts[i]));
                            }
                            else{
                                System.out.println("Entrée invalide de ressource");
                                break;
                            }
                        }   Colon c = colony.getColons().stream().filter(col -> col.getNom().equals(colon)).findFirst().get();
                        c.setPreferences(prefs);
                    }
                    case 3 -> {
                        // Vérifier que tous les colons ont leurs préférences complètes
                        boolean tousOntPreferences = true;
                    
                        for (Colon colon : colony.getColons()) {
                            if (colon.getPreferences() == null || colon.getPreferences().isEmpty()) {
                                tousOntPreferences = false;
                                System.out.println("Le colon " + colon.getNom() + " n'a pas de préférences complètes.");
                            }
                        }
                    
                        if (tousOntPreferences) {
                            System.out.println("Tous les colons ont leurs préférences complètes.");
                            
                        } else {
                            System.out.println("Il y a des colons sans préférences complètes.");
                        }
                    
                        // Sortir du menu si tous les colons ont des préférences complètes
                        if (tousOntPreferences) {
                            break OUTER_1;  // Quitter la boucle OUTER si toutes les préférences sont complètes
                        }
                    }
                    
                    default -> System.out.println("Option incorrecte, veuillez entrer un numéro entre 1 et 3.");
                }
                  }catch (NumberFormatException e) {
                System.out.println("Entrée invalide, veuillez entrer un nombre.");
            }
        }

        affectation.affecterResources();
        affectation.afficherAffecation();

        OUTER:
        while (true) {
            System.out.println("1) Échanger les ressources de deux colons\n2) Afficher le nombre de colons jaloux\n3) Fin");
            
            // Vérification de l'entrée
            if (scanner.hasNextInt()) {
                int choix = scanner.nextInt(); // Récupère l'entrée comme entier
        
                switch (choix) {
                    case 1 -> {
                        System.out.println("Entrez les deux colons dont vous souhaitez échanger les ressources, seule la première lettre sera prise en compte (ex : A B) : ");
                        String colon1, colon2;
                        char lettreMax = (char) ('A' + n - 1);
        
                        while (true) { // Boucle pour valider les entrées
                            colon1 = scanner.next();
                            colon2 = scanner.next();
        
                            char char_colon1 = colon1.charAt(0);
                            char char_colon2 = colon2.charAt(0);
        
                            if ((char_colon1 >= 'A' && char_colon1 <= lettreMax) && (char_colon2 >= 'A' && char_colon2 <= lettreMax)) {
                                affectation.echangerResources(char_colon1 + "", char_colon2 + "");
                                System.out.println("Entree valide");
                                affectation.afficherAffecation();
                                break; // Sortir de la boucle si les deux entrées sont valides
                            } else {
                                System.out.println("Erreur : Les deux colons doivent être des lettres majuscules entre A et " + n);
                                System.out.println("Veuillez réessayer (ex : A B) : ");
                            }
                        }
                    }
                    case 2 -> System.out.println("Nombre de colons jaloux : " + affectation.calculerJalousie());
                    case 3 -> {
                        System.out.println("Fin du programme.");
                        break OUTER; // Sortir de la boucle principale
                    }
                    default -> System.out.println("Option incorrecte.");
                }
            } else {
                System.out.println("Entrée invalide. Veuillez entrer un numéro valide.");
                scanner.next(); // Consomme l'entrée invalide (lettre ou autre)
            }
        }
        
    }
}
