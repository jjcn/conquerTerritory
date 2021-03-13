package edu.duke.ece651.group4.RISK.shared;

import org.junit.jupiter.api.Test;

public class ShufflerTest {
    @Test
    public void testShuffle() {
        int arr[] = {0, 1, 2, 3, 4, 5, 6, 7, 8};

        Shuffler shuffler = new Shuffler();
        shuffler.shuffle(arr);

        String delim = "";
        for (int i : arr) {
            System.out.print(delim + i);
            delim = ", ";
        }
    }
}
