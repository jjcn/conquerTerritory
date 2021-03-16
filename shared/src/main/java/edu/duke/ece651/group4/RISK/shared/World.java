package edu.duke.ece651.group4.RISK.shared;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.util.NoSuchElementException;

import java.util.Random;

import java.io.Serializable;

/**
 * This class models the world which constitutes 
 * a certain number of territories connected with each other.
 */
public class World implements Serializable {
    /**
     * Error messages
     */
    final String NOT_ENOUGH_TROOP_MSG = "The troop size you want is larger than that on this territory.";
    final String INDIVISIBLE_MSG = "Number of territories is not divisible by number of groups.";
    final String TERRITORY_NOT_FOUND_MSG = "The territory specified by the name '%s' is not found.";
    final String NOT_POSITIVE_MSG = "Number of groups should be positive.";
    
    /**
     * All territories in the world. Implemented with a graph structure.
     */
    public Graph<Territory> territories;
    private final OrderChecker basicOrderChecker;

    public World() {
        this(new Graph<Territory>());
    }

    public World(Graph<Territory> terrs) {
        territories = terrs;
        basicOrderChecker = new OrderChecker();
    }
    
    /**
     * Creates a world with only its number of territories specified.
     * Territory names are: 1, 2, 3, ... 
     * Number of connections is propotional to number of territories.
     * @param numTerrs is the number of territories.
     */
    public World(int numTerrs, Random rand) {
        this();
        for (int i = 1; i <= numTerrs; i++) {
            addTerritory(new Territory(String.format("%d", i)));
        }
        territories.createRandomEdges(numTerrs, rand);
    }

    public World(int numTerrs) {
        this(numTerrs, new Random());
    }

    /**
     * Get all the territories in the world.
     * @return a list of all territories in the world.
     */
    public List<Territory> getAllTerritories() {
        return territories.getVertices();
    }

    /**
     * Get all the territories that are adjacent to a certain territory.
     * @param key is the territory to search adjacents.
     * @return a list of adjacent territories.
     */
    public List<Territory> getAdjacents(Territory key) {
        return territories.getAdjacentVertices(key);
    }

    /**
     * Set a random seed to a territory.
     * @param terrName is the territory to set random seed.
     * @param seed is a random seed.
     */
    public void setRandom(Territory terr, Random seed) {
        terr.setRandom(seed);
    }

    public void setRandom(String terrName, Random seed) {
        findTerritory(terrName).setRandom(seed);
    }

    /**
     * Add a territory to the world.
     * @param terr is the territory to add.
     */
    public void addTerritory(Territory terr) {
        territories.addVertex(terr);
    }

    /**
     * Add connection between two adjacent territories so that they become adjacent.
     * @param terr1 is a territory.
     * @param terr2 is the other territory.
     */
    public void addConnection(Territory terr1, Territory terr2) {
        territories.addEdge(terr1, terr2);
    }

    /**
     * Add connection between two adjacent territories by the name of these territories.
     * @param name1 is the name of a territory.
     * @param name2 is the name of the other territory.
     */
    public void addConnection(String name1, String name2) {
        addConnection(findTerritory(name1), findTerritory(name2));
    }

    /**
     * Station troop to a territory.
     * @param terrName is the territory name.
     * @param population is the population of the troop.
     */   
    public void stationTroop(String terrName, int population) {
        Territory terr = findTerritory(terrName);
        terr.initializeTerritory(population, terr.getOwner());
    }

    public void stationTroop(String terrName, Troop troop) {
        Territory terr = findTerritory(terrName);
        terr.initializeTerritory(troop.checkTroopSize(), troop.getOwner());
    }

    /**
     * Check if two territories are adjacent to each other
     * @param terr1 is a territory.
     * @param terr2 is the other territory.
     * @return true, if two territories are adjacent;
     *         false, if not.
     */
    public boolean checkIfAdjacent(Territory terr1, Territory terr2) {
        return territories.isAdjacent(terr1, terr2);
    }

