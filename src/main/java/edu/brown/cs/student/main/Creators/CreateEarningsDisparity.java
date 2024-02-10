package edu.brown.cs.student.main.Creators;

import edu.brown.cs.student.main.Categories.EarningsDisparity;
import edu.brown.cs.student.main.FactoryFailureException;
import java.util.List;

public class CreateEarningsDisparity implements CreatorFromRow {

  @Override
  public List<String> create(List row) throws FactoryFailureException {
    EarningsDisparity ed =
        new EarningsDisparity(
            row.get(0).toString(),
            row.get(1).toString(),
            row.get(2).toString(),
            row.get(3).toString(),
            row.get(4).toString(),
            row.get(5).toString());
    return ed.getED();
  }
}
