package sgb.decoder.vesselid;

import java.util.Optional;

import sgb.decoder.HasIndentedToString;
import sgb.decoder.Indent;

public final class Mmsi implements VesselId, HasIndentedToString {

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
    public String toString(Indent indent) {
        return indent.builder() //
                .right() //
                .add("vessel id type", "MMSI") //
                .add("MMSI", mmsi) //
                .add("EPIRB MMSI", epirbMmsi)
                .left() //
                .toString();
    }

}
