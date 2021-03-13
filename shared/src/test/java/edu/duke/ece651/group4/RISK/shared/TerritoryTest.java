package edu.duke.ece651.group4.RISK.shared;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TerritoryTest {

    @Test
    void Test_sendTroop() {
        Territory test= new Territory("test");
        test.initializeTerritory(5,null);
        test.sendInTroop(new Troop(5,null));
        assertEquals(test.checkPopulation(),10);
        test.sendOutTroop(new Troop(4,null));
        assertEquals(test.checkPopulation(),6);
        test.sendInEnemyTroop(new Troop(4,null));
        assertEquals(test.checkPopulation(),6);
    }

    @Test
    void Test_addUnit() {
        Territory test= new Territory("test");
        test.initializeTerritory(5,null);
        test.addUnit(5);
        assertEquals(test.checkPopulation(),10);
    }

    @Test
    void Test_initializeTerritory() {
        Territory test= new Territory("test");
        test.initializeTerritory(5,null);
        assertEquals(test.checkPopulation(),5);
    }

    @Test
    void Test_doBattles() throws IOException {
        Player p1=new TextPlayer(null,"p1",null,null);
        Player p2=new TextPlayer(null,"p2",null,null);
        Player p3=new TextPlayer(null,"p3",null,null);
        Territory test= new Territory("test",p1,4,new Random(0));



        test.sendInEnemyTroop(new Troop(2,p2,new Random(0)));
        test.sendInEnemyTroop(new Troop(1,p3,new Random(0)));
        test.doBattles();


        assertEquals(test.getOwner().getName(),"p1");
        assertEquals(test.checkPopulation(),2);

    }

    @Test
    void Test_equals() throws IOException {
        Player p1=new TextPlayer(null,"p1",null,null);
        Territory test= new Territory("test",p1,4,new Random(0));
        Territory test2= new Territory("test",p1,4,new Random(0));
        Territory test3= new Territory("test1",p1,4,new Random(0));

        assertEquals(test.equals(test2),true);
        assertEquals(test.equals(test3),false);
        assertEquals(test,test2);
    }

}