package edu.duke.ece651.group4.RISK.shared;

import java.io.Serializable;
import java.util.Random;

public class Soldier implements Unit, Serializable {

  private final String jobName;
  private final Random dice;

  public Soldier() {
    this.jobName = "Soldier";
    this.dice = new Random();
  }

  /**
   * Construct a soldier with specific seed
   * @param rand is the random seed.
   */
  public Soldier(Random rand) {
    this.jobName = "Soldier";
    this.dice = rand;
  }

  /**
   * Soldier fight with another unit by rolling a 20faced dice
   * @param enemy is the enemy unit.
   */
  @Override
  public boolean fight(Unit enemy) {
    int myRoll = 0, enemyRoll = 0;

    while (myRoll == enemyRoll) {
      myRoll = randInt(1, 20);
      enemyRoll = randInt(1, 20);

    }

    return myRoll > enemyRoll;
  }

  /**
   *return a random number
   */
  private int randInt(int min, int max) {
    int randomNum = dice.nextInt((max - min) + 1) + min;

    return randomNum;
  }

  public Soldier clone(){
    Soldier clone= new Soldier(this.dice);
    return clone;
  }

}