package edu.duke.ece651.group4.RISK.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class ShufflerTest {
    @Test
    public void visualDisplay() {
        int arr[] = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        Shuffler shuffler = new Shuffler(new Random(0));

        shuffler.shuffle(arr);

        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (int i : arr) {
            sb.append(delim + i);
            delim = ", ";
        }
        System.out.print(sb);
    }

}
