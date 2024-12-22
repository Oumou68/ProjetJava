package solver;

import colony.Colon;
import colony.Colonie;
import java.util.*;

/**
 * Implémente l'algorithme naïf (partie 2)
 * adapté pour des ressources représentées par des chaînes (Strings).
 * 
 * Les préférences des colons sont des List<String>, 
 */
public class NaiveSolver extends Solver {

    private final Random rand = new Random();

    /**
     * Construit et renvoie une solution 
     * où chaque colon reçoit la première ressource (String) disponible 
     * qu'il préfère. Puis applique éventuellement des échanges aléatoires.
     * @return une map<String,String>
     */
    @Override
    public Map<String, String> solve(Colonie colonie) {
        // 1) Construction naïve
        Map<String, String> currentSolution = buildInitialSolution(colonie);

        // 2) un certain nombre d'échanges pour essayer d'améliorer le coût
        int k = 50; 
        for (int i = 0; i < k; i++) {
            List<Colon> all = colonie.getColons();
            Colon c1 = all.get(rand.nextInt(all.size()));
            Colon c2 = all.get(rand.nextInt(all.size()));
            if (c1 == c2) continue;

            // On crée une nouvelle solution en échangeant les ressources de c1 et c2
            Map<String, String> newSol = swap(currentSolution, c1.getNom(), c2.getNom());

            // Si la nouvelle solution a un coût plus faible, on la garde
            if (computeCost(newSol, colonie) < computeCost(currentSolution, colonie)) {
                currentSolution = newSol;
            }
        }

        return currentSolution;
    }  
}
