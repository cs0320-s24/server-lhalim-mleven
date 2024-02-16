package edu.brown.cs.student;

import edu.brown.cs.student.main.server.Server;
import org.junit.Test;

import static org.junit.Assert.fail;

public class testServer {

    @Test
    public void testServerInitialization() {
        // Test if the server starts without throwing any exceptions
        try {
            Server.main(new String[]{});
        } catch (Exception e) {
            fail("Server initialization failed: " + e.getMessage());
        }
    }

}
