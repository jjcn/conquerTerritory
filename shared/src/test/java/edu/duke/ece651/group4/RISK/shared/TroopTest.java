//package edu.duke.ece651.group4.RISK.shared;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.Random;
//
//public class TroopTest {
//    @Test
//    void Test_checkTroopSize() {
//        Troop test=new Troop(5,null);
//        assertEquals(test.checkTroopSize(),5);
//    }
//
//    @Test
//    void Test_sendTroop() {
//        Troop test=new Troop(5,null);
//        Troop sub=test.sendTroop(3);
//        assertEquals(test.checkTroopSize(),2);
//        assertEquals(sub.checkTroopSize(),3);
//    }
//
//
//    @Test
//    void Test_receiveTroop(){
//        Troop test=new Troop(5,null);
//        Troop receive=new Troop(5,null);
//        test.receiveTroop(receive);
//        assertEquals(test.checkTroopSize(),10);
//
//    }
//
//    @Test
//    void Test_combat() {
//        Random rnd = new Random(0);
//        Troop test=new Troop(4,null,rnd);
//        Troop enemy=new Troop(4,null,rnd);
//
//        assertEquals(test.combat(enemy).checkTroopSize(),0);
//        assertEquals(test.checkTroopSize(),3);
//        assertEquals(test.checkWin(),true);
//    }
//
//
//}
