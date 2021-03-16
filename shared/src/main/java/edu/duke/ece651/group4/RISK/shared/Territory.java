package edu.duke.ece651.group4.RISK.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Territory implements Serializable {

    private final String name;

    private Troop ownerTroop;

    private final HashMap<String,Troop> enemyOnTerritory;

    private Random rnd;

    public Territory(String name, Player owner, int population, Random rnd) {
        this.name = name;
        this.enemyOnTerritory = new HashMap<>();
        this.ownerTroop = new Troop(population, owner, rnd);
        this.rnd = rnd;
    }

    public Territory(String name) {
        this.name = name;
        this.enemyOnTerritory = new HashMap<>();
        this.ownerTroop = new Troop(0, null, new Random()); // default Troop.owner == null, cannot call equals()
        this.rnd = new Random();
    }

    public Territory(String name,Random rnd) {
        this.name = name;
        this.enemyOnTerritory = new HashMap<>();
        this.ownerTroop = new Troop(0, null, new Random()); // default Troop.owner == null, cannot call equals()
        this.rnd = rnd;
    }

    public Troop sendOutTroop(Troop subTroop) {
        return this.ownerTroop.sendTroop(subTroop.checkTroopSize());
    }

    public void sendInTroop(Troop subTroop) {
        this.ownerTroop.receiveTroop(subTroop);
    }

    public void sendInEnemyTroop(Troop enemy) {
        this.enemyOnTerritory.put(enemy.getOwner().getName(),enemy);
    }

    public void doOneBattle(Troop enemy){
        Troop enemyRemain=this.ownerTroop.combat(enemy);
        this.ownerTroop = this.ownerTroop.checkWin()?this.ownerTroop:enemyRemain;
    }

    public Player getOwner() {
        return this.ownerTroop.getOwner();
    }

    public String getName(){
        return this.name;
    }

    public void addUnit(int num){
        Troop newTroop=new Troop(num,this.getOwner());
        this.sendInTroop(newTroop);
    }

    public void initializeTerritory(int num, Player owner){
        this.ownerTroop=new Troop(num,owner);
    }

    public int checkPopulation(){
        return this.ownerTroop.checkTroopSize();
    }

    public void doBattles(){
        if(this.enemyOnTerritory.size()==0) return;

        ArrayList<String> enemyPlayers = new ArrayList<String>(this.enemyOnTerritory.keySet());
        Collections.sort(enemyPlayers);
        while(enemyPlayers.size()>0){

            int diceResult=randInt(0,enemyPlayers.size()-1);

            String enemy=enemyPlayers.get(diceResult);

            Troop enemyTroop=this.enemyOnTerritory.get(enemy);
            doOneBattle(enemyTroop);
            enemyPlayers.remove(diceResult);
        }
        this.enemyOnTerritory.clear();
    }


    private int randInt(int min, int max) {
        int randomNum = rnd.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other.getClass().equals(getClass())) {
            Territory otherTerritory = (Territory)other;
            return name.equals(otherTerritory.name);
        }
        else {
            return false;
        }
    }

    public void setRandom(Random seed){
        this.rnd=seed;
    }
    
}







