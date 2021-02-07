package sgb.decoder.vesselid;

import java.util.Map;
import java.util.Optional;

import sgb.decoder.HasFormatter;
import sgb.decoder.Indent;
import sgb.decoder.internal.Util;

public final class Mmsi implements VesselId, HasFormatter {

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

    @Override
    public Map<String, Object> fields() {
        return Util.fieldsBuilder() //
                .add("vesselIDType", "MMSI") //
                .add("MMSI", mmsi) //
                .add("EPIRB MMSI", epirbMmsi)
                .build();
    }

}
