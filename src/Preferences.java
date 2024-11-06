import java.util.*;

public class Preferences {
    // Map qui associe chaque colon à une liste de préférences de ressources
    private Map<String, List<Integer>> preferences;

    // Initialiser les préférences des colons
    public Preferences(int nombreDeColonies) {
        preferences = new HashMap<>();
        for (int i = 0; i < nombreDeColonies; i++) {
            preferences.put(String.valueOf((char)('A' + i)), new ArrayList<>());
        }
    }

    public void setPreferences(String colon, List<Integer> preferenceList) {
        preferences.put(colon, preferenceList);
    }

    public List<Integer> getPreferences(String colon) {
        return preferences.get(colon);
    }

    public boolean hasPreferences(String colon) {
        return preferences.containsKey(colon) && !preferences.get(colon).isEmpty();
    }

    public Set<String> getAllColons() {
        return preferences.keySet();
    }
}
