package edu.duke.ece651.group4.RISK.shared;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class TextPlayer implements Player {
    private String playerName;
    final private PrintStream out;
    final private BufferedReader inputReader;
    private HashSet<Character> actionTypes;

    public TextPlayer(PrintStream out, Reader inputReader, String playerName) throws IOException {
        this.playerName = playerName;
        this.inputReader = (BufferedReader) inputReader;
        this.out = out;
        this.actionTypes = new HashSet<Character>();
        actionTypes.add('D');
        actionTypes.add('M');
        actionTypes.add('A');
    }

    /**
     * This asks the user to input their name for this game.
     *
     * @param out
     * @param inputReader
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
        out.println(instr);
        String input = inputReader.readLine();
        if (playerName == null) {
            throw new EOFException("Can't read name of the player.\n");
        }
        return input;
    }

    /**
     * Order information for move and attack includes source, destination and action name.
     * The action type is checked here. The validity of source and destination are not checked here.
     *
     * @return an Order containing the information of the action; null if the user has done their actions in this turn.
     * @throws IOException
     */
    public Order doOneAction() throws IOException {
        Character actionName = readActionName();
        if (actionName == 'D') {
            return null;
        } else {
            String src = readInput("Please input the territory name you would like to send out troop from:");
            String des = readInput("Please input the territory name you would like to send troop to:");
            int pop = readInteger("Please input the number of soldiers you would like to send.");
            Troop troop = new Troop(pop, this);
            return new BasicOrder(src, des, troop, actionName);
        }
    }

    /**
     * Here checks if the input action name is in the action types.
     *
     * @return a character representing the action name.
     * @throws IOException
     */
    private Character readActionName() throws IOException {
        String instr = "Please choose the action type you would like to take:";
        Character action = ' ';
        while (!actionTypes.contains(action)) {
            String actionName = readInput(instr);
            action = actionName.trim().charAt(0);
            instr = "Please choose a valid action type:";
        }
        return action;
    }

    /**
     * Displays the int to territories mapping to user and asks them to choose one group.
     *
     * @param map containing the info of created territories.
     * @return an int the user input representing the territory they choose.
     * @throws IOException
     */
    public int chooseTerritory(HashMap<Integer, List<Territory>> map) throws IOException {
        String info = "The world has following groups of integers:\n";
        for (Map.Entry<Integer, List<Territory>> entry : map.entrySet()) {
            String oneGroup = entry.getKey() + ":";
            String sep = " ";
            for (Territory terr : entry.getValue()) {
                oneGroup.concat(sep + terr.getName());
                sep = ", ";
            }
            info.concat(oneGroup + "\n");
        }
        info.concat("Please input the group number you would like to choose:");
        out.println(info);
        String instr = "Please input the number of one territory you would like to choose:";
        int num = readInteger(instr);
        return num;
    }

    private int readInteger(String instr) throws IOException {
        String inputInt = "";
        while (!isNumeric(inputInt)) {
            inputInt = readInput(instr);
        }
        int num = Integer.parseInt(inputInt);
        return num;
    }

    /**
     * Blanks before and after the string is accepted.
     *
     * @param str from the input source.
     * @return true is the input string contains one and only one valid integer.
     */
    private boolean isNumeric(String str) {
        str = str.trim();
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return name of a player.
     **/
    public String getName() {
        return playerName;
    }
}
