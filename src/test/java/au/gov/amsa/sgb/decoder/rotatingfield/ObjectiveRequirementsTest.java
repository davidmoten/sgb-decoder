package au.gov.amsa.sgb.decoder.rotatingfield;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

import au.gov.amsa.sgb.decoder.rotatingfield.ActivationMethod;
import au.gov.amsa.sgb.decoder.rotatingfield.GnssStatus;
import au.gov.amsa.sgb.decoder.rotatingfield.ObjectiveRequirements;
import au.gov.amsa.sgb.decoder.rotatingfield.Range;
import au.gov.amsa.sgb.decoder.rotatingfield.RangeEndType;
import au.gov.amsa.sgb.decoder.rotatingfield.RotatingFieldType;

public class ObjectiveRequirementsTest {

    @Test
    public void testToString() {
        ObjectiveRequirements a = new ObjectiveRequirements(13, 47, 60,
                Optional.of(Range.create(3, RangeEndType.EXCLUSIVE, 4, RangeEndType.INCLUSIVE)),
                Optional.empty(), ActivationMethod.MANUAL_ACTIVATION_BY_USER, Optional.empty(),
                GnssStatus.LOCATION_3D);
        assertEquals(RotatingFieldType.OBJECTIVE_REQUIREMENTS, a.rotatingFieldType());
    }
}