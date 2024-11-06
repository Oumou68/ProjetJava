import java.util.*;

public class Colonie {

    /**
     * nombreDeColonies :Nombre total de colons dans la colonie
     * Map : associe chaque colon à un ensemble de colons qu'il n'aime pas
     */
    private int nombreDeColonies;
    private Map<Character, Preferences> colons;
    private Map<String, Set<String>> relations;


    // initialiser le nombre de colons et les relations
    public Colonie(int nombreDeColonies) {
        this.nombreDeColonies = nombreDeColonies;
        relations = new HashMap<>();

        // Initialiser chaque colon avec un ensemble vide de relations
        for (int i = 0; i < nombreDeColonies; i++) {
            String colon = String.valueOf((char)('A' + i));
            relations.put(colon, new HashSet<>());  // Initialiser chaque colon avec un Set vide
        }
    }

    public void ajouterRelation(String colon1, String colon2) {
        /*
        * vérification dans la méthode ajouterRelation pour
        * s'assurer que les deux colons fournis existent dans la carte
        * */
        if (relations.containsKey(colon1) && relations.containsKey(colon2)) {
            relations.get(colon1).add(colon2);
            relations.get(colon2).add(colon1);
        } else {
            System.out.println("Un ou les deux colons n'existent pas.");
        }
    }

    public Set<String> getRelations(String colon) {
        return relations.get(colon);
    }

    public int getNombreDeColonies() {
        return nombreDeColonies;
    }
}
