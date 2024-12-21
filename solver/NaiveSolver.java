package solver;

import colony.Colon;
import colony.Colonie;
import java.util.*;

/**
 * Implémente l'algorithme naïf (partie 2)
 * adapté pour des ressources représentées par des chaînes (Strings).
 * 
 * Les préférences des colons sont des List<String>, 
 * par ex : ["o3","o9","o8",...]
 */
public class NaiveSolver implements Solver {

    private final Random rand = new Random();

    /**
     * Construit et renvoie une solution 
     * où chaque colon reçoit la première ressource (String) disponible 
     * qu'il préfère. Puis applique éventuellement des échanges aléatoires.
     */
    @Override
    public Map<String, String> solve(Colonie colonie) {
        // 1) Construction naïve
        Map<String, String> currentSolution = buildInitialSolution(colonie);

        // 2) (Optionnel) un certain nombre d'échanges pour essayer d'améliorer le coût
        int k = 100; 
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

    /**
     * Calcule le nombre de colons jaloux dans une solution donnée.
     * Un colon c est jaloux si c "n'aime pas" un colon e 
     * et préfère la ressource de e à la sienne (ordre dans sa liste de prefs).
     * 
     * @param solution  map <nomColon, nomRessource>
     * @param colonie   la colonie
     * @return le nombre total de colons jaloux
     */
    @Override
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
     * Construit la solution initiale "naïve" : 
     * chaque colon reçoit la première ressource encore disponible 
     * dans son ordre de préférences.
     * 
     * @param colonie la colonie
     * @return une map <nomColon, nomRessource>
     */
    private Map<String, String> buildInitialSolution(Colonie colonie) {
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

    /**
     * Échange la ressource de deux colons (c1 et c2) 
     * dans une solution existante, renvoie une nouvelle map.
     * 
     * @param original solution existante
     * @param c1 nom du premier colon
     * @param c2 nom du second colon
     * @return une copie modifiée de la solution (échange de ressources)
     */
    private Map<String, String> swap(Map<String, String> original, String c1, String c2) {
        // Copie la map
        Map<String, String> newSol = new HashMap<>(original);
        // Échange
        String tmp = newSol.get(c1);
        newSol.put(c1, newSol.get(c2));
        newSol.put(c2, tmp);
        return newSol;
    }
}
