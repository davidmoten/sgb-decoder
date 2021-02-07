package sgb.decoder;

import org.junit.Test;

public class IndentTest {
    
    @Test(expected = IllegalArgumentException.class)
    public void testIndentLeftTooManyTimes() {
        new Indent(0, 2).left();
    }

}
