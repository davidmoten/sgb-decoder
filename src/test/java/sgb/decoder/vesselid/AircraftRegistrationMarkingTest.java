package sgb.decoder.vesselid;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sgb.decoder.TestingUtil;

public class AircraftRegistrationMarkingTest {

    @Test
    public void testToString() {
        AircraftRegistrationMarking a = new AircraftRegistrationMarking("VH-ABC");
        assertEquals(TestingUtil.readResource("/aircraft-registration-marking-to-string.txt"),
                a.toString());
    }
}
