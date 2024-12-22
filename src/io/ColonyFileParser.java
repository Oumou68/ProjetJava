package io;

import colony.Colonie;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Lit un fichier texte pour construire la colonie (Partie 2).
 */
public class ColonyFileParser {

    private final File inputFile;

    /**
     * Constructeur.
     * @param path chemin du fichier
     * @throws FileNotFoundException si le fichier n'existe pas
     */
    public ColonyFileParser(String path) throws FileNotFoundException {
        this.inputFile = new File(path);
        if (!inputFile.exists()) {
            throw new FileNotFoundException("Fichier introuvable : " + path);
        }
    }

    /**
     * Parse le fichier et construit la Colonie si tout est correct.
     * @return la Colonie
     * @throws IOException si le format est incorrect
     */
    public Colonie parseFile() throws IOException {
        List<String> lines = readAllLines();

        // 1) Lire d'abord tous les colon(...) => créer Colonie
        // 2) Lire ressource(...) => on peut ignorer si on gère les ressources en "1..n" 
        // 3) Lire deteste(...) 
        // 4) Lire preferences(...)

        int state = 0;  // 0=colon,1=ressource,2=deteste,3=preferences
        List<String> colonsNames = new ArrayList<>(); 
        List<String> detesteLines = new ArrayList<>();
        List<String> preferencesLines = new ArrayList<>();
        int lineIndex = 0;
        int ressources = 0;
        int preferences = 0;
        int deteste = 0;
        int nbcolons = 0;

        // Lecture
        for (String rawLine : lines) {
            lineIndex++;
            String line = rawLine.trim();
            if (line.isEmpty()) continue;

            // Vérifier si ça se termine par un point
            if (!line.endsWith(".")) {
                throw new IOException("Erreur syntaxe: pas de point final (ligne " + lineIndex + ").");
            }
            
            if ( line.charAt(line.length() - 2) != ')') {
            	throw new IOException("Erreur syntaxe: il manque une parenthese fermante (ligne "+lineIndex+").");
            }

            if (line.startsWith("colon(")) {
            	nbcolons ++;
                if (state > 0) {
                    throw new IOException("colon(...) hors de l'ordre attendu (ligne " + lineIndex + ").");
                }
                String inside = line.substring("colon(".length(), line.length()-2);
                if (inside.contains(",")) {
                    throw new IOException("colon(...) ne doit avoir qu'un seul argument (ligne " + lineIndex + ").");
                }
                colonsNames.add(inside);
                nbcolons ++;
            }
            else if (line.startsWith("ressource(")) {
            	ressources ++;
                if (state < 1) {
                    state = 1;
                }
                if (state > 1) {
                    throw new IOException("ressource(...) hors de l'ordre (ligne " + lineIndex + ").");
                }
                ressources ++;

            }
            else if (line.startsWith("deteste(")) {
            	deteste ++ ;
                if (state < 2) {
                	state = 2;  
                	if ( nbcolons != ressources) {
                		throw new IOException("nombre de ressources different du nombre de colons (ligne "+ lineIndex + ").");
                	}
                }
                if (state > 2) {
                    throw new IOException("deteste(...) hors de l'ordre (ligne " + lineIndex + ").");
                }
                detesteLines.add(line);
            }
            else if (line.startsWith("preferences(")) {
            	preferences ++;
                if (state < 3) {
                	state = 3;
                }
                preferencesLines.add(line);
               
            }
            else {
                throw new IOException("Mot clé inconnu (ligne " + lineIndex + ").");
            }
        }
        

        // Création de la colonie
        if (colonsNames.isEmpty()) {
            throw new IOException("Aucun colon(...) trouvé !");
        }
        int n = colonsNames.size();
        if (n > 26) {
            throw new IOException("Trop de colons (>26) !");
        }
        
        
        
       
        // On construit la Colonie
        Colonie colony = new Colonie();

        for (String colons : colonsNames){
            colony.addColon(colons);
        }

        // 2) deteste(...)
        for (String dLine : detesteLines) {
            // ex: deteste(nom1,nom2).
            String inside = dLine.substring("deteste(".length(), dLine.length()-2);
            String[] parts = inside.split(",");
            if (parts.length != 2) {
                throw new IOException("deteste(...) doit avoir 2 arguments. (ligne " +(int)(nbcolons +detesteLines.indexOf(dLine)+1)+").");
            }
            String c1 = parts[0].trim();
            String c2 = parts[1].trim();
            colony.ajouterRelation(c1, c2);
        }

        // 3) preferences(...)
        for (String pLine : preferencesLines) {
            // ex: preferences(nomColon,1,2,3,...).
            String inside = pLine.substring("preferences(".length(), pLine.length()-2);
            String[] parts = inside.split(",");
            if (parts.length != preferences + 1) {
                throw new IOException("preferences(...) pas assez d'arguments. (ligne "+ (int)(nbcolons+deteste+preferencesLines.indexOf(pLine)+1)+").");
            }
            String cName = parts[0].trim();

            // On retrouve le colon (ex: "A")
            colony.getColons().stream()
                   .filter(col -> col.getNom().equals(cName))
                   .findFirst()
                   .ifPresentOrElse(col -> {
                       List<String> prefs = new ArrayList<>();
                       for (int i = 1; i < parts.length; i++) {
                           try {
                               String val = parts[i].trim();
                               prefs.add(val);
                           } catch (NumberFormatException e) {
                               System.out.println("Valeur non entiere");// Valeur non entière => on skip ou on could throw new RuntimeException
                           }
                       }
                       col.setPreferences(prefs);
                   }, () -> {
                       // colon non trouvé => possible erreur
                   });
        }

        // Vérifier si tous les colons ont des prefs
        if (!colony.tousLesColonsOntPreferences()) {
            throw new IOException("Certains colons n'ont pas de préférences !");
        }

        

        return colony;
    }

    /** Lit toutes les lignes du fichier. 
     * @return une liste de chaque lignes lues
     * @throws IOException si une ligne n'est pas lue
     * */
    
    private List<String> readAllLines() throws IOException {
        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
        }
        return result;
    }
}
