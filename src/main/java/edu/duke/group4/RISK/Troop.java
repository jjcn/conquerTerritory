package edu.duke.group4.RISK;

import java.util.ArrayList;

public class Troop {

    private ArrayList<Unit> population;
    //private final Player owner;

    public Troop(int number) {
        //this.owner=owner;
        this.population = new ArrayList<Unit>();
        for(int i=0;i<number;i++) {
            population.add(new Soldier());
        }
    }
}

