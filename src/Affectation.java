import java.util.*;

// Classe responsable de l'affectation des ressources aux colons selon leurs préférences.
public class Affectation {
    /**
     * Map : associe chaque colon à la ressource qui lui est attribuée.
     * Colonie : contient des informations sur les relations entre colons.
     * l' Objet Preferences contient les préférences de chaque colon.
     */
    private Map<String, Integer> affectation;
    private Colonie colonie;
    private Preferences preferences;

    // Constructeur pour initialiser la colonie, les préférences et la map d'affectation.
    public Affectation(Colonie colonie, Preferences preferences) {
        this.colonie = colonie;
        this.preferences = preferences;
        affectation = new HashMap<>();
    }

    // Méthode qui effectue une première affectation naïve des ressources en respectant les préférences.
    public void affecterResources() {
        Set<Integer> availableResources = new HashSet<>();
        for (int i = 1; i <= colonie.getNombreDeColonies(); i++) {
            availableResources.add(i);
        }

        // Parcourir chaque colon et attribuer la ressource préférée encore disponible
        for (String colon : preferences.getAllColons()) {
            List<Integer> prefs = preferences.getPreferences(colon);
            for (int pref : prefs) {
                if (availableResources.contains(pref)) {
                    affectation.put(colon, pref);
                    availableResources.remove(pref);
                    break; // Arrêter après avoir trouvé une ressource disponible
                }
            }
        }
    }

    // Calculer le nombre de colons jaloux
    public int calculerJalousie() {
        int jaloux = 0;
        for (String colon : affectation.keySet()) {
            int assignedResource = affectation.get(colon);
            Set<String> dislikedColons = colonie.getRelations(colon);

            // Vérifier si un colon n'aime pas qu'un autre ait une ressource mieux classée
            for (String dislikedColon : dislikedColons) {
                List<Integer> dislikedColonPreferences = preferences.getPreferences(dislikedColon);
                if (dislikedColonPreferences.indexOf(assignedResource) < dislikedColonPreferences.indexOf(affectation.get(dislikedColon))) {
                    jaloux++;
                    break;
                }
            }
        }
        return jaloux;
    }

    // Afficher l'affectation actuelle des ressources
    public void afficherAffecation() {
        for (Map.Entry<String, Integer> entry : affectation.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    // Échanger les ressources entre deux colons
    public void echangerResources(String colon1, String colon2) {
        int temp = affectation.get(colon1);
        affectation.put(colon1, affectation.get(colon2));
        affectation.put(colon2, temp);
    }
}
