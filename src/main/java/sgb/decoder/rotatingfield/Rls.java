package sgb.decoder.rotatingfield;

import java.util.Optional;

import sgb.decoder.HasFormatter;
import sgb.decoder.Indent;

public final class Rls implements RotatingField, HasFormatter {

    private final boolean canProcessAutomaticallyGeneratedAckRlmType1;
    private final boolean canProcessManuallyGeneratedRlm;
    private final RlsProvider rlsProvider;
    private final Optional<BeaconFeedback> beaconFeedback;

    public Rls(boolean canProcessAutomaticallyGeneratedAckRlmType1,
            boolean canProcessManuallyGeneratedRlm, RlsProvider rlsProvider,
            Optional<BeaconFeedback> beaconFeedback) {
        this.canProcessAutomaticallyGeneratedAckRlmType1 = canProcessAutomaticallyGeneratedAckRlmType1;
        this.canProcessManuallyGeneratedRlm = canProcessManuallyGeneratedRlm;
        this.rlsProvider = rlsProvider;
        this.beaconFeedback = beaconFeedback;
    }

    public boolean canProcessAutomaticallyGeneratedAckRlmType1() {
        return canProcessAutomaticallyGeneratedAckRlmType1;
    }

    public boolean canProcessManuallyGeneratedRlm() {
        return canProcessManuallyGeneratedRlm;
    }

    public RlsProvider rlsProvider() {
        return rlsProvider;
    }

    public Optional<BeaconFeedback> beaconFeedback() {
        return beaconFeedback;
    }
    
    @Override
    public String toString() {
        return toStringDefault();
    }

    @Override
    public String toString(Indent indent) {
        return indent.builder() //
                .right() //
                .add("Rotating field type", "RLS") //
                .add("Can process automatically generated acknowledgement - RLM Type 1",
                        canProcessAutomaticallyGeneratedAckRlmType1) //
                .add("Can process manually generated RLM", canProcessManuallyGeneratedRlm) //
                .add("RLS provider", rlsProvider) //
                .add("Beacon feedback", beaconFeedback) //
                .left() //
                .toString();
    }

}
