package src;

import java.util.*;

public class Affectation {
    private Map<String, Integer> affectation;
    private Colonie colonie;

    public Affectation(Colonie colonie) {
        this.colonie = colonie;
        this.affectation = new HashMap<>();
    }

    public void affecterResources() {
        Set<Integer> availableResources = new HashSet<>();
        for (int i = 1; i <= colonie.getColons().size(); i++) {
            availableResources.add(i);
        }

        // Parcourir chaque colon et attribuer la ressource préférée encore disponible
        for (Colon colon : colonie.getColons()) {
            for (int pref : colon.getPreferences()) {
                if (availableResources.contains(pref)) {
                    affectation.put(colon.getNom(), pref);
                    availableResources.remove(pref);
                    break;
                }
            }
        }
    }

    public int calculerJalousie() {
        int jaloux = 0;
        for (Map.Entry<String, Integer> entry : affectation.entrySet()) {
            String colon = entry.getKey();
            int assignedResource = entry.getValue();
            Colon c = colonie.getColons().stream().filter(col -> col.getNom().equals(colon)).findFirst().get();
            Set<String> dislikedColons = c.getRelations();

            for (String dislikedColon : dislikedColons) {
                Colon disliked = colonie.getColons().stream().filter(col -> col.getNom().equals(dislikedColon)).findFirst().get();
                if (disliked.getPreferences().indexOf(assignedResource) < disliked.getPreferences().indexOf(affectation.get(dislikedColon))) {
                    jaloux++;
                    break;
                }
            }
        }
        return jaloux;
    }

    public void afficherAffecation() {
        for (Map.Entry<String, Integer> entry : affectation.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void echangerResources(String colon1, String colon2) {
        int temp = affectation.get(colon1);
        affectation.put(colon1, affectation.get(colon2));
        affectation.put(colon2, temp);
    }
}
