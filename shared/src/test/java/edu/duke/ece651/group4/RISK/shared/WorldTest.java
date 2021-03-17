package edu.duke.ece651.group4.RISK.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

public class WorldTest {
    /**
     * Error messages
     */
    final String INDIVISIBLE_MSG = "Number of territories is not divisible by number of groups.";
    final String TERRITORY_NOT_FOUND_MSG = "The territory specified by the name '%s' is not found.";
    final String NON_POSITIVE_MSG = "Number of groups should be positive.";

    /**
     * Creates a world for test. 
     * Territory layout is the same as that on Evolution 1 requirements.
     * N-----M--O--G
     * |   /  |/ \ | 
     * |  /   S ---M     
     * |/   / |  \ |
     * E------R----H
     * Can specify territory names and troops stationed on the territories.
     * @param names is an array of territory names. 
     * @param troops is the corresponding troops on these territories.
     * @return a world object.
     */
    public World createWorldSimple() {
        World world = new World();
        Territory t1 = new Territory("1");
        Territory t2 = new Territory("2");
        world.addTerritory(t1);
        world.addTerritory(t2);
        world.addConnection(t1, t2);

        return world;
    }

    PrintStream out = null;
    Reader inputReader = null;
    Player green = new TextPlayer(out, inputReader, "green");
    Player red = new TextPlayer(out, inputReader, "red");
    Player blue = new TextPlayer(out, inputReader, "blue");

    String names[] = 
            "Narnia, Midkemia, Oz, Gondor, Mordor, Hogwarts, Scadrial, Elantris, Roshar".split(", ");
    Troop troopsConnected[] = {new Troop(10, green), new Troop(12, green), new Troop(8, green),
                        new Troop(13, red), new Troop(14, red), new Troop(3, red),
                        new Troop(5, blue), new Troop(6, blue), new Troop(3, blue)};
    Troop troopsSeparated[] = {new Troop(10, green), new Troop(12, red), new Troop(8, green),
                        new Troop(13, red), new Troop(14, red), new Troop(3, green),
                        new Troop(5, blue), new Troop(6, blue), new Troop(3, green)};
    Troop troopsSamePlayer[] = {new Troop(10, red), new Troop(12, red), new Troop(8, red),
                        new Troop(13, red), new Troop(14, red), new Troop(3, red),
                        new Troop(5, red), new Troop(6, red), new Troop(3, red)};
    /**
     * Creates a test world. Troop not specified. Same as the one on Evolution 1 requirements.
     */
    public World createWorld(Troop... troops) {
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

        for (int i = 0; i < troops.length; i++) {
            world.stationTroop(names[i], troops[i]);
        }
        
        return world;
    }

    @Test
    public void testCreation() {
        World randomWorldWithSeed = new World(9, new Random(0));
        World randomWorld = new World(9);
    }

    @Test
    public void testClone() {
        World world = createWorldSimple();
        World worldClone = world.clone();

        assertEquals(world, worldClone);
        assertEquals(worldClone.getAllTerritories(), 
            new ArrayList<Territory>(Arrays.asList(new Territory("1"), new Territory("2"))));
        assertEquals(worldClone.getAdjacents("1"), 
            new ArrayList<Territory>(Arrays.asList(new Territory("2"))));
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
        World world = createWorld();

        List<Territory> expected = new ArrayList<>();
        for (String name : names) {
            expected.add(new Territory(name));
        }

        assertTrue(world.getAllTerritories().containsAll(expected));
    }

    @Test
    public void testCheckIfAdjacent() {
        World world = createWorld();

        assertTrue(world.checkIfAdjacent("Narnia", "Midkemia"));
        assertTrue(world.checkIfAdjacent("Narnia", "Elantris"));
        assertFalse(world.checkIfAdjacent("Narnia", "Oz"));

        assertTrue(world.checkIfAdjacent("Scadrial", "Roshar"));
        assertFalse(world.checkIfAdjacent("Scadrial", "Gondor"));
    }

    @Test
    public void testFindTerritory() {
        World world = createWorld();

        assertEquals(world.findTerritory("Narnia"), new Territory("Narnia"));
        assertThrows(NoSuchElementException.class, () -> world.findTerritory("Remnants"));
        assertThrows(NoSuchElementException.class, () -> world.findTerritory(""));
    }

    @Test
    public void testDivideTerritories() {
        // test if exceptions are thrown correctly
        
        World world = createWorld(); // evolution 1 example world, has 9 territories
        
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
        World world = createWorldSimple();
        Territory t1 = new Territory("1");
        Territory t2 = new Territory("2");
        assertTrue(world.checkIfAdjacent(t1, t2));
    }

    @Test
    public void testCheckLost() {
        World world1 = createWorld(troopsConnected);
        assertFalse(world1.checkLost("red"));
        assertFalse(world1.checkLost("blue"));
        assertFalse(world1.checkLost("green"));

        World world2 = createWorld(troopsSamePlayer);
        assertFalse(world2.checkLost("red"));
        assertTrue(world2.checkLost("blue"));
        assertTrue(world2.checkLost("green"));
    }

    @Test
    public void testIsGameEnd() {
        World world1 = createWorld(troopsConnected);
        assertFalse(world1.isGameEnd());

        World world2 = createWorld(troopsSamePlayer);
        assertTrue(world2.isGameEnd());
    }

    @Test
    public void testGetWinner() {
        World world1 = createWorld(troopsConnected);
        assertEquals(null, world1.getWinner());

        World world2 = createWorld(troopsSamePlayer);
        assertEquals("red", world2.getWinner());
    }

    /*
    @Test
    public void testEqualsRandom() { // can Random objects be equal?
        Random r1 = new Random(0);
        Random r2 = new Random(0);
        assertEquals(r1, r2);
    }
    */
}
