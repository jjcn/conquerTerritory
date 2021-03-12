package edu.duke.ece651.group4.RISK.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TroopTest {
    @Test
    void Test_checkTroopSize() {
        Troop test=new Troop(5);
        assertEquals(test.checkTroopSize(),5);
    }

    @Test
    void Test_sendTroop() {
        Troop test=new Troop(5);
        Troop sub=test.sendTroop(3);
        assertEquals(test.checkTroopSize(),2);
        assertEquals(sub.checkTroopSize(),3);
    }

    @Test
    void Test_combat() {
        Random rnd = new Random(0);
        Troop test=new Troop(4,rnd);
        Troop enemy=new Troop(4,rnd);

        assertEquals(test.combat(enemy).checkTroopSize(),0);
        assertEquals(test.checkTroopSize(),3);
        assertEquals(test.checkWin(),true);
    }


}