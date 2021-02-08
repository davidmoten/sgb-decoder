package sgb.decoder.vesselid;

import java.util.Optional;

import sgb.decoder.HasFormatter;
import sgb.decoder.internal.Fields;

@Fields(fields = { "vesselIdType", "mmsi", "epirbMmsi" }, //
		serializedNames = { VesselId.VESSEL_ID_TYPE_SERIALIZED_NAME, "MMSI", "EPIRB MMSI" })
public final class Mmsi implements VesselId, HasFormatter {

	@SuppressWarnings("unused")
	// used for serialization
	private final VesselIdType vesselIdType = VesselIdType.MMSI;
	private final Optional<Integer> mmsi;
	private final Optional<Integer> epirbMmsi;

	public Mmsi(Optional<Integer> mmsi, Optional<Integer> epirbMmsi) {
		this.mmsi = mmsi;
		this.epirbMmsi = epirbMmsi;
	}

	public Optional<Integer> mmsi() {
		return mmsi;
	}

	public Optional<Integer> epirbMmsi() {
		return epirbMmsi;
	}

	@Override
	public String toString() {
		return toStringDefault();
	}

}
