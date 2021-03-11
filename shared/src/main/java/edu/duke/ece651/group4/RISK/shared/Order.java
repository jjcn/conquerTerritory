package edu.duke.ece651.group4.RISK.shared;


public interface Order {
  public String getSrcName();
  public String getDesName();
  public Troop getActTroop();
  public Character getActionName();
}

