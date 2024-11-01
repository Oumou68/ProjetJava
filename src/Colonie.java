package src;

import java.util.*;

public class Colonie {
    private Map<Character, Colon> colons;
    private Map<Colon, List<Colon>> relations;
    private List<Integer> ressources;

    public Colonie(int nbColons) {
        this.colons = new HashMap<>();
        this.relations = new HashMap<>();
        this.ressources = new ArrayList<>();
        for (int i = 1; i <= nbColons; i++) {
            ressources.add(i);
        }
        initialiserColons(nbColons);
    }

    private void initialiserColons(int nbColons) {
        for (int i = 0; i < nbColons; i++) {
            char nom = (char) ('A' + i);
            colons.put(nom, new Colon(nom, new ArrayList<>()));
        }
    }

    public void ajouterRelation(char nom1, char nom2) {
        Colon c1 = colons.get(nom1);
        Colon c2 = colons.get(nom2);
        if (c1 != null && c2 != null) {
            relations.computeIfAbsent(c1, k -> new ArrayList<>()).add(c2);
            relations.computeIfAbsent(c2, k -> new ArrayList<>()).add(c1);
        } else {
            System.out.println("Colon(s) invalide(s) !");
        }
    }

    public void ajouterPreferences(char nom, List<Integer> preferences) {
        Colon colon = colons.get(nom);
        if (colon != null && preferences.size() == ressources.size()) {
            colon.getPreferences().addAll(preferences);
        } else {
            System.out.println("Colon ou préférences invalide(s) !");
        }
    }

    public void affectationNaive() {
        Set<Integer> ressourcesAttribuees = new HashSet<>();
        for (Colon colon : colons.values()) {
            for (int ressource : colon.getPreferences()) {
                if (!ressourcesAttribuees.contains(ressource)) {
                    colon.setRessourceAttribuee(ressource);
                    ressourcesAttribuees.add(ressource);
                    break;
                }
            }
        }
        afficherAffectation();
    }

    public int calculerJalousie() {
        int jaloux = 0;
        for (Map.Entry<Colon, List<Colon>> entry : relations.entrySet()) {
            Colon colon = entry.getKey();
            int ressource = colon.getRessourceAttribuee();
            for (Colon ennemi : entry.getValue()) {
                if (colon.getPreferences().indexOf(ressource) > colon.getPreferences().indexOf(ennemi.getRessourceAttribuee())) {
                    jaloux++;
                    break;
                }
            }
        }
        return jaloux;
    }

    public void echangerRessources(char nom1, char nom2) {
        Colon c1 = colons.get(nom1);
        Colon c2 = colons.get(nom2);
        if (c1 != null && c2 != null) {
            int temp = c1.getRessourceAttribuee();
            c1.setRessourceAttribuee(c2.getRessourceAttribuee());
            c2.setRessourceAttribuee(temp);
        } else {
            System.out.println("Colon(s) invalide(s) !");
        }
    }

    public void afficherAffectation() {
        for (Colon colon : colons.values()) {
            System.out.println(colon);
        }
    }

    public boolean verifierPreferencesCompletes() {
        for (Colon colon : colons.values()) {
            if (colon.getPreferences().size() != ressources.size()) {
                System.out.println("Préférences manquantes pour le colon " + colon.getNom());
                return false;
            }
        }
        return true;
    }
}
