package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test deserializing soup recipes
 *
 * <p>Because we're using JUnit here, we needed to add JUnit to pom.xml.
 *
 * <p>In a real application, we'd want to test better---e.g., if it's part of our spec that
 * SoupHandler throws an IOException on invalid JSON, we'd want to test that.
 */
public class TestCensusAPIUtilities {

    @BeforeEach
    public void setup() {
        // No setup
    }

    @AfterEach
    public void teardown() {
        // No setup
    }

    @Test
    public void test() throws IOException {

    }

}
