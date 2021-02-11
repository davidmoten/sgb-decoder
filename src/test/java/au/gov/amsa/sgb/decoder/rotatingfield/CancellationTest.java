package au.gov.amsa.sgb.decoder.rotatingfield;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import au.gov.amsa.sgb.decoder.rotatingfield.Cancellation;
import au.gov.amsa.sgb.decoder.rotatingfield.DeactivationMethod;
import au.gov.amsa.sgb.decoder.rotatingfield.RotatingFieldType;

public class CancellationTest {

    @Test
    public void testToString() {
        Cancellation a = new Cancellation(DeactivationMethod.MANUAL_DEACTIVATION_BY_USER);
        assertEquals(RotatingFieldType.CANCELLATION, a.rotatingFieldType());
    }
}
