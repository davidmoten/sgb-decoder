package sgb.decoder.rotatingfield;

import java.time.OffsetTime;
import java.util.Optional;

import sgb.decoder.HasFormatter;
import sgb.decoder.internal.Fields;

@Fields(fields = {"rotatingFieldType", "timeOfLastEncodedLocation", "altitudeEncodedLocationMetres", "triggeringEvent", "gnssStatus",
		"remainingBatteryCapacityPercent" }, //
		serializedNames = {RotatingFieldConstants.ROTATING_FIELD_TYPE_SERIALIZED_NAME, "timeOfLastEncodedLocation", "altitudeEncodedLocationMetres", "triggeringEvent",
				"gnssStatus", "remainingBatteryCapacityPercent" })
public final class EltDtInFlightEmergency implements RotatingField, HasFormatter {
	
	// used for serialization
	private final RotatingFieldType rotatingFieldType = RotatingFieldType.ELT_DT_IN_FLIGHT_EMERGENCY;

	private final OffsetTime timeOfLastEncodedLocation;
	private final int altitudeEncodedLocationMetres;
	private final TriggeringEvent triggeringEvent;
	private final GnssStatus gnssStatus;
	private final Optional<Range> remainingBatteryCapacityPercent;

	public EltDtInFlightEmergency(OffsetTime timeOfLastEncodedLocation, int altitudeEncodedLocationMetres,
			TriggeringEvent triggeringEvent, GnssStatus gnssStatus, Optional<Range> remainingBatteryCapacityPercent) {
		this.timeOfLastEncodedLocation = timeOfLastEncodedLocation;
		this.altitudeEncodedLocationMetres = altitudeEncodedLocationMetres;
		this.triggeringEvent = triggeringEvent;
		this.gnssStatus = gnssStatus;
		this.remainingBatteryCapacityPercent = remainingBatteryCapacityPercent;
	}
	
	public RotatingFieldType rotatingFieldType() {
		return rotatingFieldType;
	}

	public OffsetTime timeOfLastEncodedLocation() {
		return timeOfLastEncodedLocation;
	}

	public int altitudeEncodedLocationMetres() {
		return altitudeEncodedLocationMetres;
	}

	public TriggeringEvent triggeringEvent() {
		return triggeringEvent;
	}

	public GnssStatus gnssStatus() {
		return gnssStatus;
	}

	public Optional<Range> remainingBatteryCapacityPercent() {
		return remainingBatteryCapacityPercent;
	}

	@Override
	public String toString() {
		return toStringDefault();
	}

}
