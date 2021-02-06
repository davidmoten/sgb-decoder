package sgb.decoder.rotatingfield;

import sgb.decoder.HasIndentedToString;
import sgb.decoder.Indent;

public final class NationalUse implements RotatingField, HasIndentedToString {

    private final String bitString;

    public NationalUse(String bitString) {
        this.bitString = bitString;
    }

    public String bitString() {
        return bitString;
    }

    @Override
    public String toString(Indent indent) {
        return indent.builder() //
                .right() //
                .add("rotating field type", "National Use") //
                .add("bits", bitString) //
                .toString();
    }
}
