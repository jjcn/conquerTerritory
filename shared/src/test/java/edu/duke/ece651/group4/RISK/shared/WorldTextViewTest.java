package edu.duke.ece651.group4.RISK.shared;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorldTextViewTest {

    @Test
    void displayWorld() {
        World world = new World();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Player p1 = new TextPlayer(System.out, input, "p1");
        Player p2 = new TextPlayer(System.out, input, "p2");
        ArrayList<Territory> terrs = new ArrayList<Territory>();
        terrs.add(new Territory("terr1", p1, 2, new Random(0)));
        terrs.add(new Territory("terr2", p1, 3, new Random(0)));
        terrs.add(new Territory("terr3", p2, 1, new Random(0)));
        terrs.add(new Territory("terr4", p2, 4, new Random(0)));
        for (Territory terr : terrs) {
            world.addTerritory(terr);
        }
        world.addConnection("terr1", "terr2");
        world.addConnection("terr2", "terr3");
        world.addConnection("terr3", "terr4");

        WorldTextView display = new WorldTextView(world);
        String expected = "p1 Player:\n" +
                "-----------\n" +
                "2 units in terr1 (next to: terr2)\n" +
                "3 units in terr2 (next to: terr1, terr3)\n\n" +
                "p2 Player:\n" +
                "-----------\n" +
                "1 units in terr3 (next to: terr2, terr4)\n" +
                "4 units in terr4 (next to: terr3)\n\n";
        assertEquals(expected, display.displayWorld());
        assertEquals(expected, display.displayWorld(world));
    }
}