package edu.duke.ece651.group4.RISK.shared;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


public class Troop {

    private final ArrayList<Unit> population;

    private Player owner;

    public Troop(int number, Player owner,Random rand) {
        this.owner=owner;
        this.population = new ArrayList<>();
        for(int i=0;i<number;i++) {
            population.add(new Soldier(rand));
        }
    }

    public Troop(int number,Player owner) {
        this(number,owner,new Random());

    }

    public Troop(ArrayList<Unit> subTroop,Player owner){

        this.population=subTroop;
        this.owner=owner;
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

    public Unit dispatchUnit() {
        return this.population.get(0);
    }

    public void receiveUnit(Unit target) {
        this.population.add(target);
    }

    public Troop sendTroop(int number){
        ArrayList<Unit> sub=new ArrayList<>();
        for(int i=0;i<number;i++){
            Unit movedUnit=this.dispatchUnit();
            sub.add(movedUnit);
            this.loseUnit(movedUnit);
        }
        return new Troop(sub,getOwner());
    }

    public boolean checkWin(){
        return this.checkTroopSize()>0;
    }

    public void receiveTroop(Troop subTroop){
        while(subTroop.checkTroopSize()!=0){
            this.receiveUnit(subTroop.dispatchUnit());
        }
    }
    public Player getOwner(){

        return this.owner;
    }

}

