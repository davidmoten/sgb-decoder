package sgb.decoder.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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

}
