package edu.brown.cs.student.main;

/** The Main class of our project. This is where execution begins. */
public final class Main {
  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private Main(String[] args) {}

  private void run() {
    // dear student: you can remove this. you can remove anything. you're in cs32. you're free!
    System.out.println("Server started");
  }
}
