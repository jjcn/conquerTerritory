package edu.duke.group4.RISK;

import org.junit.jupiter.api.Test;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SoldierTest {

    @Test
    void test_fight() {
        Random rnd = new Random(0);
        Soldier mySoldier= new Soldier(rnd);
        Soldier enemy= new Soldier(rnd);

        assertEquals(mySoldier.fight(enemy),false);
        assertEquals(mySoldier.fight(enemy),true);


    }


}