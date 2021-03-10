package edu.duke.group4.RISK;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

public class World {
    public Graph<Territory> territories;

    public World() {
        this.territories = new Graph<Territory>();
    }

    public addTerritory(Territory terr) {
        territories.addVertex(new Vertex(terr));
    }

    /**
     * Move a troop to a different a territory.
     * @param start is the territory troop starts from.
     * @param troop is the troop to move.
     * @param end is the territory troop ends in.
     */
    public void moveTroop(Territory start, Troop troop, Territory end) {
        start.sendOutTroop(troop);
        end.sendInTroop(troop);
    }

    /**
     * Iterate over all territories around the world, and do battles on them.
     */
    public void doAllBattles() {
        for (Territory t: territories.getVertices()) {
            end.doBattles();
        }
    }

    /**
     * divide territories into n equal parts
     * @return a HashMap with the values being grouped territories
     */
    public HashMap<Integer, List<Territory>> divideTerritories(int n) {
        // TODO: check if size / n is an integer
        int randomInd[] = new int[terriories.size]; 
        for (int i = 0; i < terriories.size; i++) {
            random[i] = i;
        }    
        shuffle(randomInd);
        // divide
        int nInGroup = territories.size / n;
        Map<Integer, Territory[]> map = new HashMap<>(); 
        for (int group = 0; group < n; group++) {
            List<Territory> terrs = new ArrayList<>();
            for (int i = group * n; i < group * n + nInGroup; i++) {
                terrs.add(territories[i]);
            }
            map.put(group, terrs);
        }

        return map;
    }

    /**
     * Shuffle an array by Fisher-Yates shuffle algorithm.
     * @param arr is the array to shuffle.
     */
    private void shuffle(int[] arr) {
        Random rand = new Random(); 
        for (int i = randomInd.length - 1; i >= 0; i--) {
            int randomNum = rand.nextInt(i + 1);
            int swap = arr[randomNum]; 
            arr[randomNum] = arr[i];
            arr[i] = swap;
        }
    }

    // TODO: a function that searchs if a territory exists by name.
    // If exist, returns that territory.
    // If not, exception
    // TODO: check if a territory exists
    public boolean findTerritory (String terrName) {

    }

    // TODO: set territory owner to certain player
}
