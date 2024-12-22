package solver;

import colony.Colon;
import colony.Colonie;
import java.util.*;

/**
 * Implémente l'algorithme de recuit simulé pour améliorer la répartition
 * des ressources tout en minimisant le nombre de colons jaloux.
 */
public class SimulatedAnnealingSolver extends Solver {

    private final Random rand = new Random();

    /**
     * Résolution avec l'algorithme de recuit simulé.
     * @param colonie La colonie à résoudre.
     * @return Une Map représentant l'affectation colon -> ressource.
     */
    @Override
    public Map<String, String> solve(Colonie colonie) {
        // Générer une solution initiale naïve
        Map<String, String> currentSolution = buildInitialSolution(colonie);
        Map<String, String> bestSolution = new HashMap<>(currentSolution);
        
        int currentCost = computeCost(currentSolution, colonie);
        int bestCost = currentCost;

        double temperature = 1000.0;  // Température initiale
        double coolingRate = 0.995;  // Taux de refroidissement

        while (temperature > 1) {
            // Générer une nouvelle solution en échangeant deux ressources
            Map<String, String> newSolution = generateNeighbor(currentSolution);
            int newCost = computeCost(newSolution, colonie);

            // Accepter la nouvelle solution selon la probabilité de Boltzmann
            if (acceptanceProbability(currentCost, newCost, temperature) > rand.nextDouble()) {
                currentSolution = new HashMap<>(newSolution);
                currentCost = newCost;
            }

            // Mise à jour de la meilleure solution
            if (newCost < bestCost) {
                bestSolution = new HashMap<>(newSolution);
                bestCost = newCost;
            }

            // Refroidir
            temperature *= coolingRate;
        }

        return bestSolution;
    }

    /**
     * Génère une solution voisine en échangeant les ressources de deux colons.
     * @param solution La solution actuelle.
     * @return Une nouvelle solution modifiée.
     */
    private Map<String, String> generateNeighbor(Map<String, String> solution) {
        Map<String, String> neighbor = new HashMap<>(solution);

        // Sélectionner deux colons au hasard
        List<String> colons = new ArrayList<>(solution.keySet());
        String colon1 = colons.get(rand.nextInt(colons.size()));
        String colon2 = colons.get(rand.nextInt(colons.size()));

        // Échanger leurs ressources
        String temp = neighbor.get(colon1);
        neighbor.put(colon1, neighbor.get(colon2));
        neighbor.put(colon2, temp);

        return neighbor;
    }

    /**
     * Calcule la probabilité d'accepter une solution pire.
     * @param currentCost Coût de la solution actuelle.
     * @param newCost Coût de la nouvelle solution.
     * @param temperature Température actuelle.
     * @return Une probabilité entre 0 et 1.
     */
    private double acceptanceProbability(int currentCost, int newCost, double temperature) {
        if (newCost < currentCost) {
            return 1.0;
        }
        return Math.exp((currentCost - newCost) / temperature);
    }

    /**
     * Génère une solution initiale naïve :
     * chaque colon reçoit la première ressource encore disponible
     * selon son ordre de préférences.
     * @param colonie La colonie.
     * @return Une Map contenant l'affectation initiale colon -> ressource.
     */
    private Map<String, String> buildInitialSolution(Colonie colonie) {
        Map<String, String> solution = new HashMap<>();
        Set<String> usedResources = new HashSet<>();

        for (Colon c : colonie.getColons()) {
            for (String pref : c.getPreferences()) {
                if (!usedResources.contains(pref)) {
                    solution.put(c.getNom(), pref);
                    usedResources.add(pref);
                    break;
                }
            }
        }

        return solution;
    }
}
