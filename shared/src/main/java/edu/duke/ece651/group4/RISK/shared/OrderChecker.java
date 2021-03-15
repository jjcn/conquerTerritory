package edu.duke.ece651.group4.RISK.shared;

/**
 * Checks if a order is valid.
 */
public class OrderChecker { // FIXIT: bad code design
 
    public OrderChecker() {}

    /**
     * Checks if an order is legal.
     * @param order is the order given
     * @param world is the world onject
     * @return null, if the order is legal;
     *         a String indicating the problem, if not.
     */
    public String checkOrder(BasicOrder order, World world) { 
        if (order.getActionName() == 'A') {
            AttackOrderChecker aoc = new AttackOrderChecker();
            aoc.checkMyOrder(order, world);
        }
        else if (order.getActionName() == 'M') {
            MoveOrderChecker moc = new MoveOrderChecker();
            moc.checkMyOrder(order, world);
        }
        return "Not a valid order type.";
    }
}
