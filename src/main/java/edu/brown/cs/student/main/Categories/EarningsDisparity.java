package edu.brown.cs.student.main.Categories;

import java.util.List;

public class EarningsDisparity {

  private String state;
  private String dataType;
  private String avgEarnings;
  private String numWorkers;
  private String earningDisparty;
  private String employedPercent;

  public EarningsDisparity(
      String state,
      String dataType,
      String avgEarnings,
      String numWorkers,
      String earningDisparty,
      String employedPercent) {
    this.state = state;
    this.dataType = dataType;
    this.avgEarnings = avgEarnings;
    this.numWorkers = numWorkers;
    this.earningDisparty = earningDisparty;
    this.employedPercent = employedPercent;
  }

  // Returning a list of strings containing the Earning Disparity iformation
  public List<String> getED() {
    return List.of(
        this.state,
        this.dataType,
        this.avgEarnings,
        this.numWorkers,
        this.earningDisparty,
        this.employedPercent);
  }
}
