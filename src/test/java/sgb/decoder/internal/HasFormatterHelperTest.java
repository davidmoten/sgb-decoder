package sgb.decoder.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.text.DecimalFormat;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.junit.Test;

import com.github.davidmoten.junit.Asserts;

public class HasFormatterHelperTest {

	@Test
	public void isUtilityClass() {
		Asserts.assertIsUtilityClass(HasFormatterHelper.class);
	}

	@Test(expected = RuntimeException.class)
	public void testGetValueMethodDoesNotExist() {
		HasFormatterHelper.getValue(new Object(), "abcde");
	}

	@Test
	public void test() {
		assertEquals("null", HasFormatterHelper.toJson(Optional.empty()));
	}

	@Test
	public void testIsPresent() {
		assertFalse(HasFormatterHelper.isPresent(null));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testToJsonUnsupportedObject() {
		HasFormatterHelper.toJson(new DecimalFormat("00"));
	}
	
	@Test
	public void testToJsonOffsetTime() {
		String json = HasFormatterHelper.toJson(OffsetTime.of(15, 35, 44, 0, ZoneOffset.UTC));
		assertEquals("\"15:35:44Z\"", json);
	}

}
