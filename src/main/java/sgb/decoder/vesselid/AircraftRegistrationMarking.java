package sgb.decoder.vesselid;

import java.util.Optional;

import com.github.davidmoten.guavamini.Preconditions;

public final class AircraftRegistrationMarking implements VesselId {

    private final Optional<String> value;

    public AircraftRegistrationMarking(String value) {
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

}
