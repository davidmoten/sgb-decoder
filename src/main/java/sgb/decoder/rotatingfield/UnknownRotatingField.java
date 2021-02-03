package sgb.decoder.rotatingfield;

public final class UnknownRotatingField implements RotatingField {

    private final String bitString;

    public UnknownRotatingField(String bitString) {
        this.bitString = bitString;
    }

    public String bitString() {
        return bitString;
    }
}
