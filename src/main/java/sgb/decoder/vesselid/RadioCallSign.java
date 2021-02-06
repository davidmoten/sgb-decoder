package sgb.decoder.vesselid;

import java.util.Optional;

import com.github.davidmoten.guavamini.Preconditions;

import sgb.decoder.HasIndentedToString;
import sgb.decoder.Indent;

public final class RadioCallSign implements VesselId, HasIndentedToString {

    private final Optional<String> value;

    public RadioCallSign(String value) {
        Preconditions.checkNotNull(value);
        String s = value.trim();
        if (s.isEmpty()) {
            this.value = Optional.empty();
        } else {
            this.value = Optional.of(s);
        }
    }

    public Optional<String> value() {
        return value;
    }

    @Override
    public String toString(Indent indent) {
        return indent.builder() //
                .right() //
                .add("vessel id type", "radio call sign") //
                .add("value", value) //
                .left() //
                .toString();
    }

}
