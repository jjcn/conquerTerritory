//package edu.duke.ece651.group4.RISK.shared;
//
//import org.junit.jupiter.api.Test;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//class TextPlayerTest {
//
//    @Test
//    void test_doOneAction() throws IOException {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        String input = "M\nsrc1\ndes1\n12\n" + "A\nsrc2\ndes2\n4t\n3\n" + "k\nD\n";
//        TextPlayer player = createTextPlayer(bytes, input, "Red");
//        Order o1 = player.doOneAction();
//        Order o2 = player.doOneAction();
//        Order o3 = player.doOneAction();
//
//        String instr = "what would you like to do?\n(A)ttack\n(M)ove\n(D)one\n\n";
//        String instr2 = "Please input the territory name you would like to send out troop from:\n" +
//                "Please input the territory name you would like to send troop to:\n" +
//                "Please input the number of soldiers you would like to send:\n";
//        String reinputInstr1 = "The input is not one int, please input again:\n";
//        String reinputInstr2 = "Please choose a valid action type:\n";
//        String expected = instr + instr2 + instr + instr2 + reinputInstr1 + instr + reinputInstr2;
//        BasicOrder ot1 = new BasicOrder("src1", "des1", new Troop(12, player), 'M');
//        BasicOrder ot2 = new BasicOrder("src2", "des2", new Troop(3, player), 'A');
//        assertTrue(checkBasicOrderEqual((BasicOrder) o1, ot1));
//        assertTrue(checkBasicOrderEqual((BasicOrder) o2, ot2));
//        assertEquals('D', o3.getActionName());
//        assertEquals(expected, bytes.toString());
//    }
//
//    /*
//    checks if two order contains same content.
//     */
//    private boolean checkBasicOrderEqual(BasicOrder a, BasicOrder b) {
//        Troop ta = a.getActTroop();
//        Troop tb = b.getActTroop();
//        return a.getActionName().equals(b.getActionName()) &&
//                a.getSrcName().equals(b.getSrcName()) &&
//                a.getDesName().equals(b.getDesName()) &&
//                ta.getOwner().equals(tb.getOwner()) &&
//                ta.checkTroopSize() == tb.checkTroopSize();
//    }
//
//    private boolean checkPlaceOrderEqual(PlaceOrder a, PlaceOrder b) {
//        Troop ta = a.getActTroop();
//        Troop tb = b.getActTroop();
//        return a.getActionName().equals(b.getActionName()) &&
//                a.getActionName().equals('P') &&
//                a.getDesName().equals(b.getDesName()) &&
//                ta.getOwner().equals(tb.getOwner()) &&
//                ta.checkTroopSize() == tb.checkTroopSize();
//    }
//
//    /*
//    helps to create player. If no name parameter: ask to input one.
//     */
//    private TextPlayer createTextPlayer(OutputStream bytes, String input) throws IOException {
//        PrintStream out = new PrintStream(bytes, true);
//        BufferedReader in = new BufferedReader(new StringReader(input));
//        return new TextPlayer(out, in);
//    }
//
//    private TextPlayer createTextPlayer(OutputStream bytes, String input, String name) throws IOException {
//        PrintStream out = new PrintStream(bytes, true);
//        BufferedReader in = new BufferedReader(new StringReader(input));
//        return new TextPlayer(out, in, name);
//    }
//
//    @Test
//    void chooseTerritory() throws IOException {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        String input = "3\n2\n";
//        TextPlayer player = createTextPlayer(bytes, input, "Red");
//        HashMap<Integer, List<Territory>> map = new HashMap<>();
//        ArrayList<Territory> g1 = new ArrayList<>();
//        g1.add(new Territory("terr1"));
//        ArrayList<Territory> g2 = new ArrayList<>();
//        g2.add(new Territory("terr2"));
//        g2.add(new Territory("terr3"));
//        map.put(1, g1);
//        map.put(2, g2);
//
//        int choice = player.chooseTerritory(map);
//        String expected = "The world has following groups of territories:\n" +
//                "1: terr1\n" +
//                "2: terr2, terr3\n" +
//                "\n" +
//                "Please input the group number you would like to choose:\n" +
//                "You have chose an invalid group number, please choose again:\n";
////        assertEquals(expected, bytes.toString());
//        assertEquals(2, choice);
//    }
//
//    @Test
//    void test_getName() throws IOException {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        String input = "Red\r";
//        TextPlayer player = createTextPlayer(bytes, input);
//
//        String outInfo = "Please enter your name in this game:\n";
//        assertEquals(outInfo, bytes.toString());
//        assertEquals("Red", player.getName());
//    }
//
//    @Test
//    void test_doPlacement() throws IOException {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        String input = "3\n2\n";
//        TextPlayer p1 = createTextPlayer(bytes, input, "p1");
//        ArrayList<Territory> terrs = new ArrayList<Territory>();
//        terrs.add(new Territory("terr1"));
//        terrs.add(new Territory("terr2"));
//        int total = 5;
//
//        List<Order> orders = p1.doPlacement(terrs, total);
//        String expected = "Please input the number of soldiers you want to place in terr1:\n" +
//                "Please input the number of soldiers you want to place in terr2:\n";
//        checkPlaceOrderEqual((PlaceOrder) orders.get(0), new PlaceOrder("terr1", new Troop(3, p1)));
//        checkPlaceOrderEqual((PlaceOrder) orders.get(1), new PlaceOrder("terr2", new Troop(2, p1)));
//        assertEquals(expected, bytes.toString());
//    }
//}