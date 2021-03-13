package edu.duke.ece651.group4.RISK.shared;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TextPlayerTest {

    @Test
    void test_doOneAction() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        String input = "M\nsrc1\ndes1\n12\n" + "A\nsrc2\ndes2\n4t\n3\n" + "k\nD\n";
        TextPlayer player = createTextPlayer(bytes, input, "Red\n");
        BasicOrder o1 = player.doOneAction();
        BasicOrder o2 = player.doOneAction();
        BasicOrder o3 = player.doOneAction();

        String instr = "what would you like to do?\n(A)ttack\n(D)one\n(M)ove\n\n";
        String instr2 = "Please input the territory name you would like to send out troop from:\n" +
                "Please input the territory name you would like to send troop to:\n" +
                "Please input the number of soldiers you would like to send:\n";
        String reinputInstr1 = "The input is not one int, please input again:\n";
        String reinputInstr2 = "Please choose a valid action type:\n";
        String expected = instr + instr2 + instr + instr2 + reinputInstr1 + instr + reinputInstr2;
        BasicOrder ot1 = new BasicOrder("src1", "des1", new Troop(12, player), 'M');
        BasicOrder ot2 = new BasicOrder("src2", "des2", new Troop(3, player), 'A');
        // assertTrue(o1.equals(ot1));
        // assertTrue(o2.equals(ot2));
        assertNull(o3);
        assertEquals(expected, bytes.toString());
    }

    /*
    helps to create player. If no name parameter: ask to input one.
     */
    private TextPlayer createTextPlayer(OutputStream bytes, String input) throws IOException {
        PrintStream out = new PrintStream(bytes, true);
        BufferedReader in = new BufferedReader(new StringReader(input));
        return new TextPlayer(out, in);
    }

    private TextPlayer createTextPlayer(OutputStream bytes, String input, String name) throws IOException {
        PrintStream out = new PrintStream(bytes, true);
        BufferedReader in = new BufferedReader(new StringReader(input));
        return new TextPlayer(out, in, name);
    }

    @Disabled
    @Test
    void chooseTerritory() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        String input = "Red\r";
        TextPlayer player = createTextPlayer(bytes, input, "Red");
    }

    @Test
    void test_getName() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        String input = "Red\r";
        TextPlayer player = createTextPlayer(bytes, input);

        String outInfo = "Please enter your name in this game:\n";
        assertEquals(outInfo, bytes.toString());
        assertEquals("Red", player.getName());
    }
}