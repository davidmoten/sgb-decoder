package sgb.decoder.vesselid;

import java.util.Optional;

import com.github.davidmoten.guavamini.Preconditions;

import sgb.decoder.HasFormatter;
import sgb.decoder.internal.Fields;

@Fields(fields = { "vesselIdType", "value" }, serializedNames = {
		VesselId.VESSEL_ID_TYPE_SERIALIZED_NAME, "value" })
public final class AircraftRegistrationMarking implements VesselId, HasFormatter {

	@SuppressWarnings("unused")
	// used for serialization
	private final VesselIdType vesselIdType = VesselIdType.AIRCRAFT_REGISTRATION_MARKING;
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

	@Override
	public String toString() {
		return toStringDefault();
	}

}
