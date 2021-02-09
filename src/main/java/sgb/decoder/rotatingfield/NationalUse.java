package sgb.decoder.rotatingfield;

import sgb.decoder.HasFormatter;
import sgb.decoder.internal.Fields;

@Fields(fields = { "rotatingFieldType", "bitString" }, //
		serializedNames = { RotatingFieldConstants.ROTATING_FIELD_TYPE_SERIALIZED_NAME, "bits" })
public final class NationalUse implements RotatingField, HasFormatter {

	// used for serialization
	private final RotatingFieldType rotatingFieldType = RotatingFieldType.NATIONAL_USE;
	private final String bitString;

	public NationalUse(String bitString) {
		this.bitString = bitString;
	}
	
	public RotatingFieldType rotatingFieldType() {
		return rotatingFieldType;
	}

	public String bitString() {
		return bitString;
	}

	@Override
	public String toString() {
		return toStringDefault();
	}

}
