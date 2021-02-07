package sgb.decoder.rotatingfield;

import static org.junit.Assert.assertEquals;

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.junit.Test;

import sgb.decoder.TestingUtil;

public class EltDtInFlightEmergencyTest {

    @Test
    public void testToString() {
        EltDtInFlightEmergency a = new EltDtInFlightEmergency(
                OffsetTime.of(15, 44, 32, 0, ZoneOffset.UTC), 57,
                TriggeringEvent.MANUAL_ACTIVATION_BY_CREW, GnssStatus.LOCATION_3D,
                Optional.of(Range.create(50, RangeEndType.EXCLUSIVE, 75, RangeEndType.INCLUSIVE)));
        System.out.println(a.toString());
        assertEquals(TestingUtil.readResource("/elt-dt-in-flight-emergency-to-string.txt"),
                a.toString());
    }

}
