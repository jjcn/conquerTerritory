package edu.duke.ece651.group4.RISK.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class WorldTest {

    @Test
    public void testAddTerritory() {
        World world = new World();
        Territory t1 = new Territory("1");
        Territory t2 = new Territory("2");
        world.addTerritory(t1);
        world.addTerritory(t2);
        world.addConnection(t1, t2);
        
        List<Territory> expected = new ArrayList<>();
        expected.add(t1);
        expected.add(t2);

        assertTrue(world.getAllTerritories().containsAll(expected));
    }
}
