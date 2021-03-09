package edu.duke.group4.RISK;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TrialToyExampleTest {
  @Test
  public void test_getHelloWorld() {
    TrialToyExample exmaple = new TrialToyExample();
    assertEquals("hello wolrd", exmaple.getHelloWorld());
  }

}
