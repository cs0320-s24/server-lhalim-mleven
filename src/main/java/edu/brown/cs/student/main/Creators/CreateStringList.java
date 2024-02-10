package edu.brown.cs.student.main.Creators;

import edu.brown.cs.student.main.FactoryFailureException;
import java.util.List;

public class CreateStringList implements CreatorFromRow<List<String>> {

  @Override
  public List<String> create(List<String> row) throws FactoryFailureException {
    return row;
  }
}
