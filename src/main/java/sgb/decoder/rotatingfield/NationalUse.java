package sgb.decoder.rotatingfield;

public final class NationalUse implements RotatingField {

    private final String bitString;

    public NationalUse(String bitString) {
        this.bitString = bitString;
    }

    public String bitString() {
        return bitString;
    }

}
