package test;

import colony.Colonie;
import io.ColonyFileParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ColonyFileParserTest {

    @Test
    public void testParseFile() throws IOException {
        String filePath = "src/equipage1";
        
        ColonyFileParser parser = new ColonyFileParser(filePath);

        Colonie colonie = parser.parseFile();
        assertNotNull(colonie, "La colonie ne devrait pas être null.");
        assertEquals(10, colonie.getColons().size(), "La colonie devrait contenir 10 colons.");
    }

    @Test
    public void testParseFileWithError() {
        String invalidFilePath = "src/invalid_colonie";

        assertThrows(IOException.class, () -> {
            ColonyFileParser parser = new ColonyFileParser(invalidFilePath);
            parser.parseFile();
        }, "Une exception devrait être levée pour un fichier invalide.");
    }
}
