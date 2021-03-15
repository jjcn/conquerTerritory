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
public class MoveOrderChecker<T> extends OrderChecker<T> {
    private final String NOT_SAME_OWNER_MSG = "Cannot move troop to a territory with different owner.";
    private final String NOT_MOVE_ORDER_MSG = "This is not a move order.";
    private final String NOT_LINKED_MSG = "There is not a path of territories that all belongs to you.";

    public MoveOrderChecker(OrderChecker<T> next) {
        super(next);
    }

    @Override
    protected String checkMyOrder(Order order, World world) {
        if (order.getActionName() == 'M') {
            Territory start = world.findTerritory(order.getSrcName());
            Territory end = world.findTerritory(order.getDesName());
            Player owner = start.getOwner();
            // if the start and end do not have the same owner
            if (!start.getOwner().equals(end.getOwner())) {
                return NOT_SAME_OWNER_MSG;
            }
            Stack<Territory> toCheck = new Stack<>();
            Set<Territory> checked = new HashSet<>();
            // search the map from 'start'
            toCheck.add(start);
            while (!toCheck.empty()) {
                Territory key = toCheck.pop();
                if (!checked.contains(key)) {
                    for (Territory terr : world.getAdjacents(key)) {
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
            return NOT_LINKED_MSG;
        }
        return NOT_MOVE_ORDER_MSG;
    }
    
}
