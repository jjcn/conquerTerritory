package edu.duke.ece651.group4.RISK.shared;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class Troop implements Serializable {

    private final ArrayList<Unit> population;

    private Player owner;

    public Troop(int number, Player owner, Random rand) {
        this.owner = owner;
        this.population = new ArrayList<>();
        this.owner=new TextPlayer(owner.getName());
        for (int i = 0; i < number; i++) {
            population.add(new Soldier(rand));
        }
    }

    public Troop(int number, Player owner) {
        this(number, owner, new Random());
    }

    public Troop(ArrayList<Unit> subTroop, Player owner){
        this.population = subTroop;
        this.owner = owner;
    }

    public Troop combat(Troop enemy) {
        while (this.checkTroopSize() != 0 && enemy.checkTroopSize() != 0) {
            Unit myUnit = this.dispatchUnit();
            Unit enemyUnit = enemy.dispatchUnit();

            if (myUnit.fight(enemyUnit)) {
                enemy.loseUnit(enemyUnit);
            }
            else {
                this.loseUnit(myUnit);
            }
        }
        return enemy;
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
        ArrayList<Unit> sub = new ArrayList<>();
        for(int i = 0; i < number; i++){
            Unit movedUnit = this.dispatchUnit();
            sub.add(movedUnit);
            this.loseUnit(movedUnit);
        }
        return new Troop(sub, getOwner());
    }

    public boolean checkWin(){
        return this.checkTroopSize()>0;
    }

    public void receiveTroop(Troop subTroop){
        while(subTroop.checkTroopSize() != 0){
            Unit newMember=subTroop.dispatchUnit();
            this.receiveUnit(newMember);
            subTroop.loseUnit(newMember);
        }
    }
    public Player getOwner(){

        return this.owner;
    }

}