    /**
     * Check if two territories are adjacent to each other by their names. 
     * @param name1 is the name of one territory to check.
     * @param name2 is the name of thr other territory.
     * @return true, if two territories are adjacent;
     *         false, if not.
     */
    public boolean checkIfAdjacent(String name1, String name2) {
        return checkIfAdjacent(findTerritory(name1), findTerritory(name2));
    }

    /**
     * Finds a territory by its name. 
     * If the territory exists, returns that territory of that name.
     * If not, an exception will be thrown.
     * @param terrName is the territory name to search.
     * @return the specified territory.
     */
    public Territory findTerritory(String terrName) {
        for (Territory terr : territories.getVertices()) {
            if (terr.getName().equals(terrName)) {
                return terr;
            }
        }
        throw new NoSuchElementException(String.format(TERRITORY_NOT_FOUND_MSG, terrName));
    }

    /**
     * Move a troop to a different a territory. Owner of the troop is not checked.
     * Also checks if the troop is valid to send from the starting territory.
     * @param start is the territory the troop starts from.
     * @param troop is the troop to move.
     * @param end is the territory the troop ends in.
     */
    public void moveTroop(Territory start, Troop troop, Territory end) {
        if (start.checkPopulation() < troop.checkTroopSize()) {
            throw new IllegalArgumentException(NOT_ENOUGH_TROOP_MSG);
        }
        start.sendOutTroop(troop);
        end.sendInTroop(troop);
    }

    /**
     * Send a troop to a territory with different owner, in order to engage in battle. 
     * Also checks if the troop is valid to send from the starting territory.
     * @param start is the territory the troop starts from.
     * @param troop is the troop to send.
     * @param end is the territory the troop ends in.
     */
    public void attackATerritory(Territory start, Troop troop, Territory end) {
        if (start.checkPopulation() < troop.checkTroopSize()) {
            throw new IllegalArgumentException(NOT_ENOUGH_TROOP_MSG);
        }
        start.sendOutTroop(troop);
        end.sendInEnemyTroop(troop);
    }

    /**
     * Iterate over all territories around the world, and do battles on them.
     */
    public void doAllBattles() {
        for (Territory terr : territories.getVertices()) {
            terr.doBattles(); // FIXIT: doOneBattle requires a Troop argument
        }
    }

    /**
     * Divide territories into n equal groups.
     * @param nGroup is the number of groups the world is divided into.
     * @return a HashMap. The mapping being: group number -> grouped territories.
     * NOTE: group number starts from 0.
     */
    public Map<Integer, List<Territory>> divideTerritories(int nGroup, Random rand) {
        // check if it is an integer > 0
        if (nGroup <= 0) {
            throw new IllegalArgumentException(NOT_POSITIVE_MSG);
        } 
        // check if size / n is an integer
        else if (territories.size() % nGroup != 0) {
            throw new IllegalArgumentException(INDIVISIBLE_MSG);
        }
        // create a array of indices
        int nTerritories = territories.size();
        int randomInds[] = new int[nTerritories]; 
        for (int i = 0; i < nTerritories; i++) {
            randomInds[i] = i;
        }
        // shuffle indices to create random groups
        Shuffler shuffler = new Shuffler(rand);
        shuffler.shuffle(randomInds);
        // divide
        List<Territory> terrList = territories.getVertices();
        Map<Integer, List<Territory>> groups = new HashMap<>(); 
        int nInGroup = nTerritories / nGroup;
        for (int group = 0; group < nGroup; group++) {
            List<Territory> terrs = new ArrayList<>();
            for (int i = 0; i < nInGroup; i++) {
                terrs.add(terrList.get(randomInds[group * nInGroup + i]));
            }
            groups.put(group, terrs);
        }

        return groups;
    }
    
    /**
     * Checks if an order is legal.
     * @param order is the order to check.
     * @return null, if the order is legal;
     *         a String indicating the problem, if not.
     */
    public String checkBasicOrder(BasicOrder order) {
        return basicOrderChecker.checkOrder(order, this);
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other.getClass().equals(getClass())) {
            World otherWorld = (World)other;
            return otherWorld.territories.equals(territories);
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        // TODO: change placeholder
        return "World.toString placeholder";
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}

