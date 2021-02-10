package sgb.decoder.vesselid;

import java.util.Optional;

public final class Aviation24BitAddress implements VesselId {

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

}
