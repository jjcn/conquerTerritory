package edu.duke.ece651.group4.RISK.shared;

import java.io.Serializable;

/**
 * This class checks if a basic order (Move & Attack) is valid.
 */
public class OrderChecker implements Serializable { // TODO: bad design of OrderCheckers
    /**
     * Error Messages
     */
    protected final String NOT_YOUR_TROOP_MSG = "Error: You try to move troops on another player's territory";
    protected final String UNKNOWN_BASIC_ORDER_TYPE = "'%c' is not a valid basic order type.";
    protected final String NOT_ENOUGH_TROOP_MSG = "The troop size you want is larger than that on this territory.";

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
        // territory does not belong to the order sender
        if (!start.getOwner().equals(order.getActTroop().getOwner())) {
            return NOT_YOUR_TROOP_MSG;
        }
        // troop size larger than that on the territory
        if (start.checkPopulation() < order.getActTroop().checkTroopSize()) {
            return NOT_ENOUGH_TROOP_MSG;
        }
        char orderType = Character.toUpperCase(order.getActionName());
        if (orderType == 'A') { 
            return aoc.checkMyOrder(order, world);
        }
        else if (orderType == 'M') {   
            return moc.checkMyOrder(order, world);
        }
        // not Attack or Move
        return String.format(UNKNOWN_BASIC_ORDER_TYPE, order.getActionName());
    }
}
