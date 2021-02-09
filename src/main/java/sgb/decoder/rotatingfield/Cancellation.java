package sgb.decoder.rotatingfield;

import sgb.decoder.HasFormatter;
import sgb.decoder.internal.Fields;

@Fields(fields = { "rotatingFieldType", "deactivationMethod" }, serializedNames = {
		RotatingFieldConstants.ROTATING_FIELD_TYPE_SERIALIZED_NAME, "deactivationMethod" })
public final class Cancellation implements RotatingField, HasFormatter {

	private final RotatingFieldType rotatingFieldType = RotatingFieldType.CANCELLATION;
	private final DeactivationMethod deactivationMethod;

	public Cancellation(DeactivationMethod deactivationMethod) {
		this.deactivationMethod = deactivationMethod;
	}
	
	public RotatingFieldType rotatingFieldType() {
		return rotatingFieldType;
	}

	public DeactivationMethod deactivationMethod() {
		return deactivationMethod;
	}

	@Override
	public String toString() {
		return toStringDefault();
	}

}
