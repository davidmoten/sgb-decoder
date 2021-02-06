package sgb.decoder.rotatingfield;

import java.util.Optional;

import sgb.decoder.HasIndentedToString;
import sgb.decoder.Indent;

public final class Rls implements RotatingField, HasIndentedToString {

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
    public String toString(Indent indent) {
        return indent.builder() //
                .right() //
                .add("rotating field type", "RLS") //
                .add("can process automatically generated acknowledgement - RLM Type 1",
                        canProcessAutomaticallyGeneratedAckRlmType1) //
                .add("can process manually generated RLM", canProcessManuallyGeneratedRlm) //
                .add("RLS provider", rlsProvider) //
                .add("beacon feedback", beaconFeedback) //
                .left() //
                .toString();
    }

}
