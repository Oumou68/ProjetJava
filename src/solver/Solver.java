package solver;

import colony.Colon;
import colony.Colonie;

import java.util.List;
import java.util.Map;

/**
 * Classe abstraitre pour un solveur :
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
}
