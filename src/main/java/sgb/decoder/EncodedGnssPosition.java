package sgb.decoder;

import java.util.Map;

import sgb.decoder.internal.Util;

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
    public Map<String, Object> fields() {
        return Util.fieldsBuilder()//
                .add("latitude", lat) //
                .add("longitude", lon) //
                .build();
    }

}