package io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Gère la sauvegarde (partie 2) d'une solution (colon->ressource).
 */
public class ColonyFileWriter {

    /**
     * Sauvegarde la solution dans un fichier, format:
     *   nomColon:ressource
     * @param path chemin du fichier
     * @param solution Map <String, Integer> par ex. => "A":1
     * @throws IOException si erreur d'écriture
     */
    public void saveSolution(String path, Map<String, String> solution) throws IOException {
        try (FileWriter fw = new FileWriter(path)) {
            for (Map.Entry<String, String> entry : solution.entrySet()) {
                fw.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
        }catch(IOException e){
            System.out.println("Erreur lors de l'ecriture");
        }
    }
}
