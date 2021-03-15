package edu.duke.ece651.group4.RISK.shared;

/**
 * Checks if a order is valid.
 */
public abstract class OrderChecker<T> {
    /**
     * the next rule checker in the rule-checking chain.
     */
    private final OrderChecker<T> next;

    public OrderChecker(OrderChecker<T> next) {
        this.next = next; 
    }

    /**
     * Subclasses will override this method to specify how they check their own rule.
     * @param order is the order given
     * @param world is the world onject
     * @return null, if the rule passes;
     *         a String indicating the problem, if not.
     */
    protected abstract String checkMyOrder(Order order, World world);

    /**
     * Subclasses will generally NOT override this method
     * @param order is the order given
     * @param world is the world onject
     * @return null, if the rule passes;
     *         a String indicating the problem, if not.
     */
    public String checkOrder(Order order, World world) {
        // if we fail our own rule: stop the placement is not legal
        if (checkMyOrder(order, world) != null) {
            return "That order is invalid.\n";
        }
        // other wise, ask the rest of the chain.
        if (next != null) {
            return next.checkOrder(order, world);
        }
        // if there are no more rules, then the order is legal
        return null;
    }
}
