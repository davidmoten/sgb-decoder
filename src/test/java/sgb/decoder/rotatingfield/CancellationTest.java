package sgb.decoder.rotatingfield;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sgb.decoder.TestingUtil;

public class CancellationTest {

    @Test
    public void testToString() {
        Cancellation a = new Cancellation(DeactivationMethod.MANUAL_DEACTIVATION_BY_USER);
        System.out.println(a);
        assertEquals(TestingUtil.readResource("/cancellation-to-string.txt"), a.toString());
    }
}
