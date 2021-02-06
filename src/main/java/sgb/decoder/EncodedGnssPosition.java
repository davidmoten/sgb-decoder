package sgb.decoder;

public final class EncodedGnssPosition implements HasIndentedToString {

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
    public String toString(Indent indent) {
        return indent.builder().add("Latitude", lat).add("Longitude", lon).toString();
    }

}