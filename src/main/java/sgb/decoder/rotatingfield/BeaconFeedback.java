package sgb.decoder.rotatingfield;

import java.util.Optional;

import sgb.decoder.HasIndentedToString;
import sgb.decoder.Indent;

public final class BeaconFeedback implements HasIndentedToString {

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
    public String toString(Indent indent) {
        return indent.builder() //
                .left() //
                .add("RLM Type 1 Feedback received", rlmType1FeedbackReceived) //
                .add("RLM Type 2 Feedback received", rlmType2FeedbackReceived) //
                .add("RLS type", rlsType) //
                .add("short RLM parameters bits", shortRlmParametersBitString) //
                .right() //
                .toString();
    }
}
