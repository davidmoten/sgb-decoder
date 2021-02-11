package au.gov.amsa.sgb.decoder.internal;

import org.junit.Test;

import com.github.davidmoten.junit.Asserts;

import au.gov.amsa.sgb.decoder.internal.Hex;

public class HexTest {

    @Test
    public void isUtilityClass() {
        Asserts.assertIsUtilityClass(Hex.class);
    }

}
