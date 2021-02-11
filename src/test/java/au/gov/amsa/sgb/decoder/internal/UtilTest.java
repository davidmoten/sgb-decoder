package au.gov.amsa.sgb.decoder.internal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.davidmoten.junit.Asserts;

import au.gov.amsa.sgb.decoder.internal.Util;

public class UtilTest {

    @Test
    public void isUtilityClass() {
        Asserts.assertIsUtilityClass(Util.class);
    }

    @Test
    public void testRepeat() {
        assertEquals("aaa", Util.repeat('a', 3));
    }

}
