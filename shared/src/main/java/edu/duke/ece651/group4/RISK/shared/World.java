package edu.duke.ece651.group4.RISK.shared;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.util.Random;

import java.util.NoSuchElementException;

/**
 * This class models the world which constitutes a certain number of territories.
 */
public class World {
    /**
     * Error messages
     */
    final String INDIVISIBLE_MSG = "Number of territories is not divisible by number of groups.";
    final String TERRITORY_NOT_FOUND_MSG = "The territory specified by the name '%s' is not found.";
    final String NON_POSITIVE_MSG = "Number of groups should be positive.";

    /**
     * All territories in the world. Implemented with a graph structure.
     */
    public Graph<Territory> territories;

    public World() {
        this.territories = new Graph<Territory>();
    }

    public World(Graph<Territory> territories) {
        this.territories = territories;
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
     * Move a troop to a different a territory. Owner of the troop is not checked.
     * @param start is the territory the troop starts from.
     * @param troop is the troop to move.
     * @param end is the territory the troop ends in.
     */
    public void moveTroop(Territory start, Troop troop, Territory end) {
        start.sendOutTroop(troop);
        end.sendInTroop(troop);
    }

    /**
     * Send a troop to a territory with different owner, in order to engage in battle. 
     * @param start is the territory the troop starts from.
     * @param troop is the troop to send.
     * @param end is the territory the troop ends in.
     */
    public void attackATerritory(Territory start, Troop troop, Territory end) {
        start.sendOutTroop(troop);
        end.sendInEnemyTroop(troop);
    }

    /**
     * Iterate over all territories around the world, and do battles on them.
     */
    /*
    public void doAllBattles() {
        for (Territory terr : territories.getAllData()) {
            terr.doOneBattle(); // FIXIT: doOneBattle requires a Troop argument
        }
    }
    */

    /**
     * Divide territories into n equal groups.
     * @param nGroup is the number of groups the world is divided into.
     * @return a HashMap. The mapping being: group number -> grouped territories.
     * NOTE: group number starts from 0.
     */
    public Map<Integer, List<Territory>> divideTerritories(int nGroup) {
        // check if it is an integer > 0
        if (nGroup <= 0) {
            throw new IllegalArgumentException(NON_POSITIVE_MSG);
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
        shuffle(randomInds);
        // divide
        List<Territory> terrList = territories.getVertices();
        Map<Integer, List<Territory>> groups = new HashMap<>(); 
        int nInGroup = nTerritories / nGroup;
        for (int group = 0; group < nGroup; group++) {
            List<Territory> terrs = new ArrayList<>();
            for (int i = 0; i < nInGroup; i++) {
                terrs.add(terrList.get(group * nInGroup + i));
            }
            groups.put(group, terrs);
        }

        return groups;
    }

    /**
     * Shuffle an array by Fisher-Yates shuffle algorithm.
     * @param arr is the array to shuffle.
     */
    private void shuffle(int[] arr) {
        Random rand = new Random(); 
        for (int i = arr.length - 1; i >= 0; i--) {
            int randomNum = rand.nextInt(i + 1);
            int swap = arr[randomNum]; 
            arr[randomNum] = arr[i];
            arr[i] = swap;
        }
    }

    /**
     * Finds a territory by its name. 
     * If the territory exists, returns that territory specified by that name.
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
     * Get all the territories in the world.
     * @return A list of all territories in the world.
     */
    public List<Territory> getAllTerritories() {
        return territories.getVertices();
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
     * Set the owner of a teritory to a certain player.
     * @param terrName is the territory name.
     * @param ownerName is the player name.
     */
    /*
    public void setTerritoryOwner(String terrName, String ownerName) {
        Territory terr = findTerritory(terrName);
        terr.setOwner(ownerName); // FIXIT: no setOwner() function in Territory.java
    }
    */

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
        // TODO
        return "World.toString() placeholder";
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}

