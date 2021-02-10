package sgb.decoder.vesselid;

import java.util.Optional;

import org.junit.Test;

public class Aviation24BitAddressTest {

    @Test
    public void testToString() {
        Aviation24BitAddress a = new Aviation24BitAddress("ab34", Optional.of("ABCFR"));
    }
}
