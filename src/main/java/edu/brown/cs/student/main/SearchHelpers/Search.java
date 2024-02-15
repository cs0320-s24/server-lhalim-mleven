package edu.brown.cs.student.main.SearchHelpers;

import edu.brown.cs.student.main.Caching.Searching;
import edu.brown.cs.student.main.Creators.CreatorFromRow;
import edu.brown.cs.student.main.FactoryFailureException;
import edu.brown.cs.student.main.Parser;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Search<T> implements Searching<List<T>, String> {

  private List<List<String>> parsedData;
  private boolean hasHeaders;
  private Parser parser;

  public Search(Reader inputReader, CreatorFromRow inputCreator, boolean headersBool)
      throws FactoryFailureException {
    this.parser = new Parser<>(inputReader, inputCreator, headersBool);
    this.parsedData = parser.read();
    this.hasHeaders = headersBool;
  }

  // Search using a target and index
  public Collection<List<T>> search(String target, int index) {
    if (index > this.parsedData.get(0).size()) {
      System.err.println("Index out of bounds");
      System.exit(0);
    }
    Collection<List<T>> found = new ArrayList<>();
    for (List row : this.parsedData) {
      if (row.get(index).equals(target)) {
        System.out.println(row);
        found.add(row);
      }
    }
    if (found.isEmpty()) {
      System.err.println("Target not found");
    }
    return found;
  }

  // Search using a target and column name
  public Collection<List<T>> search(String target, String column) {
    Collection<List<T>> found = new ArrayList<>();
    try {
      int index = this.parser.getHeader().indexOf(column);
      for (List row : this.parsedData) {
        if (row.get(index).equals(target)) {
          System.out.println(row);
          found.add(row);
        }
      }

    } catch (ArrayIndexOutOfBoundsException e) {
      System.err.println("Column not found");
    }
    if (found.isEmpty()) {
      System.err.println("Target not found");
    }
    return found;
  }

  // Search using just a target
  public Collection<List<T>> search(String target) {
    Collection<List<T>> found = new ArrayList<>();
    for (int row = 0; row < this.parsedData.size(); row++) {
      for (int col = 0; col < this.parsedData.get(row).size(); col++) {
        String current = this.parsedData.get(row).get(col);
        if (target.equals(current)) {
          List foundRow = this.parsedData.get(row);
          System.out.println(foundRow);
          found.add(foundRow);
        }
      }
    }
    if (found.isEmpty()) {
      System.err.println("Target not found");
    }
    return found;
  }
  ;
}
