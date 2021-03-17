package edu.duke.ece651.group4.RISK.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaceOrderTest {
    @Test
    void test_all_get() {
        Player p1 = new TextPlayer(null,null,"p1");
        Troop troop = new Troop(5, p1);
        PlaceOrder order = new PlaceOrder("des", troop);
        assertEquals("des", order.getDesName());
        assertEquals('P', order.getActionName());
        assertEquals(troop, order.getActTroop());
    }
}