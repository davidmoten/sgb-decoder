package sgb.decoder.rotatingfield;

import java.util.Map;
import java.util.Optional;

import sgb.decoder.HasFormatter;
import sgb.decoder.Indent;
import sgb.decoder.internal.Util;

public final class ObjectiveRequirements implements RotatingField, HasFormatter {

    private final int elapsedTimeSinceActivationHours;
    private final int timeSinceLastEncodedLocationMinutes;
    private final int altitudeEncodedLocationMetres;
    private final int dilutionPrecisionHdop;
    private final Optional<Range> dilutionPrecisionDop;
    private final ActivationMethod activationMethod;
    private final Optional<Range> remainingBatteryCapacityPercent;
    private final GnssStatus gnssStatus;

    public ObjectiveRequirements(int elapsedTimeSinceActivationHours,
            int timeSinceLastEncodedLocationMinutes, int altitudeEncodedLocationMetres,
            int dilutionPrecisionHdop, Optional<Range> dilutionPrecisionDop,
            ActivationMethod activationMethod, Optional<Range> remainingBatteryCapacityPercent,
            GnssStatus gnssStatus) {
        this.elapsedTimeSinceActivationHours = elapsedTimeSinceActivationHours;
        this.timeSinceLastEncodedLocationMinutes = timeSinceLastEncodedLocationMinutes;
        this.altitudeEncodedLocationMetres = altitudeEncodedLocationMetres;
        this.dilutionPrecisionHdop = dilutionPrecisionHdop;
        this.dilutionPrecisionDop = dilutionPrecisionDop;
        this.activationMethod = activationMethod;
        this.remainingBatteryCapacityPercent = remainingBatteryCapacityPercent;
        this.gnssStatus = gnssStatus;
    }

    public int elapsedTimeSinceActivationHours() {
        return elapsedTimeSinceActivationHours;
    }

    public int timeSinceLastEncodedLocationMinutes() {
        return timeSinceLastEncodedLocationMinutes;
    }

    public int altitudeEncodedLocationMetres() {
        return altitudeEncodedLocationMetres;
    }

    public int dilutionPrecisionHdop() {
        return dilutionPrecisionHdop;
    }

    public Optional<Range> dilutionPrecisionDop() {
        return dilutionPrecisionDop;
    }

    public ActivationMethod activationMethod() {
        return activationMethod;
    }

    public Optional<Range> remainingBatteryCapacityPercent() {
        return remainingBatteryCapacityPercent;
    }

    public GnssStatus gnssStatus() {
        return gnssStatus;
    }
    
    @Override
    public String toString() {
        return toStringDefault();
    }

    @Override
    public Map<String, Object> fields() {
        return Util.fieldsBuilder() //
                .add("rotatingFieldType", "Objective Requirements") //
                .add("elapsedTimeSinceActivationHours", elapsedTimeSinceActivationHours) //
                .add("timeSinceLastEncodedLocationMinutes",
                        timeSinceLastEncodedLocationMinutes) //
                .add("altitudeEncodedLocationMetres", altitudeEncodedLocationMetres) //
                .add("dilutionPrecisionHDOP", dilutionPrecisionHdop) //
                .add("dilutionPrecisionDOP", dilutionPrecisionDop) //
                .add("activationMethod", activationMethod) //
                .add("remainingBatteryCapacityPercent", remainingBatteryCapacityPercent) //
                .add("GNSSStatus", gnssStatus) //
                .build();
    }
}
