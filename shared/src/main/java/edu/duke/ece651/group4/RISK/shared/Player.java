package edu.duke.ece651.group4.RISK.shared;

import java.io.IOException;

public interface Player {
  /**
Reads in a Character representing the action user want to do and the following needed information if needed (for move and attack: the source territory, the destination territory and the number of populations to send).
@return an Order class containing the action information user did, null if user has done all his action in one turn.
   **/
  public Order doOneAction()throws IOException;
  /**
At the start of a game. Reads in an int repersenting the territory the player choose and return.If the input is not valid. ask the player to input again.
@param a HashMap containing the info of created territories.
@return an int if valid.
   **/
  public int chooseTerritory(HashMap<int,Territory> map)throws IOException;
  /**
@return name of a player.
   **/
  public String getName();
}

