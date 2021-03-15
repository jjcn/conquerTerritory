package edu.duke.ece651.group4.RISK.shared;

import java.util.Set;
import java.util.Stack;
import java.util.HashSet;

public class MoveRuleChecker<T> extends OrderRuleChecker<T> {

    public MoveRuleChecker(OrderRuleChecker<T> next) {
        super(next);
    }

    @Override
    protected String checkMyRule(Order order, World world) {
        if (order.getActionName() == 'M') {
            Territory start = world.findTerritory(order.getSrcName());
            Territory end = world.findTerritory(order.getDesName());
            Stack<Territory> toCheck = new Stack<>();
            Set<Territory> checked = new HashSet<>();

            toCheck.add(start);
            while (!toCheck.empty()) {
                
            }
        }
        return null;
    }
    
}
