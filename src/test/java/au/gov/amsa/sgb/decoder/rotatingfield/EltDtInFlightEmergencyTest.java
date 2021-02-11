package au.gov.amsa.sgb.decoder.rotatingfield;

import static org.junit.Assert.assertEquals;

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.junit.Test;

import au.gov.amsa.sgb.decoder.rotatingfield.EltDtInFlightEmergency;
import au.gov.amsa.sgb.decoder.rotatingfield.GnssStatus;
import au.gov.amsa.sgb.decoder.rotatingfield.Range;
import au.gov.amsa.sgb.decoder.rotatingfield.RangeEndType;
import au.gov.amsa.sgb.decoder.rotatingfield.RotatingFieldType;
import au.gov.amsa.sgb.decoder.rotatingfield.TriggeringEvent;

public class EltDtInFlightEmergencyTest {

    @Test
    public void testToString() {
        EltDtInFlightEmergency a = new EltDtInFlightEmergency(OffsetTime.of(15, 44, 32, 0, ZoneOffset.UTC), 57,
                TriggeringEvent.MANUAL_ACTIVATION_BY_CREW, GnssStatus.LOCATION_3D,
                Optional.of(Range.create(50, RangeEndType.EXCLUSIVE, 75, RangeEndType.INCLUSIVE)));
        assertEquals(RotatingFieldType.ELT_DT_IN_FLIGHT_EMERGENCY, a.rotatingFieldType());
    }

}