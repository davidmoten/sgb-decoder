package sgb.decoder.rotatingfield;

import java.util.Map;
import java.util.Optional;

import sgb.decoder.HasFormatter;
import sgb.decoder.Indent;
import sgb.decoder.internal.Util;

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
    public Map<String, Object> fields() {
        return Util.fieldsBuilder() //
                .add("rotatingFieldType", "RLS") //
                .add("canProcessAutomaticallyGeneratedAcknowledgementRLMType1",
                        canProcessAutomaticallyGeneratedAckRlmType1) //
                .add("canProcessManuallyGeneratedRLM", canProcessManuallyGeneratedRlm) //
                .add("rlsProvider", rlsProvider) //
                .add("beaconFeedback", beaconFeedback) //
                .build();
    }

}
