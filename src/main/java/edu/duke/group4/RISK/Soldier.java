package edu.duke.group4.RISK;
import java.util.Random;

public class Soldier implements Unit {

    private final String jobName;
    private final Random dice;

    public Soldier() {
        this.jobName="Soldier";
        this.dice=new Random();
    }


    public Soldier(Random rand) {
        this.jobName="Soldier";
        this.dice=rand;
    }

    @Override
    public boolean fight(Unit enemy) {

        int myRoll=0,enemyRoll=0;

        while(myRoll==enemyRoll) {

            myRoll=randInt(1,20);
            enemyRoll=randInt(1,20);

        }

        return myRoll>enemyRoll;
    }

    private int randInt(int min, int max) {

        int randomNum = dice.nextInt((max - min) + 1) + min;

        return randomNum;
    }


}
