package edu.brown.cs.student.main.CensusHelpers;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.io.IOException;

/** This class deserializes JSON from the API. */
public class CensusAPIUtilities {

  /**
   * Deserializes JSON from the API.
   *
   * @param jsonActivity
   * @return
   */
  public static Census deserializeCensus(String jsonActivity) {
    try {
      // Initializes Moshi
      Moshi moshi = new Moshi.Builder().build();

      // Initializes an adapter to a Census class then uses it to parse the JSON.
      JsonAdapter<Census> adapter = moshi.adapter(Census.class);

      Census census = adapter.fromJson(jsonActivity);

      return census;
    } catch (IOException e) { // needs better error handling
      e.printStackTrace();
      return new Census();
    }
  }
}
