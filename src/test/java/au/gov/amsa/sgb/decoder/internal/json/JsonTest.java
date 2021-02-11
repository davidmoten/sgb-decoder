package au.gov.amsa.sgb.decoder.internal.json;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.davidmoten.junit.Asserts;

import au.gov.amsa.sgb.decoder.internal.json.Json;

public class JsonTest {

    @Test
    public void isUtilityClass() {
        Asserts.assertIsUtilityClass(Json.class);
    }

    @Test(expected = RuntimeException.class)
    public void testEqualsWithInvalidJson() {
        assertTrue(Json.equals("boo", "you"));
    }

    @Test(expected = RuntimeException.class)
    public void testToJsonWithNoDefinedSerializer() {
        Json.toJson(new Object());
    }

    @Test(expected = RuntimeException.class)
    public void testPrettyPrintInvalidJson() {
        Json.prettyPrint("boo");
    }

}
