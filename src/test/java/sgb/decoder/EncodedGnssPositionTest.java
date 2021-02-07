package sgb.decoder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EncodedGnssPositionTest {
    
    @Test
    public void testToString() {
        EncodedGnssPosition a = new EncodedGnssPosition(-43, 140);
        assertEquals(TestingUtil.readResource("/encoded-gnss-position-to-string.txt"),
                a.toString());
    }

}
