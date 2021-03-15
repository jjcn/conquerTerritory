package edu.duke.ece651.group4.RISK.shared;

import java.util.Set;
import java.util.Stack;
import java.util.HashSet;
import java.util.List;

/**
 * See "Evolution 1: 4. Turn structure" for rules related with move order.
 * a. A move order must specify the number of units to move, the source territory, and
 *    the destination territory.
 * b. Units moving with a move order must have a path formed by adjacent territories
 *    controlled by their player from the source to the destination.
 * c. Move orders move units from one territory to another territory controlled by the
 *    same player.
 * 
 * This class also checks if the order is a move order.
 * 
 */
public class AttackOrderChecker<T> extends OrderChecker<T> {
    private final String SAME_OWNER_MSG = "Cannot attack a territory with the same owner.";
    private final String NOT_ATTACK_ORDER_MSG = "This is not an attack order.";
    private final String NOT_ADJACENT_MSG = "The attack should be performed on adjacent territories.";

    public AttackOrderChecker(OrderChecker<T> next) {
        super(next);
    }

    @Override
    protected String checkMyOrder(Order order, World world) {
        if (order.getActionName() == 'A') {
            Territory start = world.findTerritory(order.getSrcName());
            Territory end = world.findTerritory(order.getDesName());
            // if the start and end have the same owner
            if (start.getOwner().equals(end.getOwner())) {
                return SAME_OWNER_MSG;
            }
            // if not adjacent
            if (!world.getAdjacents(start).contains(end)) {
                return NOT_ADJACENT_MSG;
            }
            return null;
        }
        // if not an attack order
        return NOT_ATTACK_ORDER_MSG;
    }
    
}
