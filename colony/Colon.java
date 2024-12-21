package colony;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Représente un colon (nom, préférences, relations "ne s'aiment pas").
 */
public class Colon {

    /** Nom du colon*/
    private String nom;

    /** Liste ordonnée des ressources préférées*/
    private List<String> preferences;

    /** Ensemble des noms de colons que ce colon "n'aime pas". */
    private Set<String> relations;


    /**
     * Constructeur de colon
     * @param nom Nom du colon
     */
    public Colon(String nom) {
        this.nom = nom;
        this.preferences = new ArrayList<>();
        this.relations = new HashSet<>();
    }

    /**
     * @return Le nom du colon.
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return La liste des préférences dans l'ordre.
     */
    public List<String> getPreferences() {
        return preferences;
    }

    /**
     * Définit la liste de préférences du colon.
     * @param prefs liste d'entiers
     */
    public void setPreferences(List<String> prefs) {
        this.preferences = prefs;
    }

    /**
     * @return L'ensemble des noms de colons que ce colon n'aime pas.
     */
    public Set<String> getRelations() {
        return relations;
    }

    /**
     * Ajoute un nom de colon "ennemi" dans les relations.
     * @param autreColon nom du colon "détesté"
     */
    public void ajouterRelation(String autreColon) {
    	relations.add(autreColon);
    }

    /**
     * Vérifie si ce colon a des préférences non vides.
     * @return true si la liste de prefs n'est pas vide
     */
    public boolean aDesPreferences() {
        return (preferences != null && !preferences.isEmpty());
    }
}
