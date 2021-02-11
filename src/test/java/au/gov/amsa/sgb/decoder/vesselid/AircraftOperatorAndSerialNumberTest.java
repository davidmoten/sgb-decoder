package au.gov.amsa.sgb.decoder.vesselid;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import au.gov.amsa.sgb.decoder.vesselid.AircraftOperatorAndSerialNumber;
import au.gov.amsa.sgb.decoder.vesselid.VesselIdType;

public class AircraftOperatorAndSerialNumberTest {

    @Test
    public void testToString() {
        AircraftOperatorAndSerialNumber a = new AircraftOperatorAndSerialNumber("ABC", 1234);
        assertEquals(VesselIdType.AIRCRAFT_OPERATOR_AND_SERIAL_NUMBER, a.vesselIdType());
    }
}
