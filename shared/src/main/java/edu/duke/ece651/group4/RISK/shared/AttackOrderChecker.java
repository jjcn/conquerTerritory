package edu.duke.ece651.group4.RISK.shared;

import java.io.Serializable;

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
public class AttackOrderChecker implements Serializable {
    /**
     * Error messages
     */
    protected final String NOT_ATTACK_ORDER_MSG = "This is not an attack order.";
    protected final String SAME_OWNER_MSG = 
        "Cannot attack %s, which belongs to you.";
    protected final String NOT_ADJACENT_MSG = 
        "You tried to attack from %s to %s, which are not adjacent territories. %n" +
        "The attack should be performed on adjacent territories.";

    public AttackOrderChecker() {}

    /**
     * Checks if an attack order is legal.
     * @param order is the order given.
     * @param world is the world object.
     * @return null, if the order is legal;
     *         a String indicating the problem, if not.
     */
    protected String checkMyOrder(BasicOrder order, World world) {
        if (Character.toUpperCase(order.getActionName()) == 'A') {
            Territory start = world.findTerritory(order.getSrcName());
            Territory end = world.findTerritory(order.getDesName());
            // if the start and end have the same owner
            if (start.getOwner().equals(end.getOwner())) {
                return String.format(SAME_OWNER_MSG, 
                                    end.getOwner().getName());
            }
            // if not adjacent
            if (!world.getAdjacents(start).contains(end)) {
                return String.format(NOT_ADJACENT_MSG,
                                    start.getName(), 
                                    end.getName());
            }
            return null;
        }
        // if not an attack order
        return NOT_ATTACK_ORDER_MSG;
    }
    
}
