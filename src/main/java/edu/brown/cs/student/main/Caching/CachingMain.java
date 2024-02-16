package edu.brown.cs.student.main.Caching;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/** UserStory 3 Main */
public class CachingMain {

  public static void main(String[] args) {
    try {
      FileReader reader = new FileReader("src/main/resources/frankenstein.txt");


    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
