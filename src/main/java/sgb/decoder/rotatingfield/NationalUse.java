package sgb.decoder.rotatingfield;

import java.util.Map;

import sgb.decoder.HasFormatter;
import sgb.decoder.internal.Util;

public final class NationalUse implements RotatingField, HasFormatter {

    private final String bitString;

    public NationalUse(String bitString) {
        this.bitString = bitString;
    }

    public String bitString() {
        return bitString;
    }
    
    @Override
    public String toString() {
        return toStringDefault();
    }

    @Override
    public Map<String, Object> fields() {
        return Util.fieldsBuilder() //
                .add("rotatingFieldType", "National Use") //
                .add("bits", bitString) //
                .build();
    }
}
