package edu.duke.group4.RISK;

public class BasicOrder implements Order {
  private String src;
  private String des;
  private Troop troop;
  private Character actionName;

  public MoveOrder(String src, String des, Troop troop, Character name){
    this.src = src;
    this.des = des;
    this.population = population;
    this.actionName = name;
  }

  public String getSrcName(){
    return src;
  }
  public String getDesName(){
    return des
  }
  public Troop getActTroop(){
    return troop;
  }
  public Character getActionName(){
    return actionName;
  }
}
