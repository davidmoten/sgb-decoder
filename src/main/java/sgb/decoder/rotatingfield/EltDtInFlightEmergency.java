package sgb.decoder.rotatingfield;

import java.time.OffsetTime;
import java.util.Optional;

import sgb.decoder.HasFormatter;
import sgb.decoder.internal.Fields;

@Fields(fields = {"rotatingFieldType", "timeOfLastEncodedLocationSeconds", "altitudeEncodedLocationMetres", "triggeringEvent", "gnssStatus",
		"remainingBatteryCapacityPercent" }, //
		serializedNames = {RotatingField.ROTATING_FIELD_TYPE_SERIALIZED_NAME, "timeOfLastEncodedLocationSeconds", "altitudeEncodedLocationMetres", "triggeringEvent",
				"gnssStatus", "remainingBatteryCapacityPercent" })
public final class EltDtInFlightEmergency implements RotatingField, HasFormatter {
	
	@SuppressWarnings("unused")
	// used for serialization
	private final RotatingFieldType rotatingFieldType = RotatingFieldType.ELT_DT_IN_FLIGHT_EMERGENCY;

	private final OffsetTime timeOfLastEncodedLocationSeconds;
	private final int altitudeEncodedLocationMetres;
	private final TriggeringEvent triggeringEvent;
	private final GnssStatus gnssStatus;
	private final Optional<Range> remainingBatteryCapacityPercent;

	public EltDtInFlightEmergency(OffsetTime timeOfLastEncodedLocationSeconds, int altitudeEncodedLocationMetres,
			TriggeringEvent triggeringEvent, GnssStatus gnssStatus, Optional<Range> remainingBatteryCapacityPercent) {
		this.timeOfLastEncodedLocationSeconds = timeOfLastEncodedLocationSeconds;
		this.altitudeEncodedLocationMetres = altitudeEncodedLocationMetres;
		this.triggeringEvent = triggeringEvent;
		this.gnssStatus = gnssStatus;
		this.remainingBatteryCapacityPercent = remainingBatteryCapacityPercent;
	}

	public OffsetTime timeOfLastEncodedLocationSeconds() {
		return timeOfLastEncodedLocationSeconds;
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
