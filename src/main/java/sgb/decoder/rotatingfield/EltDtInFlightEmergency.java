package sgb.decoder.rotatingfield;

import java.time.OffsetTime;
import java.util.Optional;

import sgb.decoder.HasFormatter;
import sgb.decoder.Indent;

public final class EltDtInFlightEmergency implements RotatingField, HasFormatter {

    private final OffsetTime timeOfLastEncodedLocationSeconds;
    private final int altitudeEncodedLocationMetres;
    private final TriggeringEvent triggeringEvent;
    private final GnssStatus gnssStatus;
    private final Optional<Range> remainingBatteryCapacityPercent;

    public EltDtInFlightEmergency(OffsetTime timeOfLastEncodedLocationSeconds,
            int altitudeEncodedLocationMetres, TriggeringEvent triggeringEvent,
            GnssStatus gnssStatus, Optional<Range> remainingBatteryCapacityPercent) {
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

    @Override
    public String toString(Indent indent) {
        return indent.builder() //
                .right() //
                .add("Time of last encoded location", timeOfLastEncodedLocationSeconds) //
                .add("Altitude of encoded location (metres)", altitudeEncodedLocationMetres) //
                .add("Triggering event", triggeringEvent) //
                .add("GNSS status", gnssStatus) //
                .add("Remaining battery capacity percent", remainingBatteryCapacityPercent) //
                .left() //
                .toString();
    }

}
