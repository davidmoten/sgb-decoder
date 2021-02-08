package sgb.decoder.rotatingfield;

import sgb.decoder.HasFormatter;
import sgb.decoder.internal.Fields;

@Fields(fields = { "rotatingFieldType", "bitString" }, //
		serializedNames = { RotatingField.ROTATING_FIELD_TYPE_SERIALIZED_NAME, "bits" })
public final class UnknownRotatingField implements RotatingField, HasFormatter {

	@SuppressWarnings("unused")
	// used for serialization
	private final RotatingFieldType rotatingFieldType = RotatingFieldType.UNKNOWN;
	private final String bitString;

	public UnknownRotatingField(String bitString) {
		this.bitString = bitString;
	}

	public String bitString() {
		return bitString;
	}

	@Override
	public String toString() {
		return toStringDefault();
	}

}
