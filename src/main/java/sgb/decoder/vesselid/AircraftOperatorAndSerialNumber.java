package sgb.decoder.vesselid;

public final class AircraftOperatorAndSerialNumber implements VesselId {

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

}
