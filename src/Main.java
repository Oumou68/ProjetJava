package src;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Entrez le nombre de colons : ");
        int nbColons = scanner.nextInt();
        scanner.nextLine();

        Colonie colonie = new Colonie(nbColons);

        boolean finConfiguration = false;
        while (!finConfiguration) {
            System.out.println("\n1) Ajouter une relation entre deux colons");
            System.out.println("2) Ajouter les préférences d'un colon");
            System.out.println("3) Fin de la configuration");
            int choix = scanner.nextInt();
            switch (choix) {
                case 1 -> {
                    System.out.print("Entrez les noms des deux colons (ex : A B) : ");
                    char nom1 = scanner.next().charAt(0);
                    char nom2 = scanner.next().charAt(0);
                    scanner.nextLine();
                    colonie.ajouterRelation(nom1, nom2);
                    System.out.println(nom1 + " " + nom2 + " ne s'aiment pas.");
                }
                case 2 -> {
                    System.out.print("Entrez le nom du colon : ");
                    char nom = scanner.next().charAt(0);
                    System.out.print("Entrez les préférences (ex : 1 2 3...) : ");
                    List<Integer> preferences = new ArrayList<>();
                    int i = 0;
                    while (i < nbColons) {
                        int preference = scanner.nextInt();
                        if (preference >= 1 && preference <= nbColons && !preferences.contains(preference)) {
                            preferences.add(preference);
                            i++;  // Incrémente seulement si la préférence est valide
                        } else {
                            System.out.println("Erreur : la préférence doit être un nombre unique entre 1 et " + nbColons + ".");
                        }
                    }    
                    scanner.nextLine(); // Pour consommer le reste de la ligne
                    colonie.ajouterPreferences(nom, preferences);
                }

                case 3 -> {
                    if (colonie.verifierPreferencesCompletes()) {
                        finConfiguration = true;
                        System.out.println("Configuration terminée.");
                    }
                }
                default -> System.out.println("Choix invalide.");
            }
        }

        colonie.affectationNaive();
        
        boolean finSimulation = false;
        while (!finSimulation) {
            System.out.println("\n1) Échanger les ressources de deux colons");
            System.out.println("2) Afficher le nombre de colons jaloux");
            System.out.println("3) Fin");
            System.out.print("Choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> {
                    System.out.print("Entrez les noms des deux colons : ");
                    char nom1 = scanner.next().charAt(0);
                    char nom2 = scanner.next().charAt(0);
                    scanner.nextLine();
                    colonie.echangerRessources(nom1, nom2);
                    colonie.afficherAffectation();
                }
                case 2 -> {
                    int jalousie = colonie.calculerJalousie();
                    System.out.println("Nombre de colons jaloux : " + jalousie);
                    colonie.afficherAffectation();
                    
                }
                case 3 -> finSimulation = true;
                default -> System.out.println("Choix invalide.");
            }
        }
        scanner.close();
    }
}
