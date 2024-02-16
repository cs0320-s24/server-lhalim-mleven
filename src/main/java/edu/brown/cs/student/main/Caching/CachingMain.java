package edu.brown.cs.student.main.Caching;

import edu.brown.cs.student.main.Creators.CreateStringList;
import edu.brown.cs.student.main.FactoryFailureException;
import edu.brown.cs.student.main.SearchHelpers.Search;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/** UserStory 3 Main */
public class CachingMain {

  public static void main(String[] args) {
    try {
      FileReader reader = new FileReader("src/main/resources/frankenstein.txt");
      // run without cache
      demo(new Search(reader, new CreateStringList(), false));
      // run with cache
      demo(new CachedSearch(new Search(reader, new CreateStringList(), false)));

    } catch (FactoryFailureException e) {
      System.out.println("Factory Failure Exception"); // could use better error handling here
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Search for "target"
   *
   * @throws IOException if there is trouble reading the file
   */
  static void demo(Searching<List<String>, String> fs) throws IOException {
    // Search once
    for (List<String> line : fs.search("target")) {
      System.out.println(line);
    }
    // Search twice
    System.out.println(fs.search("target").size());
  }
}
