import java.util.*;

public class Affectation {
    private Map<String, Integer> affectation;
    private Colonie colonie;
    private Preferences preferences;

    public Affectation(Colonie colonie, Preferences preferences) {
        this.colonie = colonie;
        this.preferences = preferences;
        affectation = new HashMap<>();
    }

    // Première affectation naïve
    public void affecterResources() {
        Set<Integer> availableResources = new HashSet<>();
        for (int i = 1; i <= colonie.getNombreDeColonies(); i++) {
            availableResources.add(i);
        }

        // Affecter les ressources selon les préférences
        for (String colon : preferences.getAllColons()) {
            List<Integer> prefs = preferences.getPreferences(colon);
            for (int pref : prefs) {
                if (availableResources.contains(pref)) {
                    affectation.put(colon, pref);
                    availableResources.remove(pref);
                    break;
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
