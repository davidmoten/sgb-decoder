package au.gov.amsa.sgb.decoder.vesselid;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

import au.gov.amsa.sgb.decoder.vesselid.Aviation24BitAddress;
import au.gov.amsa.sgb.decoder.vesselid.VesselIdType;

public class Aviation24BitAddressTest {

    @Test
    public void testToString() {
        Aviation24BitAddress a = new Aviation24BitAddress("ab34", Optional.of("ABCFR"));
        assertEquals(VesselIdType.AVIATION_24_BIT_ADDRESS, a.vesselIdType());
    }
}
