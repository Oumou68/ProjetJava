package test;

import colony.Colonie;
import colony.Colon;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ColonieTest {

    @Test
    public void testColonieInitialization() {
        Colonie colonie = new Colonie(3);
        assertEquals(3, colonie.getColons().size(), "La colonie devrait avoir 3 colons.");
        assertEquals("A", colonie.getColons().get(0).getNom(), "Le premier colon doit être 'A'.");
    }

    @Test
    public void testAddRelation() {
        Colonie colonie = new Colonie(2);
        colonie.ajouterRelation("A", "B");

        Colon colonA = colonie.findColonByName("A");
        Colon colonB = colonie.findColonByName("B");

        assertTrue(colonA.getRelations().contains("B"), "Colon A devrait détester B.");
        assertTrue(colonB.getRelations().contains("A"), "Colon B devrait détester A.");
    }

    @Test
    public void testSetPreferences() {
        Colonie colonie = new Colonie(1);
        Colon colon = colonie.findColonByName("A");
        colon.setPreferences(List.of("o1", "o2", "o3"));

        assertEquals(List.of("o1", "o2", "o3"), colon.getPreferences(), "Les préférences devraient correspondre.");
    }

    @Test
    public void testSetAndGetPreferences() {
        Colon colon = new Colon("A");
        colon.setPreferences(List.of("o1", "o2", "o3"));

        assertEquals(List.of("o1", "o2", "o3"), colon.getPreferences(), 
                     "Les préférences devraient correspondre à celles définies.");
    }

    @Test
    public void testPreferencesVides() {
        Colon colon = new Colon("B");

        assertFalse(colon.aDesPreferences(), 
                    "Un colon sans préférences définies devrait retourner false.");
    }

    @Test
    public void testRessourceInexistanteDansPreferences() {
        Colon colon = new Colon("C");
        colon.setPreferences(List.of("o1", "o2"));

        assertFalse(colon.getPreferences().contains("o3"), 
                    "Une ressource inexistante ne devrait pas être présente dans les préférences.");
    }

}
