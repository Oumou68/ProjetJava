package test;

import colony.Colonie;
import solver.NaiveSolver;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class NaiveSolverTest {

    @Test
    public void testSolve() {
        Colonie colonie = new Colonie(3);
        colonie.findColonByName("A").setPreferences(List.of("o1", "o2", "o3"));
        colonie.findColonByName("B").setPreferences(List.of("o2", "o3", "o1"));
        colonie.findColonByName("C").setPreferences(List.of("o3", "o1", "o2"));

        NaiveSolver solver = new NaiveSolver();
        Map<String, String> solution = solver.solve(colonie);

        assertEquals(3, solution.size(), "Chaque colon devrait avoir une ressource.");
        assertTrue(solution.values().contains("o1"), "La ressource o1 devrait être assignée.");
        assertTrue(solution.values().contains("o2"), "La ressource o2 devrait être assignée.");
        assertTrue(solution.values().contains("o3"), "La ressource o3 devrait être assignée.");
    }
}
