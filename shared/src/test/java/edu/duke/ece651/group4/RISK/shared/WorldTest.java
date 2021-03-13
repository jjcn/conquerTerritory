package edu.duke.ece651.group4.RISK.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

public class WorldTest {
    /**
     * Error messages
     */
    final String INDIVISIBLE_MSG = "Number of territories is not divisible by number of groups.";
    final String TERRITORY_NOT_FOUND_MSG = "The territory specified by the name '%s' is not found.";
    final String NON_POSITIVE_MSG = "Number of groups should be positive.";

    public World createWorld1() {
        World world = new World();
        Territory t1 = new Territory("1");
        Territory t2 = new Territory("2");
        world.addTerritory(t1);
        world.addTerritory(t2);
        world.addConnection(t1, t2);

        return world;
    }

    String[] names = 
        "Narnia, Midkemia, Oz, Gondor, Mordor, Hogwarts, Scadrial, Elantris, Roshar".split(", ");

    /**
     * Creates a test graph. Same as the one on Evolution 1 requirements.
     */
    public World createWorld2() {
        World world = new World();
        for (String name: names) {
            world.addTerritory(new Territory(name));
        }
        world.addConnection("Narnia", "Midkemia");
        world.addConnection("Narnia", "Midkemia");
        world.addConnection("Narnia", "Elantris");
        world.addConnection("Midkemia", "Elantris");
        world.addConnection("Midkemia", "Scadrial");
        world.addConnection("Midkemia", "Oz");
        world.addConnection("Oz", "Scadrial");
        world.addConnection("Oz", "Mordor");
        world.addConnection("Oz", "Gondor");
        world.addConnection("Gondor", "Mordor");
        world.addConnection("Elantris", "Scadrial");
        world.addConnection("Elantris", "Roshar");
        world.addConnection("Scadrial", "Roshar");
        world.addConnection("Scadrial", "Hogwarts");
        world.addConnection("Scadrial", "Mordor");
        world.addConnection("Mordor", "Hogwarts");

        return world;
    }

    @Test
    public void testAddTerritory() {
        World world = new World();
        List<Territory> expected = new ArrayList<>();
        Territory t1 = new Territory("1");
        Territory t2 = new Territory("2");
        Territory t3 = new Territory("3");

        world.addTerritory(t1);
        expected.add(new Territory("1"));
        assertTrue(world.getAllTerritories().containsAll(expected));

        world.addTerritory(t2);
        expected.add(new Territory("2"));
        assertTrue(world.getAllTerritories().containsAll(expected));
        
        world.addTerritory(t3);
        expected.add(new Territory("4"));
        assertFalse(world.getAllTerritories().containsAll(expected));
    }

    @Test
    public void testAddConnection() {
        World world = new World();
        List<Territory> expected = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Territory t = new Territory(String.format("%s", i));
            world.addTerritory(t);
        }
        assertFalse(world.checkIfAdjacent("1", "2"));
        
        world.addConnection("1", "2");
        world.addConnection("1", "3");
        assertTrue(world.checkIfAdjacent("1", "2"));
        assertTrue(world.checkIfAdjacent("1", "3"));
        assertFalse(world.checkIfAdjacent("1", "4"));
        assertFalse(world.checkIfAdjacent("2", "3"));
        assertFalse(world.checkIfAdjacent("2", "4"));
        assertFalse(world.checkIfAdjacent("3", "4"));

        world.addConnection("3", "4");
        assertTrue(world.checkIfAdjacent("1", "2"));
        assertTrue(world.checkIfAdjacent("1", "3"));
        assertFalse(world.checkIfAdjacent("1", "4"));
        assertFalse(world.checkIfAdjacent("2", "3"));
        assertFalse(world.checkIfAdjacent("2", "4"));
        assertTrue(world.checkIfAdjacent("3", "4"));
    }

    @Test
    public void testGetAllTerritories() {
        World world = createWorld2();

        List<Territory> expected = new ArrayList<>();
        for (String name : names) {
            expected.add(new Territory(name));
        }

        assertTrue(world.getAllTerritories().containsAll(expected));
    }

    @Test
    public void testCheckIfAdjacent() {
        World world = createWorld2();

        assertTrue(world.checkIfAdjacent("Narnia", "Midkemia"));
        assertTrue(world.checkIfAdjacent("Narnia", "Elantris"));
        assertFalse(world.checkIfAdjacent("Narnia", "Oz"));

        assertTrue(world.checkIfAdjacent("Scadrial", "Roshar"));
        assertFalse(world.checkIfAdjacent("Scadrial", "Gondor"));
    }

    @Test
    public void testDivideTerritories() {
        // test if exceptions are thrown correctly
        World world = createWorld2(); // evolution 1 example world, has 9 territories
        assertThrows(IllegalArgumentException.class, () -> world.divideTerritories(-1), NON_POSITIVE_MSG);
        assertThrows(IllegalArgumentException.class, () -> world.divideTerritories(0), NON_POSITIVE_MSG);
        assertDoesNotThrow(() -> world.divideTerritories(1));
        assertThrows(IllegalArgumentException.class, () -> world.divideTerritories(2), INDIVISIBLE_MSG);
        assertDoesNotThrow(() -> world.divideTerritories(3));
        assertThrows(IllegalArgumentException.class, () -> world.divideTerritories(18), INDIVISIBLE_MSG);
    
        
        Map<Integer, List<Territory>> groups = world.divideTerritories(3);
        // test number of groups
        assertEquals(groups.size(), 3);
        // test keys
        assertEquals(groups.keySet(), new HashSet<Integer>(Arrays.asList(0, 1, 2)));
        // test number in each list, print names 
        for (int i = 0; i < groups.size(); i++) {
            assertEquals(groups.get(i).size(), 3);
            System.out.println(String.format("Group %d:", i));
            groups.get(i).forEach(e -> System.out.println(e.getName()));
            System.out.println();
        }
    }

    @Test
    public void testcheckAdjacent() {
        World world = createWorld1();
        Territory t1 = new Territory("1");
        Territory t2 = new Territory("2");
        assertTrue(world.checkIfAdjacent(t1, t2));
    }


    // below are the testcases for equals() in dependent classes
    @Test
    public void testEqualsTerritory() {
        Territory t0 = new Territory("1");
        Territory t1 = new Territory("1"); 
        Territory t2 = new Territory("2");
        assertEquals(t0, t1);
        assertNotEquals(t0, t2);
    }

    @Test
    public void testEqualsTroop() {
        Troop t1 = new Troop(0, null, new Random());
        Troop t2 = new Troop(0, null, new Random());
        assertEquals(t1, t2);
    }

    @Test
    public void testEqualsSoldier() {
        Soldier s1 = new Soldier();
        Soldier s2 = new Soldier();
        assertEquals(s1, s2);
    }

    @Test
    public void testEqualsRandom() { // can Random objects be equal?
        Random r1 = new Random();
        Random r2 = new Random();
        assertEquals(r1, r2);
    }
}
