package edu.duke.ece651.group4.RISK;

import java.util.HashSet;
import java.util.Random;

public class Territory {

    private final String name;

    private Troop ownerTroop;

    private final HashSet<Troop> enemyOnTerritory;



    public Territory(String name,Player owner, int population, Random rnd){

        this.name=name;
        this.enemyOnTerritory=new HashSet<>();
        this.ownerTroop=new Troop(population,owner,rnd);


    }

    public Territory(String name){

        this.name=name;
        this.enemyOnTerritory=new HashSet<>();
        this.ownerTroop=new Troop(0,null,new Random());
    }

    public Troop sendOutTroop(Troop subTroop) {
      return  this.ownerTroop.sendTroop(subTroop.checkTroopSize());
    }

    public void sendInTroop(Troop subTroop) {
       this.ownerTroop.receiveTroop(subTroop);
    }

    public void doOneBattle(Troop enemy){
        this.ownerTroop=this.ownerTroop.combat(enemy);
    }

    public Player getOwner() {
        return this.ownerTroop.getOwner();
    }

    public String getName(){
        return this.name;
    }

//    public void addUnit(int num){
//
//    }

    }
//- doOneBattle(troop: Troop): Troop
//+ doBattles(): void
//- isInBattle(): boolean
//+ addUnit(num: int): void



