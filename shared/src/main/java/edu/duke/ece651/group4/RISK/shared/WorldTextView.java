package edu.duke.ece651.group4.RISK.shared;

import java.io.Serializable;

/*
This class hadles the textual display of a World by output a string.
i.e. 
Green player: 
------------- 
10 units in Narnia (next to: Elantris, Midkemia) 
12 units in Midkemia (next to: Narnia, Elantris, Scadrial, Oz) 
8 units in Oz (next to: Midkemia, Scadrial, Mordor, Gondor) 

Blue player: 
------------ 
6 units in Elantris (next to: Roshar, Scadrial, Midkemia, Narnia) 
3 units in Roshar (next to: Hogwarts, Scadrial, Elantris) 
5 units in Scadrial (next to: Elantris, Roshar, Hogwats, Mordor Oz, Midkemia, Elantris)
 
Red player: 
----------- 
13 units in Gondor (next to: Oz, Mordor) 
14 units in Mordor (next to: Hogwarts, Gondor, Oz, Scadrial) 
3 units in Hogwarts (next to: Mordor, Scadrial, Roshar)
 */
public class WorldTextView implements WorldView<Character>, Serializable {
    private final Graph<Territory> toDisplay;

    /**
     * constructer.
     **/
    public WorldTextView(World toDisplay) {
        this.toDisplay = toDisplay.territories;
    }

    /**
     * The text view of world has two situations: 1. the player can immediately see
     * the moves he did; 2. the player cannot see the results of other players'
     * action.
     *
     * @param toDisplay: the World to be displayed.
     **/
    @Override
    public void displayWorld(World toDisplay) {
        StringBuilder display = new StringBuilder();
    }

    private String displayOneTerritory(Territory terr) {
        String ownerName = terr.getOwner().getName();
        StringBuilder display = new StringBuilder();
        String title = ownerName + " Player:\n" + "-----------\n";
        for (Territory adjacentTerr : toDisplay.getAdjacentVertices(terr)) {
            display.append(""); //e.g. 13 units in Gondor (next to: Oz, Mordor)
        }
        return display.toString();
    }

}
