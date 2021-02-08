package sgb.decoder.vesselid;

import java.util.Optional;

import sgb.decoder.HasFormatter;
import sgb.decoder.internal.Fields;

@Fields(fields = { "vesselIdType", "addressHex", "aircraftOperatorDesignator" }, //
		serializedNames = { VesselId.VESSEL_ID_TYPE_SERIALIZED_NAME, "addressHex",
				"aircraftOperatorDesignator" })
public final class Aviation24BitAddress implements VesselId, HasFormatter {

	@SuppressWarnings("unused")
	// used for serialization
	private final VesselIdType vesselIdType = VesselIdType.AVIATION_24_BIT_ADDRESS;
	private final String addressHex;
	private Optional<String> aircraftOperatorDesignator;

	public Aviation24BitAddress(String addressHex, Optional<String> aircraftOperatorDesignator) {
		this.addressHex = addressHex;
		this.aircraftOperatorDesignator = aircraftOperatorDesignator;
	}

	public String addressHex() {
		return addressHex;
	}

	public Optional<String> aircraftOperatorDesignator() {
		return aircraftOperatorDesignator;
	}

	@Override
	public String toString() {
		return toStringDefault();
	}

}
