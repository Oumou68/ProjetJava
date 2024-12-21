package solver;

import colony.Colonie;
import java.util.Map;

/**
 * Interface pour un solveur :
 *  - solve() produit une affectation
 *  - computeCost() calcule le nb de colons jaloux
 */
public interface Solver {

    /**
     * Crée une solution colon->ressource
     * @param colonie la colonie
     * @return Map <String, Integer>  (ex: "A":2, "B":1,...)
     */
    Map<String, String> solve(Colonie colonie);

    /**
     * Calcule le nb de colons jaloux.
     * @param solution la map colon->ressource
     * @param colonie la colonie
     * @return nombre de colons jaloux
     */
    int computeCost(Map<String, String> solution, Colonie colonie);
}
