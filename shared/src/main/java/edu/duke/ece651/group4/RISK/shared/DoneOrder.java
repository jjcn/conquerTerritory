package edu.duke.ece651.group4.RISK.shared;

import java.io.Serializable;

public class DoneOrder implements Order, Serializable {
    final private Character actionName;

    public DoneOrder() {
        this.actionName = 'D';
    }

    @Override
    public Character getActionName() {
        return actionName;
    }
}
