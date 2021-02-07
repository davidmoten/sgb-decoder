package sgb.decoder.vesselid;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sgb.decoder.TestingUtil;

public class RadioCallSignTest {

    @Test
    public void testToString() {
        RadioCallSign a = new RadioCallSign("FLIGHT");
        assertEquals(TestingUtil.readResource("/radio-call-sign-to-string.txt"), a.toString());
    }
}
