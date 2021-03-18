package edu.duke.ece651.group4.RISK.shared;

import java.io.Serializable;

/**
 * This class checks if a basic order (Move & Attack) is valid.
 */
public class OrderChecker implements Serializable { // TODO: bad design of OrderCheckers
    /**
     * Error Message
     */
    protected final String NOT_YOUR_TROOP_MSG = "Error: You try to move troops on other's territory";

    protected AttackOrderChecker aoc;
    protected MoveOrderChecker moc;

    public OrderChecker() {
        aoc = new AttackOrderChecker();
        moc = new MoveOrderChecker();
    }

    /**
     * Checks if an order is legal.
     * @param order is the order given
     * @param world is the world onject
     * @return null, if the order is legal;
     *         a String indicating the problem, if not.
     */
    public String checkOrder(BasicOrder order, World world) { 
        Territory start = world.findTerritory(order.getSrcName());
        if (!start.getOwner().equals(order.getActTroop().getOwner())) {
            return NOT_YOUR_TROOP_MSG;
        }
        char orderType = Character.toUpperCase(order.getActionName());
        if (orderType == 'A') { 
            return aoc.checkMyOrder(order, world);
        }
        else if (orderType == 'M') {   
            return moc.checkMyOrder(order, world);
        }
        return String.format("'%c' is not a valid basic order type.", orderType); // that is, not Attack or Move
    }
}
