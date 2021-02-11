package au.gov.amsa.sgb.decoder.vesselid;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import au.gov.amsa.sgb.decoder.vesselid.AircraftRegistrationMarking;
import au.gov.amsa.sgb.decoder.vesselid.VesselIdType;

public class AircraftRegistrationMarkingTest {

    @Test
    public void testToString() {
        AircraftRegistrationMarking a = new AircraftRegistrationMarking("VH-ABC");
        assertEquals(VesselIdType.AIRCRAFT_REGISTRATION_MARKING, a.vesselIdType());
    }
}
