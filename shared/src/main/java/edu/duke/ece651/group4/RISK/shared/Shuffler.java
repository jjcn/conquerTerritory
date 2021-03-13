package edu.duke.ece651.group4.RISK.shared;

import java.util.Random;

/**
 * This class does shuffling of an array.
 */
public class Shuffler {
    
    public Shuffler() {}
    
    /**
     * Shuffle an array. Uses Fisher-Yates shuffle algorithm.
     * @param arr is the array to shuffle.
     */
    public void shuffle(int[] arr) {
        Random rand = new Random(); 
        for (int i = arr.length - 1; i >= 0; i--) {
            int randomNum = rand.nextInt(i + 1);
            int swap = arr[randomNum]; 
            arr[randomNum] = arr[i];
            arr[i] = swap;
        }
    }
}
