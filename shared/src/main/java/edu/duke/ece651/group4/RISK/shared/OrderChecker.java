package edu.duke.ece651.group4.RISK.shared;

import java.io.Serializable;

/**
 * This class checks if a basic order (Move & Attack) is valid.
 */
public class OrderChecker implements Serializable { // TODO: bad design of OrderCheckers
    /**
     * Error Messages
     */
    protected final String NOT_YOUR_TROOP_MSG = 
        "Error: You tried to move troops on %s, which belongs to another player: %s";
    protected final String UNKNOWN_BASIC_ORDER_TYPE = 
        "'%c' is not a valid basic order type.";
    protected final String NOT_ENOUGH_TROOP_MSG = 
        "Cannot move out a troop of size larger than %d on %s, " +
        "but you entered a troop of size %d.";

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
            return String.format(NOT_YOUR_TROOP_MSG, 
                                start.getName(),
                                start.getOwner().getName());
        }
        // troop size larger than that on the territory
        if (start.checkPopulation() < order.getActTroop().checkTroopSize()) {
            return String.format(NOT_ENOUGH_TROOP_MSG, 
                                start.checkPopulation(),
                                start.getName(), 
                                order.getActTroop().checkTroopSize());
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
