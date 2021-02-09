package sgb.decoder.rotatingfield;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

import sgb.decoder.TestingUtil;

public class RlsTest {

    @Test
    public void testToString() {
        Rls a = new Rls(true, false, RlsProvider.GALILEO, Optional.empty());
        assertEquals(TestingUtil.readResource("/rls-to-string.txt"), a.toString());
        assertEquals(RotatingFieldType.RLS, a.rotatingFieldType());
    }
}
