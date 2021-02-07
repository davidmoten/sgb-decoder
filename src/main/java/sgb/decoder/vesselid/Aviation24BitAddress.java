package sgb.decoder.vesselid;

import java.util.Map;
import java.util.Optional;

import sgb.decoder.HasFormatter;
import sgb.decoder.Indent;
import sgb.decoder.internal.Util;

public final class Aviation24BitAddress implements VesselId, HasFormatter {

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
    public Map<String, Object> fields() {
        return Util.fieldsBuilder() //
                .add("vesselIDType", "Aviation 24 Bit Address") //
                .add("addressHex", addressHex) //
                .add("aircraftOperatorDesignator", aircraftOperatorDesignator) //
                .build();
    }

}
