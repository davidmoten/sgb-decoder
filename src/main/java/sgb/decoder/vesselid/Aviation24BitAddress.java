package sgb.decoder.vesselid;

import java.util.Optional;

import sgb.decoder.HasIndentedToString;
import sgb.decoder.Indent;

public final class Aviation24BitAddress implements VesselId, HasIndentedToString {

    private final String addressHex;
    private Optional<String> aircraftOperatorDesignator;

    public Aviation24BitAddress(String addressHex, Optional<String> aircraftOperatorDesignator) {
        this.addressHex = addressHex;
        this.aircraftOperatorDesignator = aircraftOperatorDesignator;
    }

    public String addressHex() {
        return addressHex;
    }

    public Optional<String> aircraftOperatorDesignator() {
        return aircraftOperatorDesignator;
    }
    
    @Override
    public String toString() {
        return toStringDefault();
    }

    @Override
    public String toString(Indent indent) {
        return indent.builder() //
                .right() //
                .add("vessel id type", "aviation 24 bit address") //
                .add("address hex", addressHex) //
                .add("aircraft operator designator", aircraftOperatorDesignator) //
                .left() //
                .toString();

    }

}
