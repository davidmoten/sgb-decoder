package sgb.decoder.vesselid;

import sgb.decoder.HasFormatter;
import sgb.decoder.internal.Fields;

@Fields(fields = { "vesselIdType", "aircraftOperatorDesignator", "serialNumber" }, //
		serializedNames = { VesselId.VESSEL_ID_TYPE_SERIALIZED_NAME, "aircraftOperatorDesignator",
				"serialNumber" })
public final class AircraftOperatorAndSerialNumber implements VesselId, HasFormatter {

	@SuppressWarnings("unused")
	// used for serialization
	private final VesselIdType vesselIdType = VesselIdType.AIRCRAFT_OPERATOR_AND_SERIAL_NUMBER;
	private final String aircraftOperatorDesignator;
	private final int serialNumber;

	public AircraftOperatorAndSerialNumber(String aircraftOperatorDesignator, int serialNumber) {
		this.aircraftOperatorDesignator = aircraftOperatorDesignator;
		this.serialNumber = serialNumber;
	}

	public String aircraftOperatorDesignator() {
		return aircraftOperatorDesignator;
	}

	public int serialNumber() {
		return serialNumber;
	}

	@Override
	public String toString() {
		return toStringDefault();
	}

}
