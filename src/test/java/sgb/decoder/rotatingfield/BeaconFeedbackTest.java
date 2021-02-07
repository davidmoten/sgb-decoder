package sgb.decoder.rotatingfield;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

import sgb.decoder.TestingUtil;

public class BeaconFeedbackTest {
    
    @Test
    public void testToString() {
        BeaconFeedback b = new BeaconFeedback(true,false,RlsType.ACKNOWLEDGEMENT_SERVICE, Optional.of("1110001"));
        System.out.println(b.toString());
        assertEquals(TestingUtil.readResource("/beacon-feedback-to-string.txt"), b.toString());
    }

}
