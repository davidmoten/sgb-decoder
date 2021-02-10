package sgb.decoder.vesselid;

import java.util.Optional;

import org.junit.Test;

public class MmsiTest {

    @Test
    public void testToString() {
        Mmsi a = new Mmsi(Optional.of(123456789), Optional.of(234567890));
    }
}
