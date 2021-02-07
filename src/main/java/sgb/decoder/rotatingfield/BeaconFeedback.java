package sgb.decoder.rotatingfield;

import java.util.Map;
import java.util.Optional;

import sgb.decoder.HasFormatter;
import sgb.decoder.internal.Util;

public final class BeaconFeedback implements HasFormatter {

    private final boolean rlmType1FeedbackReceived;
    private final boolean rlmType2FeedbackReceived;
    private final RlsType rlsType;
    private final Optional<String> shortRlmParametersBitString;

    public BeaconFeedback(boolean rlmType1FeedbackReceived, boolean rlmType2FeedbackReceived,
            RlsType rlsType, Optional<String> shortRlmParametersBitString) {
        this.rlmType1FeedbackReceived = rlmType1FeedbackReceived;
        this.rlmType2FeedbackReceived = rlmType2FeedbackReceived;
        this.rlsType = rlsType;
        this.shortRlmParametersBitString = shortRlmParametersBitString;
    }

    public boolean rlmType1FeedbackReceived() {
        return rlmType1FeedbackReceived;
    }

    public boolean rlmType2FeedbackReceived() {
        return rlmType2FeedbackReceived;
    }

    public RlsType rlsType() {
        return rlsType;
    }

    public Optional<String> shortRlmParametersBitString() {
        return shortRlmParametersBitString;
    }

    @Override
    public String toString() {
        return toStringDefault();
    }

    @Override
    public Map<String, Object> fields() {
        return Util.fieldsBuilder() //
                .add("RLMType1FeedbackReceived", rlmType1FeedbackReceived) //
                .add("RLMType2FeedbackReceived", rlmType2FeedbackReceived) //
                .add("RLSType", rlsType) //
                .add("shortRLMParametersBits", shortRlmParametersBitString) //
                .build();
    }
}
