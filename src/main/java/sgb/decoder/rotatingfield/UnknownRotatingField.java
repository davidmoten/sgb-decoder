package sgb.decoder.rotatingfield;

import sgb.decoder.HasIndentedToString;
import sgb.decoder.Indent;

public final class UnknownRotatingField implements RotatingField, HasIndentedToString {

    private final String bitString;

    public UnknownRotatingField(String bitString) {
        this.bitString = bitString;
    }

    public String bitString() {
        return bitString;
    }

    @Override
    public String toString(Indent indent) {
        return indent.builder() //
                .right() //
                .add("Rotating field type", "unknown") //
                .add("Bits", bitString) //
                .left() //
                .toString();
    }
}
