package src;

import java.util.*;

public class Colonie {
    private final List<Colon> colons;  // Liste des colons
    private final int nombreDeColonies;

    public Colonie(int nombreDeColonies) {
        this.nombreDeColonies = nombreDeColonies;
        colons = new ArrayList<>();
        for (int i = 0; i < nombreDeColonies; i++) {
            colons.add(new Colon(String.valueOf((char)('A' + i)), nombreDeColonies));  // Crée un colon A, B, C, ...
        }
    }

    public List<Colon> getColons() {
        return colons;
    }

    public void ajouterRelation(String colon1, String colon2) {
        Colon c1 = findColonByNom(colon1);
        Colon c2 = findColonByNom(colon2);

        if (c1 != null && c2 != null) {
            c1.ajouterRelation(colon2);
            c2.ajouterRelation(colon1);
            System.out.println(colon1 + " et " + colon2 + " ne s'aiment pas.");
        } else {
            System.out.println("Un ou les deux colons n'existent pas.");
        }
    }

    private Colon findColonByNom(String nom) {
        for (Colon colon : colons) {
            if (colon.getNom().equals(nom)) {
                return colon;
            }
        }
        return null;
    }
    
    public boolean tousLesColonsOntPreferences() {
        for (Colon colon : colons) {
            if (!colon.aDesPreferences()) {
                return false;
            }
        }
        return true;
    }
}

