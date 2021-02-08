package sgb.decoder.vesselid;

import java.util.Optional;

import com.github.davidmoten.guavamini.Preconditions;

import sgb.decoder.HasFormatter;
import sgb.decoder.internal.Fields;
import sgb.decoder.rotatingfield.RotatingField;

@Fields(fields = { "vesselIdType", "value" }, //
		serializedNames = { RotatingField.ROTATING_FIELD_TYPE_SERIALIZED_NAME, "value" })
public final class RadioCallSign implements VesselId, HasFormatter {

	@SuppressWarnings("unused")
	// used for serialization
	private final VesselIdType vesselIdType = VesselIdType.RADIO_CALL_SIGN;
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
	public String toString() {
		return toStringDefault();
	}

}
