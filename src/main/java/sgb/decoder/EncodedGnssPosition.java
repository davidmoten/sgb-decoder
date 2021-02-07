package sgb.decoder;

public final class EncodedGnssPosition implements HasFormatter {

    private final double lat;
    private final double lon;

    public EncodedGnssPosition(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double lat() {
        return lat;
    }

    public double lon() {
        return lon;
    }
    
    @Override
    public String toString() {
        return toStringDefault();
    }

    @Override
    public String toString(Indent indent) {
        return indent.builder().right().add("Latitude", lat).add("Longitude", lon).left().toString();
    }

}