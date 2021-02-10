package sgb.decoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class Beacon23HexIdTest {

    @Test
    public void testDecode() {
        Beacon23HexId b = Beacon23HexId.fromHex("9934039823d000000000000");
        assertEquals(201, b.countryCode());
        assertEquals(230, b.tac());
        assertEquals(573, b.serialNumber());
        assertFalse(b.testProtocolFlag());
        assertFalse(b.vesselId().isPresent());
        assertEquals(
                "{\"countryCode\":201,\"tac\":230,\"serialNumber\":573,\"testProtocolFlag\":false,\"vesselId\":null}",
                b.toJson());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecodeHexWrongSize() {
        Beacon23HexId.fromHex("123");
    }

}
