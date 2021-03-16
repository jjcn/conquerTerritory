package edu.duke.ece651.group4.RISK.shared;

import java.io.Serializable;

/**
 * Checks if a order is valid.
 */
public class OrderChecker implements Serializable { // TODO: bad code design
    AttackOrderChecker aoc;
    MoveOrderChecker moc;

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
        if (order.getActionName() == 'A') { 
            aoc.checkMyOrder(order, world);
        }
        else if (order.getActionName() == 'M') {   
            moc.checkMyOrder(order, world);
        }
        return "Not a valid basic order type."; // that is, not Attack or Move
    }
}
