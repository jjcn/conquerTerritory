package edu.duke.ece651.group4.RISK.shared;

import java.util.Set;
import java.util.Stack;
import java.util.HashSet;
import java.util.List;

public class MoveOrderChecker<T> extends OrderChecker<T> {

    public MoveOrderChecker(OrderChecker<T> next) {
        super(next);
    }

    @Override
    protected String checkMyOrder(Order order, World world) {
        if (order.getActionName() == 'M') {
            Territory start = world.findTerritory(order.getSrcName());
            Territory end = world.findTerritory(order.getDesName());
            Player owner = start.getOwner();
            Stack<Territory> toCheck = new Stack<>();
            Set<Territory> checked = new HashSet<>();

            toCheck.add(start);
            while (!toCheck.empty()) {
                Territory key = toCheck.pop();
                if (!checked.contains(key)) {
                    List<Territory> adjacents = world.getAdjacents(key);
                    for (Territory terr : adjacents) {
                        if (!checked.contains(terr) && terr.getOwner().equals(owner)) {
                            if (terr.equals(end)) {
                                return null;
                            }
                            toCheck.push(terr);
                        }
                    }
                }
                checked.add(key);
            }
        }
        return "This move order is invalid.";
    }
    
}
