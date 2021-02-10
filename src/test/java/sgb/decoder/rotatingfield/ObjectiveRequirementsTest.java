package sgb.decoder.rotatingfield;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

public class ObjectiveRequirementsTest {

    @Test
    public void testToString() {
        ObjectiveRequirements a = new ObjectiveRequirements(13, 47, 60, 3, Optional.empty(),
                ActivationMethod.MANUAL_ACTIVATION_BY_USER, Optional.empty(), GnssStatus.LOCATION_3D);
        assertEquals(RotatingFieldType.OBJECTIVE_REQUIREMENTS, a.rotatingFieldType());
    }
}
