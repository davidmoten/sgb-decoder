package sgb.decoder.rotatingfield;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sgb.decoder.TestingUtil;

public class NationalUseTest {

    @Test
    public void testToString() {
        NationalUse a = new NationalUse("11011");
        System.out.println(a.toString());
        assertEquals(TestingUtil.readResource("/national-use-to-string.txt"),
                a.toString());
    }
}
