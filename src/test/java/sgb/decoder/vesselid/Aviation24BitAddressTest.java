package sgb.decoder.vesselid;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

import sgb.decoder.TestingUtil;

public class Aviation24BitAddressTest {

    @Test
    public void testToString() {
        Aviation24BitAddress a = new Aviation24BitAddress("ab34", Optional.of("ABCFR"));
        assertEquals(TestingUtil.readResource("/aviation-24-bit-address-to-string.txt"),
                a.toString());
    }
}
