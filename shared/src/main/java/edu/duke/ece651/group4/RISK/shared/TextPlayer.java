package edu.duke.ece651.group4.RISK.shared;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;

public class TextPlayer implements Player {
    private String playerName;
    final PrintStream out;
    final Reader inputReader;

    public TextPlayer(Reader inputSource, String playerName, PrintStream out, Reader inputReader) throws IOException {
        this.playerName = playerName;
        this.inputReader = inputReader;
        this.playerName = readName();
        this.out = out;
    }

    private String readName() {
        return "";
    }

    public Order doOneAction() throws IOException {
        return null;
    }

    public int chooseTerritory(HashMap<Integer, List<Territory>> map) throws IOException {
        return 0;
    }

    /**
     * @return name of a player.
     **/
    public String getName() {
        return playerName;
    }

}
