package edu.duke.group4.RISK;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Troop {

    private final ArrayList<Unit> population;

//    private Player owner;

    public Troop(int number, Random rand) {
        //this.owner=owner;
        this.population = new ArrayList<>();
        for(int i=0;i<number;i++) {
            population.add(new Soldier(rand));
        }
    }

    public Troop(int number) {
        this(number,new Random());

    }

    public Troop(ArrayList<Unit> subTroop){

        this.population=subTroop;
        //this.owner=owner;
    }

    public Troop combat(Troop enemy){
        while(this.checkTroopSize()!=0&&enemy.checkTroopSize()!=0) {
            Unit myUnit=this.dispatchUnit();
            Unit enemyUnit=enemy.dispatchUnit();

            if(myUnit.fight(enemyUnit)){
                enemy.loseUnit(enemyUnit);
            }else{
                this.loseUnit(myUnit);
            }
        }
        return this.checkTroopSize()==0?this:enemy;
    }

    public int checkTroopSize() {
        return this.population.size();
    }

    private void loseUnit(Unit loss) {
        this.population.remove(loss);
    }

    private Unit dispatchUnit() {
        return this.population.get(0);
    }

    public Troop sendTroop(int number){
        ArrayList<Unit> sub=new ArrayList<>();
        for(int i=0;i<number;i++){
            Unit movedUnit=this.dispatchUnit();
            sub.add(movedUnit);
            this.loseUnit(movedUnit);
        }
        return new Troop(sub);
    }

    public boolean checkWin(){
        return this.checkTroopSize()>0;
    }
//    public Player getOwner(){
//
//        return this.owner;
//    }

}

