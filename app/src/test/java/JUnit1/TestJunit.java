package JUnit1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by root on 08.05.17.
 */

public class TestJunit {
    String message = "Hello World";
    MessageUtil messageUtil = new MessageUtil(message);

    @Test
    public void testPrintMessage(){
        assertEquals(message, messageUtil.printMessage());
    }
}
