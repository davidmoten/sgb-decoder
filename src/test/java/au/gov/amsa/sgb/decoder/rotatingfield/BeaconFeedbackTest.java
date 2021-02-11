package au.gov.amsa.sgb.decoder.rotatingfield;

import java.util.Optional;

import org.junit.Test;

import au.gov.amsa.sgb.decoder.rotatingfield.BeaconFeedback;
import au.gov.amsa.sgb.decoder.rotatingfield.RlsType;

public class BeaconFeedbackTest {

    @Test
    public void testConstructor() {
        new BeaconFeedback(true, false, RlsType.ACKNOWLEDGEMENT_SERVICE, Optional.of("1110001"));
    }

}
