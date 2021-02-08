package sgb.decoder.rotatingfield;

import java.util.Optional;

import sgb.decoder.HasFormatter;
import sgb.decoder.internal.Fields;

@Fields(fields = { "rotatingFieldType", "canProcessAutomaticallyGeneratedAckRlmType1", "canProcessManuallyGeneratedRlm",
		"rlsProvider", "beaconFeedback" }, //
		serializedNames = { RotatingField.ROTATING_FIELD_TYPE_SERIALIZED_NAME,
				"canProcessAutomaticallyGeneratedAckRlmType1", "canProcessManuallyGeneratedRlm", "rlsProvider",
				"beaconFeedback" })
public final class Rls implements RotatingField, HasFormatter {

	@SuppressWarnings("unused")
	// used for serialization
	private final RotatingFieldType rotatingFieldType = RotatingFieldType.RLS;
	private final boolean canProcessAutomaticallyGeneratedAckRlmType1;
	private final boolean canProcessManuallyGeneratedRlm;
	private final RlsProvider rlsProvider;
	private final Optional<BeaconFeedback> beaconFeedback;

	public Rls(boolean canProcessAutomaticallyGeneratedAckRlmType1, boolean canProcessManuallyGeneratedRlm,
			RlsProvider rlsProvider, Optional<BeaconFeedback> beaconFeedback) {
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

}
