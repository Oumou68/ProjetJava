import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Entrée du nombre de colons (limité à 26)
        System.out.println("Entrez le nombre de colons (1 à 26) : ");
        int n = scanner.nextInt();

        if (n < 1 || n > 26) {
            System.out.println("Le nombre de colons doit être compris entre 1 et 26.");
            return; // Fin du programme si le nombre est incorrect
        }
        scanner.nextLine(); // Nettoyer le buffer

        Colonie colony = new Colonie(n);
        Preferences preferences = new Preferences(n);

        while (true) {
            System.out.println("1) Ajouter une relation entre colons\n2) Ajouter les préférences d'un colon\n3) Fin");

            // Gestion des erreurs d'entrée pour les options du menu
            String input = scanner.nextLine(); // Lire l'entrée comme une chaîne

            try {
                int choice = Integer.parseInt(input); // Convertir l'entrée en entier

                if (choice == 1) {
                    // Ajouter une relation "ne s’aiment pas" entre deux colons
                    System.out.println("Entrez les deux colons (ex : A B) : ");
                    String colon1 = scanner.next();
                    String colon2 = scanner.next();
                    colony.ajouterRelation(colon1, colon2);
                    scanner.nextLine(); // Nettoyer le buffer après next()
                } else if (choice == 2) {
                    // Ajouter les préférences d’un colon
                    System.out.println("Entrez le colon et ses préférences (ex : A 1 2 3 4) : ");
                    String colonInput = scanner.nextLine();  // Lire l'entrée complète
                    String[] parts = colonInput.split(" ");  // Diviser l'entrée par espaces

                    String colon = parts[0];  // Le premier élément est le nom du colon
                    List<Integer> prefs = new ArrayList<>();

                    // Boucle pour extraire les préférences (à partir du deuxième élément)
                    for (int i = 1; i < parts.length; i++) {
                        try {
                            prefs.add(Integer.parseInt(parts[i]));
                        } catch (NumberFormatException e) {
                            System.out.println("Erreur : Les préférences doivent être des nombres.");
                            break;
                        }
                    }

                    // Vérifier si le nombre de préférences est correct
                    if (prefs.size() != n) {
                        System.out.println("Le colon " + colon + " doit avoir des préférences pour toutes les ressources.");
                    } else {
                        preferences.setPreferences(colon, prefs);
                    }
                } else if (choice == 3) {
                    // Vérifier que tous les colons ont des préférences
                    for (int i = 0; i < n; i++) {
                        String colon = String.valueOf((char)('A' + i));
                        if (!preferences.hasPreferences(colon)) {
                            System.out.println("Le colon " + colon + " n'a pas de préférences complètes.");
                            return;
                        }
                    }
                    break;
                } else {
                    System.out.println("Option incorrecte, veuillez entrer un numéro entre 1 et 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide, veuillez entrer un nombre pour choisir une option.");
            }
        }

        // Le reste du programme pour l'affectation des ressources
        Affectation affectation = new Affectation(colony, preferences);
        affectation.affecterResources();
        affectation.afficherAffecation();
        System.out.println("Nombre de colons jaloux : " + affectation.calculerJalousie());

        // Menu pour échange de ressources et affichage des colons jaloux
        while (true) {
            System.out.println("1) Échanger les ressources de deux colons\n2) Afficher le nombre de colons jaloux\n3) Fin");
            String userInput = scanner.nextLine(); // Lire l'entrée comme une chaîne

            try {
                int choix = Integer.parseInt(userInput); // Convertir en entier

                if (choix == 1) {
                    // Échanger les ressources de deux colons
                    System.out.println("Entrez les deux colons dont vous souhaitez échanger les ressources (ex : A B) : ");
                    String colon1 = scanner.next();
                    String colon2 = scanner.next();

                    if (!preferences.hasPreferences(colon1) || !preferences.hasPreferences(colon2)) {
                        System.out.println("Un ou les deux colons n'existent pas.");
                    } else {
                        affectation.echangerResources(colon1, colon2);
                        System.out.println("Ressources échangées.");
                        affectation.afficherAffecation();
                    }
                    scanner.nextLine(); // Nettoyer le buffer après next()
                } else if (choix == 2) {
                    // Afficher le nombre de colons jaloux
                    System.out.println("Nombre de colons jaloux : " + affectation.calculerJalousie());
                } else if (choix == 3) {
                    break; // Sortie du menu
                } else {
                    System.out.println("Option incorrecte, veuillez entrer un numéro entre 1 et 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide, veuillez entrer un nombre pour choisir une option.");
            }
        }

    }
}
