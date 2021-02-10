package sgb.decoder.rotatingfield;

import java.util.Optional;

import org.junit.Test;

public class BeaconFeedbackTest {

	@Test
	public void testToString() {
		BeaconFeedback b = new BeaconFeedback(true, false, RlsType.ACKNOWLEDGEMENT_SERVICE, Optional.of("1110001"));
	}

}
