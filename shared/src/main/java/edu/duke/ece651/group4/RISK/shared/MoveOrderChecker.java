package edu.duke.ece651.group4.RISK.shared;

import java.util.Set;
import java.util.Stack;
import java.util.HashSet;

/**
 * See "Evolution 1: 4. Turn structure" for rules related with move order.
 * a. A move order must specify the number of units to move, the source territory, and
 *    the destination territory.
 * b. Units moving with a move order must have a path formed by adjacent territories
 *    controlled by their player from the source to the destination.
 * c. Move orders move units from one territory to another territory controlled by the
 *    same player.
 * 
 * Also checks if the order is a move order.
 * 
 */
public class MoveOrderChecker {
    private final String NOT_SAME_OWNER_MSG = "Cannot move troop to a territory with different owner.";
    private final String NOT_MOVE_ORDER_MSG = "This is not a move order.";
    private final String NOT_LINKED_MSG = "There is not a path of territories that all belongs to you.";

    public MoveOrderChecker() {
        super();
    }

    /**
     * Checks if a move order is legal.
     * @param order is the order given.
     * @param world is the world object.
     * @return null, if the order is legal;
     *         a String indicating the problem, if not.
     */
    protected String checkMyOrder(BasicOrder order, World world) {
        if (order.getActionName() == 'M') {
            Territory start = world.findTerritory(order.getSrcName());
            Territory end = world.findTerritory(order.getDesName());
            Player owner = start.getOwner();
            // if the start and end do not have the same owner
            if (!start.getOwner().equals(end.getOwner())) {
                return NOT_SAME_OWNER_MSG;
            }  
            // if not linked
            // FIXIT: this has bugs
            Stack<Territory> toCheck = new Stack<>();
            Set<Territory> checked = new HashSet<>();
            toCheck.push(start);
            while (!toCheck.empty()) {
                Territory key = toCheck.pop();
                if (key.equals(end)) {
                    return null;
                }
                if (!checked.contains(key)) {
                    for (Territory terr : world.getAdjacents(key)) {
                        if (terr.getOwner().equals(owner)) {
                            toCheck.push(terr);
                        }
                    }
                }
                checked.add(key);
            }
            return NOT_LINKED_MSG;
        }
        // if not move order
        return NOT_MOVE_ORDER_MSG;
    }
    
}
