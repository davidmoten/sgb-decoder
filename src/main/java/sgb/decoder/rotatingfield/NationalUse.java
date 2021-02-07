package sgb.decoder.rotatingfield;

import sgb.decoder.HasFormatter;
import sgb.decoder.Indent;

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
    public String toString(Indent indent) {
        return indent.builder() //
                .right() //
                .add("Rotating field type", "National Use") //
                .add("Bits", bitString) //
                .toString();
    }
}
