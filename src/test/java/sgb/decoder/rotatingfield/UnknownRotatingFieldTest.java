package sgb.decoder.rotatingfield;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sgb.decoder.TestingUtil;

public class UnknownRotatingFieldTest {

    @Test
    public void testToString() {
        UnknownRotatingField a = new UnknownRotatingField("11011");
        assertEquals(TestingUtil.readResource("/unknown-rotating-field-to-string.txt"), a.toString());
        assertEquals(RotatingFieldType.UNKNOWN, a.rotatingFieldType());
    }
}
