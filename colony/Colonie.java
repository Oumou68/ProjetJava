package colony;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente la colonie : ensemble de colons (et éventuellement de ressources).
 */
public class Colonie {

    /** Liste de tous les colons. */
    private List<Colon> colons;

    /** Nombre total de colons (et de ressources). */
    private int nombreDeColonies;

    /**
     * Constructeur.
     * Par défaut, crée des colons nommés A, B, C... si n <= 26.
     * @param nombreDeColonies le nombre total (1..26)
     */
    public Colonie(int nombreDeColonies) {
        this.nombreDeColonies = nombreDeColonies;
        this.colons = new ArrayList<>();
        // On crée des colons A, B, C... => ex: 'A'+0 = 'A', 'A'+1 = 'B', etc.
        for (int i = 0; i < nombreDeColonies; i++) {
            char nom = (char) ('A' + i);
            colons.add(new Colon(String.valueOf(nom)));
        }
    }
    
    /**
     * Constructeur avec aucun colon
     */

    public Colonie() {
        this.colons = new ArrayList<>();
        this.nombreDeColonies = colons.size();
    }


    /**
     * @return la liste de tous les colons.
     */
    public List<Colon> getColons() {
        return colons;
    }

    /**
     * @return le nombre de colons et donc ressources.
     */
    public int getNombreDeColonies() {
        return nombreDeColonies;
    }

    /**
     * Ajoute une relation "ne s'aiment pas" entre deux colons donnés par leurs noms.
     * @param colon1 nom de colon
     * @param colon2 nom de colon
     */
    public void ajouterRelation(String colon1, String colon2) {
        Colon c1 = findColonByNom(colon1);
        Colon c2 = findColonByNom(colon2);
        if (colon1.equals(colon2)) {
        	System.out.println("Entrez deux colons differents");
        }else if (c1 != null && c2 != null) {
            c1.ajouterRelation(colon2);
            c2.ajouterRelation(colon1);
            System.out.println("Relation ajoutée !\n"+colon1 + " et " + colon2 + " ne s'aiment pas.");
        } else {
            System.out.println("Erreur : un ou les deux colons n'existent pas.");
        }
    }

    /**
     * Cherche un colon par son nom (ex: "A").
     * @param nom nom du colon
     * @return l'objet Colon ou null si introuvable
     */
    private Colon findColonByNom(String nom) {
        for (Colon c : colons) {
            if (c.getNom().equals(nom)) {
                return c;
            }
        }
        return null;
    }

     /**
     * Ajoute un nouveau colon à la colonie, en créant un objet Colon
     * portant le nom donné.
     * Par exemple, si le fichier texte contient "colon(0).",
     * on invoquera addColon("0") pour ajouter le colon nommé "0".
     * 
     * @param nomColon nom du colon à créer (ex : "0", "A", "Colon7", etc.)
     * @throws IllegalArgumentException si un colon portant déjà ce nom existe
     */
    public void addColon(String nomColon) throws IllegalArgumentException {
        // Optionnel : on peut vérifier si le colon existe déjà
        if (findColonByName(nomColon) != null) {
            throw new IllegalArgumentException("Le colon '" + nomColon + "' existe déjà !");
        }
        
        // Création et ajout
        Colon nouveau = new Colon(nomColon);
        colons.add(nouveau);
        
        System.out.println("Colon '" + nomColon + "' ajouté à la colonie.");
    }
    
    /**
     * Recherche un colon par son nom.
     * @param nom nom du colon
     * @return l'objet Colon correspondant, ou null s'il n'existe pas
     */
    public Colon findColonByName(String nom) {
        for (Colon c : colons) {
            if (c.getNom().equals(nom)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Vérifie si tous les colons ont des préférences non vides.
     * @return true si aucun colon n'est sans prefs
     */
    public boolean tousLesColonsOntPreferences() {
        for (Colon c : colons) {
            if (!c.aDesPreferences()) {
                return false;
            }
        }
        return true;
    }
}
