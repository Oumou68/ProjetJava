package src;

import java.util.List;

public class Colon {
    private char nom;
    private List<Integer> preferences;
    private int ressourceAttribuee;

    public Colon(char nom, List<Integer> preferences) {
        this.nom = nom;
        this.preferences = preferences;
        this.ressourceAttribuee = -1; // Non attribuée par défaut
    }

    public char getNom() {
        return nom;
    }

    public List<Integer> getPreferences() {
        return preferences;
    }

    public int getRessourceAttribuee() {
        return ressourceAttribuee;
    }

    public void setRessourceAttribuee(int ressourceAttribuee) {
        this.ressourceAttribuee = ressourceAttribuee;
    }

    @Override
    public String toString() {
        return nom + ": " + ressourceAttribuee;
    }
}
