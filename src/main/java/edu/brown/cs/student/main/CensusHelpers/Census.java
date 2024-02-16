package edu.brown.cs.student.main.CensusHelpers;

/**
 * This is a class that models an Activity received from the BoredAPI. It doesn't have a lot but
 * there are a few fields that you could filter on if you wanted!
 */
public class Census {
  private String census;

  public Census() {}

  @Override
  public String toString() {
    return this.census;
  }
}
