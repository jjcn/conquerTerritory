package edu.duke.ece651.group4.RISK.shared;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextPlayer implements Player {
    private String playerName;
    final private PrintStream out;
    final private BufferedReader inputReader;
    final private HashMap<Character, String> actionTypes;

    public TextPlayer(PrintStream out, Reader inputReader, String playerName) {
        this.playerName = playerName;
        this.inputReader = (BufferedReader) inputReader;
        this.out = out;
        this.actionTypes = new HashMap<>();
        actionTypes.put('D', "(D)one");
        actionTypes.put('M', "(M)ove");
        actionTypes.put('A', "(A)ttack");
    }

    /**
     * This asks the user to input their name for this game and then constructs the Player. Output:
     * <p>
     * "Please enter your name in this game:
     *
     * @param out         the output stream.
     * @param inputReader to read from user.
     * @throws IOException
     */
    public TextPlayer(PrintStream out, Reader inputReader) throws IOException {
        this(out, inputReader, "");
        this.playerName = readInput("Please enter your name in this game:");
    }

    /**
     * Print instructions out to user and reader one line of input.
     *
     * @return String of user's input
     * @throws IOException
     */
    private String readInput(String instr) throws IOException {
        out.print(instr + "\n");
        String input = inputReader.readLine();
        if (playerName == null) {
            throw new EOFException("Can't read name of the player.\n");
        }
        return input;
    }

    /**
     * Output sample:
     * <p>
     * what would you like to do?\n
     * (A)ttack\n
     * (M)ove\n
     * (D)one\n
     * <\p>
     * The action type is checked here. The validity of action is not checked here.
     * After choose a valid 'M' or 'A' action, ask user to input following:
     * <p>
     * Please input the territory name you would like to send out troop from:\n
     * Please input the territory name you would like to send troop to:\n
     * Please input the number of soldiers you would like to send:\n
     * <\p>
     * Territory name is not checked here. Number for troop only requires input an integer here.
     * If input is not an integer, ask the user to input again until receive a valid number:
     * <p>
     * Please choose a valid action type:\n
     * </p>
     *
     * @return an Order containing the information of the action; null if the user has done their actions in this turn.
     * Order information for move and attack includes source, destination and action name.
     * @throws IOException
     */
    @Override
    public Order doOneAction() throws IOException {
        StringBuilder instr = new StringBuilder("what would you like to do?\n");
        for (String act : actionTypes.values()) {
            if (act != "(D)one") {
                instr.append(act + "\n");
            }
        }
        instr.append("(D)one\n");
        Character actionName = readActionName(instr.toString());
        if (actionName == 'D') {
            return new DoneOrder();
        } else {
            String src = readInput("Please input the territory name you would like to send out troop from:");
            String des = readInput("Please input the territory name you would like to send troop to:");
            int pop = readInteger("Please input the number of soldiers you would like to send:");
            Troop troop = new Troop(pop, this);
            return new BasicOrder(src, des, troop, actionName);
        }
    }

    /**
     * Here checks if the input action name is in the action types.
     * If not belong to any action type, output:
     * <p>
     * Please choose a valid action type:\n
     * <\p>
     * until a valid action type is received.
     *
     * @return a character representing the action name.
     * @throws IOException
     */
    private Character readActionName(String instr) throws IOException {
        char action = ' ';
        while (!actionTypes.keySet().contains(action)) {
            String actionName = readInput(instr);
            action = actionName.trim().charAt(0);
            instr = "Please choose a valid action type:";
        }
        return action;
    }

    /**
     * Displays the int to territories mapping to user and asks them to choose one group.
     * Input of users must be an integer and should belong to the group.
     *
     * @param map containing the info of created territories.
     * @return an int the user input representing the territory they choose.
     * @throws IOException
     */
    @Override
    public int chooseTerritory(HashMap<Integer, List<Territory>> map) throws IOException {
        StringBuilder info = new StringBuilder("The world has following groups of territories:\n");
        for (Map.Entry<Integer, List<Territory>> entry : map.entrySet()) {
            info.append(entry.getKey()).append(":");
            String sep = " ";
            for (Territory terr : entry.getValue()) {
                info.append(sep).append(terr.getName());
                sep = ", ";
            }
            info.append("\n");
        }
        out.println(info);
        String instr = "Please input the group number you would like to choose:";
        int choice = readInteger(instr);
        while (!map.keySet().contains(choice)) {
            instr = "You have chose an invalid group number, please choose again:";
            choice = readInteger(instr);
        }
        return choice;
    }

    /**
     * Asks the user to re-input their placement if
     *
     * @param terrs that player own
     * @param total number of soldiers player can place.
     * @return an Order list.
     */
    public List<Order> doPlacement(List<Territory> terrs, int total) throws IOException {
        List<Order> orders = tryPlacement(terrs, total);
        while (orders.equals(null)) {
            out.print("Your have placed wrong number of total soldiers.Please input again.\n");
            orders = tryPlacement(terrs, total);
        }
        return orders;
    }

    /**
     * Automatically assign rest territories with 0 soldier if
     *
     * @param terrs
     * @param total
     * @return List<Order> if total of soldiers are place; null if exceeded or not enough soldier are placed.
     * @throws IOException
     */
    private List<Order> tryPlacement(List<Territory> terrs, int total) throws IOException {
        List<Order> orders = new ArrayList<Order>();
        int currSum = 0;
        while (currSum < total && !terrs.isEmpty()) {
            Territory terr = terrs.remove(0);
            String name = terr.getName();
            int add = readInteger("Please input the number of soldiers you want to place in " + name + ":");
            currSum += add;
            orders.add(new PlaceOrder(name, new Troop(add, this)));
        }
        if (currSum == total) {
            for (Territory terr : terrs) {
                out.print("You have used up soldiers, remaining territories automatically have 0.");
                orders.add(new PlaceOrder(terr.getName(), new Troop(0, this)));
            }
        } else {
            return null;
        }
        return orders;
    }

    private int readInteger(String instr) throws IOException {
        String inputInt = "";
        while (!isNumeric(inputInt)) {
            inputInt = readInput(instr);
            instr = "The input is not one int, please input again:";
        }
        return Integer.parseInt(inputInt);
    }

    /**
     * Blanks before and after the string is accepted.
     *
     * @param str from the input source.
     * @return true is the input string contains one and only one valid integer.
     */
    private boolean isNumeric(String str) {
        str = str.trim();
        if (!str.equals("")) {
            for (int i = 0; i < str.length(); i++) {
                if (!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * @return name of a player.
     **/
    @Override
    public String getName() {
        return playerName;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(this.getClass())) {
            TextPlayer c = (TextPlayer) o;
            return c.getName().equals(playerName);
        }
        return false;
    }
}
