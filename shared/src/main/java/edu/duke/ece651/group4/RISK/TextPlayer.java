package edu.duke.ece651.group4.RISK;

import java.io.IOException;

public class TextPlayer implements Player {
  private String playername;
  final PrintStream out;
  final Reader inputReader;

  public TextPlayer(Reader inputSource, PrintStream out)throws IOException{
    this.playername = getName();
    this.out = out;
    this.inputReader = inputSource;
  }

  public Order doOneAction()throws IOException{
    return null;
  }

  public int chooseTerritory(HashMap<int,Territory> map) throws IOException {
    return 0;
  }
  /**
@return name of a player.
   **/
  public String getName(){
    return playername;
  }

}
