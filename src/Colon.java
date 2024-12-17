package src;

public class Colon {
    private String nom;  // Le nom du colon (A, B, C, ...)
    private Set<String> relations;  // Relations négatives (les colons qu'il n'aime pas)
    private List<Integer> preferences;  // Liste des préférences de ressources du colon

    public Colon(String nom, int nombreDeRessources) {
        this.nom = nom;
        this.relations = new HashSet<>();
        this.preferences = new ArrayList<>(); // Initialiser avec une taille correspondant aux ressources
    }

    public String getNom() {
        return nom;
    }

    public Set<String> getRelations() {
        return relations;
    }

    public List<Integer> getPreferences() {
        return preferences;
    }

    public void ajouterRelation(String colon) {
        relations.add(colon);
    }

    public void setPreferences(List<Integer> preferences) {
        this.preferences = preferences;
    }

    public boolean aDesPreferences() {
        return !preferences.isEmpty();
    }

}

