package sgb.decoder.vesselid;

import java.util.Map;

import sgb.decoder.HasFormatter;
import sgb.decoder.Indent;
import sgb.decoder.internal.Util;

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
    public Map<String, Object> fields() {
        return Util.fieldsBuilder() //
                .add("aircraftOperatorDesignator", aircraftOperatorDesignator) //
                .add("serialNumber", serialNumber) //
                .build();
    }

}
