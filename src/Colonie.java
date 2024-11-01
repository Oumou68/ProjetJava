import java.util.*;

public class Colonie {
    private int nombreDeColonies;
    private Map<Character, Preferences> colons;
    private Map<String, Set<String>> relations;

    public Colonie(int nombreDeColonies) {
        this.nombreDeColonies = nombreDeColonies;
        relations = new HashMap<>();
        for (int i = 0; i < nombreDeColonies; i++) {
            String colon = String.valueOf((char)('A' + i));
            relations.put(colon, new HashSet<>());  // Initialiser chaque colon avec un Set vide
        }
    }

    public void ajouterRelation(String colon1, String colon2) {
        relations.get(colon2).add(colon1);
    }

    public Set<String> getRelations(String colon) {
        return relations.get(colon);
    }

    public int getNombreDeColonies() {
        return nombreDeColonies;
    }
}
