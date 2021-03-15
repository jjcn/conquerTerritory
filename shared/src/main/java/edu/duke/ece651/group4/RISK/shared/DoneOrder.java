package edu.duke.ece651.group4.RISK.shared;

public class DoneOrder implements Order {
    final private Character actionName;

    public DoneOrder() {
        this.actionName = 'D';
    }

    @Override
    public Character getActionName() {
        return actionName;
    }
}
