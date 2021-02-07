package sgb.decoder.vesselid;

import sgb.decoder.HasFormatter;
import sgb.decoder.Indent;

public final class AircraftOperatorAndSerialNumber implements VesselId, HasFormatter {

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

    @Override
    public String toString(Indent indent) {
        return indent.builder() //
                .right() //
                .add("aircraft operator designator", aircraftOperatorDesignator) //
                .add("serial number", serialNumber) //
                .left() //
                .toString();
    }

}
