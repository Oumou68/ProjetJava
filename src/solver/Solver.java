package solver;

import colony.Colon;
import colony.Colonie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Classe utilitaire abstraitre pour un solveur :
 *  - solve() produit une affectation
 *  - computeCost() calcule le nombre de colons jaloux
 */
public abstract class Solver {

    /**
     * Crée une solution colon->ressource
     * @param colonie la colonie
     * @return Map <String, Integer>  (ex: "A":2, "B":1,...)
     */
    public abstract Map<String, String> solve(Colonie colonie);

    /**
     * Calcule le nombre de colons jaloux dans une solution donnée.
     * Un colon c est jaloux si c "n'aime pas" un colon e 
     * et préfère la ressource de e à la sienne (ordre dans sa liste de prefs).
     * 
     * @param solution  map <nomColon, nomRessource>
     * @param colonie   la colonie
     * @return le nombre total de colons jaloux
     */
    public int computeCost(Map<String, String> solution, Colonie colonie) {
        int jaloux = 0;

        for (Colon c : colonie.getColons()) {
            String cName = c.getNom();
            // Ressource attribuée à c dans la solution
            String cRes = solution.get(cName);
            if (cRes == null) continue;  // pas de ressource => on ignore

            // On récupère la liste de préférences (List<String>)
            List<String> prefsC = c.getPreferences();
            int indexC = prefsC.indexOf(cRes);
            if (indexC < 0) {
                // cRes ne figure pas dans la liste de c, 
                // ou c n'a pas de prefs => on peut ignorer ce cas ou le gérer
                continue; 
            }

            boolean isJaloux = false;
            // Pour chaque "ennemi" (colon qu'il n'aime pas)
            for (String ennemiName : c.getRelations()) {
                String eRes = solution.get(ennemiName);
                if (eRes == null) continue;  // l'ennemi n'a pas de ressource ?

                int indexE = prefsC.indexOf(eRes);
                // Si c préfère eRes (indexE < indexC) => c est jaloux
                if (indexE >= 0 && indexE < indexC) {
                    isJaloux = true;
                    break;
                }
            }
            if (isJaloux) {
                jaloux++;
            }
        }
        return jaloux;
    }
    
    /**
     * Échange la ressource de deux colons (c1 et c2) 
     * dans une solution existante, renvoie une nouvelle map.
     * 
     * @param original solution existante
     * @param c1 nom du premier colon
     * @param c2 nom du second colon
     * @return une copie modifiée de la solution (échange de ressources)
     */
    public Map<String, String> swap(Map<String, String> original, String c1, String c2) {
        // Copie la map
        Map<String, String> newSol = new HashMap<>(original);
        // Échange
        String tmp = newSol.get(c1);
        newSol.put(c1, newSol.get(c2));
        newSol.put(c2, tmp);
        return newSol;
    }
    
    /**
     * Construit la solution initiale "naïve" : 
     * chaque colon reçoit la première ressource encore disponible 
     * dans son ordre de préférences.
     * 
     * @param colonie la colonie
     * @return une map <nomColon, nomRessource>
     */
    public Map<String, String> buildInitialSolution(Colonie colonie) {
        Map<String, String> sol = new HashMap<>();
        // Ensemble des ressources déjà utilisées
        Set<String> used = new HashSet<>();

        for (Colon c : colonie.getColons()) {
            for (String pref : c.getPreferences()) {
                if (!used.contains(pref)) {
                    // On assigne pref à c
                    sol.put(c.getNom(), pref);
                    used.add(pref);
                    break; // on sort dès qu'on a trouvé une ressource dispo pour c
                }
            }
        }
        return sol;
    }
}
