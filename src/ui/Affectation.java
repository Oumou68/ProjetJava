package ui;

import colony.Colon;
import colony.Colonie;
import solver.Solver;

import java.util.*;

/**
 * Gère l'affectation en mode manuel (partie 1),
 * avec colon->ressource (String) au lieu d'entier.
 */
public class Affectation extends Solver {

    /** La colonie pour laquelle on fait l'affectation. */
    private final Colonie colonie;
    
    /**
     * Map qui associe le nom d'un colon (String) 
     * à la ressource (String) qui lui est affectée.
     */
    private final Map<String, String> affectation;

    /**
     * Constructeur.
     * @param colonie la colonie (dont les colons ont des prefs en List<String>).
     */
    public Affectation(Colonie colonie) {
        this.colonie = colonie;
        this.affectation = new HashMap<>();
    }

    /**
     * Affecte chaque colon à la première ressource (String) disponible
     * selon l'ordre de préférences de chaque colon.
     */
    public void affecterResources() {
        // Ensemble des ressources déjà utilisées
        Set<String> used = new HashSet<>();

        // Parcourir chaque colon et chercher la première ressource non utilisée
        for (Colon c : colonie.getColons()) {
            for (String pref : c.getPreferences()) {
                if (!used.contains(pref)) {
                    // Affecter cette ressource à ce colon
                    affectation.put(c.getNom(), pref);
                    used.add(pref);
                    break;  // passer au colon suivant
                }
            }
        }
    }

    /**
     * Affiche l'affectation finale (colon:ressource).
     */
    public void afficherAffecation() {
        for (Map.Entry<String, String> e : affectation.entrySet()) {
            System.out.println(e.getKey() + ":" + e.getValue());
        }
    }

    /**
     * Calcule le nombre de colons jaloux (partie 1).
     * Un colon c est jaloux s'il déteste un colon d 
     * et préfère la ressource de d à la sienne 
     * (comparaison via l'index dans sa liste de préférences).
     * 
     * @return le nombre de colons jaloux
     */
    public int calculerJalousie() {
        int jaloux = 0;

        // Pour chaque colon
        for (Colon c : colonie.getColons()) {
            // Récupérer la ressource assignée à c
            String cRes = affectation.get(c.getNom());
            if (cRes == null) continue;

            // Indicateur de jalousie
            boolean cIsJaloux = false;

            // Pour chaque colon "détesté" par c
            for (String dislikedName : c.getRelations()) {
                // Ressource du colon ennemi
                String dRes = affectation.get(dislikedName);
                if (dRes == null) continue;

                // Indice de la ressource de c vs. celle de l'ennemi
                List<String> prefs = c.getPreferences();
                int idxC = prefs.indexOf(cRes);
                int idxD = prefs.indexOf(dRes);

                // Si c préfère dRes à cRes (idxD < idxC), alors c est jaloux
                if (idxD >= 0 && idxD < idxC) {
                    cIsJaloux = true;
                    break;
                }
            }
            if (cIsJaloux) {
                jaloux++;
            }
        }
        return jaloux;
    }

    /**
     * Échange les ressources de deux colons (ex: "A", "B").
     * @param colon1 nom du premier colon
     * @param colon2 nom du second colon
     */
    public void echangerResources(String colon1, String colon2) {
        // Vérification
        if (!affectation.containsKey(colon1) || !affectation.containsKey(colon2)) {
            System.out.println("Impossible d'échanger: l'un des colons n'a pas de ressource.");
            return;
        }
        // Échange
        String temp = affectation.get(colon1);
        affectation.put(colon1, affectation.get(colon2));
        affectation.put(colon2, temp);
    }

	@Override
	public Map<String, String> solve(Colonie colonie) {
		// TODO Auto-generated method stub
		return null;
	}
}
