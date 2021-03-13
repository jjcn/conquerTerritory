package edu.duke.ece651.group4.RISK.shared;


public interface Order {
  String getSrcName();

  String getDesName();

  Troop getActTroop();

  Character getActionName();
}
