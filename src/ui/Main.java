package ui;

import colony.Colonie;
import io.ColonyFileParser;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Classe principale (main).
 * - Si un fichier est passé en argument => partie 2 (lecture + menu 3 options).
 * - Sinon => partie 1 (mode manuel).
 */

public class Main {

    public static void main(String[] args) {
        Colonie colonie = null;

        // 1) Tenter la lecture du fichier (partie 2)
        if (args.length > 0) {
            String filePath = args[0];
            try {
                ColonyFileParser parser = new ColonyFileParser(filePath);
                // Le parser doit construire la colonie avec prefs en List<String>
                colonie = parser.parseFile();
                System.out.println("Colonie chargée depuis le fichier : " + filePath);
            } catch (FileNotFoundException e) {
                System.err.println("Fichier introuvable : " + e.getMessage());
                System.out.println("Arret du programme");
                return;
            } catch (IOException e) {
                System.err.println("Erreur de format/lecture : " + e.getMessage());
                System.out.println("Arret du programme");
                return;
            }
        }

        // 2) Si aucun arg => mode manuel (partie 1)
        if (colonie == null) {
            System.out.println("Mode manuel ...");
            BuildColony.runPartie1();
        } else {
            BuildColony.runPartie2(colonie);
        }
    }
}
