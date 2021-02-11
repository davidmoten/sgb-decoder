package au.gov.amsa.sgb.decoder.rotatingfield;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

import au.gov.amsa.sgb.decoder.rotatingfield.Rls;
import au.gov.amsa.sgb.decoder.rotatingfield.RlsProvider;
import au.gov.amsa.sgb.decoder.rotatingfield.RotatingFieldType;

public class RlsTest {

    @Test
    public void testToString() {
        Rls a = new Rls(true, false, RlsProvider.GALILEO, Optional.empty());
        assertEquals(RotatingFieldType.RLS, a.rotatingFieldType());
    }
}
