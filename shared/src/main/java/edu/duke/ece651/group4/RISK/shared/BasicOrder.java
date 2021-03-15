package edu.duke.ece651.group4.RISK.shared;

import java.io.Serializable;

public class BasicOrder implements Order, Serializable {
    private String src;
    private String des;
    private Troop troop;
    private Character actionName;

    public BasicOrder(String src, String des, Troop troop, Character name) {
        this.src = src;
        this.des = des;
        this.troop = troop;
        this.actionName = name;
    }

    @Override
    public String getSrcName() {
        return src;
    }

    @Override
    public String getDesName() {
        return des;
    }

    @Override
    public Troop getActTroop() {
        return troop;
    }

    @Override
    public Character getActionName() {
        return actionName;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(this.getClass())) {
            BasicOrder c = (BasicOrder) o;
            return c.getActionName().equals(actionName) &&
                    c.getSrcName().equals(src) &&
                    c.getDesName().equals(des) &&
                    c.getActTroop().equals(troop);
        }
        return false;
    }
}



