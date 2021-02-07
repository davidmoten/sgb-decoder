package sgb.decoder.vesselid;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sgb.decoder.TestingUtil;

public class AircraftOperatorAndSerialNumberTest {

    @Test
    public void testToString() {
        AircraftOperatorAndSerialNumber a = new AircraftOperatorAndSerialNumber("ABC", 1234);
        System.out.println(a.toString());
        assertEquals(TestingUtil.readResource("/aircraft-operator-and-serial-number-to-string.txt"),
                a.toString());
    }
}
